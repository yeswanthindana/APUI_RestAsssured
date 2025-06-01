import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class reusable {




    public static JsonPath raw2json(Response response){
        String resDString = response.asString();
        JsonPath jp = new JsonPath(resDString);
        return jp;
    }

    public static String GenerateStringfromResource(String path) throws IOException {
         String jsonbody = new String(Files.readAllBytes(Paths.get(path)));
         return jsonbody;
    }

    @DataProvider(name="data")
    public Object[][] getData(){
         Object data[][] = {
                 {"62644355","Learn Rest Assured","Yes"},
                 {"0044399","Learned Java","Wanth"},
                 {"12344217","Learned Selenium","Sow"},
                 {"54244354","Learned Playwright","janya"}
         };
         return data;
    };

    public static String addBookReq(String bookid, String name, String author) {
        String request = "{\n" +
                "\n" +
                "\"name\":\""+name+"\",\n" +
                "\"isbn\":\"bcd\",\n" +
                "\"aisle\":\" "+bookid+" \",\n" +
                "\"author\":\""+author+"\"\n" +
                "}\n";
        return request;
    }


}
