import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class libraryHttps extends reusable {

    public static JsonPath js;
    public static String path = System.getProperty("user.dir")+"\\src\\test\\resources\\test.json";
    public static String jsonFolder = System.getProperty("user.dir")+"\\src\\test\\resources";
    public final String baseURI ="http://216.10.245.166";

    @Test(enabled = false)
    public void addBook() throws IOException {
        RestAssured.baseURI = baseURI;

        Response response =
        given()
                .header("content-type","application/json")


               // .body(Files.readAllBytes(Paths.get("C:\\Users\\indan\\OneDrive\\Documents\\API\\RestAPIAutomation\\src\\test\\resources\\test.json")))
                .body(GenerateStringfromResource(path))
               // .body(reusable.addBookReq(bookid,name,author))
                .log().body()

                .when()
                .post("Library/Addbook.php")

                .then()
                .assertThat().statusCode(200)
                .log().body()
                .body("Msg", equalTo("successfully added"))
                .extract().response();

        js = reusable.raw2json(response);
        String id = js.getString("ID");
        System.out.println("Book ID: " + id);
        System.out.println(response.getHeaders());
        Assert.assertEquals(js.getString("Msg").toString(),"successfully added");
    }

    @Test(priority = 2)
    public void getBookbyAuthorname(){
        RestAssured.baseURI = baseURI;

        Response resp =
        given()
                .header("content-type","application/json")
                //.pathParam("AuthorName","Yes")
                .log().body()

                .when()
                .get("/Library/GetBook.php?AuthorName=Yes")

                .then()
                .assertThat().statusCode(200)
         //       .body("book_name",equalTo("Learned Java"))
                .log().body()
                .extract().response();

        js = reusable.raw2json(resp);
        String bookName = js.getString("book_name");
        System.out.println("book name: " + bookName);
      //  Assert.assertEquals(bookName, name );

    }

    @Test(priority = 3)
    public void getBookbyID(){
        RestAssured.baseURI = baseURI;

        Response resp =
                given()
                        .header("content-type","application/json")
                  //      .pathParam("ID","3389")
                        .log().body()

                        .when()
                        .get("/Library/GetBook.php?ID=12344217")

                        .then()
                        .assertThat().statusCode(200)
                        //       .body("book_name",equalTo("Learned Java"))
                        .log().body()
                        .extract().response();

        js = reusable.raw2json(resp);
        String bookName = js.getString("book_name");
        System.out.println("book name: " + bookName);
        //  Assert.assertEquals(bookName, name );

    }





    @Test(priority = 1, dataProvider = "data")
    public void addBookwithdata(String bookid,String name,String author) throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";

        Response response =
                given()
                        .header("content-type","application/json")


                        // .body(Files.readAllBytes(Paths.get("C:\\Users\\indan\\OneDrive\\Documents\\API\\RestAPIAutomation\\src\\test\\resources\\test.json")))
                        //.body(GenerateStringfromResource(path))
                        .body(reusable.addBookReq(bookid,name,author))
                        .log().body()

                        .when()
                        .post("Library/Addbook.php")

                        .then()
                        .assertThat().statusCode(200)
                        .log().body()
                        .body("Msg", equalTo("successfully added"))
                        .extract().response();

        js = reusable.raw2json(response);
        String id = js.getString("ID");
        System.out.println("Book ID: " + id);
        System.out.println(response.getHeaders());
        Assert.assertEquals(js.getString("Msg").toString(),"successfully added");
    }

    @Test(enabled = false)
    public void addBookmultiplejsonfiles() throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";
        Files.list(Paths.get(jsonFolder))
                .filter(path -> path.toString().endsWith(".json"))
                .forEach(path -> {

                    try {
                        String  jsonbody = reusable.GenerateStringfromResource(path.toString());
                        Response response =
                                given()
                                        .header("content-type","application/json")

                                        // .body(Files.readAllBytes(Paths.get("C:\\Users\\indan\\OneDrive\\Documents\\API\\RestAPIAutomation\\src\\test\\resources\\test.json")))
                                        .body(jsonbody)
                                        // .body(reusable.addBookReq(bookid,name,author))
                                        .log().body()

                                        .when()
                                        .post("Library/Addbook.php")

                                        .then()
                                        .assertThat().statusCode(200)
                                        .log().body()
                                        .body("Msg", equalTo("successfully added"))
                                        .extract().response();

                        js = reusable.raw2json(response);
                        String id = js.getString("ID");
                        System.out.println("Book ID: " + id);
                        System.out.println(response.getHeaders());
                        Assert.assertEquals(js.getString("Msg").toString(),"successfully added");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}

