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
		
		return remoteResult.mergeWith(localResult).mergeWith(remoteLatestConsolidatedResult)
	}
	
	
	
}
