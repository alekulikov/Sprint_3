package ru.yandex;

import static org.apache.http.HttpStatus.SC_OK;
import static ru.yandex.Steps.*;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.order.Order;
import ru.yandex.order.OrdersClient;
import ru.yandex.order.OrdersList;

@DisplayName("Получение списка заказов")
public class OrdersGetTest {

	OrdersClient ordersClient;
	Order order;

	@Before
	public void setUp() {
		ordersClient = new OrdersClient();
		order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.",
				"4", "+7 800 355 35 35", 5, "2020-06-06",
				List.of("BLACK", "GREY"), "Saske, come back to Konoha");
	}

	@Test
	@DisplayName("Список заказов может быть получен")
	public void ordersListMayBeReceived() {
		ValidatableResponse getResponse = ordersClient.getOrdersList();
		OrdersList orderList = getResponse.extract().body().as(OrdersList.class);

		checkStatusCode(getResponse, SC_OK);
		checkArrayListIsNotEmpty(orderList.getOrders());
	}

}
