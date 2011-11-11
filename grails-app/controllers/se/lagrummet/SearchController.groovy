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
	
	def ext = {
		def searchResult
		def offset = parseInt(params.offset, 0)
		def itemsPerPage = parseInt(params.max, 20)

		def queryBuilder = new QueryBuilder(params)
		def docTypes = params.typ ? Category.extendedSearchTypes[params.typ] : Category.getFromString(params.kategori)?.getTypes()
		queryBuilder.setType(docTypes);
		
		def dateFrom = params.datumMin
		def dateTo = params.datumMax
		
		def dateType = params.datum
				
		if(params.fromDate && validDate(params.fromDate)){
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
		
		if(params.toDate && validDate(params.toDate)){
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
		
		if(!queryBuilder.isEmpty()) {
			queryBuilder.setPageAndPageSize((int)(offset/itemsPerPage), itemsPerPage)
			searchResult = rdlSearchService.searchWithQuery(queryBuilder.getQueryParams())
		}
		def cat = params.kategori ?: Category.LAGAR.toString()
		render(view: 'extendedSearch', model: [queryParams: queryBuilder.getQueryParams(), query: queryBuilder, cat: cat, searchResult: searchResult, page: new Page(), offset: offset])
	}
	
	def findAvailablePublishers = {
		
		def publishers = rdlSearchService.getAvailablePublishers(params.q)
		
		def response = ['publishers' : publishers]
		render response as JSON
	}
	
	private boolean validDate(String date) {
		def sdf = new SimpleDateFormat("yyyy-MM-dd")
		
		Date testDate = null
		
		try {
			testDate = sdf.parse(date)
		} catch(Exception e) {
		System.out.println(e.getMessage());
		
		System.out.println("The date format is invalid: " + date);
			return false
		}
		
		if(!sdf.format(testDate).equals(date)) {
			System.out.println("The date is not a valid date");
			return false
		}
		
		return true
	}
	
	private Integer parseInt(String input, Integer defaultValue = 0) {
		try {
			return input.toInteger()
		} catch (Exception e) {
			return defaultValue
		}
	}
}
