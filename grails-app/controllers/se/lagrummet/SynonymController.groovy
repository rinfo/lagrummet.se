package se.lagrummet

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
class SynonymController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [synonymInstanceList: Synonym.list(params), synonymInstanceTotal: Synonym.count()]
    }

    def create = {
        def synonymInstance = new Synonym()
        synonymInstance.properties = params
        return [synonymInstance: synonymInstance]
    }

    def save = {
        def synonymInstance = new Synonym(params)
        if (synonymInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'synonym.label', default: 'Synonym'), synonymInstance.id])}"
            redirect(action: "edit", id: synonymInstance.id)
        }
        else {
            render(view: "create", model: [synonymInstance: synonymInstance])
        }
    }

    def show = {
        def synonymInstance = Synonym.get(params.id)
        if (!synonymInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'synonym.label', default: 'Synonym'), params.id])}"
            redirect(action: "list")
        }
        else {
            [synonymInstance: synonymInstance]
        }
    }

    def edit = {
        def synonymInstance = Synonym.get(params.id)
        if (!synonymInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'synonym.label', default: 'Synonym'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [synonymInstance: synonymInstance]
        }
    }

    def update = {
        def synonymInstance = Synonym.get(params.id)
        if (synonymInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (synonymInstance.version > version) {
                    
                    synonymInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'synonym.label', default: 'Synonym')] as Object[], "Another user has updated this Synonym while you were editing")
                    render(view: "edit", model: [synonymInstance: synonymInstance])
                    return
                }
            }
            synonymInstance.properties = params
            if (!synonymInstance.hasErrors() && synonymInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'synonym.label', default: 'Synonym'), synonymInstance.id])}"
                redirect(action: "edit", id: synonymInstance.id)
            }
            else {
                render(view: "edit", model: [synonymInstance: synonymInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'synonym.label', default: 'Synonym'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def synonymInstance = Synonym.get(params.id)
        if (synonymInstance) {
            try {
                synonymInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'synonym.label', default: 'Synonym'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'synonym.label', default: 'Synonym'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'synonym.label', default: 'Synonym'), params.id])}"
            redirect(action: "list")
        }
    }
}
