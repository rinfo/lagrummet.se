package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SearchController {
	
	def rinfoService

    def index = {
		
		def searchResult = null
		
		if(params.query) {
			searchResult = rinfoService.plainTextSearch(params.query)
		}
		render(view: 'searchForm', model: [query: params.query, searchResult: searchResult])
	}
}
