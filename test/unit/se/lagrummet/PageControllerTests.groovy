package se.lagrummet


import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin

import spock.lang.Specification
import org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder

@TestFor(PageController)
@Mock([SecUser])
@TestMixin(DomainClassUnitTestMixin)
class PageControllerTests extends Specification {
    void setupSpec() {
        mockDomain(Page, [new Page(permalink: 'home')])
    }

    void "Test Uniqueness of Permalink when creating a page"() {
        given:
            def tokenHolder = SynchronizerTokensHolder.store(session)
            params[SynchronizerTokensHolder.TOKEN_URI] = '/pageController/save'
            params[SynchronizerTokensHolder.TOKEN_KEY] = tokenHolder.generateToken(params[SynchronizerTokensHolder.TOKEN_URI])
            controller.springSecurityService = [principal: [id: 42]]
            def mockContract = mockFor(Page)
            mockContract.demand.static.findAllByPermalink() { new Page(permalink: 'home') }

        when:
            request.method = 'POST'
            controller.params << [permalink: 'home']
            controller.save()
        then:
            assertEquals("page.permalink.not.unique.message", flash.message)
    }
}
