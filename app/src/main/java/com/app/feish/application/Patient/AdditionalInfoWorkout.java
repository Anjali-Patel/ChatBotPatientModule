package com.app.feish.application.Patient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.feish.application.R;
import com.app.feish.application.Remote.ApiUtils;
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

import static com.app.feish.application.fragment.ListFamilyhistory.JSON;

public class AdditionalInfoWorkout extends AppCompatActivity {
    EditText sugar,colastrol,heartbeat,bloodpressure;
    ListView listView;
    Context context=this;
    Button submitaddwotkout;
    String string_sugar = "", string_colastrol = "", string_heartbeat = "",string_bloodprssure="";
    private ArrayList<AdditionalInfoWorkoutDatum> additionalInfoWorkoutData;
    private PojoAdditionalWorkout pojoAdditionalWorkout;
    private CustomAdapterAdditionalWorkout customAdapterAdditionalWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info_workout);
        sugar=findViewById(R.id.sugar);
        colastrol=findViewById(R.id.colestrol);
        heartbeat=findViewById(R.id.heartbeat);
        bloodpressure=findViewById(R.id.bloodpressure);
submitaddwotkout=findViewById(R.id.submitaddworkout);
        listView=findViewById(R.id.listviewaddworkout);


        fetchdata();




        submitaddwotkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string_sugar=sugar.getText().toString();
                string_bloodprssure=bloodpressure.getText().toString();
                string_heartbeat=heartbeat.getText().toString();
                string_colastrol=colastrol.getText().toString();
                if(validateaddworkoutdetails())
                {
                    AdditionalWorkotData();
                }
            }
        });

    }

    void AdditionalWorkotData()
    {

        OkHttpClient client = new OkHttpClient();
        Request request = AddData();
        Log.i("", "onClick: " + request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final String body = response.body().string();
                Log.i("1234check", "onResponse: " + body);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            if (jsonObject.getString("message").equals("ADD_SUCCESSFULLY")) {
                                Toast.makeText(getApplicationContext(), "Data Added SuccesFully", Toast.LENGTH_SHORT).show();

                                } else {

                                Toast.makeText(getApplicationContext(), "Data Added SuccesFully", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    private Request AddData()
    {
        JSONObject postdata = new JSONObject();
        try {

            postdata.put("user_id",Integer.parseInt(Prefhelper.getInstance(context).getUserid()));
            postdata.put("workout_id","1");
            postdata.put("sugar",string_sugar);
            postdata.put("blood_pressure",string_bloodprssure);
            postdata.put("hearts_beats",string_heartbeat);
            postdata.put("colastrol",string_colastrol);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", ApiUtils.XAPI_KEY)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(ApiUtils.BASE_URL1 +"create_workout_additional_info")
                .post(body)
                .build();
    }


    private boolean validateaddworkoutdetails() {
        if (string_sugar.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Sugar field empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if (string_bloodprssure.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Blood Pressure field empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (string_heartbeat.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "HeartBeat field empty", Toast.LENGTH_LONG).show();
            return false;

        } else if (string_colastrol.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Colastrol field empty", Toast.LENGTH_LONG).show();
            return false;

        } else {
            return true;
        }
    }



    private void fetchdata() {
        OkHttpClient client = new OkHttpClient();
        Request request = femalecycledetail();
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
                            Gson gson = new GsonBuilder().create();
                            pojoAdditionalWorkout = gson.fromJson(body, PojoAdditionalWorkout.class);
                            additionalInfoWorkoutData = (ArrayList)pojoAdditionalWorkout.getAdditionalInfoWorkout();
                            // Log.i("menstrutaion data",menstruationData.toString());
                            customAdapterAdditionalWorkout = new CustomAdapterAdditionalWorkout(AdditionalInfoWorkout.this, additionalInfoWorkoutData);

                            listView.setAdapter(customAdapterAdditionalWorkout);

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
    private Request femalecycledetail() {
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", ApiUtils.XAPI_KEY)
                .url(ApiUtils.BASE_URL1+"get_workout_additional_info/"+Integer.parseInt(Prefhelper.getInstance(context).getUserid()))
                .get()
                .build();
        return request;
    }

    public void CloseActivity(View view)
    {
        finish();
    }

}
