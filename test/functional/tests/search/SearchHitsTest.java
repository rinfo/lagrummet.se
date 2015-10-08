package tests.search;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.SearchResultPage;

import java.net.MalformedURLException;

import static junit.framework.TestCase.assertEquals;
import static setup.SeleniumDriver.getDriver;

public class SearchHitsTest {

    private SearchResultPage searchResultPage;

    public SearchHitsTest() {
        searchResultPage = PageFactory.initElements(getDriver(), SearchResultPage.class);
    }

    @Test
    public void searchForRäksmörgåsar() {
        searchResultPage.searchFor("räksmörgåsar");

        assertEquals(1, searchResultPage.getTotalHits());
        assertEquals(1, searchResultPage.getNumberOfCourtCasesHits());

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "AD 2005 nr 4"));
        assertEquals("räksmörgåsar", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "AD 2005 nr 4").get(0));
    }

    @Test
    public void searchForSvartbukigFlyghöna() {
        searchResultPage.searchFor("svartbukig flyghöna");

        assertEquals(5, searchResultPage.getTotalHits());
        assertEquals(5, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Artskyddsförordning (1998:179)"));
        assertEquals("svartbukig", searchResultPage.getMatchTermsBySearchHit("LagarList", "Artskyddsförordning (1998:179)").get(0));
        assertEquals("flyghöna", searchResultPage.getMatchTermsBySearchHit("LagarList", "Artskyddsförordning (1998:179)").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Artskyddsförordning (2007:845)"));
        assertEquals("svartbukig", searchResultPage.getMatchTermsBySearchHit("LagarList", "Artskyddsförordning (2007:845)").get(0));
        assertEquals("flyghöna", searchResultPage.getMatchTermsBySearchHit("LagarList", "Artskyddsförordning (2007:845)").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Förordning (2006:1017) om ändring i artskyddsförordningen\n" +
                "(1998:179)"));
        assertEquals("svartbukig", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2006:1017) om ändring i artskyddsförordningen\n" +
                "(1998:179)").get(0));
        assertEquals("flyghöna", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2006:1017) om ändring i artskyddsförordningen\n" +
                "(1998:179)").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Förordning (2001:447) om ändring i artskyddsförordningen\n" +
                "(1998:179)"));
        assertEquals("svartbukig", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2001:447) om ändring i artskyddsförordningen\n" +
                "(1998:179)").get(0));
        assertEquals("flyghöna", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2001:447) om ändring i artskyddsförordningen\n" +
                "(1998:179)").get(1));
    }

    @Test
    public void searchForColumbaOenasSkogsduva() {
        searchResultPage.searchFor("Columba oenas skogsduva");

        assertEquals(5, searchResultPage.getTotalHits());
        assertEquals(5, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Artskyddsförordning (1998:179)"));
        assertEquals("oenas", searchResultPage.getMatchTermsBySearchHit("LagarList", "Artskyddsförordning (1998:179)").get(0));
        assertEquals("skogsduva", searchResultPage.getMatchTermsBySearchHit("LagarList", "Artskyddsförordning (1998:179)").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Artskyddsförordning (2007:845)"));
        assertEquals("Columba", searchResultPage.getMatchTermsBySearchHit("LagarList", "Artskyddsförordning (2007:845)").get(0));
        assertEquals("oenas", searchResultPage.getMatchTermsBySearchHit("LagarList", "Artskyddsförordning (2007:845)").get(1));
        assertEquals("skogsduva", searchResultPage.getMatchTermsBySearchHit("LagarList", "Artskyddsförordning (2007:845)").get(2));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Förordning (2006:1017) om ändring i artskyddsförordningen\n" +
                "(1998:179)"));
        assertEquals("oenas", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2006:1017) om ändring i artskyddsförordningen\n" +
                "(1998:179)").get(0));
        assertEquals("skogsduva", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2006:1017) om ändring i artskyddsförordningen\n" +
                "(1998:179)").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Förordning (2001:447) om ändring i artskyddsförordningen\n" +
                "(1998:179)"));
        assertEquals("Columba", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2001:447) om ändring i artskyddsförordningen\n" +
                "(1998:179)").get(0));
        assertEquals("oenas", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2001:447) om ändring i artskyddsförordningen\n" +
                "(1998:179)").get(1));
        assertEquals("skogsduva", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2001:447) om ändring i artskyddsförordningen\n" +
                "(1998:179)").get(2));
    }

    @Test
    public void searchForHembranning() {
        searchResultPage.searchFor("hembränning");

        assertEquals(4, searchResultPage.getTotalHits());
        assertEquals(4, searchResultPage.getNumberOfCourtCasesHits());

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "RH 2000:81"));
        assertEquals("hembränning", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 2000:81").get(0));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "RH 2003:37"));
        assertEquals("Hembränningsapparaten", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 2003:37").get(0));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "RH 1999:26"));
        assertEquals("hembränningsapparat", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 1999:26").get(0));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "NJA 2002 s. 123"));
        assertEquals("hembränningsapparat", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "NJA 2002 s. 123").get(0));
    }

    @Test
    public void searchForTillsynOverHundarOchKatter() {
        searchResultPage.searchFor("tillsyn över hundar och katter");

        assertEquals(24, searchResultPage.getTotalHits());
        assertEquals(8, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(16, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "RH 2007:5"));
        assertEquals("tillsyn", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 2007:5").get(0));
        assertEquals("hundar", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 2007:5").get(1));
        assertEquals("katter", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 2007:5").get(2));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "NJA 1990 s. 80"));
        assertEquals("tillsyn", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 2007:5").get(0));
        assertEquals("hundar", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 2007:5").get(1));
        assertEquals("katter", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 2007:5").get(2));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "NJA 2001 s. 115"));
        assertEquals("hundar", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "NJA 2001 s. 115").get(0));
        assertEquals("katter", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "NJA 2001 s. 115").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "B1479-05"));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Förordning (1995:1300) om statliga myndigheters riskhantering"));
        assertEquals("hundar", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (1995:1300) om statliga myndigheters riskhantering").get(0));
        assertEquals("katter", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (1995:1300) om statliga myndigheters riskhantering").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Kungörelse (1972:416) om statsmyndigheternas\n" +
                "skadereglering i vissa fall"));
        assertEquals("hundar", searchResultPage.getMatchTermsBySearchHit("LagarList", "Kungörelse (1972:416) om statsmyndigheternas\n" +
                "skadereglering i vissa fall").get(0));
        assertEquals("katter", searchResultPage.getMatchTermsBySearchHit("LagarList", "Kungörelse (1972:416) om statsmyndigheternas\n" +
                "skadereglering i vissa fall").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Lag (1943:459)"));
        assertEquals("tillsyn", searchResultPage.getMatchTermsInTitleBySearchHit("LagarList", "Lag (1943:459)").get(0));
        assertEquals("hundar", searchResultPage.getMatchTermsInTitleBySearchHit("LagarList", "Lag (1943:459)").get(1));
        assertEquals("katter", searchResultPage.getMatchTermsInTitleBySearchHit("LagarList", "Lag (1943:459)").get(2));
        assertEquals("tillsyn", searchResultPage.getMatchTermsBySearchHit("LagarList", "Lag (1943:459)").get(0));
        assertEquals("hundar", searchResultPage.getMatchTermsBySearchHit("LagarList", "Lag (1943:459)").get(1));
        assertEquals("katterUtfärdad", searchResultPage.getMatchTermsBySearchHit("LagarList", "Lag (1943:459)").get(2));
        assertEquals("katter", searchResultPage.getMatchTermsBySearchHit("LagarList", "Lag (1943:459)").get(3));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Förordning (1995:1301) om handläggning av skadeståndsanspråk mot staten"));
        assertEquals("tillsyn", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (1995:1301) om handläggning av skadeståndsanspråk mot staten").get(0));
        assertEquals("hundar", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (1995:1301) om handläggning av skadeståndsanspråk mot staten").get(1));
        assertEquals("katter", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (1995:1301) om handläggning av skadeståndsanspråk mot staten").get(2));
    }

    @Test
    public void searchForInternationelltMaterial() {
        searchResultPage.searchFor("internationellt material");

        assertEquals(198, searchResultPage.getTotalHits());
        assertEquals(4, searchResultPage.getNumberOfInformationHits());
        assertEquals(62, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(132, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals(2, searchResultPage.searchHitsByCategoryAndTitle("LagrummetList", "Internationellt material"));
        assertEquals("Internationellt", searchResultPage.getMatchTermsBySearchHit("LagrummetList", "Internationellt material").get(0));
        assertEquals("material", searchResultPage.getMatchTermsBySearchHit("LagrummetList", "Internationellt material").get(1));
        assertEquals("internationellt", searchResultPage.getMatchTermsBySearchHit("LagrummetList", "Internationellt material").get(2));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagrummetList", "Rättsinformation - rättskällorna"));
        assertEquals("internationellt", searchResultPage.getMatchTermsBySearchHit("LagrummetList", "Rättsinformation - rättskällorna").get(0));
        assertEquals("material", searchResultPage.getMatchTermsBySearchHit("LagrummetList", "Rättsinformation - rättskällorna").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagrummetList", "Ordlista A–Ö"));
        assertEquals("internationellt", searchResultPage.getMatchTermsBySearchHit("LagrummetList", "Ordlista A–Ö").get(0));
        assertEquals("material", searchResultPage.getMatchTermsBySearchHit("LagrummetList", "Ordlista A–Ö").get(1));
    }

    @Test
    public void searchForLagOmDjurskydd() {
        searchResultPage.searchFor("lag om djurskydd");

        assertEquals(1, searchResultPage.getTotalHits());
        assertEquals(1, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", " (2009:619) om djurskyddskontrollregister"));
        assertEquals("Lag", searchResultPage.getMatchTermsInTitleBySearchHit("LagarList", " (2009:619) om djurskyddskontrollregister").get(0));
        assertEquals("Lag", searchResultPage.getMatchTermsBySearchHit("LagarList", " (2009:619) om djurskyddskontrollregister").get(0));
        assertEquals("djurskyddskontrollregister", searchResultPage.getMatchTermsBySearchHit("LagarList", " (2009:619) om djurskyddskontrollregister").get(1));
    }

    @Test
    public void searchForDjurskydd() {
        searchResultPage.searchFor("djurskydd");

        assertEquals(399, searchResultPage.getTotalHits());
        assertEquals(51, searchResultPage.getNumberOfCourtCasesHits());
        assertEquals(348, searchResultPage.getNumberOfLawsAndRegulationsHits());

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "RÅ 2009 ref. 75"));
        assertEquals("djurskyddslagen", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RÅ 2009 ref. 75").get(0));
        assertEquals("Djurskyddsmyndighetens", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RÅ 2009 ref. 75").get(1));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "RÅ 2003 ref. 8"));
        assertEquals("djurskydd", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RÅ 2003 ref. 8").get(0));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "RH 2014:14"));
        assertEquals("djurskyddslagen", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "RH 2014:14").get(0));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("RattsfallList", "MÖD 2007:27"));
        assertEquals("djurskydd", searchResultPage.getMatchTermsBySearchHit("RattsfallList", "MÖD 2007:27").get(0));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Lag (1944:219)\nom "));
        assertEquals("djurskydd", searchResultPage.getMatchTermsInTitleBySearchHit("LagarList", "Lag (1944:219)\nom ").get(0));
        assertEquals("djurskyddSFS 1944:219", searchResultPage.getMatchTermsBySearchHit("LagarList", "Lag (1944:219)\nom ").get(0));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Lag (2008:1048) om skyldighet för kommunerna att lämna uppgifter om "));
        assertEquals("djurskydd", searchResultPage.getMatchTermsInTitleBySearchHit("LagarList", "Lag (2008:1048) om skyldighet för kommunerna att lämna uppgifter om ").get(0));
        assertEquals("djurskydd", searchResultPage.getMatchTermsBySearchHit("LagarList", "Lag (2008:1048) om skyldighet för kommunerna att lämna uppgifter om ").get(0));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", " (1979:287)"));
        assertEquals("Djurskyddsförordning", searchResultPage.getMatchTermsInTitleBySearchHit("LagarList", " (1979:287)").get(0));
        assertEquals("djurskydd", searchResultPage.getMatchTermsBySearchHit("LagarList", " (1979:287)").get(0));

        assertEquals(1, searchResultPage.searchHitsByCategoryAndTitle("LagarList", "Förordning (2003:1125) med instruktion för "));
        assertEquals("Djurskyddsmyndigheten", searchResultPage.getMatchTermsInTitleBySearchHit("LagarList", "Förordning (2003:1125) med instruktion för ").get(0));
        assertEquals("Djurskyddsmyndighetens", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2003:1125) med instruktion för ").get(0));
        assertEquals("djurskydd", searchResultPage.getMatchTermsBySearchHit("LagarList", "Förordning (2003:1125) med instruktion för ").get(1));
    }
}
