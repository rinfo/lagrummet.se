package se.lagrummet

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
class SynonymController {

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [synonymInstanceList: Synonym.list(params)]
    }
	
	def updateList = { UpdateListCommand cmd ->

		cmd.synonyms.each { synonym ->
    		synonym?.save(flush:true)
		}

		redirect(action: "list")
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
				redirect(action: "list", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'synonym.label', default: 'Synonym'), params.id])}"
			redirect(action: "list")
		}
	}
}

class UpdateListCommand {
    List<Synonym> synonyms = [].withLazyDefault {new Synonym()}
}
