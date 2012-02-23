package se.lagrummet

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN', 'ROLE_EDITOR', 'IS_AUTHENTICATED_FULLY'])
class AdminController {

    def index = {
		def c = Search.createCriteria()
		def searches = c {
			gt('dateCreated', new Date() - 30)
		    projections {
		        countDistinct 'id', 'myCount'
		        groupProperty 'query'
		    }
		    order ('myCount', 'desc')
			maxResults(10)
		}
		
		def totalSearches = Search.createCriteria().count {
			gt('dateCreated', new Date() - 30)
		}
		
		[searches: searches, totalSearches: totalSearches, daysOfSearches: 30, numberOfQueries: 10]
	}
}
