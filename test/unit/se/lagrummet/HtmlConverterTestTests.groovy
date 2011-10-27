package se.lagrummet

import grails.test.*

class HtmlConverterTestTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testHtmlToString() {
		def htmlString = "<b>hello</b> &auml;r du d&auml;r?"
		def expected = "hello är du där?"
		
		HtmlConverter conv = new HtmlConverter()
		def plainString = conv.toString(htmlString, null)
		
		assertEquals(expected, plainString)
		

    }
	
	void testPlainString() {
		def original = "hello är du där?"
		
		HtmlConverter conv = new HtmlConverter()
		def converted = conv.toString(original, null)
		
		assertEquals(original, converted)
	}
}
