package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import net.sf.json.JSONObject

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class RdlSearchService {

    static transactional = true
	
	def availableCategories = [Category.RATTSFALL, Category.LAGAR, Category.PROPOSITIONER, Category.UTREDNINGAR]
	
    public SearchResult plainTextSearch(String query, Category cat) {
		def queryParams = [q:query]
		
		if(cat && availableCategories.contains(cat)){
			queryParams['type'] = cat.getTypes()
		} else if (cat) {
			return new SearchResult()
		}
		
		return searchWithQuery(queryParams)
	}
	
	public SearchResult plainTextSearchPaged(String query, Category cat, Integer offset, Integer itemsPerPage) {
		def queryParams = [q:query]
		if(cat && availableCategories.contains(cat)){
			queryParams['type'] = cat.getTypes()
		} else if (cat) {
			return new SearchResult()
		}
		queryParams['_page'] = offset / itemsPerPage
		queryParams['_pageSize'] = itemsPerPage
		
		return searchWithQuery(queryParams)
	}
	
	private SearchResult searchWithQuery(queryParams) {
		def searchResult = new SearchResult()
		def http = new HTTPBuilder()
		try {
			http.request(ConfigurationHolder.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) { req ->
				uri.path = "/-/publ"
				uri.query = queryParams
				req.getParams().setParameter("http.connection.timeout", new Integer(5000));
				req.getParams().setParameter("http.socket.timeout", new Integer(5000));
				
				response.success = {resp, json ->
					searchResult.totalResults = json.totalResults
					searchResult.maxItemsPerCategory = queryParams._pageSize ?: searchResult.maxItemsPerCategory
					
					json.items.each { item ->
						def searchResultItem = new SearchResultItem(
														title: item.title,
														iri: item.iri,
														issued: item.issued,
														describedBy: item.describedby,
														identifier: item.identifier,
														matches: getBestMatch(item),
														type: item.type
														)
						
						searchResult.addItemByType(searchResultItem)
					}
				}
				
				response.failure = { resp ->
					searchResult.errorMessages.add("Något gick fel. Det är inte säkert att sökresultatet är komplett.")
				}
			}
		} catch (SocketTimeoutException) {
			searchResult.errorMessages.add("Något gick fel. Det är inte säkert att sökresultatet är komplett.")
		}
		return searchResult
	}
	
	public String getBestMatch(JSONObject item) {
		def bestMatch = ""
		item.matches?.each{ matchKey, matchesList ->
				if(!bestMatch) {
					bestMatch = matchesList.get(0)
				}
		}
		return bestMatch
	}
}
