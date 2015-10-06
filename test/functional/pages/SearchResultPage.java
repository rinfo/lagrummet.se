package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static setup.SeleniumDriver.getDriver;

public class SearchResultPage extends BasePage {

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        return false;
    }

    @Override
    public String getUrl() {
        return null;
    }

    public void searchFor(String query){
        getDriver().get("https://beta.lagrummet.se/search?cat=Alla&query=" + query);
    }

    public List<String> getMatchTerms() {
        List<String> terms = new ArrayList();
        List<WebElement> matchTerms = getDriver().findElements(By.cssSelector(".match"));
        for (WebElement matchTerm : matchTerms) {
            terms.add(matchTerm.getText());
        }
        return terms;
    }

    public int getNumberOfCourtCasesHits() {
        String text = getDriver().findElement(By.cssSelector("#RattsfallHead > .count")).getText();
        return Integer.parseInt(getNumberInsideParenthesis(text));
    }

    public int getNumberOfLawsAndRegulationsHits() {
        String text = getDriver().findElement(By.cssSelector("#LagarHead > .count")).getText();
        return Integer.parseInt(getNumberInsideParenthesis(text));
    }

    public List<String> getCourtCaseHits() {
        List<String> courtCases = new ArrayList();

        List<WebElement> hits = getDriver().findElements(By.cssSelector("#RattsfallList > li > p > a"));
        for (WebElement hit : hits) {
            courtCases.add(hit.getText());
        }

        return courtCases;
    }

    public List<String> getLawsAndRegulationsHits() {
        List<String> courtCases = new ArrayList();

        List<WebElement> hits = getDriver().findElements(By.cssSelector("#LagarList > li > p > a"));
        for (WebElement hit : hits) {
            courtCases.add(hit.getText());
        }

        return courtCases;
    }

    public static String getNumberInsideParenthesis(String string) {
        return  string.substring(string.indexOf("(") + 1, string.indexOf(")"));
    }
}
