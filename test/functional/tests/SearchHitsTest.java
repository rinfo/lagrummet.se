package tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.SearchResultPage;

import java.net.MalformedURLException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static setup.SeleniumDriver.getDriver;

public class SearchHitsTest {

    private SearchResultPage searchResultPage = PageFactory.initElements(getDriver(), SearchResultPage.class);

    @Test
    public void searchForRäksmörgåsar() {
        //when
        searchResultPage.searchFor("räksmörgåsar");

        //then
        assertEquals(1, searchResultPage.getMatchTerms().size());
        assertEquals("räksmörgåsar", searchResultPage.getMatchTerms().get(0));
        assertEquals(1, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals("AD 2005 nr 4", searchResultPage.getCourtCaseHits().get(0));
    }

    @Test
    public void searchForSvartbukigFlyghöna() {
        //when
        searchResultPage.searchFor("svartbukig flyghöna");

        //then
        List<String> matchTerms = searchResultPage.getMatchTerms();
        List<String> lawsAndRegulationsHits = searchResultPage.getLawsAndRegulationsHits();

        assertEquals(8, matchTerms.size()); //expect 8 because search term is 2 words and only 4 are displayed
        assertEquals("svartbukig", matchTerms.get(0));
        assertEquals("flyghöna", matchTerms.get(1));
        assertEquals("svartbukig", matchTerms.get(2));
        assertEquals("flyghöna", matchTerms.get(3));
        assertEquals("svartbukig", matchTerms.get(4));
        assertEquals("flyghöna", matchTerms.get(5));
        assertEquals("svartbukig", matchTerms.get(6));
        assertEquals("flyghöna", matchTerms.get(7));

        assertEquals(5, searchResultPage.getNumberOfLawsAndRegulationsHits());
        assertEquals("Artskyddsförordning (2007:845)", lawsAndRegulationsHits.get(0));
        assertEquals("Artskyddsförordning (1998:179)", lawsAndRegulationsHits.get(1));
        assertEquals("Förordning (2006:1017) om ändring i artskyddsförordningen (1998:179)", lawsAndRegulationsHits.get(2));
        assertEquals("Förordning (2001:447) om ändring i artskyddsförordningen (1998:179)", lawsAndRegulationsHits.get(3));
    }

    @AfterClass
    public static void tearDown() throws MalformedURLException {
        getDriver().quit();
    }
}
