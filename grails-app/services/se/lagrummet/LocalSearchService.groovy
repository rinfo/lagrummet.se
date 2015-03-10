package se.lagrummet

import org.compass.core.CompassQuery;

class LocalSearchService {

    public static final String regex = "([+\\-|&!\\(\\){}\\[\\]\\/^~*?:\\\\]|[&\\|]{2})";
    public static final String replacement = "\\\\\$1";

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
        Benchmark.section("DB Query time ", log) {
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
                                    queryString(qs.replaceAll(regex, replacement))
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
                        if(i < 5) {
                            searchResult.addTopHit(searchResultItem)
                        }
                    }
                    searchResult.itemsList = searchResult.items['Ovrigt'] // Lapptäcke...
                }
                catch(e) {
                    if (e in org.compass.core.engine.SearchEngineQueryParseException) {
                        log.error "Parse exeeption", e
                        searchResult.errorMessages.add("Något gick fel. Det är inte säkert att sökresultatet är komplett.")
                    } else {
                        log.error("DB search failed", e)
                        searchResult.errorMessages.add("Något gick fel. Det är inte säkert att sökresultatet är komplett.")
                    }
                }

            }
        }
        return searchResult
	}
	
}
