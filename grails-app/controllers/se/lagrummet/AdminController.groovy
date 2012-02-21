package se.lagrummet

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN', 'ROLE_EDITOR', 'IS_AUTHENTICATED_FULLY'])
class AdminController {

    def index = {
		def daysOfSearches = params.daysOfSearches ? params.daysOfSearches.toInteger() : 30
		def numberOfQueries = params.numberOfQueries ? params.numberOfQueries.toInteger() : 10
		def c = Search.createCriteria()
		def searches = c {
			gt('dateCreated', new Date() - daysOfSearches)
		    projections {
		        countDistinct 'id', 'myCount'
		        groupProperty 'query'
		    }
		    order ('myCount', 'desc')
			maxResults(10)
		}
		
		def totalSearches = Search.createCriteria().count {
			gt('dateCreated', new Date() - daysOfSearches)
		}
		
		[searches: searches, totalSearches: totalSearches, daysOfSearches: daysOfSearches, numberOfQueries: numberOfQueries]
	}
}
