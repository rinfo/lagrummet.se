package se.lagrummet


import static groovyx.net.http.ContentType.JSON
import groovyx.net.http.*

import javax.servlet.http.HttpServletResponse

class RinfoController {
	
	def rinfoService

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
			
			render(view:'show', model: [page: page, 
										docInfo: docInfo, 
										content: docContent, 
										docEntry: docEntry])
		} catch (HttpResponseException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, message(code:"error.rinfodocnotfound"))
		}
	}
}
