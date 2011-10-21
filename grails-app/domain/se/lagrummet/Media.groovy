package se.lagrummet

import java.util.Date;

class Media {

    static constraints = {
		parent(nullable: true)
    }
	
	String title
	String filename
	
	static belongsTo = [
		parent: Page
	]
	
	Date dateCreated
	Date lastUpdated
}
