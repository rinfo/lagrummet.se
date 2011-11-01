package se.lagrummet

import grails.converters.*

class SearchController {
	
	def searchService

    def index = {
		
		def searchResult = null

		if(params.query) {
			searchResult = searchService.plainTextSearch(params.query, Category.getFromString(params.cat))
		}

		if (params.ajax) {
			def response = [query: params.query, searchResult: searchResult]
			render response as JSON
		} else if(params.cat) {
			render(view: 'searchResultByCategory', model: [query: params.query, cat: params.cat,  searchResult: searchResult, page: new Page()])
		} else {
			render(view: 'searchForm', model: [query: params.query, searchResult: searchResult, page: new Page()])
		}
		
	}
}
