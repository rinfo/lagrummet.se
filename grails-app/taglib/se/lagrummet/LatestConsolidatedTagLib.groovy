package se.lagrummet

class LatestConsolidatedTagLib {

	def latestConsolidated = { attrs, body ->
		def var = attrs.var ? attrs.var : "latest"
		def latestVersion = null
		attrs.in?.each { 
			if(!latestVersion) {
				latestVersion = it
			} else {
				if(it.issued > latestVersion.issued) {
					latestVersion = it
				}
			}
		}
		out << body((var):latestVersion)
	}
}
