package se.lagrummet

class LegalSource {

	String url
	String name
	String category
	String subCategory
	String description
	String rdlName
	
    static constraints = {
		url(url: true, blank: false)
		name(blank:false)
		category(blank:false)
		subCategory(nullable:true, blank:true)
		description(nullable:true, blank:true, maxSize:1000)
		rdlName(nullable:true, blank:true)
    }
}
