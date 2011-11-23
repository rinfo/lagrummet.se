package se.lagrummet

import grails.converters.*

import java.text.SimpleDateFormat

class SearchController {
	
	def searchService
	def rdlSearchService

    def index = {
		def searchResult = null
		def offset
		if(params.query && params.cat && params.cat != "Alla")  {
			offset = parseInt(params.offset, 0)
			def itemsPerPage = parseInt(params.max, 20)
			searchResult = searchService.plainTextSearch(params.query, Category.getFromString(params.cat), offset, itemsPerPage)
			
		} else if(params.query) {
			searchResult = searchService.plainTextSearch(params.query, Category.getFromString(params.cat), null, null)
		}

		if (params.ajax) {
			def response = [query: params.query, searchResult: searchResult]
			render response as JSON
		} else if(params.cat && params.cat != "Alla") {
			render(view: 'searchResultByCategory', model: [query: params.query, cat: params.cat,  searchResult: searchResult, page: new Page(), offset:offset])
		} else {
			render(view: 'searchForm', model: [query: params.query, searchResult: searchResult, page: new Page()])
		}
		
	}
	
	def ext = { ExtendedSearchCommand esc ->
		def searchResult
		def offset = parseInt(params.offset, 0)
		def itemsPerPage = parseInt(params.max, 20)

		def queryBuilder = new QueryBuilder(params)
		def docTypes = params.typ ? Category.extendedSearchTypes[params.typ] : Category.getFromString(params.kategori)?.getTypes()
		queryBuilder.setType(docTypes);
		
		def dateType = params.datum
		
		if(params.fromDate && !esc.hasErrors()){
			if(dateType == 'ikraft') {
				queryBuilder.setIkraftFrom(params.fromDate)
			} else if(dateType == 'utfardande') {
				queryBuilder.setUtfardandedatumFrom(params.fromDate)
			} else if(dateType == 'avgorande') {
				//todo
			} else if(dateType == 'utgivande') {
				queryBuilder.setUtkomFranTryckFrom(params.fromDate)
			}
		}
		
		if(params.toDate && !esc.hasErrors()){
			if(dateType == 'ikraft') {
				queryBuilder.setIkraftTo(params.toDate)
			} else if(dateType == 'utfardande') {
				queryBuilder.setUtfardandedatumTo(params.toDate)
			} else if(dateType == 'avgorande') {
				//todo
			} else if(dateType == 'utgivande') {
				queryBuilder.setUtkomFranTryckTo(params.toDate)
			}
		} 
		
		if(!esc.hasErrors() && !queryBuilder.isEmpty()) {
			queryBuilder.setPageAndPageSize((int)(offset/itemsPerPage), itemsPerPage)
			searchResult = rdlSearchService.searchWithQuery(queryBuilder.getQueryParams(), "list")
		}
		def cat = params.kategori ?: "Forfattningar"
		render(view: 'extendedSearch', model: [queryParams: queryBuilder.getQueryParams(), query: queryBuilder, cat: cat, searchResult: searchResult, page: new Page(), offset: offset, extendedSearchCommand: esc])
	}
	
	def findCreatorsOrPublishers = {
		if (params.type) {
			if (params.type == "departement") {
				def response = ['publishers' : grailsApplication.config.lagrummet.search.availableDepartement]
				render response as JSON
			} else if (params.type == "beslutandeMyndighet") {
				def response = ['publishers' : rdlSearchService.getAvailablePublishers()]
				render response as JSON
			}
		} else {
			def publishers = rdlSearchService.getExistingPublishers(params.q)
			
			def response = ['publishers' : publishers]
			render response as JSON
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

class ExtendedSearchCommand {
	String toDate
	String fromDate
	
	static constraints = {
		fromDate(
			validator : { date ->
				return validDate(date)	
			})
		
		toDate(
			validator : { date ->
				return validDate(date)
		})
			
	}
	
	private static boolean validDate(String date) {
		if(date == null || "".equals(date)) {
			return true
		}
		def sdf = new SimpleDateFormat("yyyy-MM-dd")
		Date testDate = null
		
		try {
			testDate = sdf.parse(date)
		} catch(Exception e) {
			return false
		}
		
		if(!sdf.format(testDate).equals(date)) {
			return false
		}
		
		return true
	}
}
