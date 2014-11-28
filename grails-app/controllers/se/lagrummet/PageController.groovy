package se.lagrummet

import grails.plugins.springsecurity.Secured
import grails.plugin.gson.converters.GSON

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList
import org.codehaus.groovy.runtime.InvokerHelper

class PageController {
	
	def pageService
	def springSecurityService

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
		withForm {
			def pageInstance = new Page()
			bindData(pageInstance, params)
			pageInstance.publishStart = new Date()
			return [pageInstance: pageInstance]
		}.invalidToken {
			response.status = 403
		}
    }

	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def save = {
		withForm {
			if (params.ajax) {
				params.parent = Page.get(params.parentId)
				params.publishStart = new Date()
			}

			params.author = SecUser.get(springSecurityService.principal.id)
			def pageInstance = new Page(params)
			def now = new Date()
			pageInstance.dateCreated = now
			pageInstance.lastUpdated = now

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
					render response as GSON
				} else {
					flash.message = "${message(code: 'page.created.message', args: [pageInstance.title])}"
					redirect(action: "edit", id: pageInstance.id)
				}
			}
			else {
				if (params.ajax) {
					def response = [error: pageInstance.errors]
					render response as GSON
				} else {
					render(view: "create", model: [pageInstance: pageInstance])
				}
			}
		}.invalidToken {
			response.status = 403
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
			pageOrder = parentInstance.children.size() // place last
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
			render response as GSON
		}
		else {
			def response = [error: pageInstance.errors]
			render response as GSON
		}
	}

    def show = {
		def url = (params.permalink) ? params.permalink.tokenize("/") : ["home"]
		def permalink = url[url.size()-1]
		
		def now = new Date()
		def page = Page.withCriteria(uniqueResult:true) {
			eq("permalink", permalink)
			eq("status", "published")
			le('publishStart', now)
			or {
				ge('publishStop', now)
				isNull('publishStop')
			}
			maxResults(1)
		}

		if(page != null && !page.metaPage) {
			if(page.publishStop && page.publishStop.before(new Date())) {
				forward(action: "error", params: [errorId: "404"])
			}
			renderPage(page)
		} else {
			forward(action: "error", params: [errorId: "404"])
		}
    }
	
    private void renderPage(Page page) {
            def model = [page: page, siteProps: SiteProperties.findByTitle("lagrummet.se")]

            if (!page.template || page.template == "default") 
            {
                    render(view: "show", model: model)
            } 
            else if (page.template == "sitemap") 
            {
                    model.pageTreeList = pageService.getSiteMap()
                    render(view: "sitemap", model: model)
            } 
            else if (page.template == "contact") 
            {
                    render(view: "contact", model: model)
            } 
            else if (page.template == "english") 
            {
                    render(view: "showEnglish", model: model)
            } 
            else if (page.template == "legalSources") 
            {
                    model.legalSourceGroups = [:]
                    grailsApplication.config.lagrummet.legalSource.categories.each { category ->
                            def categoryList = ["sokbar": [:], "inteSokbar": [:]]

                            LegalSource.findAllByCategory(category, [sort:"name", order:"asc"]).each 
                            {
                                    def rdlName = (it.rdlName) ? "sokbar" : "inteSokbar"

                                    if (!categoryList[rdlName][it.subCategory]) 
                                    {
                                            categoryList[rdlName][it.subCategory] = []
                                    }

                                    categoryList[rdlName][it.subCategory].add(it)
                            
                                    //if (category == "Foreskrifter") 
                                         categoryList[rdlName][it.subCategory].sort{it.name}   
                            }
                            
                                                  
                                
                            model.legalSourceGroups[category] = categoryList
                    }
                    render(view: "legalSources", model: model)
            } 
            else 
            {
                    render(view: page.template, model: model)
            }
    }
	
	def error = {
		if (params.errorId == "404") {
			def model = [siteProps: SiteProperties.findByTitle("lagrummet.se")]
			response.status=404
			render(view: "error404", model: model)
		}
        if (params.errorId == "500") {
            def model = [siteProps: SiteProperties.findByTitle("lagrummet.se")]
            response.status=500
            render(view: "error500", model: model)
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
			render response as GSON
		} else {
			forward(action: "show", params: [permalink: "webbkarta"])
		}
	}
	
	def contact = {
		def title = 'Från ' + params.name
		if (params.epost) title += ', ' + params.epost
		def fromAdress = params.epost ?: "lagrummet@lagrummet.se"
		
		sendMail {     
		  to grailsApplication.config.lagrummet.contact.email     
		  subject params.arende
		  from fromAdress
		  html '<p>' + title + '</p><p>Ärendetyp: ' + params.arende + '</p><p>' + params.meddelande + '</p>'

		}
		forward(action: "show", params: [permalink: "tack-for-ditt-meddelande"])
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
            return [pageInstance: pageInstance, revisions: Page.findAllByMasterRevision(pageInstance, [sort:"dateCreated", order:"desc"]), images: images]
        }
    }
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
	def saveAsDraft = {
		if (!params.id) {
			forward(action: "save", params: params)
		} else if (params.status == "draft") {
			forward(action: "update", params: params)
		} else {
			params.status = "draft"
			forward(action: "update", params: params)
		}
	}
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
	def publish = {
		params.status = "published"
		if (!params.id) {
			forward(action: "save", params: params)
		} else {
			forward(action: "update", params: params)
		}
	}
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
	def unpublish = {
		params.doUnpublish = true
		forward(action: "update", params: params)
	}
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
	def preview = {
		def page = new Page()
        bindData(page, params)

		page.puffs.eachWithIndex { puffItem, i ->
				def puffListParam = "puffs["+i+"]"
				def imgId = params."${puffListParam}"?.image?.id
				puffItem.image = imgId ? Media.findById(imgId) : null
		}
		
		def toBeDeleted = page.puffs.findAll {
			it.deleted || it.isEmpty()
		}
		if (toBeDeleted) {
			page.puffs.removeAll(toBeDeleted)
		}
		page.discard()
		page.puffs.each {
			it.discard()
		}
		renderPage(page)
	}
	
	@Secured(['ROLE_EDITOR', 'ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
    def update = {
		flash.messages = []
		def pageInstance = Page.get(params.id)
		if (pageInstance) {
            if (params.version) {
                def version = Long.valueOf(params.version)
                if (pageInstance.version > version) {
                   flash.messages.add("${message(code: 'page.updated.updatedByAnotherUser')}")
                }
            }

			pageInstance.backup()
			params.author = SecUser.get(springSecurityService.principal.id)
            bindData(pageInstance, params)

			if (params.reviewDate) pageInstance.publishStart = new Date()
			
			def now = new Date()
			if(params.status == "published") {
				pageInstance.autoSaves.each { revision ->
					if(revision.isCurrentlyPublished()) {
						if(pageInstance.publishStart > now) {
							revision.publishStop = pageInstance.publishStart
						} else {
							revision.publishStop = now
						}
					}
				}
			}
			if(params.doUnpublish) {
				pageInstance.publishStop = now
			}

            def toBeDeleted = []

            pageInstance.puffs.eachWithIndex { puffItem, i ->
                def puffListParam = "puffs["+i+"]"
                if(params."${puffListParam}"?.deleted == "true") {
                    toBeDeleted << puffItem
                }
            }
			if (toBeDeleted) {
				pageInstance.puffs.removeAll(toBeDeleted)
			}
			
			pageInstance.lastUpdated = now
			
            if (!pageInstance.hasErrors() && pageInstance.save(flush:true)) {
				flash.messages.add "${message(code: 'page.updated.message', args: [pageInstance.h1])}"
                redirect(action: "edit", id: pageInstance.id)
            }
            else {
                render(view: "edit", model: [pageInstance: pageInstance])
            }
        }
        else {
            flash.messages.add "${message(code: 'default.not.found.message', args: [message(code: 'page.label', default: 'Page'), params.id])}"
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
					render response as GSON
				} else {
	                flash.message = "${message(code: 'page.deleted.message', args: [params.title])}"
	                redirect(action: "list")
				}
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
				if (params.ajax) {
					def response = [error: "${message(code: 'default.not.deleted.message', args: [message(code: 'page.label', default: 'Page'), params.id])}", pageInstance: pageInstance]
					render response as GSON
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
