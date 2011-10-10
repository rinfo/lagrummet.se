package se.lagrummet


import groovyx.net.http.*
import static groovyx.net.http.ContentType.JSON

class RinfoController {
	
	def rinfoService

    def show = {
		def page = [:]
		page.parent = null

		def docPath = "/" + params.docPath

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
	}
}
