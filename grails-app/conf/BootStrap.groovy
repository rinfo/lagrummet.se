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
			
			new Page(title: 'Kalles sida', permalink: 'kalle', h1: 'heja kalle!', content: "Första försöket", status: "published", publishStart: new Date() - 4).save()
			new Page(title: 'Lagrummet', permalink: 'home', h1: 'Välkommen till lagrummet.se', content: "Första försöket", status: "published", publishStart: new Date() - 4).save()
			new SiteProperties(title: "lagrummet.se", siteTitle: "lagrummet.se", headerNavigation: '<ul><li><a href="">Lyssna</a></li><li><a href="">Other languages</a></li><li><a href="">Webbkarta</a></li><li><a href="">Om lagrummet.se</a></li></ul>', primaryNavigation: '''
			<ul class="rinfo">
				<li class="heading">R&auml;ttsinformation</li>
				<li><a href="">Lagar och f&ouml;rordningar</a></li>
				<li><a href="">Myndigheters F&ouml;reskrifter</a></li>
			</ul>
			<ul class="turnHere">
				<li class="heading">Hit v&auml;nder du dig</li>
				<li><a href="">Myndigheters och kommuners ansvar</a></li>
			</ul>
			<ul class="learnMore">
				<li class="heading">L&auml;r dig mer</li>
				<li><a href="">Om r&auml;ttsinformation</a></li>
			</ul>''', footer: "").save()
		}
    }
	
    def destroy = {
    }
}
