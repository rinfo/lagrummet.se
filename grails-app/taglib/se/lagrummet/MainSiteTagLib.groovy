package se.lagrummet
import se.lagrummet.SiteProperties
import se.lagrummet.Page

class MainSiteTagLib {
	
	def breadcrumbs = { attrs, body ->
		out << '<a href="' << resource() << '">Hem</a>'
		if (attrs.parent) {
			out << " > " << '<a href="' << attrs.parent.url() << '">' << attrs.parent.title  << '</a>'
		}
	}
}
