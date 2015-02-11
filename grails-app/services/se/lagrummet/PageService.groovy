package se.lagrummet

class PageService {

    static transactional = true


	def getSiteMap() {
		def c = Page.createCriteria()
		def now = new Date()
		def results = c {
			eq("status", "published")
			not{ eq("template", "sitemap") }
			le('publishStart', now)
			or {
				ge('publishStop', now)
				isNull('publishStop')
			}
			order("pageOrder", "asc")
		}
		return results.unique{ page1, page2 -> page1.permalink <=> page2.permalink }
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
