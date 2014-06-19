package se.lagrummet

import java.util.Date;

class Puff {

    static constraints = {
		title(nullable: true)
		description(nullable: true, maxSize: 1024)
		image(nullable: true)
    }
	
	Media image
	String title
	String description
	String link
	
	Date dateCreated
	Date lastUpdated
	
	boolean deleted
	
	static transients = ['deleted']
	
	static belongsTo = [
		parent : Page
	]
	
	def isEmpty() {
		return !image && !title && !description && !link
	}

    static mapping = {
        autoTimestamp: true
    }
}
