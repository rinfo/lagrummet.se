import grails.util.Environment
import se.lagrummet.Page
import se.lagrummet.Puff
import se.lagrummet.SecRole
import se.lagrummet.SecUserSecRole
import se.lagrummet.User
import se.lagrummet.SiteProperties

class BootStrap {

    def init = { servletContext ->
		
		if(Environment.current == Environment.DEVELOPMENT) {
			
			def user = new User(fullName: 'Test Testsson', username: 'testadmin', password:'testadmin', enabled: true, department:"Domstolsverket").save(failOnError:true)
			def roleAdmin = new SecRole(authority: 'ROLE_ADMIN', name: 'Admin').save(failOnError:true)
			def roleEditor = new SecRole(authority: 'ROLE_EDITOR', name: 'Editor').save(failOnError:true)
			SecUserSecRole.create(user, roleAdmin, true)
			
			def kalles = new Page(title: 'Kalles sida', pageOrder: 1, permalink: 'kalle', h1: 'heja kalle!', content: "<p>Första försöket</p>", status: "published", publishStart: new Date() - 4).save()
			def kallesUndersida = new Page(title: 'Kalles undersida', pageOrder: 1, parent: kalles, permalink: 'kalle-undersida', h1: 'heja kalle igen!', content: "Andra försöket", status: "published", publishStart: new Date() - 4).save()
			def kallesUndersida2 = new Page(title: 'Kalles undersida 2', pageOrder: 0, parent: kalles, permalink: 'kalle-undersida-2', h1: 'heja kalle igen!', content: "Andra försöket", status: "published", publishStart: new Date() - 4).save()
			
			def home = new Page(title: 'Lagrummet', template: "frontpage", pageOrder: 0, permalink: 'home', h1: 'Välkommen till lagrummet.se', content: "Första försöket", status: "published", publishStart: new Date() - 4).save()
			home.addToPuffs(new Puff(title: "Kalle", description: "Läs allt om Kalle och hans otroliga äventyr. Han går till höger, vänster och ibland till och med bakåt!", link: "kalle", parent: home))
			.addToPuffs(new Puff(title: "Kalle 2", description: "Läs allt om Kalles andra otroliga äventyr.", link: "kalle-undersida", parent: home))
			.addToPuffs(new Puff(title: "Kalle 3", description: "Läs allt om Kalle och hans tredje otroliga äventyr.", link: "kalle", parent: home))
			def sitemap = new Page(title: 'Webbkarta', template: "sitemap", pageOrder: 1, permalink: 'webbkarta', h1: 'Webbkarta över lagrummet.se', content: "", status: "published", publishStart: new Date() - 4).save()
			
			new SiteProperties(title: "lagrummet.se", siteTitle: 'lagrummet<span class="hlight">.se</span>',
				headerNavigation: '''
				<li><a href="">Other languages</a></li>
				<li><a href="/om-lagrummet-se">Om lagrummet.se</a></li>
				<li><a href="/om-webbplatsen">Om webbplatsen</a></li>
				<li><a href="/webbkarta">Webbkarta</a></li>
				''', siteFooter: '''
			<ul>
			    <li class="heading">Om lagrummet.se</li>
			    <li><a href="">Juridisk information</a></li>
			    <li><a href="">Rättsinformationsprojektet</a></li>
			    <li><a href="">Domstolsverket</a></li>
			</ul>
			<ul>
			    <li class="heading">Om webbplatsen</li>
			    <li><a href="">Användningstips</a></li>
			    <li><a href="">Länkar du hittar på lagrummet.se</a></li>
			    <li><a href="">Teknisk information</a></li>
			    <li><a href="">Om kakor (cookies)</a></li>
			</ul>
			<ul class="contact">
			    <li class="heading">Kontakta</li>
			    <li><a href="">Kontakta oss</a></li>
			    <li><a href="">Så svarar vi på e-post</a></li>
			</ul>''', primaryNavigation: '''
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
			</ul>''', searchCats: ["Alla","Lagar","Foreskrifter","Rattsfall","Propositioner","Utredningar","Ovrigt"],
			footer: "").save()
		} else {
			def user = User.findByUsername("admin") ?: new User(
				fullName: 'System Admin',
				username: 'admin',
				password:'funnitssedan1975',
				enabled: true,
				department:"Domstolsverket"
				).save(failOnError:true)
			
			def roleAdmin = SecRole.findByName("Admin") ?: new SecRole(authority: 'ROLE_ADMIN', name: 'Admin').save(failOnError:true)
			def roleEditor = SecRole.findByName("Editor") ?: new SecRole(authority: 'ROLE_EDITOR', name: 'Editor').save(failOnError:true)
			SecUserSecRole.create(user, roleAdmin, true)
		
			def sProps = SiteProperties.findByTitle('lagrummet.se') ?: new SiteProperties(
				title: "lagrummet.se",
				siteTitle: 'lagrummet<span class="hlight">.se</span>',
				searchCats: ["Alla","Lagar","Foreskrifter","Rattsfall","Propositioner","Utredningar","Ovrigt"],
				headerNavigation: '''
				<li><a href="">Other languages</a></li>
				<li><a href="/om-lagrummet-se">Om lagrummet.se</a></li>
				<li><a href="/om-webbplatsen">Om webbplatsen</a></li>
				<li><a href="/webbkarta">Webbkarta</a></li>
				''',
				siteFooter: '''
				<ul>
					<li class="heading">Om lagrummet.se</li>
					<li><a href="">Juridisk information</a></li>
					<li><a href="">Rättsinformationsprojektet</a></li>
					<li><a href="">Domstolsverket</a></li>
				</ul>
				<ul>
					<li class="heading">Om webbplatsen</li>
					<li><a href="">Användningstips</a></li>
					<li><a href="">Länkar du hittar på lagrummet.se</a></li>
					<li><a href="">Teknisk information</a></li>
					<li><a href="">Om kakor (cookies)</a></li>
				</ul>
				<ul class="contact">
					<li class="heading">Kontakta</li>
					<li><a href="">Kontakta oss</a></li>
					<li><a href="">Så svarar vi på e-post</a></li>
				</ul>''',
				primaryNavigation: '''
				<ul class="rinfo">
					<li class="heading">Rättsinformation</li>
				</ul>
				<ul class="turnHere">
					<li class="heading">Hit vänder du dig</li>
				</ul>
				<ul class="learnMore">
					<li class="heading">Lär dig mer</li>
				</ul>
				''').save()
				
			def home = Page.findByPermalink('home') ?: new Page(
				title: 'Lagrummet',
				template: "frontpage",
				pageOrder: 0,
				permalink: 'home',
				h1: 'Välkommen till lagrummet.se',
				content: "Första försöket",
				status: "published", publishStart: new Date() - 4
				).save()
			if (home.puffs?.size() < 3) {
				home.addToPuffs(new Puff(title: "Puff 1", description: "", link: "home", parent: home))
				.addToPuffs(new Puff(title: "Puff 2", description: "", link: "home", parent: home))
				.addToPuffs(new Puff(title: "Puff 3", description: "", link: "home", parent: home))
			}
			
				
			def sitemap = Page.findByPermalink('webbkarta') ?: new Page(
				title: 'Webbkarta',
				template: "sitemap",
				pageOrder: 1,
				permalink: 'webbkarta',
				h1: 'Webbkarta över lagrummet.se',
				content: "",
				status: "published",
				publishStart: new Date() - 4
				).save()
		}
    }
	
    def destroy = {
    }
}
