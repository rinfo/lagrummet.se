package se.lagrummet

import org.compass.core.CompassQuery;

class LocalSearchService {

    public static final String regex = "([+\\-|&!\\(\\){}\\[\\]\\/^~*?:\\\\]|[&\\|]{2})";
    public static final String replacement = "\\\\\$1";

    static transactional = true

    public SearchResult textSearch(List<String> query, Integer offset, Integer itemsPerPage) {
        commonSearch(query, null, offset, itemsPerPage)
    }

    public SearchResult categorizedSearch(List<String> query, int limitItems = 4) {
        commonSearch(query, limitItems)
    }

    private SearchResult commonSearch(List<String> query, Integer limitItems = null, Integer offset = null, Integer itemsPerPage = null) {
        def searchResult = new SearchResult(Category.OVRIGT)
        def options = [:]
        if(offset != null && itemsPerPage) {
            options['offset'] = offset
            options['max'] = itemsPerPage
        }
        Benchmark.section("DB categorized Query time ", log) {
            if (limitItems)
                options.max = limitItems

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
                    }, options )

                    searchResult.totalResults = result.total
                    println "se.lagrummet.LocalSearchService.commonSearch count=${result.total}"
                    //searchResult.items += result.results.take(limitItems).collect translateSearchResultTimeFromQueryResult
                    searchResult.items += result.results.collect translateSearchResultTimeFromQueryResult
                    searchResult.items.eachWithIndex { item, index -> item.matches = result.highlights[index].content } // update Highlights
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

    def translateSearchResultTimeFromQueryResult = { item ->
        new SearchResultItem(
                title: item.title,
                iri: item.url(),
                issued: item.lastUpdated,
        )
    }

    def pageHighlighter = { highlighter, index, sr ->
        if (!sr.highlights) {
            sr.highlights = []
        }
        sr.highlights[index] = [
                content: highlighter.fragment("content")
        ]
    }


}
