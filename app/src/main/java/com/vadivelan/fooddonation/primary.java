package com.vadivelan.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class primary extends AppCompatActivity {
	Button donate;
	ImageButton settings,refresh;
	RecyclerView donated;
	FirebaseDatabase database;
	DatabaseReference ref;
	StringBuilder detail;
	Spinner district,city;
	List<ModelClass> city_list = new ArrayList<>(),available_list = new ArrayList<>(),district_list = new ArrayList<>();
	String[] string_detail;
	Adapter adapter;
	String selected_district,selected_city;
	ArrayAdapter<String> adapter_two;
	String[] select_city;
	LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
	AlertDialog.Builder builder;
	AlertDialog alert;
	locations cities = new locations();
	ConnectionStatusReceiver receiver = new ConnectionStatusReceiver();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_primary);
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Loading...").setCancelable(false).create();
		alert = builder.show();
		donate = findViewById(R.id.donate);
		settings = findViewById(R.id.settings);
		refresh = findViewById(R.id.refresh);
		donated = findViewById(R.id.donated);
		district = findViewById(R.id.district);
		city = findViewById(R.id.city);
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		donated.setLayoutManager(linearLayoutManager);
		registerReceiver(receiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		database = FirebaseDatabase.getInstance();
		ref = database.getReference().getRoot().child("post");
		donate.setOnClickListener((View v)->{
			Intent intent = new Intent(this,donate.class);
			startActivity(intent);
		});
		settings.setOnClickListener((View v)->{
			Intent intent2 = new Intent(this,options.class);
			intent2.putExtra("finisher",new ResultReceiver(null){
				@Override
				protected void onReceiveResult(int resultCode, Bundle resultData) {
					super.onReceiveResult(resultCode, resultData);
					primary.this.finish();
				}
			});
			startActivityForResult(intent2,1);
		});
		try{
			ref.addListenerForSingleValueEvent(new ValueEventListener(){

				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					try{
					for(DataSnapshot snapshot1 : snapshot.getChildren()){ //Getting user of root
						for(DataSnapshot snapshot2 : snapshot1.getChildren()) { //Getting post of users
							detail = new StringBuilder();
							if((System.currentTimeMillis() - (Long.parseLong(String.valueOf(snapshot2.child("timestamp").getValue()))) < 86400000)) {
								for (DataSnapshot snapshot3 : snapshot2.getChildren())
									detail.append("=").append(snapshot3.getValue());
								string_detail = (detail.toString()).split("=");
								//String address, String available, String city, String district, String food, String mobile, String name, String postId, String time, String unit
								available_list.add(new ModelClass(string_detail[1], string_detail[2], string_detail[3], string_detail[4], string_detail[5], string_detail[6], string_detail[7], string_detail[8], string_detail[9], string_detail[10],string_detail[11]));
							}
						}
					}
					} catch(Exception e){
						Toast.makeText(primary.this,e.getMessage(),Toast.LENGTH_LONG).show();
					}
					adapter = new Adapter(available_list);
					donated.setAdapter(adapter);
		            alert.dismiss();
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {
					Toast.makeText(primary.this,error.getMessage(), Toast.LENGTH_LONG).show();
				}
			});
		} catch(Exception e){
			Toast.makeText(primary.this,e.getMessage(), Toast.LENGTH_LONG).show();
		}
		district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selected_district = String.valueOf(district.getSelectedItem());
				if(selected_district.equals("Select District")) {
					adapter = new Adapter(available_list);
					donated.setAdapter(adapter);
					city.setVisibility(View.INVISIBLE);
				}
				else {
					city.setVisibility(View.VISIBLE);
					select_city = cities.cities_list(selected_district,getApplicationContext());
					adapter_two = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,select_city);
					city.setAdapter(adapter_two);
					alert = builder.show();
					district_list.clear();
					for(int i=0;i<available_list.size();i++)
						if(available_list.get(i).getDistrict().equals(selected_district))
							district_list.add(new ModelClass(available_list.get(i).getAddress(),available_list.get(i).getAvailable(),available_list.get(i).getCity(),available_list.get(i).getDistrict(),available_list.get(i).getFood(),available_list.get(i).getMobile(),available_list.get(i).getName(),available_list.get(i).getPostId(),available_list.get(i).getTime(),available_list.get(i).getUnit(),available_list.get(i).getUserId()));
						adapter = new Adapter(district_list);
						donated.setAdapter(adapter);
					alert.dismiss();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selected_city = (String) city.getSelectedItem();
				if(!selected_city.equals("Select City")){
					alert = builder.show();
					city_list.clear();
					for(int i=0;i<available_list.size();i++)
						if(available_list.get(i).getCity().equals(selected_city))
							city_list.add(new ModelClass(available_list.get(i).getAddress(),available_list.get(i).getAvailable(),available_list.get(i).getCity(),available_list.get(i).getDistrict(),available_list.get(i).getFood(),available_list.get(i).getMobile(),available_list.get(i).getName(),available_list.get(i).getPostId(),available_list.get(i).getTime(),available_list.get(i).getUnit(),available_list.get(i).getUserId()));
						//String address, String available, String city, String district, String food, String mobile, String name, String postId, String time, String unit, String userId
					adapter = new Adapter(city_list);
					donated.setAdapter(adapter);
					alert.dismiss();
				} else{
					adapter = new Adapter(district_list);
					donated.setAdapter(adapter);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		refresh.setOnClickListener((View v)->{
			Intent re = new Intent(this,primary.class);
			startActivity(re);
			finish();
			overridePendingTransition(0,0);
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}