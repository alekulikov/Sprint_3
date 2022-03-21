package ru.yandex;

import static io.restassured.http.ContentType.JSON;


import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ScooterRestClient {

	private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

	protected RequestSpecification getBaseSpec() {
		return new RequestSpecBuilder()
				.addFilter(new AllureRestAssured())
				.setContentType(JSON)
				.setBaseUri(BASE_URL)
				.build();
	}

}
