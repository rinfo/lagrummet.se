import grails.plugins.springsecurity.SecurityConfigType
import org.apache.log4j.DailyRollingFileAppender

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
def ENV_NAME = "LAGRUMMET_SE_CONFIG"
if(!grails.config.locations || !(grails.config.locations instanceof List)) {
	grails.config.locations = []
} 
if(System.getProperty(ENV_NAME)) {
	println "Including configuration file specified on command line: " + System.getProperty(ENV_NAME);
	grails.config.locations << "file:" + System.getProperty(ENV_NAME)
 
} else if(System.getenv(ENV_NAME)) {
	println "Including configuration file specified in environment: " + System.getenv(ENV_NAME);
	grails.config.locations << "file:" + System.getenv(ENV_NAME)
 
} else if(new File("/etc/lagrummet.se/lagrummet.se-config.groovy").exists()) {
	println "Using default location: /etc/lagrummet.se/lagrummet.se-config.groovy"
	grails.config.locations << "file:/etc/lagrummet.se/lagrummet.se-config.groovy"

} else {
	println "No external configuration file defined."
}

grails.gsp.tldScanPattern='classpath*:/META-INF/*.tld,/WEB-INF/tld/*.tld'

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']


grails.databinding.convertEmptyStringsToNull = false



// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.lagrummet.se"
    }
    beta {
        grails.serverURL = "http://beta.lagrummet.se"
    }
    demo {
        grails.serverURL = "http://demo.lagrummet.se"
    }
    development {
        grails.serverURL = "http://localhost:8080/lagrummet.se"
    }
    test {
        grails.serverURL = "http://localhost:8080/lagrummet.se"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '[%p] %d [%t] %x %c %M - %m%n')
        appender new DailyRollingFileAppender(
                name: 'lagrummet_file',
                datePattern: '.yyyy-MM-dd',
                fileName: "${System.properties['catalina.base']}/logs/lagrummet.log",
                layout: pattern(conversionPattern: "[%p] %d [%t] %x %c %M - %m%n"))
    }
    
    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'

    debug ''

    info  'se.lagrummet',
          'grails.app.jobs.se.lagrummet',
          'grails.app.controllers',
          'grails.app.services'


    'null' name:'stacktrace'


    root {
        debug 'stdout'
        warn 'lagrummet_file'
    }

}

//jquery plugin installation
grails.views.javascript.library="jquery"


// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'se.lagrummet.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'se.lagrummet.SecUserSecRole'
grails.plugins.springsecurity.authority.className = 'se.lagrummet.SecRole'

ipRestrictions = [
        '159.190.240.1', '159.190.240.2', '159.190.240.3', '159.190.240.254', '159.190.251.57',		//DV Jönköping
        '159.190.240.66', '159.190.251.56', '159.190.240.42',											//DV Stockholm
        '159.190.240.150', '159.190.240.151',										//DV Malmö
        '159.190.240.168', '159.190.240.169', '159.190.240.170', '159.190.240.171',	//DV Göteborg
        '193.45.43.33',																//Sogeti VPN for development and testing, TODO: remove before going live
        '94.234.170.41',                                                            // DV Sthml 4G Router
        '109.74.9.93',                                                              // ci.lagrummet.se (jenkins) for automated testing
]

//grails.plugins.springsecurity.ipRestrictions = [
//	    '/admin/*': ipRestrictions,
//        '/login/*': ipRestrictions,
// 	]


//grails.plugins.springsecurity.securityConfigType = SecurityConfigType.InterceptUrlMap
//grails.plugins.springsecurity.interceptUrlMap = [
//	'/admin/**' : ['ROLE_ADMIN', 'ROLE_EDITOR', 'IS_AUTHENTICATED_FULLY'],
//	'/**' : 	['IS_AUTHENTICATED_ANONYMOUSLY']
//	]

// Properties for the Lagrummet.se CMS

// Google Analytics kod, hämtad från (Sharepoint) Rättsinfosystemet Wiki > Lagrummet.se
// googleAnalytics.webPropertyId = "UA-10527860-1"

lagrummet {
	googleAnalytics.webPropertyId = ""
	contact.email = "betafeedback@lagrummet.se"
	upload.dir = "files/"
    onlyLocalSearch = false
    mainLayoutName = "main"
    mainEnglishLayoutName = "mainEnglish"
	rdl.service.baseurl="http://service.demo.lagrummet.se"
	rdl.rinfo.baseurl="http://rinfo.demo.lagrummet.se"
	local.rinfo.view="${grails.serverURL}/rinfo/"
	page.templates = ["default":"Standardmall", "faq":"Vanliga frågor", "legalSource/lagar":"Rättskällor:Lagar", "legalSource/forarbeten":"Rättskällor:Förarbeten", "legalSource/internationellt":"Rättskällor:Internationellt", "legalSource/rattspraxis":"Rättskällor:Rättspraxis", "legalSource/foreskrifter":"Rättskällor:Föreskrifter", "english":"English"] // "frontpage":"Startsida"
    search.maxLength = 100;
	search.availableCategories=["Alla","Lagar", "Foreskrifter","Rattsfall","Propositioner","Utredningar","Ovrigt"]
	search.availableDepartement=["Justitiedepartementet","Utrikesdepartementet","Försvarsdepartementet","Socialdepartementet","Finansdepartementet","Utbildningsdepartementet","Landsbygdsdepartementet","Miljödepartementet","Näringsdepartementet","Kulturdepartementet","Arbetsmarknadsdepartementet"]
	search.availableBeslutandeMyndigheter=[]
	search.courtList = [
		[value:'', title: 'Alla domstolar'],
		[value: '', title: '', disabled: true],
		[value:'', title: '--Allm&auml;nna domstolar--', disabled: true],
		[value: 'hoegsta_domstolen', title : 'H&ouml;gsta domstolen'],
		[value: 'svea_hovraett', title: 'Svea hovr&auml;tt'],
		[value: 'goeta_hovraett', title: 'G&ouml;ta hovr&auml;tt'],
		[value: 'hovraetten_oever_skaane_och_blekinge', title: 'Hovr&auml;tten &ouml;ver Sk&aring;ne och Blekinge'],
		[value: 'hovraetten_oever_vaestra_sverige', title: 'Hovr&auml;tten f&ouml;r V&auml;stra Sverige'],
		[value: 'hovraetten_foer_nedre_norrland', title: 'Hovr&auml;tten f&ouml;r Nedre Norrland'],
		[value: 'hovraetten_foer_oevre_norrland', title: 'Hovr&auml;tten f&ouml;r &Ouml;vre Norrland'],
		[value: '', title: '', disabled: true],
		[value: '', title: '--Allm&auml;na f&ouml;rvaltningsdomstolar--', disabled: true],
		[value: 'hoegsta_foervaltningsdomstolen', title: 'H&ouml;gsta f&ouml;rvaltningsdomstolen'],
		[value: 'kammarraetten_i_stockholm', title: 'Kammarr&auml;tten i Stockholm'],
		[value: 'kammarraetten_i_goeteborg', title: 'Kammarr&auml;tten i G&ouml;teborg'],
		[value: 'kammarraetten_i_sundsvall', title: 'Kammarr&auml;tten i Sundsvall'],
		[value: 'kammarraetten_i_joenkoeping', title: 'Kammarr&auml;tten i J&ouml;nk&ouml;ping'],
		[value: '', title: '', disabled: true],
		[value: '', title: '--Specialdomstolar--', disabled: true],
		[value: 'arbetsdomstolen', title: 'Arbetsdomstolen'],
		[value: 'marknadsdomstolen', title: 'Marknadsdomstolen'],
		[value: 'migrationsoeverdomstolen', title: 'Migrations&ouml;verdomstolen'],
		[value: 'miljoeoeverdomstolen', title: 'Mark- och Milj&ouml;&ouml;verdomstolen'],
		[value: 'patentbesvaersraetten', title: 'Patentbesv&auml;rsr&auml;tten'],
	]
	legalSource.categories = ["Foreskrifter","Lagar","Forarbeten","Rattspraxis","Internationellt"]
	legalSource.subCategories = ["Regeringen", "Riksdagen", "Lagradet", "Domstolars_Vagledande_Avgoranden", "Myndigheters_Vagledande_Avgoranden", "Domstolars_Beslut"]
}

// Mail settings for contact form (and other Grails Mail)
grails.mail.host = 'localhost'
grails.mail.port = 25
grails.mail.default.from="betafeedback@lagrummet.se"


environments {
	production {
		lagrummet.app.basedir = ""
        lagrummet.rdl.service.baseurl="http://service.lagrummet.se"
        lagrummet.rdl.rinfo.baseurl="http://rinfo.lagrummet.se"
    }
    beta {
        lagrummet.app.basedir = "web-app/"
        lagrummet.rdl.service.baseurl="http://beta.lagrummet.se"
        lagrummet.rdl.rinfo.baseurl="http://rinfo.demo.lagrummet.se"
    }
    demo {
        lagrummet.app.basedir = ""
        lagrummet.rdl.service.baseurl="http://service.demo.lagrummet.se"
        lagrummet.rdl.rinfo.baseurl="http://rinfo.demo.lagrummet.se"
    }
	development {
		lagrummet.app.basedir = "web-app/"
        lagrummet.rdl.service.baseurl="http://service.demo.lagrummet.se"
        lagrummet.rdl.rinfo.baseurl="http://rinfo.demo.lagrummet.se"
	}
	test {
		lagrummet.app.basedir = "web-app/"
	}

}

// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line 
// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside null
                scriptlet = 'none' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
remove this line */
