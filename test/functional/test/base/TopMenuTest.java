package test.base;

import org.junit.Test;
import pages.StartPage;

import static org.junit.Assert.assertEquals;

public class TopMenuTest {

    private StartPage startPage;

    public TopMenuTest() {
        startPage = new StartPage().openPage(StartPage.class);
    }

    @Test
    public void topMenuLinks() {
        assertEquals(true, startPage.getHeaderPage().englishLinkPresent());
        assertEquals(true, startPage.getHeaderPage().siteMapLinkPresent());
        assertEquals(true, startPage.getHeaderPage().listenLinkPresent());
    }
}
