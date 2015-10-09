package test.base;

import org.junit.Test;
import pages.ContactUsPage;
import pages.SiteMapPage;
import pages.StartPage;

import static org.junit.Assert.assertEquals;

public class SiteMapTest {

    private StartPage startPage;

    public SiteMapTest() {
        startPage = new StartPage().openPage(StartPage.class);
    }

    @Test
    public void clickOnSiteMap() {
        SiteMapPage siteMapPage = startPage.getHeaderPage().clickOnSiteMapLink();

        assertEquals(true, siteMapPage.isAt());
    }
}
