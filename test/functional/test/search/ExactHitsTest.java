package test.search;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.SearchResultPage;

import java.net.MalformedURLException;

import static junit.framework.TestCase.assertEquals;
import static setup.SeleniumDriver.getDriver;

public class ExactHitsTest {

    private SearchResultPage searchResultPage;

    public ExactHitsTest() {
        searchResultPage = PageFactory.initElements(getDriver(), SearchResultPage.class);
    }

    @Test
    public void searchForSFS() {
        //when
        searchResultPage.searchFor("SFS 1990:10");

        //then
        assertEquals(22, searchResultPage.getTotalHits());
        assertEquals(1, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(21, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals("RÅ 2007 ref. 44", searchResultPage.getCourtCaseHits().get(0));
        assertEquals("Förordning (1990:10) om fortsatt giltighet av förordningen (1986:419) om försöksverksamhet med en samordnad länsförvaltning i Norrbottens län", searchResultPage.getLawsAndRegulationsHits().get(0));
    }

    @Test
    public void searchForNJA() {
        //when
        searchResultPage.searchFor("NJA 2005 s. 11");

        //then
        assertEquals(134, searchResultPage.getTotalHits());
        assertEquals(134, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals("NJA 2005 s. 11", searchResultPage.getCourtCaseHits().get(0));
    }

    @Test
    public void searchForHFD() {
        //when
        searchResultPage.searchFor("HFD 2011 ref. 67");

        //then
        assertEquals(2, searchResultPage.getTotalHits());
        assertEquals(2, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals("HFD 2011 ref. 67", searchResultPage.getCourtCaseHits().get(0));
    }

    @Test
    public void searchForRA() {
        //when
        searchResultPage.searchFor("RÅ 2010 ref. 9");

        //then
        assertEquals(107, searchResultPage.getTotalHits());
        assertEquals(106, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(1, searchResultPage.getNumberOfInformationHits());

        assertEquals("Vanliga frågor", searchResultPage.getInformationHits().get(0));
        assertEquals("RÅ 2010 ref. 9", searchResultPage.getCourtCaseHits().get(2));
    }

    @Test
    public void searchForRA_2() {
        //when
        searchResultPage.searchFor("RÅ 2010 ref. 22");

        //then
        assertEquals(83, searchResultPage.getTotalHits());
        assertEquals(83, searchResultPage.getNumberOfCourtCasesHits());

        assertEquals("RÅ 2010 ref. 22", searchResultPage.getCourtCaseHits().get(0));
    }

    @Test
    public void searchForRH() {
        //when
        searchResultPage.searchFor("RH 2011:26");

        //then
        assertEquals(6, searchResultPage.getTotalHits());
        assertEquals(5, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(1, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals("RH 2011:26", searchResultPage.getCourtCaseHits().get(0));
        assertEquals("Förordning (2011:26) om ändring i förordningen (1998:940) om avgifter för prövning och tillsyn enligt miljöbalken", searchResultPage.getLawsAndRegulationsHits().get(0));
    }

    @Test
    public void searchForHFD_2() {
        //when
        searchResultPage.searchFor("RÅ 2006 ref. 1");

        //then
        assertEquals(229, searchResultPage.getTotalHits());
        assertEquals(225, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(2, searchResultPage.getNumberOfLawsAndRegulationsHits());
        assertEquals(2, searchResultPage.getNumberOfInformationHits());

        assertEquals("RÅ 2006 ref. 1", searchResultPage.getCourtCaseHits().get(1));
        assertEquals("Förordning (2009:231) om ändring i förordningen (2001:1085) om motorfordons avgasrening", searchResultPage.getLawsAndRegulationsHits().get(0));
        assertEquals("Vanliga frågor", searchResultPage.getInformationHits().get(0));
    }

    @Test
    public void searchForMOD() {
        //when
        searchResultPage.searchFor("MÖD 2003:12");

        //then
        assertEquals(4, searchResultPage.getTotalHits());
        assertEquals(3, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(1, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals("MÖD 2011:24", searchResultPage.getCourtCaseHits().get(0));
        assertEquals("MÖD 2003:12", searchResultPage.getCourtCaseHits().get(1));
        assertEquals("RH 2003:12", searchResultPage.getCourtCaseHits().get(2));
    }

    @Test
    public void searchForMOD_2() {
        //when
        searchResultPage.searchFor("MÖD 2000:30");

        //then
        assertEquals(3, searchResultPage.getTotalHits());
        assertEquals(2, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(1, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals("MÖD 2000:30", searchResultPage.getCourtCaseHits().get(0));
        assertEquals("RH 2000:30", searchResultPage.getCourtCaseHits().get(1));
    }

    @Test
    public void searchForAD() {
        //when
        searchResultPage.searchFor("AD 2010 nr 73");

        //then
        assertEquals(18, searchResultPage.getTotalHits());
        assertEquals(18, searchResultPage.getNumberOfCourtCasesHits());

        assertEquals("AD 2010 nr 73", searchResultPage.getCourtCaseHits().get(0));
    }

    @Test
    public void searchForMD() {
        //when
        searchResultPage.searchFor("MD 2011:23");

        //then
        assertEquals(5, searchResultPage.getTotalHits());
        assertEquals(4, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(1, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals("MD 2011:23", searchResultPage.getCourtCaseHits().get(0));
    }
}
