package se.lagrummet

import groovy.time.Duration
import groovy.time.TimeCategory

import static groovyx.net.http.ContentType.JSON
import groovyx.net.http.*

import javax.servlet.http.HttpServletResponse

class RinfoController {
	
	def rinfoService
	def htmlSanitizerService


    def show = {
		def page = [:]
		page.parent = null

		def docPath = "/" + params.docPath
        println "RinfoController docPath='"+docPath+"'"
        Date timeStart = new Date();
		try {
			def docInfo = rinfoService.getDocumentMetaData(docPath); Date timeDocInfo = new Date();

			def docEntry = rinfoService.getAtomEntry(docPath); Date timeDocEntry = new Date();

            def docContent = null

            println "RinfoController 0.1"

            try {
                docContent = rinfoService.getXHtmlContent(docPath)
            } catch (Exception e) {
                e.printStackTrace()
            }



            try {
                docInfo.each { mediaType  ->
                    println "docInfo.content.each "+mediaType
                    if(mediaType?.@type == "text/html" || mediaType?.@mediaType == "application/xhtml+xml") {
                        docContent = rinfoService.getHtmlContent(docPath)
                    }
                }
            } catch (Exception e) {
                e.printStackTrace()
            }


            println "RinfoController 1"

			docEntry.link.each{ link ->
                println "docEntry.link.each "+link
				if(link?.@type == "text/html") {
					docContent = rinfoService.getHtmlContent(docPath)
				}
                if(link?.@type == "application/xhtml+xml") {
                    println "RinfoController.docEntry.link xhtml"
                    docContent = rinfoService.getXHtmlContent(docPath)
                }
			}

            println "RinfoController 2"

            Date timeLinks = new Date();
			docEntry.content?.each { content ->
                println "docEntry.content.each "+content
				if(content?.@type == "text/html") {
					docContent = rinfoService.getHtmlContent(docPath)
				}
                if(content?.@type == "application/xhtml+xml") {
                    println "RinfoController.docEntry.content xhtml"
                    docContent = rinfoService.getXHtmlContent(docPath)
                }
			}

            println "RinfoController 3"

            if(docContent == null) {
                //docContent = rinfoService.getHtmlContent(docPath)
                println "*********************** docInfo ********************************"
                println docInfo
                println "*********************** docEntry *******************************"
                println docEntry
                println "****************************************************************"
            }

            println "RinfoController 4"

            Date timeContents = new Date();
			if(docContent != null) {
				docContent = htmlSanitizerService.cleanHtml(docContent)
/*
                println "*********************** docContent *****************************"
                println docContent
                println "****************************************************************"
*/
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
            println "render: "+TimeCategory.minus(timeRender, timeStart)
            println "------------ Completed ------------------------------------------"
		} catch (HttpResponseException e) {
			forward(controller: "page", action: "error", params: [errorId: "404"])
		}
	}
}
