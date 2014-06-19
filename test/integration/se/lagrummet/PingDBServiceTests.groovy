package se.lagrummet

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(PingDBService)
class PingDBServiceTests extends Specification {

    def pingDBService

    void "Test DB Ping"() {
        when: "Calling PingDBService"
        then: "Should not throw an exception"
        pingDBService.pingDB()
    }
}
