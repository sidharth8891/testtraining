package LoginAPI;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.api.util.ReadTestDataJson;

//import Helper.UserHelperClass;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
//import util.ReadTestData;
//import utils.Helper;

public class VerifyLogin {
	
 @Test(description="Verify status code for GET method-users/2 as 200")
 public static void verifyStatusCodeGET() {
	 //String token = gettoken();
	 Response resp=given().when().get("https://reqres.in/api/users");
	 System.out.println(resp.path("total").toString());
	 assertEquals(resp.getStatusCode(),200);
	 assertEquals(resp.path("total").toString(),"12");
	 
	 List<String> jsonResponse = resp.jsonPath().getList("data");
     System.out.println(jsonResponse.size());
     System.out.println("The number of data in the list is : " + jsonResponse.size());
     assertEquals(jsonResponse.size(),6);
     String usernames = resp.jsonPath().getString("data[1].email");
     System.out.println(usernames);
//   //  List<String> jsonResponses = resp.jsonPath().getList("data");
//   //  System.out.println(jsonResponses.get(0));
     Map<String, String> company = resp.jsonPath().getMap("data[1]");
     System.out.println(company.get("avatar"));
   //  List<Map<String, String>> companies = resp.jsonPath().getList("data");
   //  System.out.println(companies.get(0).get("first_name"));
    List<String> ids = resp.jsonPath().getList("data.first_name");

     for(String i:ids)
     {
    	// System.out.println(i);
        if (i.toUpperCase().contentEquals("JANET")) {
        	System.out.println("JANET is present in the response body");
        }
     }

//	 assertEquals(resp.path("data.email").toString(),"janet.weaver@reqres.in");
//	 assertEquals(resp.path("data.first_name").toString(),"Janet");
//	 assertEquals(resp.path("data.last_name").toString(),"Weaver");
//	 System.out.println(resp.path("data.first_name").toString()+ resp.path("data._name").toString());
	 
 }
 
 @Test(description="Validate status code for POST method to register a customer")
 public static void verifyStatusCodePOST() {
	 
	 Response resp = given().header("Content-Type","application/json").body("{\\r\\n\\\"FirstName\\\" : \\\"testsdfadf\\\",\\r\\n\\r\\n\\\"LastName\\\" : \\\"sfsgfsgfs\\\",\\r\\n\\r\\n\\\"UserName\\\" : \\\"hgshgshgs\\\",\\r\\n\\r\\n\\\"Password\\\" : \\\"ytytytytyttyty\\\",\\r\\n\\r\\n\\\"Email\\\"    : \\\"testone@gmail.com\\\"\\r\\n\\r\\n}").
			 when().post("http://restapi.demoqa.com/customer/register");
	 assertEquals(resp.getStatusCode(),200);
	 String faultid = resp.path("FaultId");
	 assertEquals(faultid,"User already exists");
 
 }
 
 @Test(description="Delete user 2 and validate the status code")
 public static void verifyDELETE() throws IOException {
	 String uri = ReadTestDataJson.getdatafromjson("$.uri");
	 Response resp = given().delete(uri);
	 assertEquals(resp.getStatusCode(),204);
	// assertEquals(resp.path("msg"),"User deleted successfully");
 }
 
 public static String gettoken() {
	 Response resp = given().when().get("https://gettoken");
	 String token = resp.path("Authorization");
	 return token;
 }
 
 @Test(description="Verify 404 for invalid uri with GET method")
 public static void verifyStatusCodeInvalidGET() {
	 
	 Response resp = given().
			 when().get("https://reqres.in/api/users/2");
	 
	 int statuscode = resp.getStatusCode();
	 assertEquals(statuscode,404);
	 System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@PASSSSSSSSSSSSSSSSS@@@@@@@@@@@@@@@@@@@@@@@@@@");
 }
 
 @Test
 public static void validatepathparam() {
	 Response resp = given().pathParam("raceSeason", "2017")
			 .when().get("http://ergast.com/api/f1/{raceSeason}/circuits.json");
	 assertEquals(resp.getStatusCode(),200);
	 System.out.println(resp.getBody().asString());
 
 
	
}}
