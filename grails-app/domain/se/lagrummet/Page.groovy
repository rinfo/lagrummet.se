package se.lagrummet

class Page {
	
	String title
	String h1
	String permalink
	String content
//	User author
	int pageOrder = 0
	String template = "default" // See config.groovy -> lagrummet.page.templates for possible values
	
	String status = "draft" // draft, pending, published, autoSave
	Date dateCreated
	Date lastUpdated
	Date publishStart
	Date publishStop
	
	static hasMany = [
		children : Page,
		media : Media,
		puffs : Puff,
		autoSaves : Page
	]

	static belongsTo = [
		parent : Page,
		masterRevision : Page
	]
	
	static mappedBy = [
		children : 'parent',
		autoSaves : 'masterRevision'
	]
	
    static constraints = {
		content(nullable: true, maxSize: 2147483647)
		parent(nullable: true)
		status(nullable: true)
		publishStart(nullable: true)
		publishStop(nullable: true)
		masterRevision(nullable: true)
    }
	
	static mapping = {
		sort "pageOrder"
		children sort: "pageOrder"
		puffs sort: "dateCreated", order: "asc"
	}
	
	def url = {
		def response = (parent) ? (parent.permalink + "/") : ""
		return response + permalink
	}
	
	def backup = {
		def pageBackup = new Page()
		pageBackup.properties = this.properties
		pageBackup.id = null
		pageBackup.children = null
		pageBackup.media = null
		pageBackup.puffs = null
		pageBackup.autoSaves = null
		pageBackup.status = "autoSave"
		this.addToAutoSaves(pageBackup).save()
	}
	
	static searchable = {
		spellCheck "include"
		title boost: 2.0
		publishStop nullValue: "NULL"
		content converter: "customHtmlConverter"
	}
	
	public String toString() {
		return title
	}
}
