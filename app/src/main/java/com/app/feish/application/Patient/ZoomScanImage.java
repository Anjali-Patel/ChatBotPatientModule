package com.app.feish.application.Patient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.app.feish.application.R;

public class ZoomScanImage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageview = null;
        Intent intent = null;
        setContentView(R.layout.activity_zoom_scan_image);
        imageview=findViewById(R.id.zoomimagescan);
        imageview.setImageBitmap(toImage(getIntent().getStringExtra("image")));

    }

    private Bitmap toImage(String raw)
    {
        byte[] decodedString = Base64.decode(raw, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return  decodedByte;
    }

}
