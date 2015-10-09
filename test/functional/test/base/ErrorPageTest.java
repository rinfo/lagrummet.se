package test.base;

import org.junit.Test;
import pages.ErrorPage;

import static org.junit.Assert.assertEquals;

public class ErrorPageTest {

    private ErrorPage errorPage;

    public ErrorPageTest() {
        errorPage = new ErrorPage().openPage(ErrorPage.class);
    }

    @Test
    public void errorPageDisplayed() {
        assertEquals(true, errorPage.isAt());
    }
}
