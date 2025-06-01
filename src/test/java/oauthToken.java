import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class oauthToken {

    public final String baseURI = "https://rahulshettyacademy.com/oauthapi";
    public static getCourses courses;


    @Test(priority = 1)
    public String getBearerfromOauth(){

        RestAssured.baseURI = baseURI;

        Response response =

        given()
      //          .header("content-type", "mmultipart/form-data")
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")

                .when()
                .post("/oauth2/resourceOwner/token")

                .then()
                .log().body()
                .extract().response();

        JsonPath js = reusable.raw2json(response);
        String accessToken = js.get("access_token");

        return accessToken;
    }

    @Test(priority = 2)
    public void coursesget(){

        String accessToken = getBearerfromOauth();


        courses =
        given()
                .queryParam("access_token",accessToken)

                .when()
                .get("/getCourseDetails")

                .then()
                .statusCode(401)
                .log().body()
                .extract().response().as(getCourses.class);


    }

    @Test(priority = 3)
    public void assertCourses(){
        String  crs[] = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        System.out.println(courses.getInstructor());
        System.out.println(courses.getExpertise());
        System.out.println(courses.getCourses().getWebAutomation().get(2).getCourseTitle());
        System.out.println(courses.getCourses().getApi().get(1).getPrice());

        List<api> api = courses.getCourses().getApi();
        for(int i=0;i<api.size();i++){
            System.out.println(api.get(i).getCourseTitle());
            if(api.get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java")){
                System.out.println(api.get(i).getPrice());
                break;
            }
        }

        List<webAutomation> wb = courses.getCourses().getWebAutomation();

        ArrayList<String> al = new ArrayList<String>();

        for (int i=0;i<wb.size();i++){
            System.out.println(wb.get(i).getCourseTitle() + " : " + wb.get(i).getPrice());
            al.add(wb.get(i).getCourseTitle());
        }

        List<String> exp = Arrays.asList(crs);
        Assert.assertEquals(al, exp );


    }



}





