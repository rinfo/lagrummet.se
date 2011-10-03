package se.lagrummet

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN', 'ROLE_EDITOR', 'IS_AUTHENTICATED_FULLY'])
class AdminController {

    def index = {
		[pageTreeList: Page.findAllByStatusNot("autoSave")]
	}
}
