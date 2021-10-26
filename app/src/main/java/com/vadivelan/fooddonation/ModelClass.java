package com.vadivelan.fooddonation;

public class ModelClass {
	String name,food_name,location,cell,date;
	int persons;
	ModelClass(String name,String food_name,int persons,String location,String cell,String date){
		this.name=name;
		this.food_name=food_name;
		this.persons=persons;
		this.location=location;
		this.cell=cell;
		this.date = date;
	}

	ModelClass(String food_name,int persons,String location,String cell,String date){
		this.food_name=food_name;
		this.persons=persons;
		this.location=location;
		this.cell=cell;
		this.date = date;
	}

	public String getName() {
		return name;
	}
	public String getFood_name() {
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
	}
}
