package se.lagrummet

import javax.servlet.http.HttpServletResponse;

class PageController {
	
	def scaffold = true

	def viewPage = {
		
		/*if(!params?.permalink) {
			response.sendError(404)
			return
		}*/

		def url = (params.permalink) ? params.permalink.tokenize("/") : ["home"]
		def permalink = url[url.size()-1]
		
		def page = Page.findByPermalink(permalink)
		
		if(page) {
			return [page: page, siteProps: SiteProperties.findByTitle("lagrummet.se")] 
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND)
		}
	}
}
