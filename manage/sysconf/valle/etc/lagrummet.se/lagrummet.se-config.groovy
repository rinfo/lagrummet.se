grails.serverURL = "http://valle.lagrummet.se"
lagrummet.local.rinfo.view = "${grails.serverURL}/rinfo/"

// Route rinfo-service requests through varnish
// TODO: Varnish in production?
//lagrummet.rdl.service.baseurl="http://127.0.0.1:8383/"

// Setup search interface (RDL)
//lagrummet.rdl.service.baseurl="http://service.stage.lagrummet.se/"
//lagrummet.rdl.rinfo.baseurl="http://rinfo.stage.lagrummet.se/"

// Uncomment these to display start page without search
lagrummet.mainLayoutName = "mainSimpleSearch"
lagrummet.mainEnglishLayoutName = "mainSimpleSearch"

// Use local mysql database on target server
dataSource.url = "jdbc:mysql://127.0.0.1:3306/lagrummet"
dataSource.username = "lagrummet2"
dataSource.password = "changeme"

lagrummet.googleTagManager.webPropertyId = "GTM-NKM2G5"

// TODO: ip restrictions for admin in production?
//grails.plugins.springsecurity.ipRestrictions = [
//        '/admin/**': [
//                '159.190.251.56', '159.190.251.57', '159.190.240.67',
//        ]
//]