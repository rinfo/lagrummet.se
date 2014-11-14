package se.lagrummet

import grails.test.*
import junit.framework.TestFailure

/**
 * Created with IntelliJ IDEA.
 * User: christian
 * Date: 2/13/14
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
class SearchServiceTests extends GrailsUnitTestCase {

    void testEmptyResultSearch() {

        def localSearchServiceMocker = mockFor(LocalSearchService)
        localSearchServiceMocker.demand.plainTextSearch { query, cat, offset, itemsPerPage -> return new SearchResult() }


        def localRdlSearchService = mockFor(RdlSearchService)
        localRdlSearchService.demand.plainTextSearch { query, cat, offset, itemsPerPage -> return new SearchResult() }
        localRdlSearchService.demand.plainTextLatestConsolidated { query, cat, offset, itemsPerPage -> return new SearchResult() }

        def searchService = new SearchService()

        searchService.localSearchService = localSearchServiceMocker.createMock()
        searchService.rdlSearchService = localRdlSearchService.createMock()
        searchService.grailsApplication = [config:[lagrummet:[onlyLocalSearch: false]]]

        def queries = []

        queries.add("alla")  // The text 'alla' is totally irrelevant for this test

        SearchResult searchResult = searchService.plainTextSearch(queries, null, null, null)

        localSearchServiceMocker.verify()
        localRdlSearchService.verify()

        assertNotNull searchResult
        assertEquals 0, searchResult.getTotalResults()

        def errorMessages = []
        assertEquals errorMessages, searchResult.getErrorMessages()
    }

    void testSearchWithErrorMessage() {
        def localSearchServiceMocker = mockFor(LocalSearchService)
        localSearchServiceMocker.demand.plainTextSearch { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.errorMessages.add("Error message")
            return searchResult
        }


        def localRdlSearchService = mockFor(RdlSearchService)
        localRdlSearchService.demand.plainTextSearch { query, cat, offset, itemsPerPage -> return new SearchResult() }
        localRdlSearchService.demand.plainTextLatestConsolidated { query, cat, offset, itemsPerPage -> return new SearchResult() }

        def searchService = new SearchService()

        searchService.localSearchService = localSearchServiceMocker.createMock()
        searchService.rdlSearchService = localRdlSearchService.createMock()
        searchService.grailsApplication = [config:[lagrummet:[onlyLocalSearch: false]]]

        def queries = []

        queries.add("alla")  // The text 'alla' is totally irrelevant for this test

        SearchResult searchResult = searchService.plainTextSearch(queries, null, null, null)

        localSearchServiceMocker.verify()
        localRdlSearchService.verify()

        assertNotNull searchResult
        assertEquals 0, searchResult.getTotalResults()

        def errorMessages = ["Error message"]
        assertEquals errorMessages, searchResult.getErrorMessages()
    }

    void testSearchTopPriorityHitsAll() {
        final SearchResultItem searchResultItemLocal = new SearchResultItem()
        final SearchResultItem searchResultItemRdlSearch = new SearchResultItem()
        final SearchResultItem searchResultItemRdlConsolidated = new SearchResultItem()

        def localSearchServiceMocker = mockFor(LocalSearchService)
        localSearchServiceMocker.demand.plainTextSearch { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemLocal]
            return searchResult
        }


        def localRdlSearchService = mockFor(RdlSearchService)
        localRdlSearchService.demand.plainTextSearch { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemRdlSearch]
            return searchResult
        }
        localRdlSearchService.demand.plainTextLatestConsolidated { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemRdlConsolidated]
            return searchResult
        }

        def searchService = new SearchService()

        searchService.localSearchService = localSearchServiceMocker.createMock()
        searchService.rdlSearchService = localRdlSearchService.createMock()
        searchService.grailsApplication = [config:[lagrummet:[onlyLocalSearch: false]]]

        def queries = []

        queries.add("alla")  // The text 'alla' is totally irrelevant for this test

        SearchResult searchResult = searchService.plainTextSearch(queries, null, null, null)

        localSearchServiceMocker.verify()
        localRdlSearchService.verify()

        assertNotNull searchResult
        assertEquals 0, searchResult.getTotalResults()


        def errorMessages = []
        assertEquals errorMessages, searchResult.getErrorMessages()

        def topHits = [searchResultItemRdlConsolidated, searchResultItemRdlSearch, searchResultItemLocal]

        assertEquals topHits, searchResult.getTopHits()

    }


    void testSearchTopPriorityHitsLagar() {
        final SearchResultItem searchResultItemLocal = new SearchResultItem()
        final SearchResultItem searchResultItemRdlSearch = new SearchResultItem()
        final SearchResultItem searchResultItemRdlConsolidated = new SearchResultItem()

        def localSearchServiceMocker = mockFor(LocalSearchService)
        localSearchServiceMocker.demand.plainTextSearch { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemLocal]
            return searchResult
        }


        def localRdlSearchService = mockFor(RdlSearchService)
        localRdlSearchService.demand.plainTextSearch { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemRdlSearch]
            return searchResult
        }
        localRdlSearchService.demand.plainTextLatestConsolidated { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemRdlConsolidated]
            return searchResult
        }

        def searchService = new SearchService()

        searchService.localSearchService = localSearchServiceMocker.createMock()
        searchService.rdlSearchService = localRdlSearchService.createMock()
        searchService.grailsApplication = [config:[lagrummet:[onlyLocalSearch: false]]]

        def queries = []

        queries.add("alla")  // The text 'alla' is totally irrelevant for this test

        SearchResult searchResult = searchService.plainTextSearch(queries, Category.LAGAR, null, null)

        localSearchServiceMocker.verify()
        localRdlSearchService.verify()

        assertNotNull searchResult
        assertEquals 0, searchResult.getTotalResults()


        def errorMessages = []
        assertEquals errorMessages, searchResult.getErrorMessages()

        def topHits = [searchResultItemRdlConsolidated]

        assertEquals 1, searchResult.getTopHits().size()
        assertEquals topHits, searchResult.getTopHits()

    }

    void testSearchTopPriorityHitsOvrigt() {
        final SearchResultItem searchResultItemLocal = new SearchResultItem()
        final SearchResultItem searchResultItemRdlSearch = new SearchResultItem()
        final SearchResultItem searchResultItemRdlConsolidated = new SearchResultItem()

        def localSearchServiceMocker = mockFor(LocalSearchService)
        localSearchServiceMocker.demand.plainTextSearch { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemLocal]
            return searchResult
        }


        def localRdlSearchService = mockFor(RdlSearchService)
        localRdlSearchService.demand.plainTextSearch { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemRdlSearch]
            return searchResult
        }
        localRdlSearchService.demand.plainTextLatestConsolidated { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemRdlConsolidated]
            return searchResult
        }

        def searchService = new SearchService()

        searchService.localSearchService = localSearchServiceMocker.createMock()
        searchService.rdlSearchService = localRdlSearchService.createMock()
        searchService.grailsApplication = [config:[lagrummet:[onlyLocalSearch: false]]]

        def queries = []

        queries.add("alla")  // The text 'alla' is totally irrelevant for this test

        SearchResult searchResult = searchService.plainTextSearch(queries, Category.OVRIGT, null, null)

        localSearchServiceMocker.verify()
        localRdlSearchService.verify()

        assertNotNull searchResult
        assertEquals 0, searchResult.getTotalResults()


        def errorMessages = []
        assertEquals errorMessages, searchResult.getErrorMessages()

        def topHits = [searchResultItemLocal]

        assertEquals 1, searchResult.getTopHits().size()
        assertEquals topHits, searchResult.getTopHits()

    }

    void testSearchTopPriorityHitsRemoteNotCat() {
        final SearchResultItem searchResultItemLocal = new SearchResultItem()
        final SearchResultItem searchResultItemRdlSearch = new SearchResultItem()
        final SearchResultItem searchResultItemRdlConsolidated = new SearchResultItem()

        def localSearchServiceMocker = mockFor(LocalSearchService)
        localSearchServiceMocker.demand.plainTextSearch { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemLocal]
            return searchResult
        }


        def localRdlSearchService = mockFor(RdlSearchService)
        localRdlSearchService.demand.plainTextSearch { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemRdlSearch]
            return searchResult
        }
        localRdlSearchService.demand.plainTextLatestConsolidated { query, cat, offset, itemsPerPage ->
            def searchResult = new SearchResult()
            searchResult.topHits = [searchResultItemRdlConsolidated]
            return searchResult
        }

        def searchService = new SearchService()

        searchService.localSearchService = localSearchServiceMocker.createMock()
        searchService.rdlSearchService = localRdlSearchService.createMock()
        searchService.grailsApplication = [config:[lagrummet:[onlyLocalSearch: false]]]

        def queries = []

        queries.add("alla")  // The text 'alla' is totally irrelevant for this test

        SearchResult searchResult = searchService.plainTextSearch(queries, Category.OKAND, null, null)

        localSearchServiceMocker.verify()
        localRdlSearchService.verify()

        assertNotNull searchResult
        assertEquals 0, searchResult.getTotalResults()


        def errorMessages = []
        assertEquals errorMessages, searchResult.getErrorMessages()

        def topHits = [searchResultItemRdlSearch]

        assertEquals 1, searchResult.getTopHits().size()
        assertEquals topHits, searchResult.getTopHits()

    }
}
