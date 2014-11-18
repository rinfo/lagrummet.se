package sysconf.test.alternate
//package sysconf.test.etc.lagrummet.se

grails.serverURL = "http://test.lagrummet.se"
lagrummet.local.rinfo.view = "${grails.serverURL}/rinfo/"

// Route rinfo-service requests through varnish
lagrummet.rdl.service.baseurl="http://127.0.0.1:8383/"
lagrummet.onlyLocalSearch = true
lagrummet.mainLayoutName = "mainSimpleSearch"

//lagrummet.rdl.service.baseurl="http://service.test.lagrummet.se/"
lagrummet.rdl.rinfo.baseurl="http://rinfo.test.lagrummet.se/"

dataSource.url = "jdbc:mysql://127.0.0.1:3306/lagrummet"
dataSource.username = "lagrummet2"
dataSource.password = "changeme"

lagrummet.googleAnalytics.webPropertyId = "UA-49599039-2"
