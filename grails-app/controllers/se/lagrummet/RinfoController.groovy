package se.lagrummet

import groovy.time.TimeCategory

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

        Date timeStart = new Date();

		def docPath = "/" + params.docPath
		try {

			def docInfo = rinfoService.getDocumentMetaData(docPath); Date timeDocInfo = new Date();

			def docEntry = rinfoService.getAtomEntry(docPath); Date timeDocEntry = new Date();

            def docContent = null

			docEntry.link.each{ link ->
                println "docEntry.link.each "+link
				if(link?.@type=="text/html") {
					docContent = rinfoService.getHtmlContent(docPath)
				}
                if(link?.@type=="application/xhtml+xml") {
                    docContent = rinfoService.getXHtmlContent(docPath)
                }
			}

            Date timeLinks = new Date();
			docEntry.content?.each { content ->
				if(content?.@type=="text/html") {
					docContent = rinfoService.getHtmlContent(docPath)
				}
                if(content?.@type=="application/xhtml+xml") {
                    //docContent = rinfoService.getXHtmlContent(docPath)
                    docContent = rinfoService.getXHtmlContent(content.@src.text())
                }
			}

            Date timeContents = new Date();
			if(docContent != null) {
				docContent = htmlSanitizerService.cleanHtml(docContent)
			}
            Date timeSanitizer = new Date();

			render(view:'show', model: [page: page,
										docInfo: docInfo, 
										content: docContent, 
										docEntry: docEntry])
            Date timeRender = new Date();

            println "------------ Preformance eval of "+docPath+" ------------------------"
            println "rinfoService.getDocumentMetaData(docPath): "+TimeCategory.minus(timeDocInfo, timeStart)
            println "rinfoService.getAtomEntry(docPath): "+TimeCategory.minus(timeDocEntry, timeStart)
            println "rinfoService.getHtmlContent(docPath): "+TimeCategory.minus(timeLinks, timeStart)
            println "rinfoService.rinfoService.getHtmlContent(docPath): "+TimeCategory.minus(timeContents, timeStart)
            println "rinfoService.rinfoService.getHtmlContent(Sanitizer): "+TimeCategory.minus(timeSanitizer, timeStart)
            println "render: "+TimeCategory.minus(timeRender, timeStart)
            println "------------ Completed ------------------------------------------"
		} catch (HttpResponseException e) {
			forward(controller: "page", action: "error", params: [errorId: "404"])
		}
	}
}
