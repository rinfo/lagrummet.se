import org.apache.jasper.compiler.Node.ParamsAction;

class UrlMappings {

	static excludes = ["/images/*", "/plugins/*", "/css/*", "/js/*", "/uploads/*", "/WEB-INF/*"]

	static mappings = {

		"/login/$action"(controller:'login')
		"/logout/$action"(controller:'logout')
		"/sitemap"(controller: 'page', action: "xmlSitemap")

        name extendedSearch: "/search/ext/" {
            controller = "search"
            action = "ext"
        }

		name search: "/search/" {
			controller = "search"
			action = "index"
		}
		
		"/search/$action"(controller:'search')
		
		"/profile/$action"(controller:'profile')
		
		name rinfo: "/rinfo/$docPath**" {
            controller = "rinfo"
            action = "show"
		}

		"/files/$filename**" {
			controller = "media"
			action = "viewMediaContent"
		}

		name page: "/$permalink**" {
			constraints {
				permalink(matches:/.+/, blank:false)
			}
			controller = "page"
			action = {
				params.action = params.a ?: "show"
			}
		}



		"/"(controller: 'page', action: "show")



		"/admin/user/$action"(controller: 'user')
		
		"/admin/site/$action"(controller: 'siteProperties')
		
		"/admin/media/$action"(controller: 'media')
		
		"/admin/legalSource/$action"(controller: 'legalSource')
		
		"/admin/synonym/$action"(controller: 'synonym')
		
		name contact: "/contact" {
			controller = 'page'
			action = 'contact'
		}
		
		name pageAdmin: "/admin/page/$action?" {
			controller = 'page'
		}

        "/admin/$action"(controller: 'admin')

		"/admin/"(controller: 'admin')

        "500"(view:'/page/error500')
		"404" {
			controller = "page"
			action = "error"
			errorId = "404"
		}

		//redirects from the old page
		"/lar-dig-mer/vart" {
			controller = "page"
			action = "redirect"
			destination = "/hit-vander-du-dig/myndighetersansvar"
		}
		"/rattsinformation/alla" {
			controller = "page"
			action = "redirect"
			destination = "/rattsinformation/samtliga-rattskallor"
		}
		"/rattsinformation/internationellt" {
			controller = "page"
			action = "redirect"
			destination = "/rattsinformation/internationellt-material"
		}
		"/lar-dig-mer/ordlista" {
			controller = "page"
			action = "redirect"
			destination = "/lar-dig-mer/ordlistan-a-o"
		}
		"/lar-dig-mer" {
			controller = "page"
			action = "redirect"
			destination = "/lar-dig-mer/rattsprocessens-olika-steg"
		}
		"/lar-dig-mer/forarbeten" {
			controller = "page"
			action = "redirect"
			destination = "/rattsprocessen-forarbeten"
		}
		"/lar-dig-mer/forfattningar" {
			controller = "page"
			action = "redirect"
			destination = "/rattsprocessen-forfattningar"
		}
		"/lar-dig-mer/rattspraxis" {
			controller = "page"
			action = "redirect"
			destination = "/rattsprocessen-rattspraxis"
		}
		"/lar-dig-mer/internationellt" {
			controller = "page"
			action = "redirect"
			destination = "/rattsprocessen-internationellt-material"
		}
		"/lar-dig-mer/faq" {
			controller = "page"
			action = "redirect"
			destination = "/lar-dig-mer/vanliga-fragor"
		}
		"/Om-lagrummetse/om_lagrummet_se/anvandningstips" {
			controller = "page"
			action = "redirect"
			destination = "/om-webbplatsen/teknisk-information"
		}
		"/om-lagrummetse/om_lagrummet_se/lankar" {
			controller = "page"
			action = "redirect"
			destination = "/om-lagrummet/lankar-du-hittar"
		}
		"/rattsinformation" {
			controller = "page"
			action = "redirect"
			destination = "/om-lagrummet/rattsinformationssystemet"
		}
		"/Om-lagrummetse/om_lagrummet_se" {
			controller = "page"
			action = "redirect"
			destination = "/om-lagrummet/Om-lagrummet.se"
		}
		"/Om-lagrummetse/om_lagrummet_se/rattsinformationsprojektet" {
			controller = "page"
			action = "redirect"
			destination = "/lagrummet.se/nya-lagrummetse---vad-kommer-sedan"
		}
		"/Om-lagrummetse/om_lagrummet_se/kontaktinfo" {
			controller = "page"
			action = "redirect"
			destination = "/kontakta-oss-rubrik/kontakta-oss"
		}
		"/Om-lagrummetse/om_lagrummet_se/e-postsvar" {
			controller = "page"
			action = "redirect"
			destination = "/kontakta-oss-rubrik/sa-svarar-vi-pa-e-post"
		}

	}
}
