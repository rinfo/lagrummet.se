package se.lagrummet

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import net.sf.json.JSONObject

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SearchService {

	static transactional = true

	def rdlSearchService
	def localSearchService
	
    public SearchResult plainTextSearch(List<String> query, Category cat, Integer offset, Integer itemsPerPage) {
		
		def remoteResult = rdlSearchService.plainTextSearch(query, cat, offset, itemsPerPage)
		def remoteLatestConsolidatedResult = rdlSearchService.plainTextLatestConsolidated(query, cat, offset, itemsPerPage)
		def localResult = localSearchService.plainTextSearch(query, cat, offset, itemsPerPage)
		
		def topHits = []
		def consolidatedTophits = remoteLatestConsolidatedResult.topHits
		def rdlTophits = remoteResult.topHits
		def localTopHits = localResult.topHits
		
		if('Lagar'.equals(cat?.toString())) {
			topHits = remoteLatestConsolidatedResult.topHits
			
		} else if ('Ovrigt'.equals(cat?.toString())) {
			topHits = localResult.topHits
		} else if (cat != null) {
			topHits = remoteResult.topHits
		} else {
			topHits.addAll(getTopHits(remoteLatestConsolidatedResult.topHits))
			topHits.addAll(getTopHits(remoteResult.topHits))
			topHits.addAll(getTopHits(localResult.topHits, 1))
		}
		
		def result = remoteLatestConsolidatedResult.mergeWith(remoteResult).mergeWith(localResult)
		result.topHits = topHits
		return result
	}
	
	private getTopHits(topHits, nbOfHits = 2) {
		if(topHits.size() == 0) {
			return []
		}
		def returnIndex = topHits.size() > nbOfHits ? nbOfHits -1 : topHits.size() -1
		def items = topHits[0..returnIndex]
		return items
		
	}
	
	
	
}
