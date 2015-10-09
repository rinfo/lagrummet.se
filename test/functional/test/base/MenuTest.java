package test.base;

import org.junit.Test;
import pages.StartPage;

import static org.junit.Assert.assertEquals;

public class MenuTest {

    private StartPage startPage;

    public MenuTest() {
        startPage = new StartPage().openPage(StartPage.class);
    }

    @Test
    public void menuElementsPresent() {
        assertEquals(true,startPage.getMenuPage().lawsAndRegulationsLinkPresent());
        assertEquals(true,startPage.getMenuPage().regulationsLinkPresent());
        assertEquals(true,startPage.getMenuPage().groundworksLinkPresent());
        assertEquals(true,startPage.getMenuPage().caseLawLinksPresent());
        assertEquals(true,startPage.getMenuPage().internationalMaterialLinkPresent());
        assertEquals(true,startPage.getMenuPage().allLegalSourcesLinkPresent());
        assertEquals(true,startPage.getMenuPage().responsibilitesLinkPresent());
        assertEquals(true,startPage.getMenuPage().wordListLinkPresent());
        assertEquals(true,startPage.getMenuPage().rightProcessLinkPresent());
        assertEquals(true,startPage.getMenuPage().faqLinkPresent());
    }
}
