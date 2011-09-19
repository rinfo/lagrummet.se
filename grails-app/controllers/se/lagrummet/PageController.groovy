package se.lagrummet

import javax.servlet.http.HttpServletResponse;

class PageController {
	
	def scaffold = true

	def viewPage = {
		
		if(!params?.id) {
			response.sendError(404)
			return
		}
		
		def page = Page.findByPermaLink(params.id)
		
		if(page) {
			return [page: page] 
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND)
		}
	}
	
	def startPage = {
		def page = Page.findByPermaLink('startPage')
		render view: 'viewPage', model: [page: page]
	}
}
