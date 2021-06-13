package com.example.contacts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    static ArrayList<contacts> list;
    Context context;
    MyAdapter(ArrayList<contacts> list,Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.con_list,parent, false);
       MyViewHolder list = new MyViewHolder(view);
        return list;
    }

public static void filterList(ArrayList<contacts> filter1list)
{
   list=filter1list;


}
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        contacts c= list.get(position);
        holder.t11.setText(c.getName());
        holder.t22.setText(c.getNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ContactDetail.class);
                i.putExtra("name",c.getName());
                i.putExtra("number",c.getNumber());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       private ImageView image1;
       private TextView t11,t22;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image1=itemView.findViewById(R.id.image);
            t11=itemView.findViewById(R.id.t1);
            t22=itemView.findViewById(R.id.t2);


        }
    }
}
