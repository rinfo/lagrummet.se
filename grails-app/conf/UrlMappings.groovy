import org.apache.jasper.compiler.Node.ParamsAction;

class UrlMappings {

	static excludes = ["/images/*", "/plugins/*", "/css/*", "/js/*", "/WEB-INF/*"]
	
	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		name page: "/$permalink**" {
			constraints {
				permalink(matches:/.+/, blank:false)
			}
			controller = "page"
			action = {
				params.action = params.a ?: "viewPage"
			}
		}

		"/admin/user/$action"(controller: 'user')
		"/admin/"(controller: 'admin')
		"500"(view:'/error')
		"404"(view:'/error')
		
		
	}
}
