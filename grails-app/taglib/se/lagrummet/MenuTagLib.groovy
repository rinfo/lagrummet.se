package se.lagrummet

import groovy.xml.MarkupBuilder

class MenuTagLib {
	
	def page

	/**
	* Renders a menu based on a page tree
	* @attr root REQUIRED the page node from which the menu will be generated, identified by the permalink. the root is not itself included in the menu, only its children
	* @attr headerStyles a list of css classes which will be applied to top level menu headers
	* @attr rootTag a tag that will wrap the menu as root element
	* @attr activePage the page that is currently viewed will be highlighted as "active" in the menus
	*
	*/
   def menu = { attrs, body ->
	   if(!attrs.root) {
		   throwTagError("Tag [menu] is missing required attribute [root]")
	   }
	   def headerStyles = []
	   if(attrs.headerStyles) {
		   headerStyles = attrs.headerStyles
	   }
	   def rootTag = null
	   if(attrs.rootTag) {
		   rootTag = attrs.rootTag
	   }
	   page = null
	   if(attrs.activePage) {
		   page = attrs.activePage
	   }
	   def rootNode = Page.withCriteria(uniqueResult:true) {
			   eq("permalink", attrs.root)
			   eq("status", "published")
			   le('publishStart', new Date())
			   maxResults(1)
		   }
	   if(!rootNode){
		   return false
	   }
	   
	   def writer = new StringWriter()
	   def xml = new MarkupBuilder(writer)
	   xml.setOmitEmptyAttributes(true)
	   xml.setOmitNullAttributes(true)
	   int i = 0
	   int size = headerStyles.size()
	   
	   if(rootTag) {
		   xml."$rootTag" {
			   buildMenu(xml, rootNode, body)
		   }
	   } else {
	   		buildMenu(xml, rootNode, body)
	   }
	   
	   
		out << writer.toString()
   }
   
   def buildMenu(builder, rootNode, body) {
	   rootNode.publishedChildren.each { node ->
		   
		   if(node.metaPage) {
			   subMenu(builder, node)
		   } else {
			   createListElement(builder, node)
		   }
	   }
		   
	   if(body) {
		   builder.getMkp().yieldUnescaped(body())
	   }
   }
   
   
   def subMenu(builder, metaNode) {
	   
	   builder.ul('class':metaNode.menuStyle) {
		   li('class':'heading', metaNode.title)
		   metaNode.publishedChildren.each{ node ->
			   createListElement(builder, node)
		   }
	   }
   }
   
   def createHeadlessList(builder, nodes) {
	   builder.ul {
		   nodes.each { node ->
			   createListElement(builder, node)
		   }
	   }
   }
   
   def createListElement(builder, node) {
	   def active = ""
	   if(node.id == page?.id){
		   active = "active"
	   }
	   builder.li('class':active) {
		   if(node.metaPage) {
			   subMenu(builder, node)
		   } else if(node.publishedChildren.size() > 0) {
			   createLinkElement(builder, node)
			   createHeadlessList(builder, node.publishedChildren)
		   } else {
			   createLinkElement(builder, node)
		   }
		   
	   }
   }
   
   def createLinkElement(builder, node) {
	   builder.a(href:createLink(mapping:'page', params:[permalink:node.url()]), node.title)
   }
}
