package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SearchController {

    def index = {
		
		def searchResult = null
		
		if(params.query) {
			def http = new HTTPBuilder()
			
			http.request(ConfigurationHolder.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) {
				uri.path = "/-/publ"
				uri.query = [q: params.query] 	
				response.success = {resp, json ->
					searchResult = json
				}
			}
		}
		render(view: 'searchForm', model: [query: params.query, searchResult: searchResult])
	}
}
