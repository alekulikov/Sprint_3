package ru.yandex.courier;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

	public static Courier getRandom() {
		return getRandom(10, 10, 10);
	}

	@Step("Готовим новые данные для создания курьера")
	public static Courier getRandom(int lengthLogin, int lengthPassword, int lengthFirstName) {
		String courierLogin = RandomStringUtils.randomAlphabetic(lengthLogin);
		String courierPassword = RandomStringUtils.randomAlphabetic(lengthPassword);
		String courierFirstName = RandomStringUtils.randomAlphabetic(lengthFirstName);

		Allure.addAttachment("login", courierLogin);
		Allure.addAttachment("password", courierPassword);
		Allure.addAttachment("firstName", courierFirstName);

		return new Courier(courierLogin, courierPassword, courierFirstName);
	}

}
