package se.lagrummet

import grails.test.*

import org.jsoup.Jsoup

class HtmlSanitizerServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

   void testDeleteBaseTags() {
		
	   	HtmlSanitizerService service = new HtmlSanitizerService()
		String dirtyHtml = '<html><base href="http://www.w3schools.com/images/" target="_blank" />'
		dirtyHtml += '<body><table><tr><td>cell1</td><td>cell2</td></tr></html>'

		String expectedHtml = ''
		expectedHtml += '<table><tr><td>cell1</td><td>cell2</td></tr>'

		
		String cleanHtml = service.cleanHtml(dirtyHtml)
		System.out.println(cleanHtml)
		assertEquals(1, Jsoup.parse(dirtyHtml).select("base").size())
		assertEquals(0, Jsoup.parse(cleanHtml).select("base").size())
		
		

	}
   
   void testStripTags() {
	   HtmlSanitizerService service = new HtmlSanitizerService()
	   
	   String dirtyHtml = '<div>This is text <a href="http://www.somelink.com">This is a link</a></div>'
	   String expectedHtml = '<div>This is text This is a link</div>'
	   
	   def tags = ['a']
	   String cleanHtml = service.stripTags(dirtyHtml, tags)	   
	   assertEquals(expectedHtml, cleanHtml)
   }
}
