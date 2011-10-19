package se.lagrummet

import org.compass.core.CompassQuery;

class LocalSearchService {

    static transactional = true

    public  plainTextSearch(String query) {
		def searchResult = new SearchResult()
		if(query) {
			def result = Page.search  {
				must(queryString(query))
				must(term("status", "published"))
				must(le("publishStart", new Date()))
				must{
					ge("publishStop", new Date())
					term("publishStop", "NULL")
				}
				sort(CompassQuery.SortImplicitType.SCORE, CompassQuery.SortDirection.AUTO)
			}
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
