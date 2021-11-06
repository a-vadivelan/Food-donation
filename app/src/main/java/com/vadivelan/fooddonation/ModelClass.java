package com.vadivelan.fooddonation;

public class ModelClass {
	String food_name,location,cell,date;
	String address,available,city,district,food,mobile,name,postId,time,unit;
	int persons;
	/*ModelClass(String food_name,int persons,String location,String cell,String date){
		this.food_name=food_name;
		this.persons=persons;
		this.location=location;
		this.cell=cell;
		this.date = date;
	}*/
//{unit=Items, address=Address, city=Ottanchithiram, district=Dindugal, available=50, mobile=9047429180, name=Vadivelan, postId=-MnQGhkHfJNBwbN56P49, food=Dosa, timestamp=1635766383780}
	public ModelClass(String address, String available, String city, String district, String food, String mobile, String name, String postId, String time, String unit) {
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
	/*public String getFood_name() {
		return food_name;
	}
	public String getLocation() {
		return location;
	}
	public String getCell() {
		return cell;
	}
	public int getPersons() {
		return persons;
	}
	public String getDate(){
		return date;
	}*/
}
