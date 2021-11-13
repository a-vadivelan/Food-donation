package com.vadivelan.fooddonation;

import java.lang.System;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;

public class EditDonationActivity extends AppCompatActivity {
EditText name,food_name,food_available,address,mobile;
Spinner district,city,unit;
String selected_district,post_id;
String[] select_city;
Button submit_btn;
ArrayAdapter<String> adapter;
Intent intent;
FirebaseDatabase database;
DatabaseReference ref;
Timestamp time = new Timestamp(System.currentTimeMillis());
locations cities = new locations();
String filled_name,filled_food,filled_address,filled_available,filled_district,filled_city,filled_mobile,filled_unit,timestamp = String.valueOf(time.getTime());
FirebaseAuth auth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_donation);
		intent = getIntent();
		name = findViewById(R.id.donor_name);
		food_name = findViewById(R.id.food_name);
		address = findViewById(R.id.address);
		food_available = findViewById(R.id.food_available);
		district = findViewById(R.id.district);
		city = findViewById(R.id.city);
		mobile = findViewById(R.id.mobile);
		submit_btn = findViewById(R.id.submit_donate);
		unit = findViewById(R.id.unit);
		name.setText(intent.getStringExtra("name"));
		food_name.setText(intent.getStringExtra("food"));
		address.setText(intent.getStringExtra("address"));
		food_available.setText(intent.getStringExtra("available"));
		mobile.setText(intent.getStringExtra("mobile"));
		post_id = intent.getStringExtra("id");
		auth = FirebaseAuth.getInstance();
		database = FirebaseDatabase.getInstance();
		ref = database.getReference().child(auth.getCurrentUser().getUid()).child(post_id);
		district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selected_district = String.valueOf(district.getSelectedItem());
				if(selected_district.equals("Select District"))
					city.setVisibility(View.INVISIBLE);
				else {
					city.setVisibility(View.VISIBLE);
					select_city = cities.cities_list(selected_district,getApplicationContext());
					adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,select_city);
					city.setAdapter(adapter);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		submit_btn.setOnClickListener((View v)->{
			try {
				filled_name = name.getText().toString();
				filled_food = food_name.getText().toString();
				filled_available = food_available.getText().toString();
				filled_district = (String) district.getSelectedItem();
				filled_city = (String) city.getSelectedItem();
				filled_unit = (String) unit.getSelectedItem();
				filled_address = address.getText().toString();
				filled_mobile = mobile.getText().toString();
				if (filled_name.equals(""))
					Toast.makeText(this, "Please fill your name", Toast.LENGTH_LONG).show();
				else if (filled_food.equals(""))
					Toast.makeText(this, "Please fill food name", Toast.LENGTH_LONG).show();
				else if (filled_available.equals(""))
					Toast.makeText(this, "Please fill detail of food available", Toast.LENGTH_LONG).show();
				else if (filled_district.equals("Select District") || filled_city.equals("Select City"))
					Toast.makeText(this, "Please choose correct location", Toast.LENGTH_LONG).show();
				else if (filled_address.equals(""))
					Toast.makeText(this, "Please fill your address", Toast.LENGTH_LONG).show();
				else if(filled_mobile.equals(""))
					Toast.makeText(this,"Please fill your mobile number", Toast.LENGTH_LONG).show();
				else {
					//String name, String food, String available, String district, String city, String address, String mobile
					ref.setValue(new Post(filled_address, filled_available, filled_city, filled_district, filled_food, filled_mobile, filled_name, post_id,timestamp,filled_unit));
					finish();
				}
			} catch (Exception e) {
			Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
			}
		});
	}
}
