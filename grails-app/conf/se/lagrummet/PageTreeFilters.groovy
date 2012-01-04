package se.lagrummet

class PageTreeFilters {
	
	private addPageTree(model) {
		model?.pageTreeList = Page.findAllByStatusNot("autoSave")
	}
	
	def filters = {
			addPageTreeToModel(controller:'(page)', action: '(list|quickSearch|create|save|restore|edit|update|delete)') {
				after = { model ->
					addPageTree(model)
				}
			}
			
			addPageTreeToModel(controller:'(admin|siteProperties|user|media|legalSource)', action: '*') {
				after = { model ->
					addPageTree(model)
				}
			}
	}
}
