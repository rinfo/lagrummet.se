package se.lagrummet

import grails.test.*

class SitePropertiesTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
		mockForConstraintsTests(SiteProperties)
		
		def sprops = new SiteProperties()
		assertFalse(sprops.validate())
		
		assertEquals("nullable", sprops.errors["siteTitle"])
		assertEquals("nullable", sprops.errors["footer"])
		assertEquals("nullable", sprops.errors["headerNavigation"])
		assertEquals("nullable", sprops.errors["primaryNavigation"])
    }
}