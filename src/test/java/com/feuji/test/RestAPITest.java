
package com.feuji.test;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.feuji.base.Base;
import com.feuji.context.Constants;
import com.feuji.pojos.Booking;
import com.feuji.pojos.BookingDates;
import com.feuji.utils.FileReader;
import com.feuji.utils.GenerateToken;
import com.feuji.utils.LoggerUtil;
import com.feuji.utils.Route;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * The RestAPITest class contains test cases for testing REST API endpoints
 * related to bookings. It extends the Base class for test setup and common
 * functionalities.
 */
public class RestAPITest extends Base {

	int bookingId;

	/**
	 * Test case to get all bookings from the API endpoint.
	 */
	@Test(description = "To get all the bookings", priority = 0, groups = "APIAutomation")
	public void testGetAllBookings() {
		Response response = given().contentType(ContentType.JSON).baseUri(Route.BASE_URL).log().headers().when()
				.get(Route.GET_ALL_END_POINT).then().assertThat().statusCode(200).statusLine("HTTP/1.1 200 OK")
				.contentType(ContentType.JSON).extract().response();
		List<String> list = response.getBody().as(List.class);
		extentTest.get().info("Response Status : " + response.getStatusLine());
		extentTest.get().info("Response Body : " + list.subList(0, 10));
		softAssert.assertTrue(response.getBody().asString().contains("bookingid"));
		softAssert.assertAll();
		LoggerUtil.log("Get all bookings test ended");
	}

	/**
	 * Test case to create a new booking using the API.
	 */
	@Test(description = "To create a new booking", priority = 1, groups = "APIAutomation")
	public void testCreateBooking() {
		try {
			String jsonSchema = FileReader.getData(Constants.JSON_SCHEMA).toString();
			BookingDates bookingDates = new BookingDates("2024-03-25", "2024-03-30");
			Booking booking = new Booking("Gunda", "anna", 1300, false, bookingDates, "breakfast");
			Response response = given().contentType(ContentType.JSON).body(booking).baseUri(Route.BASE_URL).log().all()
					.when().post(Route.POST_END_POINT).then().assertThat().statusCode(200).extract().response();
			bookingId = response.path("bookingid");
			extentTest.get().info("Response Status : " + response.getStatusLine());
			extentTest.get().info("Response Body : " + response.getBody().prettyPrint());
			given().contentType(ContentType.JSON).baseUri(Route.BASE_URL).when()
					.get(Route.GET_BY_ID_END_POINT, bookingId).then().assertThat().statusCode(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test case to update an existing booking using the API.
	 */
	@Test(description = "To update an existing booking", priority = 2, groups = "APIAutomation")
	public void testUpdateBookings() {
		try {
			String token = GenerateToken.getToken(Constants.TOKEN_REQUEST_BODY);
			BookingDates bookingDates = new BookingDates("2024-03-25", "2024-03-30");
			Booking booking = new Booking("Gunda", "pandu", 1300, false, bookingDates, "breakfast");
			Response response = given().contentType(ContentType.JSON).body(booking).baseUri(Route.BASE_URL)
					.header("Cookie", "token=" + token).log().all().when().put(Route.UPDATE_END_POINT, bookingId).then()
					.assertThat().statusCode(200).body("firstname", Matchers.equalTo("Gunda"))
					.body("lastname", Matchers.equalTo("pandu")).extract().response();
			extentTest.get().info("Response Status : " + response.getStatusLine());
			extentTest.get().info("Response Body : " + response.getBody().prettyPrint());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test case to patch an existing booking using the API.
	 */
	@Test(description = "To patch an existing booking", priority = 3, groups = "APIAutomation")
	public void testPatchBooking() {
		try {
			String token = GenerateToken.getToken(Constants.TOKEN_REQUEST_BODY);
			String requestBody = "{\"firstname\": \"John\"}";
			Response response = given().contentType(ContentType.JSON).body(requestBody).baseUri(Route.BASE_URL)
					.header("Cookie", "token=" + token).log().all().when().patch(Route.GET_BY_ID_END_POINT, bookingId)
					.then().assertThat().statusCode(200).body("firstname", Matchers.equalTo("Johny")).extract()
					.response();
			extentTest.get().info("Response Status : " + response.getStatusLine());
			extentTest.get().info("Response Body : " + response.getBody().prettyPrint());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test case to delete a booking using the API.
	 */
	@Test(description = "To delete the booking", priority = 4, groups = "APIAutomation")
	public void testDeleteBooking() {
		try {
			String token = GenerateToken.getToken(Constants.TOKEN_REQUEST_BODY);
			Response response = given().contentType(ContentType.JSON).baseUri(Route.BASE_URL)
					.header("Cookie", "token=" + token).log().all().when().delete(Route.DELETE_END_POINT, bookingId)
					.then().assertThat().statusCode(201).extract().response();
			extentTest.get().info("Response Status : " + response.getStatusLine());
			extentTest.get().info("Response Body : " + response.getBody().prettyPrint());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
