package com.vadivelan.fooddonation;

public class Post {
	public String name,food,available,district,city,address,mobile,postId,unit,timestamp,userId;
	public Post(String address, String available, String city, String district, String food, String mobile, String name,String postId,String timestamp,String unit,String userId) {
		this.name = name;
		this.food = food;
		this.available = available;
		this.district = district;
		this.city = city;
		this.address = address;
		this.mobile = mobile;
		this.postId = postId;
		this.timestamp = timestamp;
		this.unit = unit;
		this.userId = userId;
	}
}
