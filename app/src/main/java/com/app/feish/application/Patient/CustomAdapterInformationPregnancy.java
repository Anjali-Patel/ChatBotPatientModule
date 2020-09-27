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

public class CustomAdapterInformationPregnancy extends BaseAdapter {

    Context context;
    ArrayList<WorkoutPlanDatum> list;



    public CustomAdapterInformationPregnancy (Context context, ArrayList<WorkoutPlanDatum> list) {
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

        CustomAdapterInformationPregnancy.ViewHolder viewHolder = null;





        if (convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
            viewHolder = new CustomAdapterInformationPregnancy.ViewHolder();

            convertView = layoutInflater.inflate(R.layout.listviewinformationpregnancy,null);

            viewHolder.workoutweek= (TextView)convertView.findViewById(R.id.workoutweek);
            viewHolder.childweight= (TextView)convertView.findViewById(R.id.childweight);
            viewHolder.complication= (TextView)convertView.findViewById(R.id.complication);

            viewHolder.workoutweek.setTypeface(type);
            viewHolder.childweight.setTypeface(type);
            viewHolder.complication.setTypeface(type);


            convertView.setTag(viewHolder);


        }else {

            viewHolder = (CustomAdapterInformationPregnancy.ViewHolder)convertView.getTag();

        }


        WorkoutPlanDatum list = (WorkoutPlanDatum) getItem(position);

        // viewHolder.image.setImageResource(beans.getImage());
        viewHolder.workoutweek.setText(list.getWorkoutWeek());
        viewHolder.childweight.setText(list.getChildWeight());
        viewHolder.complication.setText(list.getComplications());

        return convertView;
    }



    private class ViewHolder{

        TextView workoutweek;
        TextView childweight;
        TextView complication;

    }
}
