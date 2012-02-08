package se.lagrummet

class PageService {

    static transactional = true


	def getSiteMap() {
		def c = Page.createCriteria()
		def results = c {
			eq("status", "published")
			not{ eq("template", "sitemap") }
			
			order("pageOrder", "asc")
			order("dateCreated", "desc")
			
			
		}
		
		return results
	}
	
	def getPageTree() {
//		Page.findAllByStatusNot("autoSave")
		def c = Page.createCriteria()
		def results = c {
			not{ eq("status", "autoSave") }
			order("pageOrder", "asc")
			order("dateCreated", "desc")
		}
	}
}
