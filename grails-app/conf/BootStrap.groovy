import grails.util.Environment
import se.lagrummet.Page
import se.lagrummet.SecRole
import se.lagrummet.SecUserSecRole
import se.lagrummet.User
import se.lagrummet.SiteProperties

class BootStrap {

    def init = { servletContext ->
		
		if(Environment.current == Environment.DEVELOPMENT) {
			
			def user = new User(fullName: 'Test Testsson', username: 'test@testsson.com', password:'password', enabled: true).save(failOnError:true)
			def roleAdmin = new SecRole(authority: 'ROLE_ADMIN', name: 'Admin').save(failOnError:true)
			def roleEditor = new SecRole(authority: 'ROLE_EDITOR', name: 'Editor').save(failOnError:true)
			SecUserSecRole.create(user, roleAdmin, true)
			
			new Page(title: 'Kalles sida', permaLink: 'kalle', h1: 'heja kalle!', content: "Första försöket").save()
			new Page(title: 'Lagrummet', permaLink: 'startPage', h1: 'välkommen till lagrummet.se', content: "Första försöket").save()
			new SiteProperties(title: "lagrummet.se", headerNav: '', footer: "").save()
		}
    }
	
    def destroy = {
    }
}
