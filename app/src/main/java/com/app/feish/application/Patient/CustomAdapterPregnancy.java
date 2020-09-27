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

public class CustomAdapterPregnancy extends BaseAdapter {
    Context context;
    ArrayList<PregnancyDatum> list;



    public CustomAdapterPregnancy(Context context, ArrayList<PregnancyDatum> list) {
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

        CustomAdapterPregnancy.ViewHolder viewHolder = null;


        if (convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
            viewHolder = new CustomAdapterPregnancy.ViewHolder();

            convertView = layoutInflater.inflate(R.layout.listviewpregnancy,null);

            viewHolder.pregnancytime=convertView.findViewById(R.id.pregnancytime);
            viewHolder.sdate=convertView.findViewById(R.id.sdatepregnancy);
            viewHolder.edate=convertView.findViewById(R.id.edatepregnancy);
            viewHolder.month=convertView.findViewById(R.id.month);
            viewHolder.week=convertView.findViewById(R.id.week);
            viewHolder.complication=convertView.findViewById(R.id.pregnancycomplication);

            viewHolder.pregnancytime.setTypeface(type);
            viewHolder.sdate.setTypeface(type);
            viewHolder.edate.setTypeface(type);
            viewHolder.month.setTypeface(type);
            viewHolder.week.setTypeface(type);
            viewHolder.complication.setTypeface(type);


            convertView.setTag(viewHolder);


        }else {

            viewHolder = (CustomAdapterPregnancy.ViewHolder)convertView.getTag();

        }


        PregnancyDatum list = (PregnancyDatum) getItem(position);
        viewHolder.pregnancytime.setText(list.getPregnancyTime());
        viewHolder.sdate.setText(list.getStartDate());
        viewHolder.edate.setText(list.getEndDate());
        viewHolder.month.setText(list.getMonth());
        viewHolder.week.setText(Integer.toString(list.getWeek()));
        viewHolder.complication.setText(list.getComment());


        return convertView;
    }



    private class ViewHolder{

        TextView pregnancytime;
        TextView sdate;
        TextView edate;
        TextView month;
        TextView week;
        TextView complication;

    }

}
