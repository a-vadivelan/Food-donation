package com.vadivelan.fooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class primary extends AppCompatActivity {
	Button donate;
	ImageButton settings;
	RecyclerView donated;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_primary);
		donate = findViewById(R.id.donate);
		settings = findViewById(R.id.settings);
		donated = findViewById(R.id.donated);
		donate.setOnClickListener((View v)->{
			Intent intent = new Intent(this,donate.class);
			startActivity(intent);
		});
		settings.setOnClickListener((View v)->{
			Intent intent2 = new Intent(this,options.class);
			startActivity(intent2);
		});
		List<ModelClass> food_list = new ArrayList<>();
		food_list.add(new ModelClass("Arun","Idly",13,"Madurai","9876543210","01-11-2021"));
		food_list.add(new ModelClass("Bala","Rice",3,"Trichy","9876543210","01-11-2021"));
		food_list.add(new ModelClass("Chandru","Dosai",13,"Coimbatore","9876543210","01-11-2021"));
		food_list.add(new ModelClass("David","Idly",13,"Thirunelveli","9876543210","01-11-2021"));
		food_list.add(new ModelClass("Elangovan","Idly",13,"Madurai","9876543210","01-11-2021"));
		Adapter adapter = new Adapter(food_list);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
		donated.setLayoutManager(linearLayoutManager);
		donated.setAdapter(adapter);

	}
}