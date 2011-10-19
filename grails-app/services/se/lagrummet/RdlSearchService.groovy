package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import net.sf.json.JSONObject

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class RdlSearchService {

    static transactional = true

    public SearchResult plainTextSearch(String query) {
		def searchResult = new SearchResult()
		def http = new HTTPBuilder()
		http.request(ConfigurationHolder.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) {
			uri.path = "/-/publ"
			uri.query = [q: query.encodeAsURL()]
			response.success = {resp, json ->
				searchResult.itemsPerPage = json.itemsPerPage
				searchResult.startIndex = json.startIndex
				searchResult.totalResults = json.totalResults
				
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
