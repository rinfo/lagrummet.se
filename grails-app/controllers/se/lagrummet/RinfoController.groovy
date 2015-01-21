package se.lagrummet

import static groovyx.net.http.ContentType.JSON
import groovyx.net.http.*

import javax.servlet.http.HttpServletResponse

class RinfoController {
	
	def rinfoService
	def htmlSanitizerService
    def grailsApplication

    def show = {
        if (grailsApplication.config.lagrummet.onlyLocalSearch) {
            forward(controller: "page", action: "error", params: [errorId: "404"])
            return
        }

            def page = [:]
		page.parent = null

		def docPath = "/" + params.docPath
        Benchmark.sectionByIntermediate("Show page ${docPath}", log) { intermediate ->
            try {

                def docInfo = rinfoService.getDocumentMetaData(docPath);
                intermediate.log("Get document metadata")

                def docEntry = rinfoService.getAtomEntry(docPath);
                intermediate.log("Get atom entry")

                def docContent = null

                docEntry.link.each{ link ->
                    if(link?.@type=="text/html") {
                        docContent = rinfoService.getHtmlContent(docPath)
                    }
                    if(link?.@type=="application/xhtml+xml") {
                        docContent = rinfoService.getXHtmlContent(docPath)
                    }
                }
                intermediate.log("Get html content")

                docEntry.content?.each { content ->
                    if(content?.@type=="text/html") {
                        docContent = rinfoService.getHtmlContent(docPath)
                    }
                    if(content?.@type=="application/xhtml+xml") {
                        //docContent = rinfoService.getXHtmlContent(docPath)
                        docContent = rinfoService.getXHtmlContent(content.@src.text())
                    }
                }
                intermediate.log("Get html content - doc path")

                if(docContent != null) {
                    docContent = htmlSanitizerService.cleanHtml(docContent)
                }
                intermediate.log("Run html sanitizer")

                render(view:'show', model: [page: page,
                                            docInfo: docInfo,
                                            content: docContent,
                                            docEntry: docEntry])
                intermediate.log("Render")
            } catch (HttpResponseException e) {
                log.error("Failed to open "+docPath, e)
                forward(controller: "page", action: "error", params: [errorId: "404"])
            } catch (SocketTimeoutException e) {
                log.error("Failed to open "+docPath, e)
                forward(controller: "page", action: "error", params: [errorId: "500"])
            } catch (UnknownHostException e) {
                log.error("Failed to open "+docPath, e)
                forward(controller: "page", action: "error", params: [errorId: "500"])
            }
        }
	}
}

