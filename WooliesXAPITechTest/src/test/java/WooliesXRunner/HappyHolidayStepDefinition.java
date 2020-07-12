package WooliesXRunner;


import com.aventstack.extentreports.ExtentReports;
import com.cucumber.listener.Reporter;
import com.jayway.restassured.path.json.JsonPath;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import utility.InvocationUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Step Definition for HappyHolidayMaker feature
 */
    public class HappyHolidayStepDefinition {
    private String city;
    private String country;
    private boolean thursday;
    private InvocationUtil invokeUtilObj = new InvocationUtil();
    private String responseStr;
    private static Logger logger = Logger.getLogger("HappyHolidayStepDefinition.class");
    private Map<String, String> mapWebserviceResponse = new HashMap<>();
    private ExtentReports extent;


    /**
     * Passing the city and country code through feature file
     * @param holidayCity
     * @param holidayCountry
     */
    @Given("^I like to holiday in (.+?) of (.+?)$")
    public void i_like_to_holiday_in_city_of_country(String holidayCity, String holidayCountry) {
        city = holidayCity;
        country = holidayCountry;
        Reporter.addStepLog("City is => " +city);
        Reporter.addStepLog("Country is => " +country);
    }

    /**
     * Pass the Weekday to holiday though feature file
     * Sunday = 1, Monday = 2, Tuesday = 3 etc
     * @param holidayDayOfTheWeek
     */
    @Given("^I only like to holiday on (.+?)$")
    public void i_only_like_to_holiday_on_dayOfTheWeek(int holidayDayOfTheWeek) {
        Calendar cal = Calendar.getInstance();
        thursday = cal.get(Calendar.DAY_OF_WEEK) == holidayDayOfTheWeek;
    }

    /**
     * Invoke the service only if its Thursday
     */
    @When("^I look up the weather forecast$")
    public void i_look_up_the_weather_forecast() {
        String appId = "113a10ed29081f38ca59db22c763e86d";
        if (thursday) {
            String endPoint = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={appId}&units=metric";
            Reporter.addStepLog("Endpoint is => " +endPoint);
            mapWebserviceResponse = invokeUtilObj.invoke(endPoint, city, country, appId);
            responseStr = mapWebserviceResponse.get("response");
            logger.info("HappyHoliday Response : " + responseStr);
        } else
            logger.info("Today is not thursday. So not checking the weather... ");
    }

    /**
     * Assert if the response content Type is JSON
     */
    @Then("^I receive the weather forecast$")
    public void i_receive_the_weather_forecast() {
        String nullString = null;
        if (thursday) {
            Assert.assertTrue(!responseStr.equals(nullString) && mapWebserviceResponse.get("contentType").contains("application/json"));
            Reporter.addStepLog("Response Content Type is JSON");
        }
    }

    /**
     * Passing the required temperature through the feature file
     * Assert if the temperature is above 10 degrees
     */
    @Then("^the temperature is warmer than (\\d+) degrees$")
    public void the_temperature_is_warmer_than_degrees(int checkTemperature) {
        if (thursday) {
            com.jayway.restassured.path.json.JsonPath jp = new JsonPath(responseStr);
            String temperature = jp.get("main.temp").toString();
            String responseCity = jp.get("name").toString();
            float temp = Float.parseFloat(temperature);
            Assert.assertTrue("Assertion has failed", Float.compare(temp, (float) checkTemperature) > 0 && city.equalsIgnoreCase(responseCity));
            if (Float.compare(temp, (float) checkTemperature) > 0 && city.equalsIgnoreCase(responseCity)) {
                Reporter.addStepLog("Temperature is greater than 10 degrees in " + responseCity + ".It is forecasted to be: " + temp + "degrees");
            }
        }
    }
}