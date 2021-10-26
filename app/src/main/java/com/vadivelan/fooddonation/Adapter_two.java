package com.vadivelan.fooddonation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class Adapter_two extends RecyclerView.Adapter<Adapter_two.ViewHolder>{
List<ModelClass> active_food;
Adapter_two(List<ModelClass> active_food){
	this.active_food=active_food;
}
	@NonNull
	@Override
	public Adapter_two.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_layout,parent,false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull Adapter_two.ViewHolder holder, int position) {
holder.setData(active_food.get(position).getFood_name(),active_food.get(position).getPersons(),active_food.get(position).getLocation(),active_food.get(position).getCell(),active_food.get(position).getDate());
	}

	@Override
	public int getItemCount() {
		return active_food.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
	TextView tfood_name,tperson,tlocation,tcell,tdate;
		ViewHolder(View v){
			super(v);
			tfood_name = v.findViewById(R.id.food_name);
			tperson = v.findViewById(R.id.available);
			tlocation = v.findViewById(R.id.location);
			tcell = v.findViewById(R.id.cell);
			tdate = v.findViewById(R.id.date);
		}
		public void setData(String food_name,int person,String location,String cell,String date){
			tfood_name.setText(food_name);
			tperson.setText(String.format(Locale.ENGLISH,"%d person(s)",person));
			tlocation.setText(location);
			tcell.setText(cell);
			tdate.setText(date);
		}
	}
}
