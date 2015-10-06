package tests;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.SearchResultPage;

import java.net.MalformedURLException;

import static junit.framework.TestCase.assertEquals;
import static setup.SeleniumDriver.getDriver;

public class ExactHitsTest {

    private SearchResultPage searchResultPage = PageFactory.initElements(getDriver(), SearchResultPage.class);

    @Test
    public void searchForRäksmörgåsar() {
        //when
        searchResultPage.searchFor("SFS 1990:10");

        //then
        assertEquals(1, searchResultPage.getMatchTerms().size());
        assertEquals("SFS 1990:10", searchResultPage.getMatchTerms().get(0));
        assertEquals(1, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals("AD 2005 nr 4", searchResultPage.getCourtCaseHits().get(0));
    }

    @AfterClass
    public static void tearDown() throws MalformedURLException {
        getDriver().quit();
    }

}
