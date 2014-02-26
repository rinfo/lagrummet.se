package se.lagrummet

import grails.converters.XML
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.ParserRegistry
import groovyx.net.http.HttpResponseException
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

    public String getXHtmlContentJavaVersion(String docPath) {
        URL url = new URL(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl+docPath);
        return getXHtmlContentJavaVersion(url.openStream())
    }

    public String getXHtmlContentJavaVersion(InputStream inputStream) {
        byte[] data = readStreamToBuffer(inputStream)
        String header = readHeader(data.length, 150, data)
        String encoding = extractEncodingFromXmlHeader(header)
        String xhtml = new String(data, 0, data.length, encoding)
        if (xhtml.indexOf("html") == -1)
            return "";
        return xhtml
    }

    private byte[] readStreamToBuffer(InputStream stream) {
        byte[] data = new byte[100000]
        int read = stream.read(data)
        byte[] buffer = new byte[read]
        System.arraycopy(data, 0, buffer, 0, read)
        return buffer
    }

    private String extractEncodingFromXmlHeader(String header) {
        try {
            int indexOfEncoding = header.indexOf("encoding");
            int indexOfFirstQuote = header.indexOf("\"", indexOfEncoding + 1);
            int indexOfSecondQuote = header.indexOf("\"", indexOfFirstQuote + 1);
            String encoding = header.substring(indexOfFirstQuote + 1, indexOfSecondQuote)
            encoding
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
        return getXHtmlContentJavaVersion(docPath)
    }

    public String getXHtmlContentHttpBuilderVersion(String docPath) {
        try {
            println "RinfoService.getXHtmlContent "+docPath
            def docContent = ""

            def http = new HTTPBuilder()
            //http.parser.'text/html' = http.parser.'text/plain'
            //http.request(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl, Method.GET, "text/html") {
            //http.parser.'application/xhtml+xml' = http.parser.'text/plain'
            http.request(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl, Method.GET, "application/xhtml+xml") {
                uri.path = docPath
                response.success = {resp, reader ->
                    docContent = reader.text
                    println "RinfoService.getXHtmlContentHttpBuilderVersion *******************************************************"
                    println docContent
                    println "RinfoService.getXHtmlContentHttpBuilderVersion *******************************************************"
                }
            }
            return docContent
        } catch (HttpResponseException e) {
            throw new Exception("Problem med anrop till '"+ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl+docPath+"'",e)
        }
    }

    public String getXHtmlContentHttpBuilderVersion2(String docPath) {
        println "RinfoService.getXHtmlContentHttpBuilderVersion2 "+docPath
        def docContent = ""
        def http = new HTTPBuilder(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl)
        println "RinfoService.getXHtmlContentHttpBuilderVersion2 GET"
        http.get( path: docPath) { resp, xml ->
            println "RinfoService.getXHtmlContentHttpBuilderVersion2 resp.status="+resp.status

            //docContent = xml.text()
            println "RinfoService.getXHtmlContentHttpBuilderVersion2 **************************"
            xml.each{
                docContent += "\n" + it.text()
                println it.text()
            }
            println "RinfoService.getXHtmlContentHttpBuilderVersion2 **************************"

        }
        return docContent
    }

    /*public String getXHtmlContent(String docPath) {
        println "RinfoService.getXHtmlContent "+docPath
        def docContent = ""
        try {
            def http = new HTTPBuilder()
            http.parser.'text/html' = http.parser.'text/plain'
            http.request(ConfigurationHolder.config.lagrummet.rdl.rinfo.baseurl, Method.GET, "text/html") {
                uri.path = docPath
                response.success = {resp, reader ->
                    docContent = readXMLAsString(reader)
                    //def original = reader.text
                    //byte[] utf8bytes = original.getBytes(ParserRegistry.getCharset(resp))
                    //byte[] utf8bytes = original.getBytes("Windows-1252")
                    //docContent = new String(utf8bytes, "UTF-8")
                    //docContent = new String(utf8bytes, "Windows-1252")
                }
            }
        } catch (Exception e) {
            println "RinfoService.getXHtmlContent exception="+e.message
        }
        return docContent
    }
    */

}
