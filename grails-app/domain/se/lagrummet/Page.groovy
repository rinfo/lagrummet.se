package se.lagrummet

class Page {
	
	String title
	String h1
	String permalink
	String content
//	User author
	int pageOrder = 0
	
	String status = "draft" // draft, pending, published, autoSave
	Date dateCreated
	Date lastUpdated
	Date publishStart
	Date publishStop
	
	static hasMany = [
		children : Page
	]

	static belongsTo = [
		parent : Page
	]
	
    static constraints = {
		content(nullable: true)
		parent(nullable: true)
		status(nullable: true)
		publishStart(nullable: true)
		publishStop(nullable: true)
    }
	
	static mapping = {
		sort "pageOrder"
		children sort: "pageOrder"
	}
	
	def url = {
		def response = (parent) ? (parent.permalink + "/") : ""
		return response + permalink
	}
	
	static searchable = {
		spellCheck "include"
		title boost: 2.0
	}
}
