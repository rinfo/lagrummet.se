package se.lagrummet

class PageTreeFilters {
	
	private addPageTree(model) {
		model?.pageTreeList = Page.withCriteria() {
			ne("status", "autoSave")
			isNull("masterRevision")
			order("pageOrder", "asc")
			order("dateCreated", "desc")
		}
	}
	
	def filters = {
			addPageTreeToModel(controller:'(search)', action: '(statistics)') {
				after = { model ->
					addPageTree(model)
				}
			}
			
			addPageTreeToModel(controller:'(page)', action: '(list|quickSearch|create|save|restore|edit|update|delete)') {
				after = { model ->
					addPageTree(model)
				}
			}
			
			addPageTreeToModel(controller:'(admin|siteProperties|user|media|legalSource|synonym)', action: '*') {
				after = { model ->
					addPageTree(model)
				}
			}
	}
}
