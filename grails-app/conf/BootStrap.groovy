import grails.util.Environment
import se.lagrummet.Page
import se.lagrummet.SiteProperties

class BootStrap {

    def init = { servletContext ->
		
		if(Environment.current == Environment.DEVELOPMENT) {
			new Page(title: 'Kalles sida', permaLink: 'kalle', h1: 'heja kalle!', content: "Första försöket").save()
			new Page(title: 'Lagrummet', permaLink: 'startPage', h1: 'välkommen till lagrummet.se', content: "Första försöket").save()
			new SiteProperties(title: "lagrummet.se", headerNav: '', footer: "").save()
		}
    }
    def destroy = {
    }
}
