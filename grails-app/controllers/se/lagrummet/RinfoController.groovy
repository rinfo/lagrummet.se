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
		
		def docContent
		if(docEntry?.link[1]?.@type == "text/html") {
			docContent = rinfoService.getHtmlContent(docPath)
		}
		println docEntry.link.@href
		
		render(view:'show', model: [page: page, docPath: params.docPath, docInfo: docInfo, content: docContent, docEntry: docEntry])
	}
}
