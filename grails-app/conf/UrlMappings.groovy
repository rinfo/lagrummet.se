class UrlMappings {

	static excludes = ["/images/*", "/css/*", "/js/*", "/WEB-INF/*"]
	
	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/admin"(controller: 'admin')
		"/"(controller: 'page', action: 'startPage')
		"/$id**"(controller: 'page', action: 'viewPage')
		"500"(view:'/error')
		"404"(view:'/error')
	}
}
