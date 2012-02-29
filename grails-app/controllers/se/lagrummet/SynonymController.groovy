package se.lagrummet

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
class SynonymController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [synonymInstanceList: Synonym.list(params)]
    }
	
	def updateList = { UpdateListCommand cmd ->

		cmd.synonyms.each { synonym ->
			if(synonym?.deleted) {
				synonym.delete(flush:true)
			} else {
				synonym?.save(flush:true)
			}
		}
		redirect(action: "list")
	}

    
	
	class UpdateListCommand {
		List synonyms = new ArrayList()
		
		def getExpandableSynonymList() {
			LazyList.decorate(synonyms, FactoryUtils.instantiateFactory(Synonym.class))
		}
	}
}
