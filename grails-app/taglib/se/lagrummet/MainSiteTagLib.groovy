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
	
	def lLink = { attrs, body ->
		if (attrs.page && !attrs.page.metaPage) {
			out << '<a href="' << resource() << "/" << attrs.page.url() << '">' << attrs.page.h1 << "</a>"
		} else {
			out << attrs.page.h1
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
	
	def adminMenuItem = {attrs ->
		def pageInstance = Page.get(attrs.pageId)
		def noLinkForMetaPage = (attrs?.noLinkForMetaPage) ? true : false
		def liClass = (!pageInstance.metaPage) ? "" : "metaPage"
		def aSpan = (pageInstance.status == 'draft') ? '<span class="draft">*</span> ' : ""
		
		out << '<li id="p-' << pageInstance.id << '" class="'<< liClass << '">'
		if (noLinkForMetaPage && pageInstance.metaPage) {
			out << aSpan + pageInstance.h1
		} else {
			out << g.link(controller:"page", action:"edit", id: pageInstance.id) { aSpan + pageInstance.h1 }
		}
		
		if (pageInstance?.children?.size()){
			out << "<ul>"
			pageInstance.children.each {it ->
				out << adminMenuItem(pageId:it.id, noLinkForMetaPage: noLinkForMetaPage)
			}
			out << "</ul>"
		}
		out << '</li>'
	}
	
	/**
	 * Creates a html anchor tag that will wrap the body content if a href is provided.
	 * When no href or a blank href is provided, the body will be rendered without a link
	 * @attr href OPTIONAL href property for the anchor tag
	 */
	def linkConditional = { attrs, body ->
		def href = attrs.href
		if(href) {
			out << '<a href="' << href << '">'
		}
		
		out << body()
		
		if(href) {
			out << '</a>'
		}
	}
	
}
