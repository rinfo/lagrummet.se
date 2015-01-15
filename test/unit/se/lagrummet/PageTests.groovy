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

        println "PageTests.testConstraints *************************************************************************"
        println "page.errors.size="+page.errors.allErrors.size()
        page.errors.allErrors.each { println it }
        println "PageTests.testConstraints *************************************************************************"
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

    void testThatPageStatusDraftIsNotConsideredPublished() {
        def page = new Page()
        page.status = 'draft'
        page.publishStart = new Date()
        page.publishStart.minus(1)

        assertEquals(false, page.isCurrentlyPublished())
    }


    void testThatPageStatusPublishedIsConsideredPublished() {
        def page = new Page()
        page.status = 'published'
        page.publishStart = new Date()
        page.publishStart.minus(1)
        sleep(10) //fails otherwise sometimes. wierd.
        assertEquals(true, page.isCurrentlyPublished())
    }

    void testThatPageHasBeenPublishedEarlier() {
        def page = new Page()

        def previouslyPublishedPage = new Page()
        previouslyPublishedPage.status = 'published'
        previouslyPublishedPage.publishStart = new Date().minus(1)

        def previouslyNotPublishedPage = new Page()

        page.autoSaves = [previouslyNotPublishedPage, previouslyPublishedPage]

        assertEquals(true, page.hasBeenPublished())
    }

    void testThatPageHasNotBeenPublishedEarlier() {
        def page = new Page()

        def previouslyNotPublishedPage = new Page()

        page.autoSaves = [previouslyNotPublishedPage]

        assertEquals(false, page.hasBeenPublished())
    }
}
