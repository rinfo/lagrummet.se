grails.serverURL = "http://beta.lagrummet.se"
lagrummet.local.rinfo.view = "${grails.serverURL}/rinfo/"

lagrummet.rdl.service.baseurl="http://service.lagrummet.se/"
lagrummet.rdl.rinfo.baseurl="http://rinfo.lagrummet.se/"
lagrummet.onlyLocalSearch = true
lagrummet.mainLayoutName = "mainSimpleSearch"
lagrummet.mainEnglishLayoutName = "mainSimpleSearch"

dataSource.url = "jdbc:mysql://127.0.0.1:3306/lagrummet"
dataSource.username = "lagrummet"
dataSource.password = "<CHANGEME>"

lagrummet.googleAnalytics.webPropertyId = ""


grails.plugins.springsecurity.ipRestrictions = [
	 '/admin/**': [
		 '159.190.251.56', '159.190.251.57', '159.190.240.67', 
		 ]
	  ]

 
