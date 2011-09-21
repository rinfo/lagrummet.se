package se.lagrummet

class Page implements Comparable {
	
	String title
	String h1
	String permalink
	String content
//	User author
	int pageOrder
	
	String status = "draft" // draft, pending, published
	Date dateCreated
	Date lastUpdated
	Date publishStart
	Date publishStop
	
	SortedSet children
	static hasMany = [
		children : Page,
		
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
	
	public int compareTo(Object o) {
		pageOrder.compareTo(o.pageOrder)
	}
	
	def url = {
		def response = (parent) ? (parent.permalink + "/") : ""
		return response + permalink
	}
}
