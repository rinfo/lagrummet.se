package se.lagrummet

import grails.converters.XML
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.ParserRegistry
import net.sf.json.JSONArray
import net.sf.json.JSONObject

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class RinfoService {

    static transactional = false
	
    public JSONObject getDocumentMetaData(String docPath) {
		def docInfo = ""
		def http = new HTTPBuilder()
		http.request(ConfigurationHolder.config.lagrummet.rdl.service.baseurl, Method.GET, ContentType.JSON) {
			uri.path = docPath + "/data"
			response.success = {resp, json ->
				docInfo = json
				fixForarbeteList(docInfo)
			}
		}
		return docInfo
	}
	
	private void fixForarbeteList(JSONObject docInfo) {
		if(docInfo.forarbete && !docInfo.forarbete.isArray()) {
			def arr = new JSONArray()
			arr.add(docInfo.forarbete)
			
			docInfo.remove('forarbete')
			docInfo.put('forarbete', arr)
		}
	}
	
	public getAtomEntry(String docPath) {
		def atomEntry
		def http = new HTTPBuilder()
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
		def http = new HTTPBuilder()
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
}
