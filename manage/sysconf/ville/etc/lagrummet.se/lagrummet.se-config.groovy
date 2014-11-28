//package sysconf.test.etc.lagrummet.se

grails.serverURL = "http://ville.lagrummet.se"
lagrummet.local.rinfo.view = "${grails.serverURL}/rinfo/"

// Route rinfo-service requests through varnish
lagrummet.rdl.service.baseurl="http://127.0.0.1:8383/"

//lagrummet.rdl.service.baseurl="http://service.test.lagrummet.se/"
lagrummet.rdl.rinfo.baseurl="http://rinfo.ville.lagrummet.se/"
lagrummet.onlyLocalSearch = true
lagrummet.mainLayoutName = "mainSimpleSearch"

dataSource.url = "jdbc:mysql://127.0.0.1:3306/lagrummet"
dataSource.username = "lagrummet2"
dataSource.password = "changeme"

lagrummet.googleAnalytics.webPropertyId = ""
