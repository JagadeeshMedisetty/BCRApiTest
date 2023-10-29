package stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

public class BuggyCarsStepDefs {
    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;
    String token = null;

    private String ENDPOINT_BUGGY_CARS_REGISTRATION = "https://k51qryqov3.execute-api.ap-southeast-2.amazonaws.com/prod/users";
    private String ENDPOINT_BUGGY_CARS_LOGIN = "https://k51qryqov3.execute-api.ap-southeast-2.amazonaws.com/prod/oauth/token";

    private String userName = null;
    private String password = null;

    @Given("I register with Buggy Rating App with \\\"([^\\\"]*)\\\", \\\"([^\\\"]*)\\\", \\\"([^\\\"]*)\\\", \\\"([^\\\"]*)\\\"")
    public void i_register_with_buggy_rating_app_with(String username, String firstName, String lastName, String password) {
        Random random = new Random();
        int randomInt = random.nextInt(10000000);
        this.userName = username+randomInt;
        this.password = password;

        request = given().
                request()
                .body("{\"username\":\""+username+randomInt+"@mailinator.com\",\"firstName\":\""+firstName+"\",\"lastName\":\""+lastName+"\",\"password\":\""+password+"\",\"confirmPassword\":\""+password+"\"}")
                .header("Accept","*/*")
                .header("Content-Type","application/json")
                .header("Origin", "https://buggy.justtestit.org")
                .header("Referer", "https://buggy.justtestit.org/");

        response = request.when().post(ENDPOINT_BUGGY_CARS_REGISTRATION);
        System.out.println("Created user====================================>"+username+randomInt+"@mailinator.com&password="+password);


    }
    @When("I login with the credentials")
    public void i_login_with_the_credentials() {
        System.out.println("Logging in with created user credentials");
        String username= this.userName;
        String password = this.password;
        request = given().
                request()
                .body("grant_type=password&username="+username+"@mailinator.com&password="+password+"")
                .header("Accept","*/*")
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Origin", "https://buggy.justtestit.org")
                .header("Referer", "https://buggy.justtestit.org/");
        String token = request.when().post(ENDPOINT_BUGGY_CARS_LOGIN).jsonPath().getString("access_token");
        this.token = token;
    }
    @Then("I should get a valid token in response")
    public void i_should_get_a_valid_token_in_response() {
        assert (this.token != null);
    }


}
