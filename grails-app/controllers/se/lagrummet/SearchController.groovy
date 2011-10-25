package se.lagrummet

import grails.converters.*

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
