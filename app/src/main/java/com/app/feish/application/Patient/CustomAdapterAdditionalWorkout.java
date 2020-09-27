package com.app.feish.application.Patient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.feish.application.R;

import java.util.ArrayList;

public class CustomAdapterAdditionalWorkout extends BaseAdapter {

    Context context;
    ArrayList<AdditionalInfoWorkoutDatum> list;



    public CustomAdapterAdditionalWorkout(Context context, ArrayList<AdditionalInfoWorkoutDatum> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size() ;
    }

    @Override
    public Object getItem(int position) {
        return list.get(getCount()-position-1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomAdapterAdditionalWorkout.ViewHolder viewHolder = null;





        if (convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
            viewHolder = new CustomAdapterAdditionalWorkout.ViewHolder();

            convertView = layoutInflater.inflate(R.layout.listviewadditionalworkout,null);

            viewHolder.sugar= (TextView)convertView.findViewById(R.id.sugar1);
            viewHolder.bloodpressure= (TextView)convertView.findViewById(R.id.bloodpresure1);
            viewHolder.heartbeats= (TextView)convertView.findViewById(R.id.heartbeat1);
            viewHolder.colastrol= (TextView)convertView.findViewById(R.id.colastrol);

            viewHolder.sugar.setTypeface(type);
            viewHolder.bloodpressure.setTypeface(type);
            viewHolder.heartbeats.setTypeface(type);
            viewHolder.colastrol.setTypeface(type);



            convertView.setTag(viewHolder);


        }else {

            viewHolder = (CustomAdapterAdditionalWorkout.ViewHolder)convertView.getTag();

        }


        AdditionalInfoWorkoutDatum list = (AdditionalInfoWorkoutDatum) getItem(position);

        // viewHolder.image.setImageResource(beans.getImage());
        viewHolder.sugar.setText(list.getAverageSugar());
        viewHolder.bloodpressure.setText(list.getAverageBloodPressure());
        viewHolder.heartbeats.setText(list.getAverageHeartsBeats());
        viewHolder.colastrol.setText(list.getAverageColastrol());


        return convertView;
    }

    private class ViewHolder{

        TextView sugar;
        TextView bloodpressure;
        TextView heartbeats;
        TextView colastrol;


    }
    }

