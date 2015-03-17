package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import net.sf.json.JSONObject

class RdlSearchService {

    final static String STANDARD_ERROR_MSG = "Något gick fel. Det är inte säkert att sökresultatet är komplett."

    def grailsApplication

    static transactional = true
	
	def availableCategories = [Category.RATTSFALL, Category.MYNDIGHETERS_FORESKRIFTER, Category.PROPOSITIONER, Category.UTREDNINGAR, Category.LAGAR]

    public SearchResult textSearch(List<String> query, Category category, Integer offset = null, Integer itemsPerPage = null) {
        if (!category in availableCategories)
            throw new Exception("Selected category ${category} not within allowed categories ${availableCategories}")
        def result = new SearchResult(category)
        Benchmark.section("RDL text search time", log) {
            def queryBuilder = new QueryBuilder()

            queryBuilder.setQueries(query)
            if(offset != null && itemsPerPage) {
                queryBuilder.setPageAndPageSize((int)(offset / itemsPerPage), itemsPerPage)
            }

            queryBuilder.setType(category.getTypes())
            queryBuilder.setIkraftIfExists("")
            queryBuilder.setParam("_stats", "on")

            def queryResult = searchWithQuery(queryBuilder.getQueryParams())
            result.totalResults = queryResult.totalResults
            def res = queryResult.items.collect createResultItemsFromResult
            res.each result.addEach
        }
        return result
    }

    public SearchResultByCategory categorizedSearch(List<String> query, int limitItems = 4) {
        def result = new SearchResultByCategory()
        Benchmark.section("RDL categorized search time", log) {
            def queryBuilder = new QueryBuilder()

            queryBuilder.setQueries(query)
            queryBuilder.setPageAndPageSize(0, limitItems)

            queryBuilder.setIkraftIfExists("")
            queryBuilder.setParam("_stats", "on")

            def queryResult = searchWithQuery(queryBuilder.getQueryParams())
            result.totalResults = queryResult.totalResults
            if(queryResult.statistics) {
                result.addStats(queryResult.statistics.slices)
                if(queryResult.statistics.slices?.observations) {
                    def res = []
                    queryResult.statistics.slices?.observations[0].each { observation ->
                        res += observation.items?.collect createResultItemsFromResult
                    }
                    res.each result.addEach
                }
            }
        }
        return result
    }

    def createResultItemsFromResult = { item ->
        new SearchResultItem(
                title: prefereMatchBeforeOriginal(item,'title'),
                iri: item.iri?.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view),
                issued: item.issued,
                describedBy: item.describedby,
                identifier: prefereMatchBeforeOriginal(item,'identifier'),
                matches: prefereMatchBeforeOriginal(item,'text','referatrubrik'), //getBestMatch(item),
                type: item.type,
                ikrafttradandedatum: item.ikrafttradandedatum,
                malnummer: item.malnummer,
                text: prefereMatchBeforeOriginal(item,'text','referatrubrik'),
        )
    }

    public def searchWithQuery(Map queryParams) {
        def result = [:]
        def transferHttpResponseJsonToResult = {resp, json -> result = json }
        def logStatusLineAndAddtoErrorMessage = { resp -> log.error(resp.statusLine); result.errorMessage = STANDARD_ERROR_MSG }
        def http = createHttpBuilder()
        try {
            http.request(grailsApplication.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) { req ->
                uri.path = "/-/publ"
                uri.query = queryParams
                req.getParams().setParameter("http.connection.timeout", new Integer(100000));
                req.getParams().setParameter("http.socket.timeout", new Integer(100000));

                response.success = transferHttpResponseJsonToResult
                response.failure = logStatusLineAndAddtoErrorMessage
            }
        } catch (IOException ex) {
            log.error(ex)
            log.error("Failed to communicate with *"+grailsApplication.config.lagrummet.rdl.service.baseurl+"'");
            result.errorMessage = STANDARD_ERROR_MSG
        }
        return result
    }


	public List<String> getAvailablePublishers() {
		
		def publishers = []
		def http = new HTTPBuilder()
		try {
			http.request(grailsApplication.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) { req ->
				uri.path = "/var/common.json"
				req.getParams().setParameter("http.connection.timeout", new Integer(5000));
				req.getParams().setParameter("http.socket.timeout", new Integer(5000));
				
				response.success = { resp, json ->
					json.topic.each { topic ->
						if (topic.type == "Organization") {
							publishers.add(topic.name)
						}
					}
				}
				
			}
		} catch(SocketTimeoutException e) {
            log.error(e)
		}
		return publishers
	}
	
	public List<String> getExistingPublishers(String match) {
		
		def publishers = []
		def http = new HTTPBuilder()
		try {
			http.request(grailsApplication.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) { req ->
				uri.path = "/-/publ;stats"
				req.getParams().setParameter("http.connection.timeout", new Integer(5000));
				req.getParams().setParameter("http.socket.timeout", new Integer(5000));
				
				response.success = { resp, json ->
					json.slices.each {  
						if(it.dimension == 'publisher') {
							it.observations.each {  obs ->
								def tokens = obs.ref.tokenize('/')
								def publisher = tokens.get(tokens.size() -1)
								if(match && publisher.toLowerCase().contains(match.toLowerCase())) {
									publishers.add(publisher)
								} else if(!match) {
									publishers.add(publisher)
								}
							}
						}
					}
				}
				
			}
		} catch(SocketTimeoutException e) {
		    log.error(e)
		}
		return publishers
	}
	
	public static String getBestMatch(item) {
        def pickOrder = ['title','referatrubrik', 'text', 'identifier']
		def bestMatch = ""
        pickOrder.each {
            if (item.matches instanceof Map && item.matches?.containsKey(it)) {
                if(!bestMatch) {
                    bestMatch = item.matches[it].get(0)
                }
            }
        }
		return bestMatch
	}

    public static String prefereMatchBeforeOriginal(def item, String name) {
        return item.matches instanceof Map && item.matches?.containsKey(name)?item.matches[name].get(0):item[name];
    }

    public static String prefereMatchBeforeOriginal(def item, String name, String alternate) {
        if (item.matches instanceof Map && item.matches?.containsKey(name)) {
            return item.matches[name].get(0)
        }
        if (item.matches instanceof Map && item.matches?.containsKey(alternate)) {
            return item.matches[alternate].get(0)
        }
        if (item.containsKey(name))
            return item[name]
        return item[alternate]
    }

    def static createHttpBuilder() {
        def http = new HTTPBuilder()
        http.getClient().getParams().setParameter("http.connection.timeout", new Integer(100000))
        http.getClient().getParams().setParameter("http.socket.timeout", new Integer(100000))
        return http
    }
}
