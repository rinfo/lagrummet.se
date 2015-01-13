package se.lagrummet

import grails.plugin.gson.converters.GSON
import java.text.SimpleDateFormat
import static se.lagrummet.QueryBuilder.Operators.*
import se.lagrummet.Search
import grails.plugins.springsecurity.Secured
import grails.gsp.PageRenderer

class SearchController {

    static int STANDARD_QUERY_LIMIT_INPUT_LENGTH = 1000

	def searchService
	def rdlSearchService
	def synonymService
	def springSecurityService
    def grailsApplication
    PageRenderer groovyPageRenderer

	def statistics = {
		def daysOfSearches = params.daysOfSearches ? params.daysOfSearches.toInteger() : 30
		def numberOfQueries = params.numberOfQueries ? params.numberOfQueries.toInteger() : 10
		def c = Search.createCriteria()
		def searches = c {
			gt('dateCreated', new Date() - daysOfSearches)
			projections {
				countDistinct 'id', 'myCount'
				groupProperty 'query'
			}
			order ('myCount', 'desc')
			maxResults(numberOfQueries)
		}
		
		def totalSearches = Search.createCriteria().count {
			gt('dateCreated', new Date() - daysOfSearches)
		}
		
		[searches: searches, totalSearches: totalSearches, daysOfSearches: daysOfSearches, numberOfQueries: numberOfQueries]
	}
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
	def exportStats = {
		def daysOfSearches = params.daysOfSearches ? params.daysOfSearches.toInteger() : 30
		def searches = Search.findAllByDateCreatedGreaterThan(new Date() - daysOfSearches)

		def result = new StringBuilder()
		def dateFormatter = new SimpleDateFormat("yyyy-MM-dd")

		result.append("\uFEFF") //this is the UTF-8 BOM, MS Excel likes it.

		for(d in searches) {
			result.append("\"${d.query}\";${d.category};\"${dateFormatter.format(d.dateCreated)}\"\n")
		}

		response.setHeader("Content-disposition", "attachment; filename=search-history.csv");
		render(contentType:'text/csv',text:result,encoding:"UTF-8")
	}

    def index = {
		def searchResult = null
		def offset
		if (params.cat) session.cat = params.cat //todo this could end up in very suspicious behaviour when using multiple tabs/windows in the browser
		
		def synonyms = []
		def queries = []
        String query = params.query?.take(grailsApplication.config.lagrummet.search.maxLength) ?: ""

		queries.add(query)
		if(!params.alias || params.alias != "false"){
				synonyms = synonymService.lookupSynonyms(query)
				queries.addAll(synonyms)
				params.alias = null
		}
		
		if(query && params.cat && params.cat != "Alla")  {
			offset = parseInt(params.offset, 0)
			def itemsPerPage = parseInt(params.max, 20)
			searchResult = searchService.plainTextSearch(queries, Category.getFromString(params.cat), offset, itemsPerPage)
			
		} else if(query) {
			searchResult = searchService.plainTextSearch(queries, Category.getFromString(params.cat), null, null)
		}
		
		new Search(query: query, category: params.cat).save()

        def dynamicSearchResults = selectAndRenderContents(query, searchResult, offset, synonyms)

		if (params.ajax) {
            def response = [query: query, searchResult: searchResult, synonyms: synonyms, dynamicSearchResults: dynamicSearchResults]
            render response as GSON
		} else if(params.cat && params.cat != "Alla" || grailsApplication.config.lagrummet.onlyLocalSearch) {
			render(view: 'searchResultByCategory', model: [query: query, contents: dynamicSearchResults])
		} else {
			render(view: 'searchForm', model: [query: query, contents: dynamicSearchResults])
		}
		
	}

    private def selectAndRenderContents(String query, def searchResult, def offset, def synonyms) {
        if (grailsApplication.config.lagrummet.onlyLocalSearch)
            return groovyPageRenderer.render(view: '/grails-app/views/search/searchResultByCategoryContents', model: [query: query, cat: 'Ovrigt', searchResult: searchResult, page: new Page(metaPage: false, title: message(code: "searchResult.label")), offset: offset, synonyms: synonyms, alias: params.alias])
        if (params.cat && params.cat != "Alla")
            return groovyPageRenderer.render(view: '/grails-app/views/search/searchResultByCategoryContents', model: [query: query, cat: params.cat, searchResult: searchResult, page: new Page(metaPage: false, title: message(code: "searchResult.label")), offset: offset, synonyms: synonyms, alias: params.alias])
        return groovyPageRenderer.render(view: '/grails-app/views/search/searchFormContents', model: [query: query, searchResult: searchResult, page: new Page(metaPage: false, title: message(code: "searchResult.label")), synonyms: synonyms, alias: params.alias])
    }

    def ext = { ExtendedSearchCommand esc ->
        if (grailsApplication.config.lagrummet.onlyLocalSearch) {
            forward(controller: "page", action: "error", params: [errorId: "404"])
            return
        }
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
				queryBuilder.setOrUtfardandedatumFrom(params.fromDate)
				queryBuilder.setOrBeslutsdatumFrom(params.fromDate)
			} else if(dateType == 'avgorande') {
				queryBuilder.setAvgorandedatumFrom(params.fromDate)
			} else if(dateType == 'utgivande') {
				queryBuilder.setUtkomFranTryckFrom(params.fromDate)
			}
		}
		
		if(params.toDate && !esc.hasErrors()){
			if(dateType == 'ikraft') {
				queryBuilder.setIkraftTo(params.toDate)
			} else if(dateType == 'utfardande') {
				queryBuilder.setOrUtfardandedatumTo(params.toDate)
				queryBuilder.setOrBeslutsdatumTo(params.toDate)
			} else if(dateType == 'avgorande') {
				queryBuilder.setAvgorandedatumTo(params.toDate)
			} else if(dateType == 'utgivande') {
				queryBuilder.setUtkomFranTryckTo(params.toDate)
			}
		} 
		
		def dateFormat = new SimpleDateFormat("yyyy-MM-dd")
		def today = dateFormat.format(new Date())
		if(params.gallande && params.upphavda && params.kommande) {
			//all documents
		} else if(params.gallande && params.upphavda) {
			def ikraftDate
			if(dateType == 'ikraft' && params.toDate && params.toDate < today) {
				ikraftDate = params.toDate
			} else {
				ikraftDate = today
			}
			queryBuilder.setIkraftTo(ikraftDate)
		} else if(params.gallande && params.kommande) {
			queryBuilder.setParam('ifExists-minEx-rev.upphaver.ikrafttradandedatum', today)
		} else if(params.upphavda && params.kommande) {
			queryBuilder.setParam('or-max-rev.upphaver.ikrafttradandedatum', today)
			queryBuilder.setIkraft(today, OR, MIN_EX)
		} else if(params.gallande) {
//			max-ikraft = today
			def ikraftDate
			if(dateType == 'ikraft' && params.toDate && params.toDate < today) {
				ikraftDate = params.toDate
			} else {
				ikraftDate = today
			}
			queryBuilder.setIkraftTo(ikraftDate)
			queryBuilder.setParam('ifExists-minEx-rev.upphaver.ikrafttradandedatum', today)
		} else if(params.upphavda) {
		
			queryBuilder.setParam('max-rev.upphaver.ikrafttradandedatum', today)
		} else if(params.kommande) {
			queryBuilder.setIkraft(today, MIN_EX)
		} else {
			//all documents
		}
		
		if(!esc.hasErrors() && !queryBuilder.isEmpty()) {
			queryBuilder.setPageAndPageSize((int)(offset/itemsPerPage), itemsPerPage)
			searchResult = rdlSearchService.searchWithQuery(queryBuilder.getQueryParams(), "list")
		}
		def cat = params.kategori ?: "Forfattningar"
		render(view: 'extendedSearch', model: [queryParams: queryBuilder.getQueryParams(), query: queryBuilder, cat: cat, searchResult: searchResult, page: new Page(metaPage: false, title: message(code:"extendedSearch.label")), offset: offset, extendedSearchCommand: esc])
	}
	
	def findCreatorsOrPublishers = {
		if (params.type) {
			if (params.type == "departement") {
				def response = ['publishers' : grailsApplication.config.lagrummet.search.availableDepartement]
				render response as GSON
			} else if (params.type == "beslutandeMyndighet") {
				def response = ['publishers' : rdlSearchService.getAvailablePublishers()]
				render response as GSON
			}
		} else {
			def publishers = rdlSearchService.getExistingPublishers(params.q)
			
			def response = ['publishers' : publishers]
			render response as GSON
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
		fromDate (nullable: true,
			validator : { date ->
				return validDate(date)	
			})
		
		toDate(nullable: true,
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
