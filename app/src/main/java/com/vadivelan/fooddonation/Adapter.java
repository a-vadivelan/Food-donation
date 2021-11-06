package com.vadivelan.fooddonation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.sql.Timestamp;
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
		//holder.setData(food_list.get(position).getName(),food_list.get(position).getFood_name(),food_list.get(position).getPersons(),food_list.get(position).getLocation(),food_list.get(position).getCell(),food_list.get(position).getDate());
		holder.setData(food_list.get(position).getAddress(),food_list.get(position).getAvailable(),food_list.get(position).getCity(),food_list.get(position).getDistrict(),food_list.get(position).getFood(),food_list.get(position).getMobile(),food_list.get(position).getName(),food_list.get(position).getPostId(),food_list.get(position).getTime(),food_list.get(position).getUnit());
	}

	@Override
	public int getItemCount() {
		return food_list.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		Timestamp timestamp;
		Date date;
		TextView name,food,quantity,address,mobile,date_time;
		ViewHolder(View v){
			super(v);
			name = v.findViewById(R.id.donor_name);
			food = v.findViewById(R.id.food_name);
			quantity = v.findViewById(R.id.available);
			address = v.findViewById(R.id.location);
			mobile = v.findViewById(R.id.cell);
			date_time = v.findViewById(R.id.date);
		}
		public void setData(String donar_address,String available,String city,String district,String food_name,String donar_mobile,String donar_name,String postId,String time,String unit){
//			timestamp = new Timestamp(Long.parseLong(time));
//			date = timestamp;
			name.setText(donar_name);
			food.setText(food_name);
			quantity.setText(String.format(Locale.ENGLISH,"%s %s",available,unit));
			address.setText(String.format(Locale.ENGLISH,"%s, %s, %s",donar_address,city,district));
			mobile.setText(donar_mobile);
//			date_time.setText(date.toString());
		}
	}
}
