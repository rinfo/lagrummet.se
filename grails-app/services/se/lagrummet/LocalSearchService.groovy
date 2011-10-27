package se.lagrummet

import org.compass.core.CompassQuery;

class LocalSearchService {

    static transactional = true

    public  plainTextSearch(String query) {
		def searchResult = new SearchResult()
		
		def pageHighlighter = { highlighter, index, sr ->
			if(!sr.highlights) {
				sr.highlights = []
			}
			sr.highlights[index] = [
					content: highlighter.fragment("content")
				]
		}
		
		if(query) {
			def result = Page.search (withHighlighter: pageHighlighter)  {
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
			
			def i = 0
			result.results.each{ item ->
				def searchResultItem = new SearchResultItem(
												title: item.title,
												iri: item.url(),
												issued: item.lastUpdated,
												type: 'Lagrummet.Artikel',
												matches: result.highlights[i].content
												)
				searchResult.addItemByType(searchResultItem)
				i++
			}
		}
		return searchResult
	}
	
}
