import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class serelaiztaion {

    public static String baseURL = "https://rahulshettyacademy.com";
    public static serpojo sp;


    @Test
    public void createPlace(){

        sp = new serpojo();
        sp.setAccuracy(50);
        sp.setAddress("29, side layout, cohen 09");
        sp.setPhone_number("(+91) 983 893 3937");
        sp.setName("Frontline house");
        sp.setWebsite("http://google.com");
        sp.setLanguage("French-IN");

        List<String> al = new ArrayList<String>();
        al.add("shoe park");
        al.add("shop");

        sp.setTypes(al);


        serpo_loc spl = new serpo_loc();
        spl.setLat(-38.383494);
        spl.setLng(33.427362);

        sp.setLocation(spl);

        RestAssured.baseURI = baseURL;

        Response resp =
        given()
                .queryParam("key","qaclick123")
                .header("content-type","application/json")
                .body(sp)
                .log().body()

                .when()
                .post("/maps/api/place/add/json")

                .then()
                .assertThat().statusCode(200)
                .log().body()
                .extract().response();

        String resStr = resp.asString();
        System.out.println(resStr);
    }


}
