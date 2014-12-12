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
		
		def isTopElement = attrs.get('isTopElement', true)
		if(isTopElement && attrs.page?.title && attrs.page?.permalink != "home") {
			//out << '<a href="' << resource() << '">Startsida</a>'
                        out << '<a href="' << grailsApplication.config.grails.serverURL << '">Startsida</a>'
		}
		if (attrs.page?.parent) {
			out << g.breadcrumbs(page: attrs.page.parent, isTopElement: false)
		}
		if(attrs.page?.title && !attrs.page?.metaPage && attrs.page?.permalink != "home") {
			if(isTopElement) {
				out << " > " << attrs.page.title
			} else {
				//out << " > " << '<a href="' << resource() << "/" << attrs.page.url() << '">' << attrs.page.title  << '</a>'
                                out << " > " << '<a href="' << grailsApplication.config.grails.serverURL << "/" << attrs.page.url() << '">' << attrs.page.title  << '</a>'
			}
		}
	}

    def googleAnalytics = { attrs, body ->
        if(attrs.id != "") {
            out << '<script> (function(i,s,o,g,r,a,m){i[\'GoogleAnalyticsObject\']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o), m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m) })(window,document,\'script\',\'//www.google-analytics.com/analytics.js\',\'ga\'); ga(\'create\', \'' << attrs.id <<'\', \'auto\'); ga(\'send\', \'pageview\'); </script>'
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
	
	def sitemapItem = { attrs ->
		def pageInstance = (Page.get(attrs.pageId).masterRevision) ?: Page.get(attrs.pageId)
		def now = new Date()

		if (pageInstance.metaPage && pageInstance.parent && pageInstance.showInSitemap && pageInstance.isCurrentlyPublished()) {
			out << '<li><h3>' << pageInstance.h1 << "</h3></li>"
		} else if (!pageInstance.metaPage && pageInstance.showInSitemap && pageInstance.isCurrentlyPublished()) {
			out << '<li><a href="' << resource() << "/" << pageInstance.url() << '">' << pageInstance.title  << '</a></li>'
		}
		
		if (pageInstance?.children?.size()){
			pageInstance.children.each {it ->
				if (it.masterRevision == null|| (it.masterRevision.status == "draft" && it.status == "published")) {
					out << sitemapItem(pageId:it.id, currentPageId: attrs.currentPageId)
				}
			}
		}
	}
	
	def adminMenuItem = { attrs ->
		def pageInstance = (Page.get(attrs.pageId).masterRevision) ?: Page.get(attrs.pageId)

		def now = new Date()
		
		def liClass = (!pageInstance.metaPage) ? "" : "metaPage "
		if (attrs.currentPageId == pageInstance.id) liClass += "currentPage "
		if (pageInstance.status == 'draft') liClass += "draft "
		else if (pageInstance.status == 'published' && pageInstance.publishStart <= now && (pageInstance.publishStop == null || pageInstance.publishStop >= now)) liClass += "published "
		else if (pageInstance.status == 'published' && pageInstance.publishStart > now) liClass += "publishLater "
		else if (pageInstance.status == 'published' && pageInstance.publishStop < now) liClass += "wasPublished "
		
		if (pageInstance.autoSaves) {
			pageInstance.autoSaves.each { it ->
				if (it.status == 'published' && it.publishStart <= now && (it.publishStop == null || it.publishStop >= now)) liClass += "published "
			}
		}
		
		out << '<li id="p-' << pageInstance.id << '" class="'<< liClass << '">'
		out << g.link(controller:"page", action:"edit", id: pageInstance.id) { pageInstance.h1 }
		
		if (pageInstance?.children?.size()){
			out << "<ul>"
			pageInstance.children.each {it ->
				if (it.masterRevision == null|| (it.masterRevision.status == "draft" && it.status == "published")) {
					out << adminMenuItem(pageId:it.id, currentPageId: attrs.currentPageId)
				}
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
		def classParam = (attrs.class) ? 'class="' + attrs.class + '"' : ""
		
		if(href) {
			out << '<a href="' << href << '"'+classParam+'>'
		}
		
		out << body()
		
		if(href) {
			out << '</a>'
		}
	}
	

	/**
	 * Removes all empty values in the params map before rendering a link
	 */
	def createLinkParams = { attrs, body ->
		attrs.params?.values()?.removeAll(['', null] as Set)
		out << createLink(attrs, body)
	}

	
}
