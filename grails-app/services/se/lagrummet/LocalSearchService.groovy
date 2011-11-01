package se.lagrummet

import org.compass.core.CompassQuery;

class LocalSearchService {

    static transactional = true

    public SearchResult plainTextSearch(String query, Category cat, Map options = [:]) {
		
		if(cat && !cat.equals(Category.OVRIGT)){
			return new SearchResult()
		}
		return queryWithOptions(query, options)
	}
	
	public SearchResult plainTextSearchPaged(String query, Category cat, Integer offset, Integer itemsPerPage) {
		def options = [offset: offset, max: itemsPerPage]
		return plainTextSearch(query, cat, options)
	}
	
	
	private SearchResult queryWithOptions(String query, Map options) {
		def searchResult = new SearchResult()
		searchResult.maxItemsPerCategory = options.max ?: searchResult.maxItemsPerCategory
		
		def pageHighlighter = { highlighter, index, sr ->
			if(!sr.highlights) {
				sr.highlights = []
			}
			sr.highlights[index] = [
					content: highlighter.fragment("content")
				]
		}
		
		options['withHighlighter'] = pageHighlighter
		if(query) {
			def result = Page.search (  {
					must(queryString(query))
					must(term("status", "published"))
					must(le("publishStart", new Date()))
					must{
						ge("publishStop", new Date())
						term("publishStop", "NULL")
					}
					sort(CompassQuery.SortImplicitType.SCORE, CompassQuery.SortDirection.AUTO)
				},
				options )
			searchResult.totalResults = result.total
			
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
