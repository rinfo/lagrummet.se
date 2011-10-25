package se.lagrummet

import java.util.Date;

class Media {

    static constraints = {
		parent(nullable: true)
    }
	
	String title
	String filename
	
//	Page parent
	
	static belongsTo = [
		parent: Page
	]
	
	static mapping = {
		sort "title"
	}
	
	Date dateCreated
	Date lastUpdated
	
	def beforeDelete() {
		def file = new File("web-app/" + filename);
		file.delete();
	}
}
