package ru.yandex.courier;

import static io.restassured.RestAssured.given;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.ScooterRestClient;

public class CourierClient extends ScooterRestClient {

	private static final String COURIER_PATH = "api/v1/courier/";

	@Step("Логинимся курьером")
	public ValidatableResponse login(CourierCredentials credentials) {
		return given()
				.spec(getBaseSpec())
				.body(credentials)
				.when()
				.post(COURIER_PATH + "login")
				.then();
	}

	@Step("Создаем курьера")
	public ValidatableResponse create(Courier courier) {
		return given()
				.spec(getBaseSpec())
				.body(courier)
				.when()
				.post(COURIER_PATH)
				.then();
	}

	@Step("Удаляем курьера")
	public ValidatableResponse delete(Integer id) {
		return given()
				.spec(getBaseSpec())
				.pathParam("id", id)
				.when()
				.delete(COURIER_PATH + "{id}")
				.then();
	}

}
