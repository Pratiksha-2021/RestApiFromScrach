package com.api;

import org.testng.annotations.Test;

import com.file.Payload;
import com.file.ReusableMethods;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@Test

	public void addBook()

	{

		RestAssured.baseURI = "http://216.10.245.166";

		String response = given().

				header("Content-Type", "application/json").

				body(Payload.AddBook()).

				when().

				post("/Library/Addbook.php").

				then().log().all().assertThat().statusCode(200).

				extract().response().asString();

		JsonPath js = ReusableMethods.rawToJson(response);

		String id = js.get("ID");

		System.out.println(id);


	}

}
