package se.lagrummet

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
class ProfileController {

	def springSecurityService
	
	def edit = {
        def userInstance = User.get(springSecurityService.principal?.id)

        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userInstance: userInstance]
        }
	}
	
	def updateProfile = {
        User userInstance = User.get(springSecurityService.principal?.id)

		if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
			return
        }
		userInstance.department = params.department
		userInstance.email = params.email
		userInstance.fullName = params.fullName
		userInstance.save(flush: true)
		redirect(action: "show")
	}
	
	def show = {
        def userInstance = User.get(springSecurityService.principal?.id)
		
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		else {
			[userInstance: userInstance]
		}
	}
	
	def credentials = {
	}

	def updateCredentials = {
		String encryptedPassword = springSecurityService.encodePassword(params.currentpassword)
		
        def userInstance = User.get(springSecurityService.principal?.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "show")
		}

		if(encryptedPassword == userInstance.password) {
		}
		else {
			flash.message = "${message(code: 'user.password.wrong.label', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "credentials")
			return;
		}
		
		if(params.newpassword == params.newrepeatedpassword) {
		}
		else {
			flash.message = "${message(code: 'user.password.wrongrepetition.label', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "credentials")
			return;
		}

		userInstance.password = springSecurityService.encodePassword(params.newpassword)
		
		userInstance.save(flush: true)
		redirect(action: "show")
	}

}
