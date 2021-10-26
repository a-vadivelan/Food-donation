package com.vadivelan.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class donate extends AppCompatActivity {
EditText food_available;
Spinner district,city;
String selected_district;
String[] select_city;
ArrayAdapter<String> adapter;
Button submit_btn;
Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate);
		food_available = findViewById(R.id.food_available);
		district = findViewById(R.id.district);
		city = findViewById(R.id.city);
		submit_btn = findViewById(R.id.submit_donate);
		intent = new Intent(this,primary.class);
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
			if (district.getSelectedItem().equals("Select District")||city.getSelectedItem().equals("Select City"))
				Toast.makeText(this, "Choose correct location", Toast.LENGTH_LONG).show();
			else
				startActivity(intent);
		});
	}
}