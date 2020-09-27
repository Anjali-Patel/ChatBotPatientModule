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

public class CustomAdapterViewWorkout extends BaseAdapter {


    Context context;
    ArrayList<WorkoutDetail> list;


    public CustomAdapterViewWorkout(Context context, ArrayList<WorkoutDetail> list) {
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
        CustomAdapterViewWorkout.ViewHolder viewHolder = null;


        if (convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
            viewHolder = new CustomAdapterViewWorkout.ViewHolder();

            convertView = layoutInflater.inflate(R.layout.listviewworkout,null);

            viewHolder.date= (TextView)convertView.findViewById(R.id.dateforworkout);
            viewHolder.stime= (TextView)convertView.findViewById(R.id.stimeforworkout);
            viewHolder.etime= (TextView)convertView.findViewById(R.id.etimeforworkout);
            viewHolder.calories= (TextView)convertView.findViewById(R.id.workoutcalories);

            viewHolder.date.setTypeface(type);
            viewHolder.stime.setTypeface(type);
            viewHolder.etime.setTypeface(type);
            viewHolder.calories.setTypeface(type);


            convertView.setTag(viewHolder);


        }else {

            viewHolder = (CustomAdapterViewWorkout.ViewHolder)convertView.getTag();

        }


        WorkoutDetail list = (WorkoutDetail) getItem(position);

        // viewHolder.image.setImageResource(beans.getImage());
        viewHolder.date.setText(list.getWorkoutDate());
        viewHolder.stime.setText(list.getStartTime());
        viewHolder.etime.setText(list.getEndTime());
        viewHolder.calories.setText(list.getCalories());

        return convertView;
    }
    private class ViewHolder {

        TextView date;
        TextView stime;
        TextView etime;
        TextView calories;
    }
}
