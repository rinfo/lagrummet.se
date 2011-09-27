package se.lagrummet

import grails.plugins.springsecurity.Secured

import javax.servlet.http.HttpServletResponse

class PageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    /*def index = {
        redirect(action: "show", params: params)
    }*/
	
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [pageInstanceList: Page.list(params), pageInstanceTotal: Page.count(), pageTreeList: Page.list()]
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def create = {
        def pageInstance = new Page()
        pageInstance.properties = params
        return [pageInstance: pageInstance]
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def save = {
        def pageInstance = new Page(params)
        if (pageInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'page.label', default: 'Page'), pageInstance.id])}"
            redirect(action: "show", id: pageInstance.id)
        }
        else {
            render(view: "create", model: [pageInstance: pageInstance])
        }
    }

    def show = {
		def url = (params.permalink) ? params.permalink.tokenize("/") : ["home"]
		def permalink = url[url.size()-1]
		
		def page = Page.withCriteria(uniqueResult:true) {
			eq("permalink", permalink)
			eq("status", "published")
			le('publishStart', new Date())
			maxResults(1)
		}

		if(page) {
			if(page.publishStop && page.publishStop.before(new Date())) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND)
			}
			return [page: page, siteProps: SiteProperties.findByTitle("lagrummet.se")] 
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND)
		}
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def edit = {
        def pageInstance = Page.get(params.id)
        if (!pageInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [pageInstance: pageInstance]
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def update = {
        def pageInstance = Page.get(params.id)
        if (pageInstance) {
            if (params.version) {
                def version = Long.valueOf(params.version)
                if (pageInstance.version > version) {
                    
                    pageInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'page.label', default: 'Page')] as Object[], "Another user has updated this Page while you were editing")
                    render(view: "edit", model: [pageInstance: pageInstance])
                    return
                }
            }
			
			def pageBackup = new Page()
			pageBackup.properties = pageInstance.properties
			pageBackup.id = null
			pageBackup.children = null
			pageBackup.status = "draft"
			
            pageInstance.properties = params

            if (!pageInstance.hasErrors() && pageInstance.save(flush:true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'page.label', default: 'Page'), pageInstance.id])}"
				pageBackup.save()
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [pageInstance: pageInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def delete = {
        def pageInstance = Page.get(params.id)
        if (pageInstance) {
            try {
                pageInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
            redirect(action: "list")
        }
    }
}
