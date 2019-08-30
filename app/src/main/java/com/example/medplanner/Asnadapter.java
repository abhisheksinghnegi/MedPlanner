package com.example.medplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Asnadapter extends RecyclerView.Adapter<Asnadapter.MYVH> {
    Context context;
    ArrayList<MODEL> arrayList;
    SQLHelper sqlHelper;
    Cursor cursor;
    int prev, add, pos;
    PendingIntent pend;
    final Intent intent;

    public Asnadapter(Context context, ArrayList<MODEL> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sqlHelper = new SQLHelper(context);
        intent = new Intent(context, Alarmbroadcast.class);

    }


    @NonNull
    @Override
    public MYVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater l = LayoutInflater.from(context);

        View v = l.inflate(R.layout.recycler_layout, parent, false);
        return new MYVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MYVH holder, final int position) {
        holder.t1.setText(arrayList.get(position).alarm);
        holder.t2.setText(arrayList.get(position).medname);
        holder.t3.setText(arrayList.get(position).medType);
        holder.t3.setText(arrayList.get(position).bIO);
        holder.frame.setBackgroundColor(Color.parseColor(arrayList.get(position).color));
        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent xforce= new Intent(context,meddetails.class);
                xforce.putExtra("MODE",1);
                xforce.putExtra("MEDICINE_N",arrayList.get(position).medname);
                context.startActivity(xforce);
            }
        });
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor = sqlHelper.onGet();
                while (cursor.moveToNext()) {

                    if (arrayList.get(position).medname.equals(cursor.getString(cursor.getColumnIndex("MEDICINE_NAME")))) {
                        prev = cursor.getInt(cursor.getColumnIndex("FIRST"));
                        add = cursor.getInt(cursor.getColumnIndex("LAST"));
                        break;
                    }

                }
                Log.d("PREV", String.valueOf(prev + add));
                for (int i = prev; i < add; i++) {
                    pend = PendingIntent.getBroadcast(context, i, intent, 0);
                    if (alarmManager != null)
                        alarmManager.cancel(pend);
                    Log.d("ALARM", "ALARMCANCELLED FOR " + i);

                }
                if (sqlHelper.onDelete(arrayList.get(position).medname)) {
                    Toast.makeText(context, "removed" + position, Toast.LENGTH_SHORT).show();
                }
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MYVH extends RecyclerView.ViewHolder {
        TextView t1, t2, t3, t4;
        ImageView cross;
        FrameLayout frame;

        public MYVH(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.Med_time);
            t2 = itemView.findViewById(R.id.medicine_name);
            t3 = itemView.findViewById(R.id.Med_Type);
            t4 = itemView.findViewById(R.id.bio);
            cross = itemView.findViewById(R.id.cross);
            frame = itemView.findViewById(R.id.frameMed);
        }
    }
}
