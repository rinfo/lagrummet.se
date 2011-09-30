package se.lagrummet

class Media {

    static constraints = {
		parent(nullable: true)
    }
	
	String title
	String filename
	Page parent
}
