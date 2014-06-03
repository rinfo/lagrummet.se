package se.lagrummet

import grails.test.GrailsUnitTestCase

/**
 * Created with IntelliJ IDEA.
 * User: christian
 * Date: 6/2/14
 * Time: 8:56 AM
 * To change this template use File | Settings | File Templates.
 */
class PingDBServiceTests extends GrailsUnitTestCase {

    def pingDBService

    void testPing() {
        pingDBService.pingDB()
    }
}
