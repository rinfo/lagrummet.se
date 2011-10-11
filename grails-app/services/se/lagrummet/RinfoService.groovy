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

    public JSONObject getDocumentMetaData(String docPath) {
		def httpDocInfo = new HTTPBuilder(ConfigurationHolder.config.lagrummet.service.baseurl)
		def docInfo = ""
		httpDocInfo.request(Method.GET, ContentType.JSON) {
			uri.path = docPath + "/data"
			response.success = {resp, json ->
				docInfo = json
			}
		}
		return docInfo
	}
	
	public getAtomEntry(String docPath) {
		def http = new HTTPBuilder(ConfigurationHolder.config.lagrummet.rinfo.baseurl)
		def atomEntry
		http.parser.'application/atom+xml' = http.parser.'text/plain'
		http.request(Method.GET, "application/atom+xml"  ) {
			uri.path = docPath + "/entry"
			response.success = {resp, reader ->
				atomEntry = XML.parse(reader.text)
			}
		}
		return atomEntry
	}
	
	public String getHtmlContent(String docPath) {
		def docContent = ""
		
		def httpDocContent = new HTTPBuilder(ConfigurationHolder.config.lagrummet.rinfo.baseurl)
		httpDocContent.parser.'text/html' = httpDocContent.parser.'text/plain'
		httpDocContent.request(Method.GET, "text/html") {
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
