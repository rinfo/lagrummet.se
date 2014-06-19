package se.lagrummet

import java.util.Date;

class Search {

    static constraints = {
		category(nullable: true)
    }
	
	String query
	String category
	
	Date dateCreated
	Date lastUpdated

    static mapping = {
        autoTimestamp: true
    }
}
