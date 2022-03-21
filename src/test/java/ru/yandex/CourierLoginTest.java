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

@DisplayName("Авторизация курьера")
public class CourierLoginTest {

	CourierClient courierClient;
	Courier courier;
	int courierId;

	@Before
	public void setUp() {
		courierClient = new CourierClient();
		courier = CourierGenerator.getRandom();
		courierClient.create(courier);
	}

	@After
	public void tearDown() {
		courierId = courierClient
				.login(new CourierCredentials(courier.getLogin(), courier.getPassword()))
				.extract().path("id");
		courierClient.delete(courierId);
	}

	@Test
	@DisplayName("Курьер может авторизоваться с корректными данными")
	public void courierCanLoginWithValidCredentials() {
		ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

		checkStatusCode(loginResponse, SC_OK);
		parameterIsNot(loginResponse, "id", 0);
	}

	@Test
	@DisplayName("Курьер не может авторизоваться без пароля")
	public void courierCannotLoginWithoutPassword() {
		ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), ""));

		checkStatusCode(loginResponse, SC_BAD_REQUEST);
		parameterEqualsTo(loginResponse, "message", "Недостаточно данных для входа");
	}

	@Test
	@DisplayName("Курьер не может авторизоваться без логина")
	public void courierCannotLoginWithoutLogin() {
		ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("", courier.getPassword()));

		checkStatusCode(loginResponse, SC_BAD_REQUEST);
		parameterEqualsTo(loginResponse, "message", "Недостаточно данных для входа");
	}

	@Test
	@DisplayName("Курьер не может авторизоваться, если такой логин не существует")
	public void courierCannotLoginWithIncorrectLogin() {
		ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("incorrectLogin", courier.getPassword()));

		checkStatusCode(loginResponse, SC_NOT_FOUND);
		parameterEqualsTo(loginResponse, "message", "Учетная запись не найдена");
	}

	@Test
	@DisplayName("Курьер не может авторизоваться, если пароль не подходит")
	public void courierCannotLoginWithIncorrectPassword() {
		ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), "incorrectPassword"));

		checkStatusCode(loginResponse, SC_NOT_FOUND);
		parameterEqualsTo(loginResponse, "message", "Учетная запись не найдена");
	}
}
