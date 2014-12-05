//package sysconf.test.etc.lagrummet.se

grails.serverURL = "http://dnsplaceholderforsed"
lagrummet.local.rinfo.view = "${grails.serverURL}/rinfo/"

// Route rinfo-service requests through varnish
lagrummet.rdl.service.baseurl="http://127.0.0.1:8383/"

//lagrummet.rdl.service.baseurl="http://service.test.lagrummet.se/"
lagrummet.rdl.rinfo.baseurl="http://rinfo.dnsplaceholderforsed/"
lagrummet.onlyLocalSearch = true
lagrummet.mainLayoutName = "mainSimpleSearch"

dataSource.url = "jdbc:mysql://127.0.0.1:3306/lagrummet"
dataSource.username = "usernameplaceholderforsed"
dataSource.password = "passwordplaceholderforsed"

lagrummet.googleAnalytics.webPropertyId = ""

grails.plugins.springsecurity.ipRestrictions = [
	'/admin/**': [
		'159.190.251.56', '159.190.251.57', '159.190.240.67',
		]
	 ]
