package ru.yandex;

import static org.apache.http.HttpStatus.SC_CREATED;
import static ru.yandex.Steps.*;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ru.yandex.order.Order;
import ru.yandex.order.OrdersClient;

@RunWith(Parameterized.class)
@DisplayName("Создание заказа с указанием разных цветов")
public class OrdersCreateTest {

	OrdersClient ordersClient;
	Order order;

	private final String color;

	public OrdersCreateTest(String color) {
		this.color = color;
	}

	@Parameters(name = "colors = \"{0}\"")
	public static Collection<Object> data() {
		return Arrays.asList(new Object[]{
				"BLACK",
				"GREY",
				"BLACK, GREY",
				"",
		});
	}

	@Before
	public void setUp() {
		ordersClient = new OrdersClient();
		order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.",
				"4", "+7 800 355 35 35", 5, "2020-06-06",
				List.of(color.split(",\\s")), "Saske, come back to Konoha");
	}

	@Test
	public void orderWasCreatedWithColors() {
		ValidatableResponse createResponse = ordersClient.createOrder(order);

		checkStatusCode(createResponse, SC_CREATED);
		parameterIsNot(createResponse, "track", 0);
	}

}
