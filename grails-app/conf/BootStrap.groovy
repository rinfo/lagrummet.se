import grails.util.Environment
import se.lagrummet.Page

class BootStrap {

    def init = { servletContext ->
		
		if(Environment.current == Environment.DEVELOPMENT) {
			new Page(title: 'Kalles sida', permaLink: 'kalle', h1: 'heja kalle!').save()
			new Page(title: 'Lagrummet', permaLink: 'startPage', h1: 'välkommen till lagrummet.se').save()
		}
    }
    def destroy = {
    }
}
