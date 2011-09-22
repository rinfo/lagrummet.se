package se.lagrummet

class SiteProperties {

    static constraints = {
		siteTitle(nullable: true)
		footer(nullable: true)
		headerNavigation(nullable: true)
		primaryNavigation(nullable: true)
    }
	
	String title
	String siteTitle
	String footer
	String headerNavigation
	String primaryNavigation
}
