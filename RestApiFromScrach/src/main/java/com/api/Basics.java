package com.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import com.file.Payload;
import com.file.ReusableMethods;

public class Basics {
	public static void main(String[] args) {
		// given-all input details
		// when-submit the api
		// then-validate the response
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123")
				.headers("Content-Type", "application/json").body(Payload.AddPlace()).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		// customiza assertion jsonpath class use bydefault assertion by using
		// body
		JsonPath js = new JsonPath(response);
		String placeid = js.getString("place_id");
		System.out.println(placeid);
		// update place

		String newAddress = "70 Summer walk, USA";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeid

						+ "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n" + "\"key\":\"qaclick123\"\r\n" + "}\r\n"
						+ "")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get Place
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("", "").when()
				.get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract().response()
				.asString();
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);

		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);

	}
}
