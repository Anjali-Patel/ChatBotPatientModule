package com.app.feish.application.Patient;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.feish.application.R;

public class CustomAdapterScanReportView extends ArrayAdapter {

    private String[] Namesofreport;
    private String[] dateofreport;
    private String[] commentinreport;
    private Integer[] imageid;
    private Activity context;

    public CustomAdapterScanReportView(Activity context, String[] Namesofreport, String[] dateofreport, String[] commentinreport, Integer[] imageid) {
        super(context, R.layout.scanviewreportrow_item,  Namesofreport);
        this.context = context;

        this.Namesofreport = Namesofreport;
        this.dateofreport = dateofreport;
        this.commentinreport = commentinreport;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.scanviewreportrow_item, null, true);

        TextView textViewname = (TextView) row.findViewById(R.id.name123);
        TextView textViewdate = (TextView) row.findViewById(R.id.date123);
        TextView textViewcomment = (TextView) row.findViewById(R.id.comment123);
        ImageView image = (ImageView) row.findViewById(R.id.image123);


        textViewname.setText(Namesofreport[position]);
        textViewdate.setText( dateofreport[position]);
        textViewcomment.setText(commentinreport[position]);
        image.setImageResource(imageid[position]);
        return  row;
    }
}