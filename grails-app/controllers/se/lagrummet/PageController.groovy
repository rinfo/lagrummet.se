package se.lagrummet

import grails.plugins.springsecurity.Secured
import grails.converters.*
import groovy.xml.MarkupBuilder

import javax.servlet.http.HttpServletResponse

class PageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", move: "POST", restore: "POST"]

    /*def index = {
        redirect(action: "show", params: params)
    }*/
	
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [pageInstanceList: Page.list(params), pageInstanceTotal: Page.count()]
    }
	
	@Secured(['IS_AUTHENTICATED_FULLY'])
	def quickSearch = {
		def query = params.query
		def result = []
		def total = 0
		def searchResult = []
		if(query) {
			params.suggestQuery = true
			searchResult = Page.search(query, params, sort: "SCORE")
			result = searchResult.results
			total = searchResult.total
		}

		render (view: 'list', model: [pageInstanceList: result, pageInstanceTotal: total, searchResult: searchResult])
	}

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def create = {
        def pageInstance = new Page()
        pageInstance.properties = params
		pageInstance.publishStart = new Date()
        return [pageInstance: pageInstance]
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def save = {
		if (params.ajax) {
			params.parent = Page.get(params.parentId)
			params.publishStart = new Date()
		}

        def pageInstance = new Page(params)
		
		def instanceToSave
		if (pageInstance.parent) {
			pageInstance.parent.addToChildren(pageInstance)
			instanceToSave = pageInstance.parent
		} else {
			instanceToSave = pageInstance
		}
		
        if (instanceToSave.save(flush: true)) {
			if (params.ajax) {
				def response = [success: "true", pageInstance: pageInstance]
				render response as JSON
			} else {
				flash.message = "${message(code: 'page.created.message', args: [pageInstance.title])}"
//				render(view: "edit", model: [pageInstance: pageInstance])
				redirect(action: "edit", id: pageInstance.id)
			}
        }
        else {
			if (params.ajax) {
				def response = [error: pageInstance.errors]
				render response as JSON
			} else {
            	render(view: "create", model: [pageInstance: pageInstance])
			}
        }
    }
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
	def move = {
		def pageInstance = Page.get(params.id)
		def parentInstance
		def pageOrder = 1
		
		if (params.position == "before" || params.position == "after") { // "before", "after", "inside", "first", "last"
			// In relation to the sibling, find the parent (or no parent for top level)
			def target = Page.get(params.targetId)
			parentInstance = target.parent
			
			if (params.position == "before") {
				pageOrder = target.pageOrder - 1
			} else if (params.position == "after") {
				pageOrder = target.pageOrder + 1
			}
		} else if (params.position) {
			// In relation to the children of the new parent, the new parent is given
			parentInstance = Page.get(params.targetId)
			pageOrder = 0 // place first
		}
		
		if (pageOrder < 0) pageOrder = 0
		pageInstance.pageOrder = pageOrder
		pageInstance.parent = parentInstance
		
		// remove from old parent
		if (parentInstance && parentInstance.id != pageInstance.parent.id) {
			pageInstance.parent.removeFromChildren(pageInstance).save(flush:true)
		}
		
		def instanceToSave
		if (parentInstance) {
			parentInstance.addToChildren(pageInstance)
			instanceToSave = parentInstance
		} else {
			pageInstance.parent = null
			instanceToSave = pageInstance
		}

		// Only Ajax response
		if (instanceToSave.save(flush: true)) {
			def response = [success: "true", pageInstance: pageInstance]
			render response as JSON
		}
		else {
			def response = [error: pageInstance.errors]
			render response as JSON
		}
	}

    def show = {
		def url = (params.permalink) ? params.permalink.tokenize("/") : ["home"]
		def permalink = url[url.size()-1]

		def page
		if (url.size() < 2) {
			page = Page.withCriteria(uniqueResult:true) {
				eq("permalink", permalink)
				eq("status", "published")
				le('publishStart', new Date())
				maxResults(1)
			}
		} else {
			def parentPermalink = (url[url.size()-2])
			page = Page.withCriteria(uniqueResult:true) {
				eq("permalink", permalink)
				parent {
					eq("permalink", parentPermalink)
				}
				eq("status", "published")
				le('publishStart', new Date())
				maxResults(1)
			}
		}

		if(page) {
			if(page.publishStop && page.publishStop.before(new Date())) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND)
			}

			def model = [page: page, siteProps: SiteProperties.findByTitle("lagrummet.se")]
			if (!page.template || page.template == "default") {
				return model
			} else if (page.template == "sitemap") {
				model.pageTreeList = Page.findAllByStatusNotAndTemplateNot("autoSave","sitemap")
				render(view: "sitemap", model: model)
			} else if (page.template == "english") {
				render(view: "showEnglish", model: model)
			} else if (page.template == "legalSources") {	
				model.legalSourceGroups = [:]
				grailsApplication.config.lagrummet.legalSource.categories.each { category ->
					def categoryList = ["sokbar": [:], "inteSokbar": [:]]
					LegalSource.findAllByCategory(category).each {
						def rdlName = (it.rdlName) ? "sokbar" : "inteSokbar" 
						if (!categoryList[rdlName][it.subCategory]) {
							categoryList[rdlName][it.subCategory] = []
						}
						categoryList[rdlName][it.subCategory].add(it)
					}
					model.legalSourceGroups[category] = categoryList
				}
				render(view: "legalSources", model: model)
			} else {
				render(view: page.template, model: model)
			}
			
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND)
		}
    }
	
	def xmlSitemap = {
		def pages = Page.findAllByStatusNot("autoSave")		
		
		if (request.format == "xml") {
			def xml = new groovy.xml.StreamingMarkupBuilder()
			xml.encoding = "UTF-8"
			
			render xml.bind {
				mkp.xmlDeclaration()
				urlset(xmlns: 'http://www.sitemaps.org/schemas/sitemap/0.9') {
					url {
						loc resource(absolute: true)+"/"
						lastmod new Date().format('yyyy-MM-dd')
					}
					pages.each {item ->
						url {
							loc grailsApplication.config.grails.serverURL+"/"+item.url()
							lastmod item.lastUpdated.format('yyyy-MM-dd')
						}
					}
				}
			}.toString()
		} else if (request.format == "json") {
			def response = [pages: pages]
			render response as JSON
		} else {
			forward(action: "show", params: [permalink: "webbkarta"])
		}
	}
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
	def restore = {
		def pageInstance = Page.get(params.id)
		def master = pageInstance.masterRevision
		master.backup()
		
		master.h1 = pageInstance.h1
		master.title = pageInstance.title
		master.permalink = pageInstance.permalink
		master.content = pageInstance.content
		master.template = pageInstance.template
		master.pageOrder = pageInstance.pageOrder

		
		if (!pageInstance.hasErrors() && master.save(flush:true)) {
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'page.label', default: 'Page'), master.id])}"
			redirect(action: "edit", id: master.id)
		} else {
			redirect(action: "edit", id: pageInstance.id)
		}
	}

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def edit = {
        def pageInstance = Page.get(params.id)
        if (!pageInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
            redirect(action: "list")
        }
        else {
			def images = ["":"Ingen bild"]
			Media.findAllByParent(pageInstance).each {
				images[it.id] = it.title
			}
            return [pageInstance: pageInstance, revisions: Page.findAllByMasterRevisionAndStatus(pageInstance, "autoSave", [sort:"dateCreated", order:"desc"]), images: images]
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def update = {
        def pageInstance = Page.get(params.id)
        if (pageInstance) {
            if (params.version) {
                def version = Long.valueOf(params.version)
                if (pageInstance.version > version) {
                    pageInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'page.label', default: 'Page')] as Object[], "Another user has updated this Page while you were editing")
                    render(view: "edit", model: [pageInstance: pageInstance])
                    return
                }
            }
			
			params.puffs.each {
				if (it.value.getClass() != String) {
					def puff = Puff.get(it.value.id)
					puff.properties = it.value
				}
			}

			pageInstance.backup()
            pageInstance.properties = params

            if (!pageInstance.hasErrors() && pageInstance.save(flush:true)) {
				flash.message = "${message(code: 'page.updated.message', args: [pageInstance.h1])}"
                redirect(action: "edit", id: pageInstance.id)
            }
            else {
                render(view: "edit", model: [pageInstance: pageInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
            redirect(action: "edit", id: pageInstance.id)
        }
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def delete = {
        def pageInstance = Page.get(params.id)
        if (pageInstance) {
            try {
                pageInstance.delete(flush: true)
				if (params.ajax) {
					def response = [success: "${message(code: 'default.deleted.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"]
					render response as JSON
				} else {
	                flash.message = "${message(code: 'page.deleted.message', args: [params.title])}"
	                redirect(action: "list")
				}
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
				if (params.ajax) {
					def response = [error: "${message(code: 'default.not.deleted.message', args: [message(code: 'page.label', default: 'Page'), params.id])}", pageInstance: pageInstance]
					render response as JSON
				} else {
	                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
					redirect(action: "edit", id: pageInstance.id)
				}
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
            redirect(action: "list")
        }
    }
}
