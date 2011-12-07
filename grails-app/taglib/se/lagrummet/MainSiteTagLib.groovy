package se.lagrummet
import se.lagrummet.SiteProperties
import se.lagrummet.Page

class MainSiteTagLib {
	
	def mobileDeviceWidth = { attrs, body ->
		if (request.getHeader('user-agent') =~ /(?i)(iphone|android)/) {
			out << '<meta name="viewport" content="width=device-width,initial-scale=1" />'
		  }
	}
	
	def breadcrumbs = { attrs, body ->
		out << '<a href="' << resource() << '">Hem</a>'
		if (attrs.parent) {
			out << " > " << '<a href="' << resource() << "/" << attrs.parent.url() << '">' << attrs.parent.title  << '</a>'
		}
	}
	
	def googleAnalytics = { attrs, body ->
		if(attrs.id != "") {
			out << '<script type="text/javascript">' << 'var _gaq=_gaq||[];_gaq.push(["_setAccount","' << attrs.id << '"]);_gaq.push(["_trackPageview"]);(function(){var ga=document.createElement("script");ga.type="text/javascript";ga.async=true;ga.src=("https:"==document.location.protocol?"https://ssl":"http://www")+".google-analytics.com/ga.js";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(ga,s)})();' << '</script>'
		}
	}
	
	def dropdown = { attrs, body ->
		if (attrs.options) {
			out << '<select name="' << attrs.name << '">'
			attrs.options.each {
				if (attrs.value == it.key) {
					out << '<option value="' << it.key << '" selected="selected">' << it.value << '</option>'
				} else {
					out << '<option value="' << it.key << '">' << it.value << '</option>'
				}
			}
			out << '</select>'
		}
		
	}
	
}
