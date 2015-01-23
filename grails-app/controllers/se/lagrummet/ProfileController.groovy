package se.lagrummet

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
class ProfileController {

	def springSecurityService
	
	def edit = {
        def userInstance = (params.id) ? User.get(params.id) : User.get(springSecurityService.principal.id)
        //def userInstance = User.get(params.id)
        if (!userInstance) {
			//println "ProfileController.profile true, params="+params
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
			//println "ProfileController.profile false, params="+params
            [userInstance: userInstance]
        }
	}
	
	def updateProfile = {
		//println "ProfileController.updateProfile params="+params
        User userInstanceOld = (params.id) ? User.get(params.id) : User.get(springSecurityService.principal.id)
        //def userInstance = User.get(params.id)
        if (!userInstanceOld) {
			//println "ProfileController.profile true, params="+params
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
			return
        }
		userInstanceOld.department = params.department
		userInstanceOld.email = params.email
		userInstanceOld.fullName = params.fullName
		userInstanceOld.save(flush: true)
		redirect(action: "show")
	}
	
	def show = {
        def userInstance = (params.id) ? User.get(params.id) : User.get(springSecurityService.principal.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		else {
			[userInstance: userInstance]
		}
	}
	
	def credentials = {
		//println "ProfileController.credentials, params="+params
	}

	def updateCredentials = {
		//println "ProfileController.updateCredentials, params="+params
		String encryptedPassword = springSecurityService.encodePassword(params.currentpassword)
		//println "ProfileController.updateCredentials, krypterat lösen = "+encryptedPassword
		
        def userInstance = (params.id) ? User.get(params.id) : User.get(springSecurityService.principal.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "show")
		}

		//println "ProfileController.updateCredentials, tidigare sparat krypterat lösen = "+userInstance.password
		
		if(encryptedPassword.compareTo(userInstance.password) == 0) {
			//println "ProfileController.updateCredentials, användaren anses ha skrivit rätt lösenord"
		}
		else {
			//println "ProfileController.updateCredentials, fel lösenord från användaren."
			flash.message = "${message(code: 'user.password.wrong.label', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "credentials")
			return;
		}
		
		if(params.newpassword.compareTo(params.newrepeatedpassword) == 0) {
			//println "ProfileController.updateCredentials, användaren anses ha skrivit samma nya lösenord"
		}
		else {
			//println "ProfileController.updateCredentials, fel nytt lösenord från användaren. Olika vid repetition."
			flash.message = "${message(code: 'user.password.wrongrepetition.label', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "credentials")
			return;
		}

		//println "ProfileController.updateCredentials, bra skriv i databasen."
		userInstance.password = springSecurityService.encodePassword(params.newpassword)
		
		userInstance.save(flush: true)
		redirect(action: "show")

		
	}
//65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5
//1234567890123456789012345678901234567890123456789012345678901234567890
	/*
ProfileController.updateCredentials, params=[update:Update, newrepeatedpassword:c, currentpassword:a, 
SYNCHRONIZER_TOKEN:dfbff317-309b-46c5-a371-07f8cae4fc1b, SYNCHRONIZER_URI:/lagrummet.se/profile/credentials, 
newpassword:b, action:updateCredentials, controller:profile]
	 */
}
