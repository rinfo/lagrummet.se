package se.lagrummet

import java.util.Date;

class Puff {

    static constraints = {
		title(nullable: true)
		description(nullable: true)
		image(nullable: true)
    }
	
	Media image
	String title
	String description
	String link
	
	Date dateCreated
	Date lastUpdated
	
	static belongsTo = [
		parent : Page
	]
}
