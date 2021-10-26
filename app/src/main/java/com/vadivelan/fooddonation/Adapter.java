package com.vadivelan.fooddonation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
		holder.setData(food_list.get(position).getName(),food_list.get(position).getFood_name(),food_list.get(position).getPersons(),food_list.get(position).getLocation(),food_list.get(position).getCell(),food_list.get(position).getDate());
	}

	@Override
	public int getItemCount() {
		return food_list.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		TextView ddonor,dfood,dpersons,dlocation,dcell,ddate;
		ViewHolder(View v){
			super(v);
			ddonor = v.findViewById(R.id.donor_name);
			dfood = v.findViewById(R.id.food_name);
			dpersons = v.findViewById(R.id.available);
			dlocation = v.findViewById(R.id.location);
			dcell = v.findViewById(R.id.cell);
			ddate = v.findViewById(R.id.date);
		}
		public void setData(String donar_name,String food_name,int person,String location,String cell,String date){
			ddonor.setText(donar_name);
			dfood.setText(food_name);
			dpersons.setText(String.format(Locale.ENGLISH,"%d Person(s)",person));
			dlocation.setText(location);
			dcell.setText(cell);
			ddate.setText(date);
		}
	}
}
