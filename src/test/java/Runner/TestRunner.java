package Runner;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "features", glue = "stepDefination", plugin = { "pretty",
        "html:target/cucumber-reports/cucumber-pretty", "json:target/cucumber-reports/CucumberTestReport.json",
        "timeline:target/test-output-thread/" }, dryRun = false)
public class TestRunner extends AbstractTestNGCucumberTests {

//    @Override
//    @DataProvider(parallel = false)
//    public Object[][] scenarios() {
//        return super.scenarios();
//    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("================ BEFORE SUITE ================");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("================ AFTER SUITE ================");
    }
}
