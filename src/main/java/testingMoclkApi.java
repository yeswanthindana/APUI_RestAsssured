import io.restassured.path.json.JsonPath;

public class testingMoclkApi {

    public static void main(String[] args) {


        JsonPath js = new JsonPath(payload.nestedJson());

//        1. Print No of courses returned by API
          int coursesCount = js.getInt("courses.size()");
          System.out.println("No of courses are: " + coursesCount);

//        2.Print Purchase Amount

          int purchaseAmount = js.getInt("dashboard.purchaseAmount");
          System.out.println("Purchase Amount is: " + purchaseAmount);
//
//        3. Print Title of the first course
            String ftc = js.getString("courses[0].title");
            System.out.println("Title of the first course is: " + ftc);
//        4. Print All course titles and their respective Prices
            for(int i=0;i<coursesCount;i++){
                String titles = js.getString("courses["+i+"].title");
                int prices = js.getInt("courses["+i+"].price");
                System.out.println("Title: " + titles + " : Price: " + prices);

            }
//        5. Print no of copies sold by RPA Course
            for(int k=0;k<coursesCount;k++){
                String title = js.get("courses["+k+"].title");
                if(title.equalsIgnoreCase("Cypress")){
                    int cou = js.getInt("courses["+k+"].copies");
                    System.out.println("No of copies sold : " + cou);
                    break;
                }
            }

//
//        6. Verify if Sum of all Course prices matches with Purchase Amount

            int sum =0;
          for(int j=0;j<coursesCount;j++){

              int cop = js.getInt("courses["+j+"].copies");
              int price = js.getInt("courses["+j+"].price");
              int amt = cop * price;
              sum  += amt;
              System.out.println(amt);


          }

          if(sum==purchaseAmount){
                System.out.println("Sum of all Course prices matches with Purchase Amount: " + sum);


            } else {
                System.out.println("Sum of all Course prices does not match with Purchase Amount: " + sum);
            }
          }


}
