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
				docInfo = JSONObject.fromObject(json)
				fixForarbeteList(docInfo)
			}
		}
		return docInfo
	}
	
	private void fixForarbeteList(docInfo) {
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

    // Temporary replace sharp version until RDL returns correct encoding
    public String getXHtmlContentJavaVersion(String docPath) {
        URL url = new URL(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl+docPath);
        return getXHtmlContentJavaVersion(url.openStream())
    }

    // se above. Examines XMLs encoding to properly create string
    public String getXHtmlContentJavaVersion(InputStream inputStream) {
        byte[] data = inputStream.getBytes()
        String header = readHeader(data.length, 150, data)
        String encoding = extractEncodingFromXmlHeader(header)
        String xhtml = new String(data, 0, data.length, encoding)
        if (xhtml.indexOf("html") == -1)
            return "";
        return xhtml
    }

    private String extractEncodingFromXmlHeader(String header) {
        try {
            int indexOfEncoding = header.indexOf("encoding");
            int indexOfFirstQuote = header.indexOf("\"", indexOfEncoding + 1);
            int indexOfSecondQuote = header.indexOf("\"", indexOfFirstQuote + 1);
            return header.substring(indexOfFirstQuote + 1, indexOfSecondQuote)
        } catch (Exception e) {
            return "utf-8"
        }
    }

    private String readHeader(int read, int maxHeader, byte[] data) {
        int head = read > maxHeader ? maxHeader : read
        String header = new String(data, 0, head)
        header
    }

    public String getXHtmlContent(String docPath) {
        return getXHtmlContentJavaVersion(docPath) // Temporary redirect until RDL returns correct encoding of content
    }

    // Original version, hans problems when encoding with header that differs from encoding in XML
    public String getXHtmlContentHttpBuilderVersion(String docPath) {
        def docContent = ""
        def http = new HTTPBuilder()
        http.parser.'text/html' = http.parser.'text/plain'
        http.request(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl, Method.GET, "application/xhtml+xml") {
            uri.path = docPath
            response.success = {resp, reader ->
                docContent = reader.text
            }
        }
        return docContent
    }

    // Simplified version, still problems when encoding with header that differs from encoding in XML
    public String getXHtmlContentHttpBuilderVersion2(String docPath) {
        def docContent = ""
        def http = new HTTPBuilder(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl)
        http.get(path: docPath) { resp, xml ->
            xml.each {
                docContent += "\n" + it.text()
            }
        }
        return docContent
    }
}
