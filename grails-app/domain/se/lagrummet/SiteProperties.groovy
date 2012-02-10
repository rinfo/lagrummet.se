package se.lagrummet

class SiteProperties {

    static constraints = {
		siteTitle(nullable: true)
		searchCats(nullable: true)
    }
	
	String title
	String siteTitle
	String[] searchCats
}
