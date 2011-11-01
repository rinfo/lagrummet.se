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
	
    public SearchResult plainTextSearch(String query, Category cat) {
		
		def remoteResult = rdlSearchService.plainTextSearch(query, cat)
		def localResult = localSearchService.plainTextSearch(query, cat)
		
		return remoteResult.mergeWith(localResult)
	}
	
	public SearchResult plainTextSearchPaged(String query, Category cat, Integer offset, Integer itemsPerPage) {
		def remoteResult = rdlSearchService.plainTextSearchPaged(query, cat, offset, itemsPerPage)
		
		return remoteResult
	}
	
}
