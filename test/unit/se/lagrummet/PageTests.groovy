package se.lagrummet

import grails.test.*

class PageTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
		mockForConstraintsTests(Page)
		
		def page = new Page()
		assertFalse(page.validate())

        //todo whatever reason test constraint nullable?
		//assertEquals("nullable", page.errors["title"])
		//assertEquals("nullable", page.errors["h1"])
		//assertEquals("nullable", page.errors["permaLink"])
		
    }
	
	void testPageOrder() {
		def page1 = new Page(pageOrder: 1)
		def page2 = new Page(pageOrder: 2)
		def page3 = new Page(pageOrder: 2)
		
		assertTrue( page1.compareTo(page2) < 0)
		
		assertTrue( page2.compareTo(page1) > 0)
		
		assertTrue(page2.compareTo(page3) == 0)

		assertTrue(page3.compareTo(page2) == 0)
		
	}
}
