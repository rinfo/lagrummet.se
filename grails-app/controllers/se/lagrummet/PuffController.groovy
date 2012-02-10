package se.lagrummet

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
class PuffController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [puffInstanceList: Puff.list(params), puffInstanceTotal: Puff.count()]
    }

    def create = {
        def puffInstance = new Puff()
        puffInstance.properties = params
        return [puffInstance: puffInstance]
    }

    def save = {
        def puffInstance = new Puff(params)
        if (puffInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'puff.label', default: 'Puff'), puffInstance.id])}"
            redirect(action: "show", id: puffInstance.id)
        }
        else {
            render(view: "create", model: [puffInstance: puffInstance])
        }
    }

    def show = {
        def puffInstance = Puff.get(params.id)
        if (!puffInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'puff.label', default: 'Puff'), params.id])}"
            redirect(action: "list")
        }
        else {
            [puffInstance: puffInstance]
        }
    }

    def edit = {
        def puffInstance = Puff.get(params.id)
        if (!puffInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'puff.label', default: 'Puff'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [puffInstance: puffInstance]
        }
    }

    def update = {
        def puffInstance = Puff.get(params.id)
        if (puffInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (puffInstance.version > version) {
                    
                    puffInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'puff.label', default: 'Puff')] as Object[], "Another user has updated this Puff while you were editing")
                    render(view: "edit", model: [puffInstance: puffInstance])
                    return
                }
            }
            puffInstance.properties = params
            if (!puffInstance.hasErrors() && puffInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'puff.label', default: 'Puff'), puffInstance.id])}"
                redirect(action: "show", id: puffInstance.id)
            }
            else {
                render(view: "edit", model: [puffInstance: puffInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'puff.label', default: 'Puff'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def puffInstance = Puff.get(params.id)
        if (puffInstance) {
            try {
                puffInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'puff.label', default: 'Puff'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'puff.label', default: 'Puff'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'puff.label', default: 'Puff'), params.id])}"
            redirect(action: "list")
        }
    }
}
