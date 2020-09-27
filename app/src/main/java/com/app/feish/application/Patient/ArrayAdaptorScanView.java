package com.app.feish.application.Patient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.feish.application.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ArrayAdaptorScanView extends ArrayAdapter<ReportDatum>{

    public ArrayAdaptorScanView(Context context, ArrayList<ReportDatum> reports)
    {

        super(context,0,reports);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ReportDatum report = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.scanviewreportrow_item, parent, false);
        }
        // Lookup view for data population
        TextView reportName = (TextView) convertView.findViewById(R.id.name123);
        TextView reportDate = (TextView) convertView.findViewById(R.id.date123);
        TextView reportComment = (TextView) convertView.findViewById(R.id.comment123);
        ImageView reportImage = (ImageView)convertView.findViewById(R.id.image123);
        // Populate the data into the template view using the data object
        reportName.setText(report.getName());
        reportDate.setText(report.getDate());
        reportComment.setText(report.getComment());
        reportImage.setImageBitmap(toImage(report.getRaw()));
        return convertView;
    }

    private Bitmap toImage(String raw)
    {
        byte[] decodedString = Base64.decode(raw, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //code to store image inside sd temp
        saveTemp(decodedByte);
        return  decodedByte;
    }


    private String saveTemp(Bitmap mBitmap)
    {
        File f3=new File(Environment.getExternalStorageDirectory()+"/inpaint/");
        if(!f3.exists())
            f3.mkdirs();
        OutputStream outStream = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                Date());

        File file = new File(Environment.getExternalStorageDirectory() + "/inpaint/"+timeStamp+".jpg");
        try {
            outStream = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
