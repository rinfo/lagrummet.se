package test.base;

import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.SearchHelpPage;
import pages.SearchResultPage;

import static org.junit.Assert.assertEquals;
import static setup.SeleniumDriver.getDriver;

public class SearchHelpTest {

    private SearchResultPage searchResultPage;

    public SearchHelpTest() {
        searchResultPage = PageFactory.initElements(getDriver(), SearchResultPage.class);
    }

    @Test
    public void searchHelpLinkPresent() {
        searchResultPage.searchFor("somethingreallybad");

        SearchHelpPage searchHelpPage = searchResultPage.clickOnSearchHelpLink();
        assertEquals(true, searchHelpPage.isAt());
    }
}
