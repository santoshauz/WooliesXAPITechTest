package WooliesXRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import com.cucumber.listener.Reporter;



import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "html:target/cucumber-pretty", "json:target/cucumber.json"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
        tags={"@happyHolidayMaker"},
        features = {"src/test/resources/feature"})

public class TestRunner {
    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File("src/test/config/extent-config.xml"));
    }
}
