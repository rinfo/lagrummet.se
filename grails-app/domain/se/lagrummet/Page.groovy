package se.lagrummet

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

class Page implements Comparable<Page>{
	
	String title
	String h1
	String permalink
	String content
//	User author
	int pageOrder = 0
	String template = "default" // See config.groovy -> lagrummet.page.templates for possible values
	String menuStyle
	
	String status = "draft" // draft, pending, published, autoSave
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
		Date now = new Date()
		SortedSet published = new TreeSet()
		children.each{ it ->
			if(it.publishStart < now  && it.status.equals("published")) {
				published.add(it)
			}
			
		}
		return published
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
	}
	
	def url = {
		def response = (parent) ? (parent.permalink + "/") : ""
		return response + permalink
	}
	
	def backup = {
		def pageBackup = new Page()
		pageBackup.properties = this.properties
		pageBackup.id = null
		pageBackup.children = null
		pageBackup.media = null
		pageBackup.puffs = null
		pageBackup.autoSaves = null
		pageBackup.status = "autoSave"
		this.addToAutoSaves(pageBackup).save()
	}
	
	static searchable = {
		except = ["template", "metaPage", "pageOrder", "menuStyle"]
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
		return comp;
	}

}
