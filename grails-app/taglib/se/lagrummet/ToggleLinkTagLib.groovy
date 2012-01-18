package se.lagrummet

class ToggleLinkTagLib {
	
	def toggleLink = { attrs, body ->
		
		if(!attrs.mapping) {
			throwTagError("Tag [toggleLink] is missing required attribute [mapping]")
		}
		if(!attrs.toggleId) {
			throwTagError("Tag [toggleLink] is missing required attribute [toggleId]")
		}
		def toggleId = attrs.toggleId.toString()
		def var = attrs.var ? attrs.var : "toggledParams"
		
		def newParams = [:]

		params.entrySet().each{
			newParams.put(it.key.toString(), it.value.toString())
		}
		if(newParams[(toggleId)] == "true") {
			newParams[(toggleId)] = null
			newParams.remove(toggleId)
		} else {
			newParams[(toggleId)] = true
		}
		
		out << render(template:"/toggleLinkTagLib/toggleLink", model:[mapping:attrs.mapping, toggleId:attrs.toggleId, body: body(), toggleParams:newParams])
//		out << body((var):newParams)
	}

}
