package com.vadivelan.fooddonation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.Manifest;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
List<ModelClass> food_list;
	public Adapter(List<ModelClass> food_list) {
		super();
		this.food_list = food_list;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donated_layout,parent,false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.setData(food_list.get(position).getAddress(),food_list.get(position).getAvailable(),food_list.get(position).getCity(),food_list.get(position).getDistrict(),food_list.get(position).getFood(),food_list.get(position).getMobile(),food_list.get(position).getName(),food_list.get(position).getTime(),food_list.get(position).getUnit());
	}

	@Override
	public int getItemCount() {
		return food_list.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		TextView name,food,quantity,address,mobile,date_time;
		ImageButton call;
		Intent intent;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
		ViewHolder(View v){
			super(v);
			name = v.findViewById(R.id.donor_name);
			food = v.findViewById(R.id.food_name);
			quantity = v.findViewById(R.id.available);
			address = v.findViewById(R.id.location);
			mobile = v.findViewById(R.id.cell);
			date_time = v.findViewById(R.id.date);
			call = v.findViewById(R.id.call);
		}
		public void setData(String donar_address,String available,String city,String district,String food_name,String donar_mobile,String donar_name,String time,String unit){
			name.setText(donar_name);
			food.setText(food_name);
			quantity.setText(String.format(Locale.ENGLISH,"%s %s",available,unit));
			address.setText(String.format(Locale.ENGLISH,"%s, %s, %s",donar_address,city,district));
			mobile.setText(donar_mobile);
			call.setContentDescription(donar_mobile);
			date_time.setText(simpleDateFormat.format(new Date(Long.parseLong(time))));
			call.setOnClickListener((View v) -> {
				intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+donar_mobile));
				if(ActivityCompat.checkSelfPermission(v.getContext(),Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
					v.getContext().startActivity(intent);
				else{
					Toast.makeText(v.getContext(),"Please give Phone call permission", Toast.LENGTH_SHORT).show();
					Intent openSetting = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					openSetting.setData(Uri.parse("package:"+v.getContext().getPackageName()));
					v.getContext().startActivity(openSetting);
				}
			});
		}
	}
}
