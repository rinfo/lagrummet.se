package se.lagrummet

import grails.converters.XML
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.ParserRegistry
import net.sf.json.JSONObject

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class RinfoService {

    static transactional = false
	
	def http = new HTTPBuilder()

    public JSONObject getDocumentMetaData(String docPath) {
		def docInfo = ""
		http.request(ConfigurationHolder.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) {
			uri.path = docPath + "/data"
			response.success = {resp, json ->
				docInfo = json
			}
		}
		return docInfo
	}
	
	public getAtomEntry(String docPath) {
		def atomEntry
		http.parser.'application/atom+xml' = http.parser.'text/plain'
		http.request(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl, Method.GET, "application/atom+xml"  ) {
			uri.path = docPath + "/entry"
			response.success = {resp, reader ->
				atomEntry = XML.parse(reader.text)
			}
		}
		return atomEntry
	}
	
	public String getHtmlContent(String docPath) {
		def docContent = ""
		http.parser.'text/html' = http.parser.'text/plain'
		http.request(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl, Method.GET, "text/html") {
			uri.path = docPath
			response.success = {resp, reader ->
				def original = reader.text
				byte[] utf8bytes = original.getBytes(ParserRegistry.getCharset(resp))
				docContent = new String(utf8bytes, "UTF-8")
			}
		}
		return docContent
	}
	
	public JSONObject plainTextSearch(String query) {
		def searchResult
		http.request(ConfigurationHolder.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) {
			uri.path = "/-/publ"
			uri.query = [q: query]
			response.success = {resp, json ->
				searchResult = json
			}
		}
		orderSearchResultByType(searchResult)
		return searchResult
	}
	
	public void orderSearchResultByType(JSONObject searchResult) {
		//Lagar, Rattsfall, Propositioner, Utredningar, Ovrigt
		def typeMap = [:]
		searchResult.items.each{ item ->
			def type = item.type
			def category = getCategoryForType(type)
			if(!typeMap[(category)]) {
				typeMap[(category)] = []
			}
			typeMap[(category)].add(item)
		}
		searchResult.remove('items')
		searchResult.put('items', typeMap)
	}
	
	public String getCategoryForType(String type) {
		def typeToCategory = [	'Rattsfallsreferat' : 'Rattsfall',
								'KonsolideradGrundforfattning' : 'Lagar', 
								'Proposition' : 'Propositioner', 
								'Utredningsbetankande' : 'Utredningar']
		def unknown = 'Okand'
		return typeToCategory[(type)] ?: unknown
		
	}
}
