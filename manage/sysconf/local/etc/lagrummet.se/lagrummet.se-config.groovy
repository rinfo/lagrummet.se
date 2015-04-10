grails.serverURL = "http://localhost:8080/lagrummet.se-0.8.7"
lagrummet.local.rinfo.view = "${grails.serverURL}/rinfo/"

// Route rinfo-service requests through varnish
lagrummet.rdl.service.baseurl="http://127.0.0.1:8383/"

//lagrummet.rdl.service.baseurl="http://localhost:8383/"
lagrummet.rdl.rinfo.baseurl="http://localhost:8180/"

dataSource.url = "jdbc:mysql://lagrummet-db:3306/lagrummet"
dataSource.username = "lagrummet2"
dataSource.password = "changeme"

lagrummet.googleAnalytics.webPropertyId = ""
