package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import net.sf.json.JSONObject

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SearchService {

    static transactional = true

    public JSONObject plainTextSearch(String query) {
		def searchResult
		def http = new HTTPBuilder()
		http.request(ConfigurationHolder.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) {
			uri.path = "/-/publ"
			uri.query = [q: query]
			response.success = {resp, json ->
				searchResult = json
			}
		}
		searchResult.items.each { item ->
			setBestMatch(item)	
		}
		orderSearchResultByType(searchResult)
		return searchResult
	}
	
	public void setBestMatch(JSONObject item) {
		def bestMatch = ""
		item.matches?.each{ matchKey, matchesList ->
				if(!bestMatch) {
					bestMatch = matchesList.get(0)
				}
		}
		item.remove('matches')
		item.put('matches', bestMatch)
	}
	
	public void orderSearchResultByType(JSONObject searchResult) {
		def typeMap = [:]
		searchResult.items.each{ item ->
			def type = item.type
			def category = getCategoryForType(type)
			if(!typeMap[(category)]) {
				typeMap[(category)] = []
			}
			typeMap[(category)].add(item)
		}
		searchResult.remove('items')
		searchResult.put('items', typeMap)
	}
	
	public String getCategoryForType(String type) {
		//Lagar, Rattsfall, Propositioner, Utredningar, Ovrigt
		def typeToCategory = [	'Rattsfallsreferat' : 'Rattsfall',
								'KonsolideradGrundforfattning' : 'Lagar', 
								'Proposition' : 'Propositioner', 
								'Utredningsbetankande' : 'Utredningar']
		def unknown = 'Okand'
		return typeToCategory[(type)] ?: unknown
		
	}
	
}
