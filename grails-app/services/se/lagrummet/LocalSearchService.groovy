package se.lagrummet

import org.compass.core.CompassQuery;

class LocalSearchService {

    static transactional = true
	
    public SearchResult plainTextSearch(List<String> query, Category cat, Integer offset, Integer itemsPerPage) {
		def options = [:]
		if(cat && !cat.equals(Category.OVRIGT)){
			return new SearchResult()
		}
		
		if(offset != null && itemsPerPage) {
			options['offset'] = offset
			options['max'] = itemsPerPage
		}
		return queryWithOptions(query, options)
	}
	
	private SearchResult queryWithOptions(List<String> query, Map options) {
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
			try {
				def result = Page.search (  {
						must{
							query.each { qs ->
								queryString(qs)
							}
						}
						must(term("status", "published"))
						must(le("publishStart", new Date()))
						must{
							ge("publishStop", new Date())
							term("publishStop", "NULL")
						}
						must(term("metaPage", false))
						sort(CompassQuery.SortImplicitType.SCORE, CompassQuery.SortDirection.AUTO)
					},
					options )
				searchResult.totalResults = result.total
				
				result.results.eachWithIndex{ item, i ->
					def searchResultItem = new SearchResultItem(
													title: item.title,
													iri: item.url(),
													issued: item.lastUpdated,
													type: 'Lagrummet.Artikel',
													matches: result.highlights[i].content
													)
					searchResult.addItemByType(searchResultItem)
					if(i < 2) {
						searchResult.addTopHit(searchResultItem)
					}
				}
			}
			catch(e) {
				if (e in org.compass.core.engine.SearchEngineQueryParseException) {
					searchResult.errorMessages.add("error.ParseException")
				}
			}
			
		}
		return searchResult
	}
	
}
