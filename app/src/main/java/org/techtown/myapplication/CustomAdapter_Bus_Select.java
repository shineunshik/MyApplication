package org.techtown.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter_Bus_Select extends RecyclerView.Adapter<CustomAdapter_Bus_Select.CustomViewHolder> {

    ArrayList<Ob_Bus_Select> arrayList;
    Context context;

    public CustomAdapter_Bus_Select(ArrayList<Ob_Bus_Select> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_select_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.bus_type.setText(arrayList.get(position).getRoutetp());
        holder.bus_number.setText(arrayList.get(position).getRouteno());
        holder.bus_end.setText(arrayList.get(position).getEndnodenm());
        holder.bus_start.setText(arrayList.get(position).getStartnodenm());
        holder.last_time.setText(arrayList.get(position).getEndvehicletime());
        holder.first_time.setText(arrayList.get(position).getStartvehicletime());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView bus_type;
        TextView bus_number;
        TextView bus_end;
        TextView bus_start;
        TextView last_time;
        TextView first_time;
        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            bus_type = itemView.findViewById(R.id.bus_type);
            bus_number = itemView.findViewById(R.id.bus_number);
            bus_end = itemView.findViewById(R.id.bus_end);
            bus_start = itemView.findViewById(R.id.bus_start);
            last_time = itemView.findViewById(R.id.last_time);
            first_time = itemView.findViewById(R.id.first_time);
            view = itemView;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(context,PlaceBusSelect_1_RealTime_Address.class);
                    intent.putExtra("cityCode",arrayList.get(position).getCitycode());
                    intent.putExtra("routeId",arrayList.get(position).getRouteid());
                    intent.putExtra("endnodenm",arrayList.get(position).getEndnodenm());
                    intent.putExtra("startnodenm",arrayList.get(position).getStartnodenm());
                    intent.putExtra("station","http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteAcctoThrghSttnList");
                    intent.putExtra("realtimeAddress","http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList");
                    context.startActivity(intent);
                }
            });

        }
    }
}
