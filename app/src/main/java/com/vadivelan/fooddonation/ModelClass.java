package com.vadivelan.fooddonation;

public class ModelClass {
	String address,available,city,district,food,mobile,name,postId,time,unit,userId;
//{unit=Items, address=Address, city=Ottanchithiram, district=Dindugal, available=50, mobile=9047429180, name=Vadivelan, postId=-MnQGhkHfJNBwbN56P49, food=Dosa, timestamp=1635766383780}
	public ModelClass(String address, String available, String city, String district, String food, String mobile, String name, String postId, String time, String unit,String userId) {
		this.address = address;
		this.available = available;
		this.city = city;
		this.district = district;
		this.food = food;
		this.mobile = mobile;
		this.name = name;
		this.postId = postId;
		this.time = time;
		this.unit = unit;
		this.userId = userId;
	}


	public String getAddress() {
		return address;
	}
	public String getAvailable() {
		return available;
	}
	public String getCity() {
		return city;
	}
	public String getDistrict() {
		return district;
	}
	public String getFood() {
		return food;
	}
	public String getMobile() {
		return mobile;
	}
	public String getName() {
		return name;
	}
	public String getPostId() {
		return postId;
	}
	public String getTime() {
		return time;
	}
	public String getUnit() {
		return unit;
	}
	public String getUserId(){return userId;}
}
