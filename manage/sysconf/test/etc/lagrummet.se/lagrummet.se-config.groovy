//package sysconf.test.etc.lagrummet.se

grails.serverURL = "http://test.lagrummet.se"
lagrummet.local.rinfo.view = "${grails.serverURL}/rinfo/"

// Route rinfo-service requests through varnish
//lagrummet.rdl.service.baseurl="http://127.0.0.1:8383/"

lagrummet.rdl.service.baseurl="http://service.test.lagrummet.se/"
lagrummet.rdl.rinfo.baseurl="http://rinfo.test.lagrummet.se/"

lagrummet.onlyLocalSearch = false
//lagrummet.mainLayoutName = "mainSimpleSearch"
//lagrummet.mainEnglishLayoutName = "mainSimpleSearch"

dataSource.url = "jdbc:mysql://lagrummet-db/lagrummet"
dataSource.username = "lagrummet2"
dataSource.password = "changeme"

lagrummet.googleAnalytics.webPropertyId = "UA-49599039-2"
