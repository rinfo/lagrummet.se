package se.lagrummet

import grails.plugins.springsecurity.Secured

class LegalSourceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def index = {
        redirect(action: "list", params: params)
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
		params.sort = params.sort ?: 'name'
        [legalSourceInstanceList: LegalSource.list(params), legalSourceInstanceTotal: LegalSource.count()]
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def create = {
        def legalSourceInstance = new LegalSource()
        bindData(legalSourceInstance, params)
        return [legalSourceInstance: legalSourceInstance]
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def save = {
        def legalSourceInstance = new LegalSource(params)
        if (legalSourceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'legalSource.label', default: 'LegalSource'), legalSourceInstance.name])}"
            redirect(action: "edit", id: legalSourceInstance.id)
        }
        else {
            render(view: "create", model: [legalSourceInstance: legalSourceInstance])
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def edit = {
        def legalSourceInstance = LegalSource.get(params.id)
        if (!legalSourceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'legalSource.label', default: 'LegalSource'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [legalSourceInstance: legalSourceInstance]
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def update = {
        def legalSourceInstance = LegalSource.get(params.id)
        if (legalSourceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (legalSourceInstance.version > version) {
                    
                    legalSourceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'legalSource.label', default: 'LegalSource')] as Object[], "Another user has updated this LegalSource while you were editing")
                    render(view: "edit", model: [legalSourceInstance: legalSourceInstance])
                    return
                }
            }
            legalSourceInstance.properties = params
            if (!legalSourceInstance.hasErrors() && legalSourceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'legalSource.label', default: 'LegalSource'), legalSourceInstance.name])}"
                redirect(action: "edit", id: legalSourceInstance.id)
            }
            else {
                render(view: "edit", model: [legalSourceInstance: legalSourceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'legalSource.label', default: 'LegalSource'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def delete = {
        def legalSourceInstance = LegalSource.get(params.id)
        if (legalSourceInstance) {
            try {
                legalSourceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'legalSource.label', default: 'LegalSource'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'legalSource.label', default: 'LegalSource'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'legalSource.label', default: 'LegalSource'), params.id])}"
            redirect(action: "list")
        }
    }
}
