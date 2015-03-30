package se.lagrummet

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList
import org.codehaus.groovy.runtime.InvokerHelper

class Page implements Comparable<Page>{
	
	String title
	String h1
	String permalink
	String content
	User author
	int pageOrder = 1
	String template = "default" // See config.groovy -> lagrummet.page.templates for possible values
	String menuStyle
	boolean showInSitemap = true
	
	String status = "draft" // draft, published, autoSave
	boolean metaPage = false
	Date dateCreated
	Date lastUpdated
	Date publishStart
	Date publishStop
	
	List<Puff> puffs = new ArrayList<Puff>()
	SortedSet children
	
	def getPublishedChildren() {
		SortedSet published = new TreeSet()
		children.each{ it ->
			if(it.isCurrentlyPublished()) {
				published.add(it)
			}
			
		}
		return published
	}
	
	def isCurrentlyPublished() {
		def now = new Date()
		def pageStatus = status == "published" && publishStart < now && (publishStop == null || publishStop > now)
		if (!pageStatus && autoSaves) {
				autoSaves.each { it ->
					if (it.status == 'published' && it.publishStart <= now && (it.publishStop == null || it.publishStop >= now)) pageStatus = true
			}
		}
		return pageStatus
	}

	//this works due to how autosaves work when changing status.
	def hasBeenPublishedEarlier() {
		return autoSaves?.any {it.status == 'published'}
	}
	
	def getCurrentPageStatus() {
		def currentStatus
		def now = new Date()
		if(status == "draft") {
			currentStatus = "draft"
		} else if(publishStart > now) {
			currentStatus ="pending"
		} else if(publishStart < now && (publishStop == null || publishStop > now)) {
			currentStatus = "published"
		} else {
			currentStatus = "unpublished"
		}
		return currentStatus
	}
	
	
	static hasMany = [
		children : Page,
		media : Media,
		puffs : Puff,
		autoSaves : Page
	]

	static belongsTo = [
		parent : Page,
		masterRevision : Page
	]
	
	static mappedBy = [
		children : 'parent',
		autoSaves : 'masterRevision'
	]
	
    static constraints = {
		content(nullable: true, maxSize: 2147483647)
		parent(nullable: true)
		status(nullable: true)
		publishStart(nullable: true)
		publishStop(nullable: true)
		menuStyle(blank:true, nullable:true)
        puffs(bindable: true)
    }
	
	static mapping = {
		sort pageOrder:"asc", dateCreated: "desc"
		children sort: "pageOrder"
		puffs sort: "dateCreated", order: "asc", cascade: "all-delete-orphan"
		autoTimestamp: true
        metaPage defaultValue: false
        showInSitemap defaultValue: true
    }
	
	def url = {
		def fullUrl = ""
		if(parent) {
			fullUrl = parent.url() ? parent.url() + "/" : ""
			fullUrl += permalink
		} else if(!metaPage){
			fullUrl = permalink
		}
		return fullUrl
	}

	def absoluteParentPath(host) {
		if(parent) {
			def path = parent.url()
			if(path)
				host += "/${path}"
		}
		return host
	}
	
	def backup = {
		def pageBackup = new Page()

        InvokerHelper.setProperties(pageBackup, this.properties)

		//pageBackup.properties = this.properties
		
		pageBackup.id = null
		pageBackup.children = null
		pageBackup.parent = null
		pageBackup.media = null
		pageBackup.puffs = null
		pageBackup.autoSaves = null
		pageBackup.dateCreated = this.lastUpdated
		pageBackup.lastUpdated = new Date()
		this.addToAutoSaves(pageBackup).save()
	}
	
	static searchable = {
		except = ["template", "pageOrder", "menuStyle", "url"]
		spellCheck "include"
		title boost: 2.0
		publishStop nullValue: "NULL"
		content converter: "customHtmlConverter", nullValue: " "
	}
	
	public String toString() {
		return title
	}

	@Override
	public int compareTo(Page other) {
		def comp = pageOrder <=> other?.pageOrder
		
		if(comp == 0) {
			comp = other?.dateCreated <=> dateCreated
		}

        if(comp == 0) {
            comp = other?.id <=> id
        }
		return comp;
	}

}
