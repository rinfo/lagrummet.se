import grails.util.Environment
import se.lagrummet.Page
import se.lagrummet.SecRole
import se.lagrummet.SecUserSecRole
import se.lagrummet.User


class BootStrap {

    def init = { servletContext ->
		
		if(Environment.current == Environment.DEVELOPMENT) {
			new Page(title: 'Kalles sida', permaLink: 'kalle', h1: 'heja kalle!').save()
			new Page(title: 'Lagrummet', permaLink: 'startPage', h1: 'välkommen till lagrummet.se').save()
			
			def user = new User(fullName: 'Jonas', username: 'jonas', password:'password', enabled: true).save(failOnError:true)
			def roleAdmin = new SecRole(authority: 'ROLE_ADMIN').save(failOnError:true)
			def roleEditor = new SecRole(authority: 'ROLE_EDITOR').save(failOnError:true)
			SecUserSecRole.create(user, roleAdmin, true)
		}
    }
	
    def destroy = {
    }
}
