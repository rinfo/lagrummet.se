package se.lagrummet

class SiteProperties {

    static constraints = {
		siteTitle(nullable: true)
		footer(nullable: true, maxSize: 2147483647)
		headerNavigation(nullable: true, maxSize: 2147483647)
		primaryNavigation(nullable: true, maxSize:2147483647)
    }
	
	String title
	String siteTitle
	String footer
	String headerNavigation
	String primaryNavigation
}
