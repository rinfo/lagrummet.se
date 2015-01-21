package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import net.sf.json.JSONObject

class SearchService {

	static transactional = true

    def grailsApplication
	def rdlSearchService
	def localSearchService
	
    public SearchResult plainTextSearch(List<String> query, Category cat, Integer offset, Integer itemsPerPage) {
        if (grailsApplication.config.lagrummet.onlyLocalSearch)
            return localSearchService.plainTextSearch(query, cat, offset, itemsPerPage)
        def result = new SearchResult()
        Benchmark.section("Total search time", log) {
            def remoteResult = rdlSearchService.plainTextSearch(query, cat, offset, itemsPerPage)
            def remoteLatestConsolidatedResult = rdlSearchService.plainTextLatestConsolidated(query, cat, offset, itemsPerPage)
            def localResult = localSearchService.plainTextSearch(query, cat, offset, itemsPerPage)

            def topHits = selectTopHitsDependingOnCategory(cat, remoteLatestConsolidatedResult, localResult, remoteResult)

            result = remoteLatestConsolidatedResult.mergeWith(remoteResult).mergeWith(localResult)

            result.topHits = topHits;
        }
		return result
	}

    private ArrayList selectTopHitsDependingOnCategory(Category cat, remoteLatestConsolidatedResult, localResult, remoteResult) {
        if (cat==null) {
            def topHits = []
            topHits.addAll(reduceHits(remoteLatestConsolidatedResult.topHits))
            topHits.addAll(reduceHits(remoteResult.topHits))
            topHits.addAll(reduceHits(localResult.topHits, 1))
            return topHits
        }

        switch (cat) {
            case Category.LAGAR: return new ArrayList(remoteLatestConsolidatedResult.topHits)
            case Category.OVRIGT: return new ArrayList(localResult.topHits)
            default: return new ArrayList(remoteResult.topHits)
        }
    }

    private reduceHits(topHits, nbOfHits = 2) {
		if(topHits.size() == 0) {
			return []
		}
		def returnIndex = topHits.size() > nbOfHits ? nbOfHits -1 : topHits.size() -1
		def items = topHits[0..returnIndex]
		return items
		
	}
	
	
	
}
