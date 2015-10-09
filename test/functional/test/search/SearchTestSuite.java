package test.search;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static setup.SeleniumDriver.getDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses({ExactHitsTest.class, SearchHitsTest.class})
public class SearchTestSuite {

    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() {
        getDriver().quit();
    }
}