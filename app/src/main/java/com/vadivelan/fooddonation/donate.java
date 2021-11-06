package com.vadivelan.fooddonation;

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
import java.util.HashMap;
import java.util.Map;

public class donate extends AppCompatActivity {
EditText name,food_name,food_available,address,mobile;
Spinner district,city,unit;
String selected_district;
String[] select_city;
ArrayAdapter<String> adapter;
Button submit_btn;
Intent intent;
FirebaseDatabase database;
DatabaseReference ref,postRef,userRef;
Timestamp time = new Timestamp(System.currentTimeMillis());
String filled_name,filled_food,filled_address,filled_available,filled_district,filled_city,filled_mobile,filled_unit,timestamp = String.valueOf(time.getTime());
Map<String, Object> postIdUpdate = new HashMap<>();
FirebaseAuth auth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate);
		name = findViewById(R.id.donor_name);
		food_name = findViewById(R.id.food_name);
		address = findViewById(R.id.address);
		food_available = findViewById(R.id.food_available);
		district = findViewById(R.id.district);
		city = findViewById(R.id.city);
		mobile = findViewById(R.id.mobile);
		submit_btn = findViewById(R.id.submit_donate);
		unit = findViewById(R.id.unit);
		auth = FirebaseAuth.getInstance();
		database = FirebaseDatabase.getInstance();
		ref = database.getReference();
		district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selected_district = String.valueOf(district.getSelectedItem());
				if(selected_district.equals("Select District"))
					city.setVisibility(View.INVISIBLE);
				else {
					city.setVisibility(View.VISIBLE);
					switch (selected_district){
						case "Ariyalur":select_city=getResources().getStringArray(R.array.ariyalur);break;
						case "Chengalpet":select_city=getResources().getStringArray(R.array.chengalpet);break;
						case "Chennai":select_city=getResources().getStringArray(R.array.chennai);break;
						case "Coimbatore":select_city=getResources().getStringArray(R.array.coimbatore);break;
						case "Cuddalore":select_city=getResources().getStringArray(R.array.cuddalore);break;
						case "Dharmapuri":select_city=getResources().getStringArray(R.array.dharmapuri);break;
						case "Dindugal":select_city=getResources().getStringArray(R.array.dindugal);break;
						case "Erode":select_city=getResources().getStringArray(R.array.erode);break;
						case "Kallakurichi":select_city=getResources().getStringArray(R.array.kallakurichi);break;
						case "Kanchipuram":select_city=getResources().getStringArray(R.array.kanchipuram);break;
						case "Kanniyakumari":select_city=getResources().getStringArray(R.array.kanniyakumari);break;
						case "Karaikaal":select_city=getResources().getStringArray(R.array.karaikaal);break;
						case "Karur":select_city=getResources().getStringArray(R.array.karur);break;
						case "Krishnagiri":select_city=getResources().getStringArray(R.array.krishnagiri);break;
						case "Madurai":select_city=getResources().getStringArray(R.array.madurai);break;
						case "Mahi":select_city=getResources().getStringArray(R.array.mahi);break;
						case "Mayiladudurai":select_city=getResources().getStringArray(R.array.mayiladudurai);break;
						case "Nagapattinam":select_city=getResources().getStringArray(R.array.nagapattinam);break;
						case "Namakkal":select_city=getResources().getStringArray(R.array.namakkal);break;
						case "Nilgiris":select_city=getResources().getStringArray(R.array.nilagiri);break;
						case "Perambalur":select_city=getResources().getStringArray(R.array.perambalur);break;
						case "Pudhucheri":select_city=getResources().getStringArray(R.array.puducheri);break;
						case "Pudukkottai":select_city=getResources().getStringArray(R.array.puthukkottai);break;
						case "Ramanathapuram":select_city=getResources().getStringArray(R.array.ramnad);break;
						case "Ranipet":select_city=getResources().getStringArray(R.array.ranipet);break;
						case "Salem":select_city=getResources().getStringArray(R.array.salem);break;
						case "Sivaganga":select_city=getResources().getStringArray(R.array.sivaganga);break;
						case "Tenkasi":select_city=getResources().getStringArray(R.array.tenkasi);break;
						case "Thanjavur":select_city=getResources().getStringArray(R.array.thanjavur);break;
						case "Theni":select_city=getResources().getStringArray(R.array.theni);break;
						case "Tiruvallur":select_city=getResources().getStringArray(R.array.thiruvallur);break;
						case "Tiruvarur":select_city=getResources().getStringArray(R.array.thiruvarur);break;
						case "Tutucorin":select_city=getResources().getStringArray(R.array.tutukodi);break;
						case "Tiruchirappalli":select_city=getResources().getStringArray(R.array.trichy);break;
						case "Thirunelveli":select_city=getResources().getStringArray(R.array.thirunelveli);break;
						case "Tirupathur":select_city=getResources().getStringArray(R.array.thirupatthur);break;
						case "Tiruppur":select_city=getResources().getStringArray(R.array.thiruppur);break;
						case "Tiruvannamalai":select_city=getResources().getStringArray(R.array.thiruvannamalai);break;
						case "Vellore":select_city=getResources().getStringArray(R.array.vellore);break;
						case "Villupuram":select_city=getResources().getStringArray(R.array.villupuram);break;
						case "Virudhunagar":select_city=getResources().getStringArray(R.array.virudhunagar);break;
						case "Yaanam":select_city=getResources().getStringArray(R.array.yaanam);break;
						default:
							Toast.makeText(getApplicationContext(), "Choose correct option", Toast.LENGTH_SHORT).show();
					}
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
					Toast.makeText(this,"Please fill your mobile number",Toast.LENGTH_LONG).show();
				else {
					//String name, String food, String available, String district, String city, String address, String mobile
					userRef = ref.child(String.valueOf(auth.getCurrentUser().getPhoneNumber()));
					postRef = userRef.push();
					postRef.setValue(new Post(filled_address, filled_available, filled_city, filled_district, filled_food, filled_mobile, filled_name, null,timestamp,filled_unit));
					postIdUpdate.put("postId", postRef.getKey());
					postRef.updateChildren(postIdUpdate);
					finish();
				}
			} catch (Exception e) {
			Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
			}
		});
	}
}