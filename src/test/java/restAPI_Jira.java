import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Properties;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

public class restAPI_Jira {

    public final String baseURI = "https://indanayeswanth.atlassian.net";

    @Test(priority = 1)
    public String createBug() throws IOException {

        RestAssured.baseURI = baseURI;
        Response resp =
        given()
                .header("Content-Type", "application/json")
                .header("Authorization","Basic aW5kYW5heWVzd2FudGhAZ21haWwuY29tOkFUQVRUM3hGZkdGMGwyeHEzNkk0WTZ0aVBveXdiS0NFbUJDZDFlNlVQMjU0ZmExZnhMQm9OYlNOeC1JZ1NBamVMdU8zTHp5M04tdi1KZlZNR0xmdHU5TGh5N0g4WWJTMTlCVjR0SVlQaDRBRkppbFpLakpTc0dSdzRvaVMtM0pFTFRfNk1KRVJTcVZvRVV6UnJPaV82Tl8wUU80NXVMN2JJRzFoTWsyQ2Y0MGVlbjRhVEVPN3F5RT1BRkUwMDNDRA==")
                .body("{\n" +
                        "  \"fields\": {\n" +
                        "    \"project\": {\n" +
                        "      \"key\": \"API\"\n" +
                        "    },\n" +
                        "    \"summary\": \"Login page fails to load for users with expired sessions - DUPLICATE\",\n" +
                        "    \"issuetype\": {\n" +
                        "      \"name\": \"Bug\"\n" +
                        "    },\n" +
                        "    \"description\": {\n" +
                        "      \"type\": \"doc\",\n" +
                        "      \"version\": 1,\n" +
                        "      \"content\": [\n" +
                        "        {\n" +
                        "          \"type\": \"paragraph\",\n" +
                        "          \"content\": [\n" +
                        "            {\n" +
                        "              \"type\": \"text\",\n" +
                        "              \"text\": \"Users with expired sessions are encountering a blank page when accessing login.\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    \"labels\": [\"login\", \"session\", \"regression\"]\n" +
                        "  }\n" +
                        "}\n")
                        .log().body()

                .when()
                        .post("/rest/api/3/issue")

                .then()
                .statusCode(201)
                .log().body()
                .extract().response();

        String resStr = resp.asString();
        JsonPath js = new JsonPath(resStr);
        String issueKey = js.getString("key");
        System.out.println(issueKey);

    return issueKey;
    }

    @Test(priority = 2)
    public void addAttachment() throws IOException {
        String issueKey = createBug();
        given()
                .header("Content-Type","multipart/form-data")
                .header("Authorization","Basic aW5kYW5heWVzd2FudGhAZ21haWwuY29tOkFUQVRUM3hGZkdGMGwyeHEzNkk0WTZ0aVBveXdiS0NFbUJDZDFlNlVQMjU0ZmExZnhMQm9OYlNOeC1JZ1NBamVMdU8zTHp5M04tdi1KZlZNR0xmdHU5TGh5N0g4WWJTMTlCVjR0SVlQaDRBRkppbFpLakpTc0dSdzRvaVMtM0pFTFRfNk1KRVJTcVZvRVV6UnJPaV82Tl8wUU80NXVMN2JJRzFoTWsyQ2Y0MGVlbjRhVEVPN3F5RT1BRkUwMDNDRA==")
                .header("X-Atlassian-Token","no-check")
                .pathParam("issueKey", issueKey)
                .multiPart("file",new File("C:\\Users\\indan\\OneDrive\\Documents\\API\\RestAPIAutomation\\pom.xml"))
                .log().all()

                .when()
                .post("/rest/api/3/issue/{issueKey}/attachments")

                .then()
                .log().body();

    }
}
