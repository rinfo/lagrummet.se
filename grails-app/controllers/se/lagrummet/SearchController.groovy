package se.lagrummet

import grails.converters.*

class SearchController {
	
	def searchService

    def index = {
		
		def searchResult = null
		def offset
		if(params.query && params.cat)  {
			offset = parseInt(params.offset, 0)
			def itemsPerPage = parseInt(params.max, 20)
			searchResult = searchService.plainTextSearch(params.query, Category.getFromString(params.cat), offset, itemsPerPage)
			
		} else if(params.query) {
			searchResult = searchService.plainTextSearch(params.query, Category.getFromString(params.cat), null, null)
		}

		if (params.ajax) {
			def response = [query: params.query, searchResult: searchResult]
			render response as JSON
		} else if(params.cat) {
			render(view: 'searchResultByCategory', model: [query: params.query, cat: params.cat,  searchResult: searchResult, page: new Page(), offset:offset])
		} else {
			render(view: 'searchForm', model: [query: params.query, searchResult: searchResult, page: new Page()])
		}
		
	}
	
	private Integer parseInt(String input, Integer defaultValue = 0) {
		try {
			return input.toInteger()
		} catch (Exception e) {
			return defaultValue
		}
	}
}
