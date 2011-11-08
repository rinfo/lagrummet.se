package se.lagrummet

class SitePropertiesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "edit", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [sitePropertiesInstanceList: SiteProperties.list(params), sitePropertiesInstanceTotal: SiteProperties.count()]
    }

    def create = {
        def sitePropertiesInstance = new SiteProperties()
        sitePropertiesInstance.properties = params
        return [sitePropertiesInstance: sitePropertiesInstance]
    }

    def save = {
        def sitePropertiesInstance = new SiteProperties(params)
        if (sitePropertiesInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'siteProperties.label', default: 'SiteProperties'), sitePropertiesInstance.id])}"
            redirect(action: "show", id: sitePropertiesInstance.id)
        }
        else {
            render(view: "create", model: [sitePropertiesInstance: sitePropertiesInstance])
        }
    }

    def show = {
        def sitePropertiesInstance = SiteProperties.get(params.id)
        if (!sitePropertiesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'siteProperties.label', default: 'SiteProperties'), params.id])}"
            redirect(action: "list")
        }
        else {
            [sitePropertiesInstance: sitePropertiesInstance]
        }
    }

    def edit = {
        def sitePropertiesInstance = SiteProperties.findByTitle("lagrummet.se")
        if (!sitePropertiesInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'siteProperties.label', default: 'SiteProperties'), params.id])}"
            redirect(controller: "admin")
        }
        else {
            return [sitePropertiesInstance: sitePropertiesInstance]
        }
    }

    def update = {
        def sitePropertiesInstance = SiteProperties.get(params.id)
        if (sitePropertiesInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (sitePropertiesInstance.version > version) {
                    
                    sitePropertiesInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'siteProperties.label', default: 'SiteProperties')] as Object[], "Another user has updated this SiteProperties while you were editing")
                    render(view: "edit", model: [sitePropertiesInstance: sitePropertiesInstance])
                    return
                }
            }
			
            sitePropertiesInstance.properties = params
            if (!sitePropertiesInstance.hasErrors() && sitePropertiesInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'siteProperties.label', default: 'SiteProperties'), sitePropertiesInstance.id])}"
                redirect(action: "edit", id: sitePropertiesInstance.id)
            }
            else {
                render(view: "edit", model: [sitePropertiesInstance: sitePropertiesInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'siteProperties.label', default: 'SiteProperties'), params.id])}"
            redirect(controller: "admin")
        }
    }

    def delete = {
        def sitePropertiesInstance = SiteProperties.get(params.id)
        if (sitePropertiesInstance) {
            try {
                sitePropertiesInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'siteProperties.label', default: 'SiteProperties'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'siteProperties.label', default: 'SiteProperties'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'siteProperties.label', default: 'SiteProperties'), params.id])}"
            redirect(action: "list")
        }
    }
}
