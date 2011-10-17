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
	
	
    public SearchResult plainTextSearch(String query) {
		
		def remoteResult = rdlSearchService.plainTextSearch(query)
		def localResult = localSearchService.plainTextSearch(query)
		
		return remoteResult.mergeWith(localResult)
	}
	
}
