package com.app.feish.application.Patient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.feish.application.Adpter.CustomAdapter_cycle;
import com.app.feish.application.R;
import com.app.feish.application.clinic.DoctorSummary;
import com.app.feish.application.clinic.ListbaseAdapter;
import com.app.feish.application.model.searchdoctorpojo;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterFemaleCycle extends BaseAdapter {
    Context context;
    ArrayList<MenstruationDatum> list;
    public CustomAdapterFemaleCycle(Context context, ArrayList<MenstruationDatum> list) {
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

        CustomAdapterFemaleCycle.ViewHolder viewHolder = null;

        if (convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
            viewHolder = new CustomAdapterFemaleCycle.ViewHolder();

            convertView = layoutInflater.inflate(R.layout.listviewforfemalecycle,null);

            viewHolder.description= (TextView)convertView.findViewById(R.id.descforfemalecycle);
            viewHolder.sdate= (TextView)convertView.findViewById(R.id.sdatecycle);
            viewHolder.edate= (TextView)convertView.findViewById(R.id.edatecycle);

            viewHolder.description.setTypeface(type);
            viewHolder.sdate.setTypeface(type);
            viewHolder.edate.setTypeface(type);


            convertView.setTag(viewHolder);


        }else {

            viewHolder = (CustomAdapterFemaleCycle.ViewHolder)convertView.getTag();

        }


        MenstruationDatum list = (MenstruationDatum) getItem(position);

        // viewHolder.image.setImageResource(beans.getImage());
        viewHolder.description.setText(list.getDescription());
        viewHolder.sdate.setText(list.getStartDate());
        viewHolder.edate.setText(list.getEndDate());

        return convertView;
    }



    private class ViewHolder{

        TextView description;
        TextView sdate;
        TextView edate;

    }


}
