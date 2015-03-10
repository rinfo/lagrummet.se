package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import net.sf.json.JSONObject

class RdlSearchService {

    def grailsApplication

    static transactional = true
	
	def availableCategories = [Category.RATTSFALL, Category.MYNDIGHETERS_FORESKRIFTER, Category.PROPOSITIONER, Category.UTREDNINGAR, Category.LAGAR]
	def latestConsolidatedCategories = [Category.LAGAR]
	
    public SearchResult plainTextSearch(List<String> query, Category cat, Integer offset, Integer itemsPerPage) {
        def result = new SearchResult()
        Benchmark.section("RDL plain text search time", log) {
            def queryBuilder = new QueryBuilder()

            queryBuilder.setQueries(query)

            if(cat && availableCategories.contains(cat)){
                queryBuilder.setType(cat.getTypes())
            } else if (cat) {
                return new SearchResult()
            }

            if(offset != null && itemsPerPage) {
                queryBuilder.setPageAndPageSize((int)(offset / itemsPerPage), itemsPerPage)
            }
            //force the ikraftdatum to be returned in the search result if the document has one
            queryBuilder.setIkraftIfExists("")
            queryBuilder.setParam("_stats", "on")

            result = searchWithQuery(queryBuilder.getQueryParams(), cat ? 'list' : 'category')
            //result.resetCategory(Category.LAGAR)
        }
        return result
	}
	
	/**
	 * Find the best representation of a current or consolidated law
	 * @param query
	 * @param cat
	 * @param offset
	 * @param itemsPerPage
	 * @return
	 */
	public SearchResult plainTextLatestConsolidated(List<String> query, Category cat, Integer offset, Integer itemsPerPage) {
        def searchResult = new SearchResult()
        Benchmark.section("RDL latest consolidated search time", log) {
            if(cat && !latestConsolidatedCategories.contains(cat)) {

                return searchResult
            }

            def today = new Date().format("yyyy-MM-dd")
            def queryBuilder = new QueryBuilder()

            queryBuilder.setQueries(query)

            queryBuilder.setType(Category.LAGAR.getTypes())
            queryBuilder.setParam('exists-rev.omtryckAv.iri', 'false')
            queryBuilder.setParam('exists-rev.konsoliderar.iri', 'false')
            queryBuilder.setParam('exists-rev.ersatter.iri', 'false')
            queryBuilder.setParam('exists-upphaver.iri', 'false')
            queryBuilder.setParam('or-exists-andrar.iri', 'false')
            queryBuilder.setParam('or-exists-omtryckAv.iri', 'true')
            queryBuilder.setParam('exists-isReplacedBy.iri', 'false')
            queryBuilder.setParam('ifExists-max-ikrafttradandedatum', today)
            queryBuilder.setParam('ifExists-minEx-rev.upphaver.ikrafttradandedatum', today)
            queryBuilder.setParam('ifExists-max-omtryckAv.ikrafttradandedatum', today)
            queryBuilder.setParam('ifExists-minEx-omtryckAv.rev.upphaver.ikrafttradandedatum', today)
            queryBuilder.setParam('ifExists-max-konsoliderar.ikrafttradandedatum', today)
            queryBuilder.setParam('ifExists-minEx-konsoliderar.rev.upphaver.ikrafttradandedatum', today)

            if(offset != null && itemsPerPage) {
                queryBuilder.setPageAndPageSize((int)(offset / itemsPerPage), itemsPerPage)
            }

            queryBuilder.setParam("_stats", "on")

            searchResult = searchWithQuery(queryBuilder.getQueryParams())
        }
        return searchResult
	}
	
	
	public SearchResult searchWithQuery(Map queryParams, String resultListType = 'category') {
        println "se.lagrummet.RdlSearchService.searchWithQuery resultListType=${resultListType}"
		def searchResult = new SearchResult()
		searchResult.maxItemsPerCategory = queryParams._pageSize ?: searchResult.maxItemsPerCategory
		def http = new HTTPBuilder()
        http.getClient().getParams().setParameter("http.connection.timeout", new Integer(100000))
        http.getClient().getParams().setParameter("http.socket.timeout", new Integer(100000))
		try {
			http.request(grailsApplication.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) { req ->
				uri.path = "/-/publ"
				uri.query = queryParams
				req.getParams().setParameter("http.connection.timeout", new Integer(100000));
				req.getParams().setParameter("http.socket.timeout", new Integer(100000));
				
				response.success = {resp, json ->
					searchResult.totalResults = json.totalResults
                    println "se.lagrummet.RdlSearchService.searchWithQuery *************************************************************************************************"
					//json.items.eachWithIndex { item, i ->
                    json.items.each { item ->
						def searchResultItem = new SearchResultItem(
                                                        title: prefereMatchBeforeOriginal(item,'title'),
														iri: item.iri,
														issued: item.issued,
														describedBy: item.describedby,
                                                        identifier: prefereMatchBeforeOriginal(item,'identifier'),
														matches: prefereMatchBeforeOriginal(item,'text','referatrubrik'), //getBestMatch(item),
														type: item.type,
														ikrafttradandedatum: item.ikrafttradandedatum,
														malnummer: item.malnummer,
                                                        text: prefereMatchBeforeOriginal(item,'text','referatrubrik'),
														)
						/*if("category".equals(resultListType)) {
							searchResult.addItemByType(searchResultItem)
						} else*/
                        if ("list".equals(resultListType)) {
							searchResult.addItem(searchResultItem)
						} else
                            println "se.lagrummet.RdlSearchService.searchWithQuery IGNORED ${item.identifier}"
						/*
						if(i < 5) {
							searchResult.addTopHit(searchResultItem)
						}*/
					}
                    println "se.lagrummet.RdlSearchService.searchWithQuery ************************************* STATISTICS **************************************************"
					if(json.statistics) {
						searchResult.addStats(json.statistics.slices)

                        if(json.statistics.slices?.observations) {
                            json.statistics.slices?.observations[0].each { observation ->
                                observation.items?.each { item ->
                                    def searchResultItem = new SearchResultItem(
                                            title: prefereMatchBeforeOriginal(item,'title'),
                                            iri: item.iri,
                                            issued: item.issued,
                                            describedBy: item.describedby,
                                            identifier: prefereMatchBeforeOriginal(item,'identifier'),
                                            matches: getBestMatch(item),
                                            type: item.type,
                                            ikrafttradandedatum: item.ikrafttradandedatum,
                                            malnummer: item.malnummer,
                                            text: prefereMatchBeforeOriginal(item,'text','referatrubrik'),
                                    )
                                    if ("category".equals(resultListType)) {
                                        searchResult.addItemByType(searchResultItem)
                                    }
                                }
                                searchResult.totalResultsPerCategory[observation.term] = observation.count
                            }
                        }
					}
                    println "se.lagrummet.RdlSearchService.searchWithQuery ****************************************** END ***************************************************"
	
				}
				
				response.failure = { resp ->
					log.error(resp.statusLine)
					searchResult.errorMessages.add("Något gick fel. Det är inte säkert att sökresultatet är komplett.")
				}
			}
		} catch (SocketTimeoutException ex) {
			log.error(ex)
            log.error("Failed to communicate with *"+grailsApplication.config.lagrummet.rdl.service.baseurl+"'");
			searchResult.errorMessages.add("Något gick fel. Det är inte säkert att sökresultatet är komplett.")
		} catch (UnknownHostException ex) {
			log.error(ex)
			searchResult.errorMessages.add("Något gick fel. Det är inte säkert att sökresultatet är komplett.")
		} 
		return searchResult
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
//							def tokens = topic.iri.ref.tokenize('/')
//							def iri = tokens.get(tokens.size() -1)
//							publishers.add(["iri" : iri, "name" : topic.name])
							publishers.add(topic.name)
						}
					}
				}
				
			}
		} catch(SocketTimeoutException) {
		
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
		} catch(SocketTimeoutException) {
		
		}
		return publishers
	}
	
	public String getBestMatch(item) {
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

    public String prefereMatchBeforeOriginal(def item, String name) {
        return item.matches instanceof Map && item.matches?.containsKey(name)?item.matches[name].get(0):item[name];
    }

    public String prefereMatchBeforeOriginal(def item, String name, String alternate) {
        println "se.lagrummet.RdlSearchService.prefereMatchBeforeOriginal ${name} ${alternate}"
        println item
        if (item.matches instanceof Map && item.matches?.containsKey(name)) {
            println "se.lagrummet.RdlSearchService.prefereMatchBeforeOriginal matches ${item.matches[name]}"
            println "se.lagrummet.RdlSearchService.prefereMatchBeforeOriginal *********************"
            println item.matches[name].get(0)
            println "se.lagrummet.RdlSearchService.prefereMatchBeforeOriginal *********************"
            return item.matches[name].get(0)
        }
        if (item.matches instanceof Map && item.matches?.containsKey(alternate)) {
            println "se.lagrummet.RdlSearchService.prefereMatchBeforeOriginal alternate ${item.matches[alternate].get(0)}"
            return item.matches[alternate].get(0)
        }
        if (item.containsKey(name))
            return item[name]
        return item[alternate]
    }
}
