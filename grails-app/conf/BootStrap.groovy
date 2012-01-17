import grails.util.Environment
import se.lagrummet.LegalSource
import se.lagrummet.Page
import se.lagrummet.Puff
import se.lagrummet.SecRole
import se.lagrummet.SecUserSecRole
import se.lagrummet.SiteProperties
import se.lagrummet.User

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
			def legalSources = new Page(title: 'Alla rättskällor', template: "legalSources", pageOrder: 1, permalink: 'samtliga-rattskallor', h1: 'Samtliga rättskällor', content: "", status: "published", publishStart: new Date() - 4).save()
			def forarbeten = new Page(title: 'Förarbeten', template: "legalSource/forarbeten", pageOrder: 1, permalink: 'forarbeten', h1: 'Förarbeten', content: "", status: "published", publishStart: new Date() - 4).save()
			def foreskrifter = new Page(title: 'Myndigheters föreskrifter', template: "legalSource/foreskrifter", pageOrder: 1, permalink: 'myndigheters-foreskrifter', h1: 'Myndigheters döreskrifter', content: "", status: "published", publishStart: new Date() - 4).save()
			def lagar = new Page(title: 'Lagar och förordningar', template: "legalSource/lagar", pageOrder: 1, permalink: 'lagar-och-forordningar', h1: 'Lagar och förordningar', content: "", status: "published", publishStart: new Date() - 4).save()
			
			
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html", name: "Kommittédirektiv", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/2", name: "Kommittédirektiv 2", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/3", name: "Kommittédirektiv 3", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/4", name: "Kommittédirektiv 4", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/5", name: "Kommittédirektiv 5", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/6", name: "Kommittédirektiv 6", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/7", name: "Kommittédirektiv 7", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/8", name: "Kommittédirektiv 8", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/9", name: "Kommittédirektiv 9", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/10", name: "Kommittédirektiv 10", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://62.95.69.15/dir/dir_form2.html/11", name: "Kommittédirektiv 11", category: "Forarbeten", subCategory: "Regeringen").save()
			new LegalSource(url: "http://www.riksdagen.se/webbnav/index.aspx?nid=400", name: "Motioner", category: "Forarbeten", subCategory: "Riksdagen").save()
			new LegalSource(url: "http://www.riksdagen.se/webbnav/index.aspx?nid=400&2", name: "Motioner 2", category: "Forarbeten", subCategory: "Riksdagen").save()
			new LegalSource(url: "http://www.riksdagen.se/webbnav/index.aspx?nid=400&3", name: "Motioner 3", category: "Forarbeten", subCategory: "Riksdagen").save()
			new LegalSource(url: "http://www.riksdagen.se/webbnav/index.aspx?nid=400&4", name: "Motioner 4", category: "Forarbeten", subCategory: "Riksdagen").save()
			new LegalSource(url: "http://www.riksdagen.se/webbnav/index.aspx?nid=400&5", name: "Motioner 5", category: "Forarbeten", subCategory: "Riksdagen").save()
			new LegalSource(url: "http://62.95.69.15/sfs/sfsr_form2.html", name: "Register över Svensk författningssamling (SFS)", category: "Lagar", subCategory: "").save()
			new LegalSource(url: "http://62.95.69.15/sfs/sfsr_form2.html/2", name: "Register över Svensk författningssamling (SFS) 2", category: "Lagar", subCategory: "").save()
			new LegalSource(url: "http://62.95.69.15/sfs/sfsr_form2.html/3", name: "Register över Svensk författningssamling (SFS) 3", category: "Lagar", subCategory: "").save()
			new LegalSource(url:"http://www.lagrummet.se", name:"Lagrummet", category:"Lagar").save()
			new LegalSource(url:"http://www.domstolsverket.se", name:"Domstolsverket", category:"Lagar").save()
			new LegalSource(url:"http://www.mil.se/sv/Om-Forsvarsmakten/Dokument/Lagrum/", name:"Försvarets författningssamling", category:"Foreskrifter").save()
			new LegalSource(url:"http://www.domstol.se/Ladda-ner--bestall/Vagledande-avgoranden/", name:"Domstolar", category:"Rattspraxis").save()
			
			
			new SiteProperties(title: "lagrummet.se", siteTitle: 'lagrummet<span class="hlight">.se</span>',
				headerNavigation: '''
				<li><a href="">Other languages</a></li>
				<li><a href="/webbkarta">Webbkarta</a></li>
				''', footer: '''
			<ul>
			    <li class="heading">Om lagrummet.se</li>
			    <li><a href="">Juridisk information</a></li>
			    <li><a href="">Rättsinformationsprojektet</a></li>
			    <li><a href="">Domstolsverket</a></li>
			</ul>
			<ul>
			    <li class="heading">Om webbplatsen</li>
			    <li><a href="/om-webbplatsen">Om webbplatsen</a></li>
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
				<li><a href="/lagrummet.se/lagar-och-forordningar">Lagar och f&ouml;rordningar</a></li>
				<li><a href="/lagrummet.se/myndigheters-foreskrifter">Myndigheters F&ouml;reskrifter</a></li>
				<li><a href="/lagrummet.se/forarbeten">Förarbeten</a></li>
				<li><a href="/lagrummet.se/samtliga-rattskallor">Alla rättskällor</a></li>
			</ul>
			<ul class="turnHere">
				<li class="heading">Hit v&auml;nder du dig</li>
				<li><a href="">Myndigheters och kommuners ansvar</a></li>
			</ul>
			<ul class="learnMore">
				<li class="heading">L&auml;r dig mer</li>
				<li><a href="">Om r&auml;ttsinformation</a></li>
			</ul>''', searchCats: ["Alla","Lagar","Rattsfall","Propositioner","Utredningar","Foreskrifter","Ovrigt"]).save()
			

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
				searchCats: ["Alla","Lagar","Rattsfall","Propositioner","Utredningar","Foreskrifter","Ovrigt"],
				headerNavigation: '''
				<li><a href="/english">English</a></li>
				<li><a href="/webbkarta">Webbkarta</a></li>
				''',
				footer: '''
				<ul>
					<li class="heading">Om lagrummet.se</li>
					<li><a href="">Juridisk information</a></li>
					<li><a href="">Rättsinformationsprojektet</a></li>
					<li><a href="">Domstolsverket</a></li>
				</ul>
				<ul>
					<li class="heading">Om webbplatsen</li>
					<li><a href="/om-webbplatsen">Om webbplatsen</a></li>
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
				
			def english = Page.findByPermalink('english') ?: new Page(
				title: 'English',
				template: "english",
				pageOrder: 2,
				permalink: 'english',
				h1: 'Information in English',
				content: "",
				status: "published",
				publishStart: new Date() - 4
				).save()
				
			def legalSources = Page.findByPermalink('samtliga-rattskallor') ?: new Page(
				title: 'Alla rättskällor',
				template: "legalSources",
				pageOrder: 3,
				permalink: 'samtliga-rattskallor',
				h1: 'Samtliga rättskällor',
				content: "",
				status: "published",
				publishStart: new Date() - 4
			).save()
		}
    }
	
    def destroy = {
    }
}
