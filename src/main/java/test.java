import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.* ;

public class test {

    public final String address = "Sinkara, USA";
    public static JsonPath js;

    public static void main(String[] args) {


        RestAssured.baseURI = "https://rahulshettyacademy.com";
        test t = new test();
        t.CreatePlace();

        String placeId = t.CreatePlace();
        t.RetrievePlace(placeId);
        payload.address = t.address;
        t.UpdatePlace();
        t.RetrievePlace(placeId);
        String extractadd = t.RetrievePlace(placeId);
        System.out.println(extractadd+ " is equal/not equal to " + t.address);
        Assert.assertEquals(extractadd, t.address);
        t.removePlace();

    }

    public String CreatePlace(){

        Response response =

                given()
                   //    .log().all()
                        .log().body()
                        .queryParam("key","qaclick123")
                        .header("content-type","application/json")
                        .body(payload.addPlace())

                        .when()
                        .post("/maps/api/place/add/json")


                        .then()
           //            .log().all()\
                        .log().body()
                        .body("scope",equalTo("APP"))
                        .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                        .assertThat().statusCode(200)
                        .extract().response();


//        System.out.println(response);
//       String responseString = response.asString();
//        js = new JsonPath(responseString);
        js = payload.raw2json(response);
        String placeId = js.get("place_id");
        System.out.println(response.getHeaders());
       System.out.println(response.body());
        System.out.println(placeId);
        payload.placeid = placeId;

        return placeId;
    }

    public String RetrievePlace(String placeId){

        Response response =

            given()
                    .log().all()
                    .queryParam("key","qaclick123")
                    .queryParam("place_id", placeId)
                    .header("content-type","application/json")

                    .when()
                    .get("maps/api/place/get/json")


                    .then()
                    .log().all()
                    .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                    .body("name",equalTo("Frontline house"))
                    .assertThat().statusCode(200)
                    .extract().response();

        js = payload.raw2json(response);
//
//        String responseString = response.asString();
//        js = new JsonPath(responseString);
        String extractedAddress = js.get("address");

        return extractedAddress;



    }

    public void UpdatePlace(){


        given().
                log().all()
                .queryParam("key","qaclick123")
                .header("content-type","application/json")
                .body(payload.updatePlace())

                .when()
                .put("/maps/api/place/update/json")

                .then()
                .log().all()
                .header("server",equalTo("Apache/2.4.52 (Ubuntu)"))
                .body("msg",equalTo("Address successfully updated"))
                .assertThat().statusCode(200)
                .extract().response();



    }

    public void removePlace(){

        given()
                .log().all()
                .header("content-type","application/json")
                .queryParam("key","qaclick123")
                .body(payload.deletePlace())

                .when()
                .delete("/maps/api/place/delete/json")

                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("status",equalTo("OK"));

    }
}

