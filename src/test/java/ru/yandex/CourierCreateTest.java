package ru.yandex;

import static org.apache.http.HttpStatus.*;
import static ru.yandex.Steps.*;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.courier.Courier;
import ru.yandex.courier.CourierClient;
import ru.yandex.courier.CourierCredentials;
import ru.yandex.courier.CourierGenerator;

@DisplayName("Создание курьера")
public class CourierCreateTest {

	CourierClient courierClient;
	Courier courier;
	int courierId;

	@Before
	public void setUp() {
		courierClient = new CourierClient();
	}

	@After
	public void tearDown() {
		try {
			courierId = courierClient
					.login(new CourierCredentials(courier.getLogin(), courier.getPassword()))
					.extract().path("id");
		} catch (NullPointerException exception) {
			courierId = 0;
		}
		courierClient.delete(courierId);
	}

	@Test
	@DisplayName("Курьер может быть создан")
	public void courierCanBeCreated(){
		courier = CourierGenerator.getRandom();
		ValidatableResponse createResponse = courierClient.create(courier);

		checkStatusCode(createResponse, SC_CREATED);
		parameterEqualsTo(createResponse, "ok", true);
	}

	@Test
	@DisplayName("Один и тот же курьер не может быть создан дважды")
	public void courierMustBeUnique(){
		courier = CourierGenerator.getRandom();
		courierClient.create(courier);
		ValidatableResponse repeatCreateResponse = courierClient.create(courier);

		checkStatusCode(repeatCreateResponse, SC_CONFLICT);
		parameterEqualsTo(repeatCreateResponse, "message", "Этот логин уже используется. Попробуйте другой.");
	}

	@Test
	@DisplayName("Курьер не может быть создан без пароля")
	public void courierCannotCreatedWithoutPassword(){
		courier = CourierGenerator.getRandom(10, 0, 10);
		ValidatableResponse createResponse = courierClient.create(courier);

		checkStatusCode(createResponse, SC_BAD_REQUEST);
		parameterEqualsTo(createResponse, "message", "Недостаточно данных для создания учетной записи");
	}

	@Test
	@DisplayName("Курьер не может быть создан без логина")
	public void courierCannotCreatedWithoutLogin(){
		courier = CourierGenerator.getRandom(0, 10, 10);
		ValidatableResponse createResponse = courierClient.create(courier);

		checkStatusCode(createResponse, SC_BAD_REQUEST);
		parameterEqualsTo(createResponse, "message", "Недостаточно данных для создания учетной записи");
	}
}
