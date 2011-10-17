package se.lagrummet

class LocalSearchService {

    static transactional = true

    public  plainTextSearch(String query) {
		def searchResult = new SearchResult()
		if(query) {
			def result = Page.search(query, sort: "SCORE")
			searchResult.totalResults = result.total
			searchResult.itemsPerPage = result.max
			searchResult.startIndex = result.offset
			searchResult.originalItems = result.results
			
			searchResult.items['Ovrigt'] = []
			result.results.each{ item ->
				def searchResultItem = new SearchResultItem()
				searchResultItem.title = item.title
				searchResultItem.iri = item.url()
				searchResultItem.issued = item.lastUpdated
				
				searchResult.items['Ovrigt'].add(searchResultItem)
			}
		}
		return searchResult
	}
}
