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
	
	def googleAnalytics = { attrs, body ->
		if(attrs.id != "") {
			out << '<script type="text/javascript">' << 'var _gaq=_gaq||[];_gaq.push(["_setAccount","' << attrs.id << '"]);_gaq.push(["_trackPageview"]);(function(){var ga=document.createElement("script");ga.type="text/javascript";ga.async=true;ga.src=("https:"==document.location.protocol?"https://ssl":"http://www")+".google-analytics.com/ga.js";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(ga,s)})();' << '</script>'
		}
	}	
	
}
