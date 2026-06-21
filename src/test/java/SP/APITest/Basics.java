package SP.APITest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
RestAssured.baseURI="https://rahulshettyacademy.com";
//ADD PLACE
String Response=given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json").body("{\r\n"
		+ "  \"location\": {\r\n" 
		+ "    \"lat\": -38.383494,\r\n"
		+ "    \"lng\": 33.427362\r\n"
		+ "  },\r\n"
		+ "  \"accuracy\": 50,\r\n"
		+ "  \"name\": \"Sau house\",\r\n"
		+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
		+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
		+ "  \"types\": [\r\n"
		+ "    \"shoe park\",\r\n"
		+ "    \"shop\"\r\n"
		+ "  ],\r\n"
		+ "  \"website\": \"http://google.com\",\r\n"
		+ "  \"language\": \"French-IN\"\r\n"
		+ "}\r\n"
		+ "").when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

JsonPath js=new JsonPath(Response);
String placeID=js.get("place_id");
System.out.println("ADDRESS ADDED at "+placeID);
//UPDATE ADDRESS

String newadresss="DELHI";
given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json").body("{\r\n"
		+ "\"place_id\":\""+placeID+"\",\r\n"
		+ "\"address\":\""+newadresss+"\",\r\n"
		+ "\"key\":\"qaclick123\"\r\n"
		+ "}\r\n"
		+ "").when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
//GET PLACE and verify whether updated address is available 

String resp1=given().log().all().queryParam("key","qaclick123").queryParam("place_id", placeID)
.when().get("/maps/api/place/get/json")
.then().log().all().assertThat().statusCode(200).extract().response().asString();

JsonPath js1=new JsonPath(resp1);
String updated_address=js1.getString("address");
System.out.println("fINAL Updated address is" + updated_address);
Assert.assertEquals(updated_address, "P");
	}

}
