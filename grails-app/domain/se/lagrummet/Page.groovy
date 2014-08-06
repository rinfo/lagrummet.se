package se.lagrummet

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

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
	
	List puffs = new ArrayList()
	SortedSet children
	
	def getExpandablePuffList() {
		return LazyList.decorate(puffs,FactoryUtils.instantiateFactory(Puff.class))
	}
	
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
		def status = "published" && publishStart < now && (publishStop == null || publishStop > now)
		if (!status && autoSaves) {
				autoSaves.each { it ->
					if (it.status == 'published' && it.publishStart <= now && (it.publishStop == null || it.publishStop >= now)) status = true
			}
		}
		return status
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
		masterRevision(nullable: true)
		metaPage(nullable: false)
		menuStyle(blank:true, nullable:true)
    }
	
	static mapping = {
		sort pageOrder:"asc", dateCreated: "desc"
		children sort: "pageOrder"
		puffs sort: "dateCreated", order: "asc", cascade: "all-delete-orphan"
		autoTimestamp false
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
	
	def backup = {
		def pageBackup = new Page()
		pageBackup.properties = this.properties
		
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
