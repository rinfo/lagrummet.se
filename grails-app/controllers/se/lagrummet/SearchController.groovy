package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import grails.converters.*

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SearchController {
	
	def searchService

    def index = {
		
		def searchResult = null

		if(params.query) {
			searchResult = searchService.plainTextSearch(params.query)
		}
		if (params.ajax) {
			def response = [query: params.query, searchResult: searchResult]
			render response as JSON
		} else {
			render(view: 'searchForm', model: [query: params.query, searchResult: searchResult, page: new Page()])
		}
		
	}
}
