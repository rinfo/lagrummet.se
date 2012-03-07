package se.lagrummet


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
		try {
			def docInfo = rinfoService.getDocumentMetaData(docPath)
	
			def docEntry = rinfoService.getAtomEntry(docPath)
			
			def docContent = null
			docEntry.link.each{ link ->
				if(link?.@type == "text/html") {
					docContent = rinfoService.getHtmlContent(docPath)
				}
			}
			docEntry.content?.each { content -> 
				if(content?.@type == "text/html") {
					docContent = rinfoService.getHtmlContent(docPath)
				}
			}
			if(docContent != null) {
				docContent = htmlSanitizerService.cleanHtml(docContent)
			}
			render(view:'show', model: [page: page, 
										docInfo: docInfo, 
										content: docContent, 
										docEntry: docEntry])
		} catch (HttpResponseException e) {
			forward(controller: "page", action: "error", params: [errorId: "404"])
		}
	}
}
