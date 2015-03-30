package se.lagrummet

class SearchResultItem {

    def grailsApplication

    String describedBy
	String identifier
	String iri
	String issued
	String matches
	String title
	String type
	String ikrafttradandedatum
	String malnummer

    String text

    String iriWashed() {
        iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)
    }
}
