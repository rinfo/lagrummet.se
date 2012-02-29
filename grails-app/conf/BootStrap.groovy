import grails.util.Environment
import se.lagrummet.LegalSource
import se.lagrummet.Page
import se.lagrummet.Puff
import se.lagrummet.SecRole
import se.lagrummet.SecUserSecRole
import se.lagrummet.SiteProperties
import se.lagrummet.Synonym
import se.lagrummet.User

class BootStrap {

    def init = { servletContext ->
		
		if(Environment.current == Environment.DEVELOPMENT) {
			
			def user = new User(fullName: 'Test Testsson', username: 'testadmin', password:'testadmin', enabled: true, department:"Domstolsverket").save(failOnError:true)
			def roleAdmin = new SecRole(authority: 'ROLE_ADMIN', name: 'Admin').save(failOnError:true)
			def roleEditor = new SecRole(authority: 'ROLE_EDITOR', name: 'Editor').save(failOnError:true)
			SecUserSecRole.create(user, roleAdmin, true)
			
			def fourDaysAgo = new Date() - 4
			def kalles = new Page(title: 'Kalles sida', author: user, pageOrder: 1, permalink: 'kalle', h1: 'heja kalle!', content: "<p>Första försöket</p>", status: "published", publishStart: fourDaysAgo).save()
			kalles.addToPuffs(new Puff(title:"kalles puff", description:"detta är en sidopuff. Det blir spännande", link:"kalle", parent:kalles))
			kalles.addToPuffs(new Puff(title:"olles puff", description:"detta är också en sidopuff. Det blir spännande med lite mer text. Då kanske det kan bli något fel", link:"kalle", parent:kalles))
			def kallesUndersida = new Page(title: 'Kalles undersida', author: user, pageOrder: 1, parent: kalles, permalink: 'kalle-undersida', h1: 'heja kalle igen!', content: "Andra försöket", status: "published", publishStart: fourDaysAgo).save()
			def kallesUndersida2 = new Page(title: 'Kalles undersida 2', author: user, pageOrder: 0, parent: kalles, permalink: 'kalle-undersida-2', h1: 'heja kalle igen!', content: "Andra försöket", status: "published", publishStart: fourDaysAgo).save()
			def kallesUndersida3 = new Page(title: 'Olles undersida 3', author: user, pageOrder: 0, parent: kalles, permalink: 'olle-undersida-2', h1: 'heja olle igen!', content: "heja olle!", status: "published", publishStart: fourDaysAgo).save()
			
			def home = new Page(title: 'Lagrummet', author: user, template: "frontpage", pageOrder: 0, permalink: 'home', h1: 'Välkommen till lagrummet.se', content: "Första försöket", status: "published", publishStart: fourDaysAgo, metaPage: false).save()
			home.addToPuffs(new Puff(title: "Kalle", description: "Läs allt om Kalle och hans otroliga äventyr. Han går till höger, vänster och ibland till och med bakåt!", link: "kalle", parent: home))
			.addToPuffs(new Puff(title: "Kalle 2", description: "Läs allt om Kalles andra otroliga äventyr.", link: "kalle-undersida", parent: home))
			.addToPuffs(new Puff(title: "Kalle 3", description: "Läs allt om Kalle och hans tredje otroliga äventyr.", link: "kalle", parent: home))
			
			def huvudMeny = new Page(title: "Huvudmeny", author: user, permalink:"huvudmeny", h1:"Huvudmeny", status:"published", publishStart: fourDaysAgo, metaPage: true).save()
			
			def rattsinfo = new Page(title: "Rättsinformation", author: user, permalink:"rattsinformation", h1:"Rättsinformation", status:"published", publishStart:fourDaysAgo, pageOrder:1, metaPage:true, parent: huvudMeny, menuStyle:"huvudmeny1").save()
			def lagar = new Page(title: 'Lagar och förordningar', author: user, template: "legalSource/lagar", pageOrder: 1, permalink: 'lagar-och-forordningar', h1: 'Lagar och förordningar', content: "", status: "published", publishStart: fourDaysAgo, parent:rattsinfo).save()
			def foreskrifter = new Page(title: 'Myndigheters föreskrifter', author: user, template: "legalSource/foreskrifter", pageOrder: 1, permalink: 'myndigheters-foreskrifter', h1: 'Myndigheters föreskrifter', content: "", status: "published", publishStart: fourDaysAgo, parent:rattsinfo).save()
			def forarbeten = new Page(title: 'Förarbeten', author: user, template: "legalSource/forarbeten", pageOrder: 1, permalink: 'forarbeten', h1: 'Förarbeten', content: "", status: "published", publishStart: fourDaysAgo, parent:rattsinfo).save()
			def legalSources = new Page(title: 'Alla rättskällor', author: user, template: "legalSources", pageOrder: 1, permalink: 'samtliga-rattskallor', h1: 'Samtliga rättskällor', content: "", status: "published", publishStart: fourDaysAgo, parent:rattsinfo).save()
			
			def hitvander = new Page(title:"Hit vänder du dig", author: user, permalink:"hit-vander-du-dig", h1:"Hit vänder du dig", status:"published", publishStart:fourDaysAgo, pageOrder:2, metaPage:true, parent:huvudMeny, menuStyle:"huvudmeny2").save()
			
			def lardigmer = new Page(title:"Lär dig mer", author: user, permalink:"lar-dig-mer", h1:"Lär dig mer", status:"published", publishStart:fourDaysAgo, metaPage:true, pageOrder:3, parent:huvudMeny, menuStyle:"huvudmeny3").save()
			
			def toppmeny = new Page(title: "Toppmeny", author: user, permalink:"toppmeny", h1:"Toppmeny", status:"published", publishStart: fourDaysAgo, metaPage:true).save()
			def otherLang = new Page(title: "Other languages", author: user, permalink:"other-languages", h1:"Other languages", status:"published", content:"", publishStart: fourDaysAgo, parent:toppmeny, pageOrder:1).save()
			def sitemap = new Page(title: 'Webbkarta', author: user, template: "sitemap", pageOrder: 2, permalink: 'webbkarta', h1: 'Webbkarta över lagrummet.se', content: "", status: "published", publishStart: fourDaysAgo, parent:toppmeny).save()
			
			def footer = new Page(title:"sidfot", author: user, pageOrder:10, permalink:"sidfot", h1:"sidfot", status:"published", publishStart: fourDaysAgo, metaPage:true).save()
			def omLagrummet = new Page(title:"Om lagrummet.se", author: user, permalink:"om-lagrummet", h1:"Om lagrummet.se", status:"published", publishStart:fourDaysAgo, metaPage:true, parent: footer, pageOrder:1).save()
			def jurInfo = new Page(title:"Juridisk information", author: user, permalink:"juridisk-information", h1:"Juridisk Information", status:"published", publishStart:fourDaysAgo, parent: omLagrummet).save()
			def rattsInfo = new Page(title:"Rättsinfoprojektet", author: user, permalink:"rattsinfoprojektet", h1:"Rättsinfoprojektet", status:"published", publishStart:fourDaysAgo, parent: omLagrummet).save()
			def domv = new Page(title:"Domstolsverket", author: user, permalink:"domstolsverket", h1:"Domstolsverket", status:"published", publishStart:fourDaysAgo, parent: omLagrummet).save()
			
			def omWebbplatsen = new Page(title:"Om webbplatsen", author: user, permalink:"om-webbplatsen", h1:"Om webbplatsen", status:"published", publishStart:fourDaysAgo, metaPage:true, parent: footer, pageOrder:2).save()
			def anvTips = new Page(title:"Användningstips", author: user, permalink:"anvandningstips", h1:"Användningstips", status:"published", publishStart:fourDaysAgo, parent: omWebbplatsen).save()
			def teknisk = new Page(title:"Teknisk infomation", author: user, permalink:"teknisk-information", h1:"Teknisk information", status:"published", publishStart:fourDaysAgo, parent: omWebbplatsen).save()
			def omKakor = new Page(title:"Om kakor (cookies)", author: user, permalink:"om-kakor", h1:"Om kakor", status:"published", publishStart:fourDaysAgo, parent: omWebbplatsen).save()
			
			def kontakta = new Page(title:"Kontakta", author: user, permalink:"kontakta", h1:"kontakta", status:"published", publishStart:fourDaysAgo, metaPage:true, parent: footer, pageOrder:3).save()
			def kontaktaOss = new Page(title:"Kontakta oss", author: user, permalink:"kontakta-oss", h1:"Kontakta oss", status:"published", publishStart:fourDaysAgo, parent: kontakta).save()
			def saSvarar = new Page(title:"Så svarar vi på e-post", author: user, permalink:"sa-svarar-vi-pa-e-post", h1:"Så svarar vi på e-post", status:"published", publishStart:fourDaysAgo, parent: kontakta).save()
			
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

			new Synonym(synonym: "kalle", baseTerm: "olle").save()
			new Synonym(synonym: "olle", baseTerm: "nisse").save()
			
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
				author: user,
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
				author: user,
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
				author: user,
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
				author: user,
				publishStart: new Date() - 4
			).save()
		}
    }
	
    def destroy = {
    }
}
