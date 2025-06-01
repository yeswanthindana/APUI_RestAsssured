import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class serelaiztaionSpecs {

    public static String baseURL = "https://rahulshettyacademy.com";
    public static serpojo sp;
    public static RequestSpecification reqs;
    public static ResponseSpecification ress;


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



        reqs = new RequestSpecBuilder()
                        .setBaseUri(baseURL)
                                .setContentType(ContentType.JSON).build();

        ress = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200).build();


        RequestSpecification reg = given()

//                .queryParam("key","qaclick123")
//                .header("content-type","application/json")
                .spec(reqs)
                .body(sp)
                .log().body();

        Response resp =
                reg.when()
                .post("/maps/api/place/add/json")



                .then()
                .spec(ress)
                .log().body()
                .extract().response();

        String resStr = resp.asString();
        System.out.println(resStr);
    }


}
