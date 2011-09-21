class UrlMappings {

	static excludes = ["/images/*", "/css/*", "/js/*", "/WEB-INF/*"]
	
	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/admin/user/$action"(controller: 'user')
		"/admin/"(controller: 'admin')
		"/$id**"(controller: 'page', action: 'viewPage')
		"/"(controller: 'page', action: 'startPage')
		"500"(view:'/error')
		"404"(view:'/error')
	}
}
