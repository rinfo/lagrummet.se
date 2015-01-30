package se.lagrummet

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
class ProfileController {

	def springSecurityService
	
	def edit = {
        def userInstance = User.get(springSecurityService.principal?.id)

        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User')])
            redirect(action: "list")
        }
        else {
            [userInstance: userInstance]
        }
	}
	
	def updateProfile = {
		
		withForm {
	        User userInstance = User.get(springSecurityService.principal?.id)
	
			if (!userInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User')])
	            redirect(action: "list")
				return
	        }
			userInstance.department = params.department
			userInstance.email = params.email
			userInstance.fullName = params.fullName
			if(!userInstance.save(flush: true)) {
				flash.message = message(code: 'error.persistence.form.write')
				redirect(action: "credentials")
				return;
			}
			redirect(action: "show")
			
		}.invalidToken {
			response.status = 405
		}
		
	}
	
	def show = {
        def userInstance = User.get(springSecurityService.principal?.id)
		
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User')])
			redirect(action: "list")
		}
		else {
			[userInstance: userInstance]
		}
	}
	
	def credentials = {
	}

	def updateCredentials = {
		withForm {
			String encryptedPassword = springSecurityService.encodePassword(params.currentpassword)
			
	        def userInstance = User.get(springSecurityService.principal?.id)
			if (!userInstance) {
				flash.message = message(code: 'default.not.found.message')
				redirect(action: "show")
				return
			}
	
			if(encryptedPassword != userInstance.password) {
				flash.message = message(code: 'user.password.wrong.label')
				redirect(action: "credentials")
				return
			}
			
			if(params.newpassword != params.newrepeatedpassword) {
				flash.message = message(code: 'user.password.wrongrepetition.label')
				redirect(action: "credentials")
				return
			}
	
			userInstance.password = springSecurityService.encodePassword(params.newpassword)
			
			if(!userInstance.save(flush: true)) {
				flash.message = message(code: 'error.persistence.form.write')
				redirect(action: "credentials")
				return;
			}
			redirect(action: "show")
			
		}.invalidToken {
			response.status = 405
		}
		
	}

}
