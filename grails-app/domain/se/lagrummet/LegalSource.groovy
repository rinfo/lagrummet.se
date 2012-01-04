package se.lagrummet

class LegalSource {

	String url
	String name
	String grouping
	
    static constraints = {
		url(url: true, nullable: false)
		name(nullable:false, blank:false)
		grouping(nullable:false, blank:false)
    }
}
