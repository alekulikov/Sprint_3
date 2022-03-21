package ru.yandex.order;

import static io.restassured.RestAssured.given;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.ScooterRestClient;

public class OrdersClient extends ScooterRestClient {

	private static final String ORDER_PATH = "api/v1/orders";

	@Step("Создаем заказ")
	public ValidatableResponse createOrder(Order order) {
		return given()
				.spec(getBaseSpec())
				.body(order)
				.when()
				.post(ORDER_PATH)
				.then();
	}

	@Step("Получаем список заказов")
	public ValidatableResponse getOrdersList() {
		return given()
				.spec(getBaseSpec())
				.when()
				.get(ORDER_PATH)
				.then();
	}

}
