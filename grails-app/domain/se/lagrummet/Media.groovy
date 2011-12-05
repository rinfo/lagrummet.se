package se.lagrummet

import java.util.Date;

class Media {

    static constraints = {
		parent(nullable: true)
		title(blank: false)
    }
	
	String title
	String filename
	MediaContent content
	
	static belongsTo = [
		parent: Page
	]
	
	static mapping = {
		sort "title"
	}
	
	Date dateCreated
	Date lastUpdated
	
}
