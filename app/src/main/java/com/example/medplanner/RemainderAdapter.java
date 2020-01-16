package com.example.medplanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RemainderAdapter extends RecyclerView.Adapter<RemainderAdapter.MyVH> {
    ArrayList<Long> longs;
    Context context;
    SQLHelper sqlHelper;

    SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);

    public RemainderAdapter(ArrayList<Long> longs, Context context) {
        this.longs = longs;
        this.context = context;
        sqlHelper = new SQLHelper(context);
    }

    @NonNull
    @Override
    public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.remainder_layout,parent,false);
        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVH holder, final int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(longs.get(position));
        String date = sdf.format(calendar.getTime());
        holder.timetext.setText(date);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("K",sdf.format(longs.get(position)));

                if(sqlHelper.onDeleteTime(longs.get(position)))
                {
                    Toast.makeText(context,"removed"+position,Toast.LENGTH_SHORT).show();
                }
                longs.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,longs.size());
            }


        });

    }

    @Override
    public int getItemCount() {
        return longs.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {
        TextView timetext;
        ImageView img;
        public MyVH(@NonNull View itemView) {
            super(itemView);
            timetext = itemView.findViewById(R.id.timelist);
            img = itemView.findViewById(R.id.remainder_cross);
        }
    }
}
