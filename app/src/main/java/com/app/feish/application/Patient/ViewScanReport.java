package com.app.feish.application.Patient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feish.application.R;
import com.app.feish.application.fragment.ListFamilyhistory;
import com.app.feish.application.sessiondata.Prefhelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL1;
import static com.app.feish.application.Remote.ApiUtils.XAPI_KEY;

public class ViewScanReport extends AppCompatActivity {
    private ListView listView1234;
    private ArrayList<ReportDatum> reports;
    private PojoViewScanrReport pojoViewScanrReport;
    private ArrayAdaptorScanView arrayAdaptorScanView;
    Context context=this;
    ImageView imageView;
    ProgressBar progressbar;
    boolean isImageFitToScreen;


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scan_report);
        listView1234=findViewById(R.id.scanlist_12);


        TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText("List of Report Scanned");
        listView1234.addHeaderView(textView);

        // For populating list data
        CustomAdapterScanReportView customCountryList = new CustomAdapterScanReportView(this ,Namesofreport,dateofreport,commentinreport, imageid);
        listView1234.setAdapter(customCountryList);

        listView1234.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),"You Selected "+Namesofreport[position-1]+ " as Country", Toast.LENGTH_SHORT).show();        }
        });
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scan_report);
        listView1234=findViewById(R.id.scanlist_12);

        progressbar=findViewById(R.id.progressbar);
        TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText("List of Report Scanned");
        listView1234.addHeaderView(textView);
        fetchdata();
        progressbar.setVisibility(View.VISIBLE);
        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setAdjustViewBounds(true);
                }else{
                    isImageFitToScreen=true;
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });

        listView1234.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReportDatum item = (ReportDatum) listView1234.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "CLICK: " + item, Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(ViewScanReport.this,ZoomScanImage.class);
                intent.putExtra("image", item.getRaw());
                startActivity(intent);
                //imageView.setImageBitmap(toImage(item.getRaw()));
            }
        });


    }

    private Bitmap toImage(String raw)
    {
        byte[] decodedString = Base64.decode(raw, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //code to store image inside sd temp
        return  decodedByte;
    }

    ImageView vvv;
    private void fetchdata() {
        OkHttpClient client = new OkHttpClient();
        Request request = listReport();
        //Log.i(TAG, "onClick: " + request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final String body = response.body().string();
                Log.i("response",body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressbar.setVisibility(View.GONE);
                            Gson gson = new GsonBuilder().create();

                            pojoViewScanrReport = gson.fromJson(body, PojoViewScanrReport.class);
                            reports = (ArrayList)pojoViewScanrReport.getReports();
                            Collections.reverse(reports);

                            Log.i("hii",reports.toString());
                            arrayAdaptorScanView = new ArrayAdaptorScanView(ViewScanReport.this,reports );
                            listView1234.setAdapter(arrayAdaptorScanView);
                            arrayAdaptorScanView.add(reports.get(0));

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "No Report Found, Scan First", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    private Request listReport()
    {
        JSONObject postdata = new JSONObject();
        try {
            // postdata.put("user_id",1227);
            postdata.put("user_id",Integer.parseInt(Prefhelper.getInstance(context).getUserid()));
            } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(ListFamilyhistory.JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", XAPI_KEY)
                .addHeader("Content-Type", "application/json")
                .url(BASE_URL1+"listReports")
                .post(body)
                .build();
    }
    public void CloseActivity(View view)
    {
        finish();
    }
}

