package com.app.feish.application.Patient;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feish.application.R;
import com.app.feish.application.sessiondata.Prefhelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL1;
import static com.app.feish.application.Remote.ApiUtils.XAPI_KEY;

public class ListWorkoutDetails extends AppCompatActivity {
    ListView listview;
    Context context=this;
    private ArrayList<WorkoutDetail> workoutDetails;
    private PojoViewWorkout pojoViewWorkout;
    private CustomAdapterViewWorkout customAdapterViewWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_workout_details);
        listview=findViewById(R.id.listofworkoutdetails);
        workoutDetails=new ArrayList<>();
        TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText("List of Workout Details You Added");
        listview.addHeaderView(textView);

        fetchdata();
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

                            pojoViewWorkout = gson.fromJson(body, PojoViewWorkout.class);
                            workoutDetails = (ArrayList)pojoViewWorkout.getWorkoutDetails();
                            // Log.i("menstrutaion data",menstruationData.toString());
                            customAdapterViewWorkout = new CustomAdapterViewWorkout(ListWorkoutDetails.this, workoutDetails);
                            listview.setAdapter(customAdapterViewWorkout);

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "No Data Found, Add Data First", Toast.LENGTH_SHORT).show();
                        } }
                }); }
        });
    }
    private Request femalecycledetail() {
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", XAPI_KEY)
                .url(BASE_URL1+"get_workout_detail/"+Integer.parseInt(Prefhelper.getInstance(context).getUserid()))
                .get()
                .build();
        return request;
    }
    public void CloseActivity(View view)
    {
        finish();
    }
}
