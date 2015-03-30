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

}
