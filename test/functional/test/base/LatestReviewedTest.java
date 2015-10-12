package test.base;

import org.junit.Test;
import pages.menu.*;

import static org.junit.Assert.assertEquals;

public class LatestReviewedTest {

    @Test
    public void latestReviewedOnLawsAndRegulationsPage() {
        LawsAndRegulationsPage lawsAndRegulationsPage = new LawsAndRegulationsPage().openPage(LawsAndRegulationsPage.class);

        assertEquals(true, lawsAndRegulationsPage.getFooterPage().latestReviewsLabelPresent());
    }

    @Test
    public void latestReviewedOnRegulationsPage() {
        RegulationsPage regulationsPage = new RegulationsPage().openPage(RegulationsPage.class);

        assertEquals(true, regulationsPage.getFooterPage().latestReviewsLabelPresent());
    }

    @Test
    public void latestReviewedOnGroundworksPage() {
        GroundworksPage groundworksPage = new GroundworksPage().openPage(GroundworksPage.class);

        assertEquals(true, groundworksPage.getFooterPage().latestReviewsLabelPresent());
    }

    @Test
    public void latestReviewedOnCaseLawsPage() {
        CaseLawPage caseLawPage = new CaseLawPage().openPage(CaseLawPage.class);

        assertEquals(true, caseLawPage.getFooterPage().latestReviewsLabelPresent());
    }

    @Test
    public void latestReviewedOnInternationalMaterialPage() {
        InternationalMaterialPage internationalMaterialPage = new InternationalMaterialPage().openPage(InternationalMaterialPage.class);

        assertEquals(true, internationalMaterialPage.getFooterPage().latestReviewsLabelPresent());
    }

    @Test
    public void latestReviewedOnAllLegalSourcesPage() {
        AllLegalSourcesPage allLegalSourcesPage = new AllLegalSourcesPage().openPage(AllLegalSourcesPage.class);

        assertEquals(true, allLegalSourcesPage.getFooterPage().latestReviewsLabelPresent());
    }

    @Test
    public void latestReviewedOnResponsibilitiesPage() {
        ResponsibilitiesPage responsibilitiesPage = new ResponsibilitiesPage().openPage(ResponsibilitiesPage.class);

        assertEquals(true, responsibilitiesPage.getFooterPage().latestReviewsLabelPresent());
    }

    @Test
    public void latestReviewedOnWordLinksPage() {
        WordLinksPage wordLinksPage = new WordLinksPage().openPage(WordLinksPage.class);

        assertEquals(true, wordLinksPage.getFooterPage().latestReviewsLabelPresent());
    }

    @Test
    public void latestReviewedOnRightsProcessPage() {
        RightsProcessPage rightsProcessPage = new RightsProcessPage().openPage(RightsProcessPage.class);

        assertEquals(true, rightsProcessPage.getFooterPage().latestReviewsLabelPresent());
    }

    @Test
    public void latestReviewedOnFaqPage() {
        FaqPage faqPage = new FaqPage().openPage(FaqPage.class);

        assertEquals(true, faqPage.getFooterPage().latestReviewsLabelPresent());
    }
}
