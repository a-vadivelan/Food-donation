package com.vadivelan.fooddonation;

import android.content.Context;
import android.widget.Toast;

public class locations{
	public String[] cities_list(String selected_district, Context context){
		String[] select_city = new String[]{};
		switch (selected_district) {
			case "Ariyalur":
				select_city = context.getResources().getStringArray(R.array.ariyalur);
				break;
			case "Chengalpet":
				select_city = context.getResources().getStringArray(R.array.chengalpet);
				break;
			case "Chennai":
				select_city = context.getResources().getStringArray(R.array.chennai);
				break;
			case "Coimbatore":
				select_city = context.getResources().getStringArray(R.array.coimbatore);
				break;
			case "Cuddalore":
				select_city = context.getResources().getStringArray(R.array.cuddalore);
				break;
			case "Dharmapuri":
				select_city = context.getResources().getStringArray(R.array.dharmapuri);
				break;
			case "Dindugal":
				select_city = context.getResources().getStringArray(R.array.dindugal);
				break;
			case "Erode":
				select_city = context.getResources().getStringArray(R.array.erode);
				break;
			case "Kallakurichi":
				select_city = context.getResources().getStringArray(R.array.kallakurichi);
				break;
			case "Kanchipuram":
				select_city = context.getResources().getStringArray(R.array.kanchipuram);
				break;
			case "Kanniyakumari":
				select_city = context.getResources().getStringArray(R.array.kanniyakumari);
				break;
			case "Karaikaal":
				select_city = context.getResources().getStringArray(R.array.karaikaal);
				break;
			case "Karur":
				select_city = context.getResources().getStringArray(R.array.karur);
				break;
			case "Krishnagiri":
				select_city = context.getResources().getStringArray(R.array.krishnagiri);
				break;
			case "Madurai":
				select_city = context.getResources().getStringArray(R.array.madurai);
				break;
			case "Mahi":
				select_city = context.getResources().getStringArray(R.array.mahi);
				break;
			case "Mayiladudurai":
				select_city = context.getResources().getStringArray(R.array.mayiladudurai);
				break;
			case "Nagapattinam":
				select_city = context.getResources().getStringArray(R.array.nagapattinam);
				break;
			case "Namakkal":
				select_city = context.getResources().getStringArray(R.array.namakkal);
				break;
			case "Nilgiris":
				select_city = context.getResources().getStringArray(R.array.nilagiri);
				break;
			case "Perambalur":
				select_city = context.getResources().getStringArray(R.array.perambalur);
				break;
			case "Pudhucheri":
				select_city = context.getResources().getStringArray(R.array.puducheri);
				break;
			case "Pudukkottai":
				select_city = context.getResources().getStringArray(R.array.puthukkottai);
				break;
			case "Ramanathapuram":
				select_city = context.getResources().getStringArray(R.array.ramnad);
				break;
			case "Ranipet":
				select_city = context.getResources().getStringArray(R.array.ranipet);
				break;
			case "Salem":
				select_city = context.getResources().getStringArray(R.array.salem);
				break;
			case "Sivaganga":
				select_city = context.getResources().getStringArray(R.array.sivaganga);
				break;
			case "Tenkasi":
				select_city = context.getResources().getStringArray(R.array.tenkasi);
				break;
			case "Thanjavur":
				select_city = context.getResources().getStringArray(R.array.thanjavur);
				break;
			case "Theni":
				select_city = context.getResources().getStringArray(R.array.theni);
				break;
			case "Tiruvallur":
				select_city = context.getResources().getStringArray(R.array.thiruvallur);
				break;
			case "Tiruvarur":
				select_city = context.getResources().getStringArray(R.array.thiruvarur);
				break;
			case "Tutucorin":
				select_city = context.getResources().getStringArray(R.array.tutukodi);
				break;
			case "Tiruchirappalli":
				select_city = context.getResources().getStringArray(R.array.trichy);
				break;
			case "Thirunelveli":
				select_city = context.getResources().getStringArray(R.array.thirunelveli);
				break;
			case "Tirupathur":
				select_city = context.getResources().getStringArray(R.array.thirupatthur);
				break;
			case "Tiruppur":
				select_city = context.getResources().getStringArray(R.array.thiruppur);
				break;
			case "Tiruvannamalai":
				select_city = context.getResources().getStringArray(R.array.thiruvannamalai);
				break;
			case "Vellore":
				select_city = context.getResources().getStringArray(R.array.vellore);
				break;
			case "Villupuram":
				select_city = context.getResources().getStringArray(R.array.villupuram);
				break;
			case "Virudhunagar":
				select_city = context.getResources().getStringArray(R.array.virudhunagar);
				break;
			case "Yaanam":
				select_city = context.getResources().getStringArray(R.array.yaanam);
				break;
			default:
				Toast.makeText(context, "Choose correct option", Toast.LENGTH_SHORT).show();
		}
	return select_city;
	}
}
