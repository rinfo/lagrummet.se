import org.apache.jasper.compiler.Node.ParamsAction;

class UrlMappings {

	static excludes = ["/images/*", "/plugins/*", "/css/*", "/js/*", "/WEB-INF/*"]
	
	static mappings = {
		/*"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}*/
		
		"/login/$action"(controller:'login')
		"/logout/$action"(controller:'logout')

				
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
		
		name pageAdmin: "/admin/page/$action?" {
			controller = 'page'
		}
		
		"/admin/"(controller: 'admin')
		"500"(view:'/error')
		"404"(view:'/error')
		
		
	}
}
