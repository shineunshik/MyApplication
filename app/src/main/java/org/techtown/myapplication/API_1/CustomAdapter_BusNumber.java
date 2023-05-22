package org.techtown.myapplication.API_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.myapplication.CustomAdapter;
import org.techtown.myapplication.R;

import java.util.ArrayList;

public class CustomAdapter_BusNumber extends RecyclerView.Adapter<CustomAdapter_BusNumber.CustomViewHolder> {

    ArrayList<Ob_BusNumber> arrayList;
    Context context;

    public CustomAdapter_BusNumber(ArrayList<Ob_BusNumber> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.busnumber_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter_BusNumber.CustomViewHolder holder, int position) {

        holder.bus_lati.setText(arrayList.get(position).getGpslati());
        holder.bus_long.setText(arrayList.get(position).getGpslong());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView bus_lati;
        TextView bus_long;
        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            bus_lati = itemView.findViewById(R.id.bus_lati);
            bus_long = itemView.findViewById(R.id.bus_long);
            view = itemView;
        }
    }
}