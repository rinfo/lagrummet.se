package test.base;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static setup.SeleniumDriver.getDriver;

@RunWith(Suite.class)
@Suite.SuiteClasses({FooterTest.class, TopMenuTest.class, SiteMapTest.class, MenuTest.class, ErrorPageTest.class, SearchHelpTest.class})
public class BaseTestSuite {

    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() {
        getDriver().quit();
    }
}
