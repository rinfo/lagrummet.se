package se.lagrummet

class Page implements Comparable {
	
	String title
	String h1
	String permaLink
	String content
//	User author
	int pageOrder
	Date dateCreated
	Date lastUpdated
	
	SortedSet children
	static hasMany = [
		children : Page
	]

	static belongsTo = [
		parent : Page
	]
	
    static constraints = {
		content(nullable: true)
		parent(nullable: true)
    }
	
	public int compareTo(Object o) {
		pageOrder.compareTo(o.pageOrder)
	}
	
	def url = {
		def response = (parent) ? (parent.permalink + "/") : ""
		return response + permalink
	}
}
