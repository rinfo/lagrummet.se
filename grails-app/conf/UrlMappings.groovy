import org.apache.jasper.compiler.Node.ParamsAction;

class UrlMappings {

	static excludes = ["/images/*", "/plugins/*", "/css/*", "/js/*", "/uploads/*", "/WEB-INF/*"]
	
	static mappings = {
		"/login/$action"(controller:'login')
		"/logout/$action"(controller:'logout')
		"/sitemap"(controller: 'page', action: "xmlSitemap")

		name extendedSearch: "/search/ext/" {
			controller = "search"
			action = "ext"
		}
		
		name search: "/search/" {
			controller = "search"
			action = "index"
		}
		
		"/search/$action"(controller:'search')
		
		name rinfo: "/rinfo/$docPath**" {
			controller = "rinfo"
			action = "show"
		}	

		"/files/$filename**" {
			controller = "media"
			action = "viewMediaContent"
		}
		
		name page: "/$permalink**" {
			constraints {
				permalink(matches:/.+/, blank:false)
			}
			controller = "page"
			action = {
				params.action = params.a ?: "show"
			}
		}
		
		"/"(controller: 'page', action: "show")

		"/admin/user/$action"(controller: 'user')
		
		"/admin/site/$action"(controller: 'siteProperties')
		
		"/admin/media/$action"(controller: 'media')
		
		"/admin/legalSource/$action"(controller: 'legalSource')
		
		name pageAdmin: "/admin/page/$action?" {
			controller = 'page'
		}
		
		"/admin/"(controller: 'admin')
		
		"500"(view:'/error')
		"404"(view:'/error')
		
		
	}
}
