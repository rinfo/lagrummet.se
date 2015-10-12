package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import setup.SeleniumDriver;

import java.util.ArrayList;
import java.util.List;

import static setup.SeleniumDriver.getDriver;

public class SearchResultPage extends BasePage <SearchResultPage>{

    @FindBy(linkText = "Sökhjälp")
    WebElement searchHelpLink;

    @Override
    public boolean isAt() {
        return false;
    }

    @Override
    public String getUrl() {
        return null;
    }

    public void searchFor(String query) {
        getDriver().get(BASE_URL + "/search?cat=Alla&query=" + query);
    }

    //termar som matchar
    public List<String> getMatchTerms() {
        List<String> terms = new ArrayList();
        List<WebElement> matchTerms = getDriver().findElements(By.cssSelector(".match"));
        for (WebElement matchTerm : matchTerms) {
            terms.add(matchTerm.getText());
        }
        return terms;
    }

    //totalt antal träffar
    public int getTotalHits() {
        String total = getDriver().findElement(By.cssSelector(".showAllLabel")).getText();
        return Integer.parseInt(total.replaceAll("[\\D]", ""));
    }

    //antal träffar i rättsfall
    public int getNumberOfCourtCasesHits() {
        String text = getDriver().findElement(By.cssSelector("#RattsfallHead > .count")).getText();
        return Integer.parseInt(getNumberInsideParenthesis(text));
    }

    //antal träffar i lagar och förordningar
    public int getNumberOfLawsAndRegulationsHits() {
        String text = getDriver().findElement(By.cssSelector("#LagarHead > .count")).getText();
        return Integer.parseInt(getNumberInsideParenthesis(text));
    }

    //antalet träffar i information från lagrummet.se
    public int getNumberOfInformationHits() {
        String text = getDriver().findElement(By.cssSelector("#LagrummetHead > .count")).getText();
        return Integer.parseInt(getNumberInsideParenthesis(text));
    }

    //träffar i rättsfall
    public List<String> getCourtCaseHits() {
        List<String> courtCases = new ArrayList();

        List<WebElement> hits = getDriver().findElements(By.cssSelector("#RattsfallList > li > p > a"));
        for (WebElement hit : hits) {
            courtCases.add(hit.getText());
        }

        return courtCases;
    }

    //träffar i lagar och förordningar
    public List<String> getLawsAndRegulationsHits() {
        List<String> courtCases = new ArrayList();

        List<WebElement> hits = getDriver().findElements(By.cssSelector("#LagarList > li > p > a"));
        for (WebElement hit : hits) {
            courtCases.add(hit.getText());
        }

        return courtCases;
    }

    //träffar i information på lagrummet.se
    public List<String> getInformationHits() {
        List<String> courtCases = new ArrayList();

        List<WebElement> hits = getDriver().findElements(By.cssSelector("#LagrummetList > li > p > a"));
        for (WebElement hit : hits) {
            courtCases.add(hit.getText());
        }

        return courtCases;
    }

    public int searchHitsByCategoryAndTitle(String category, String title) {
        List<WebElement> elements = getDriver().findElements(By.xpath(String.format("//ul[@id='%s']/li/p/a[contains(text(), '%s')]", category, title)));
        for (WebElement element : elements) {
            if(! SeleniumDriver.isDisplayed(element)) {
                return 0;
            }
        }
        return elements.size();
    }

    public List<String> getMatchTermsBySearchHit(String category, String title) {
        List<String> terms = new ArrayList();
        List<WebElement> matchTerms = getDriver().findElements(By.xpath(
                String.format("//ul[@id='%s']/li/p/a[contains(text(), '%s')]/../../p/span[@class='match']", category, title)
        ));

        //hack för att ta ut match-strängar från "information från lagrummet.se"
        //kommer som <em> istället för <span>
        if(matchTerms.isEmpty()) {
            matchTerms = getDriver().findElements(By.xpath(
                    String.format("//ul[@id='%s']/li/p/a[contains(text(), '%s')]/../../p/em[@class='match']", category, title)
            ));
        }

        for (WebElement matchTerm : matchTerms) {
            terms.add(matchTerm.getText());
        }
        return terms;
    }

    public List<String> getMatchTermsInTitleBySearchHit(String category, String title) {
        List<String> terms = new ArrayList();
        List<WebElement> matchTerms = getDriver().findElements(By.xpath(
                String.format("//ul[@id='%s']/li/p/a[contains(text(), '%s')]/../../p/a/span[@class='match']", category, title)
        ));

        for (WebElement matchTerm : matchTerms) {
            terms.add(matchTerm.getText());
        }
        return terms;
    }

    public static String getNumberInsideParenthesis(String string) {
        return string.substring(string.indexOf("(") + 1, string.indexOf(")"));
    }

    public SearchHelpPage clickOnSearchHelpLink() {
        SeleniumDriver.clickOn(searchHelpLink);
        return new SearchHelpPage();
    }
}
