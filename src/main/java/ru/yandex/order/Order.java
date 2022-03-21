package ru.yandex.order;

import java.util.List;

public class Order {

	private int id;
	private int courierId;
	private String firstName;
	private String lastName;
	private String address;
	private String metroStation;
	private String phone;
	private int rentTime;
	private String deliveryDate;
	private int track;
	private List<String> color;
	private String comment;
	private String createdAt;
	private String updatedAt;
	private int status;

	public Order(String firstName, String lastName, String address, String metroStation,
				 String phone, int rentTime, String deliveryDate, List<String> color,
				 String comment) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.metroStation = metroStation;
		this.phone = phone;
		this.rentTime = rentTime;
		this.deliveryDate = deliveryDate;
		this.color = color;
		this.comment = comment;
	}

	public Order() {}
}
