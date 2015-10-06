package setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumDriver {

    static WebDriver driver;

    public static final String USERNAME = System.getProperty("lagrummet.browserstack.username");
    public static final String AUTOMATE_KEY = System.getProperty("lagrummet.browserstack.automatekey");
    public static final String REMOTE_URL = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";


    public static WebDriver getDriver() {
        if (driver == null) {
//            driver = new FirefoxDriver();
            try {
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                caps.setCapability("browser", "Firefox");
                caps.setCapability("browser_version", "40.0");
                caps.setCapability("os", "OS X");
                caps.setCapability("os_version", "Yosemite");
                caps.setCapability("resolution", "1280x960");
                caps.setCapability("browserstack.debug", "true");
                driver = new RemoteWebDriver(new URL(REMOTE_URL), caps);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return driver;
    }

}