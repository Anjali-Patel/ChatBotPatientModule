package com.app.feish.application.Patient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feish.application.R;
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

import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL1;
import static com.app.feish.application.Remote.ApiUtils.XAPI_KEY;
import static com.app.feish.application.fragment.ListFamilyhistory.JSON;

public class InformationPregnancy extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    ListView listView;
    Button adddietplan,viewworkout;
    Button consultdoc,viewconsultreport,addremainder;
    Button submitworkout;
    Spinner selectweek,selectworkoutplan;
    Context context=this;
    private ArrayList<WorkoutPlanDatum> workoutPlanData;
    private PojoInformationPregnancy pojoInformationPregnancy;
    private CustomAdapterInformationPregnancy listbaseAdapter;

    EditText complication,week1,week2,week3,week4,week5,week6,week7,week8;
    String string_selectweek_spinner="",string_complication="",string_workoutlabel="",string_week1="",string_week2="",string_week3="",string_week4="",string_week5="",string_week6="",string_week7="",string_week8="";
    static Spinner spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_pregnancy);
        listView=findViewById(R.id.listforInformationpregnancyinfo);
        submitworkout=findViewById(R.id.submitweight);
        complication=findViewById(R.id.complicationforwomen);
        //selectworkoutplan=findViewById(R.id.workoutexcercise_spinner);
        selectweek=findViewById(R.id.workout_spinner);
        week1=findViewById(R.id.week1);

        spinner = (Spinner)this.findViewById(R.id.Spinner01);
        workoutPlanData=new ArrayList<>();

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, new WorkoutPlanIdName[] {
                new WorkoutPlanIdName( 1, "BriskWalking" ),
                new WorkoutPlanIdName( 2, "Swimming" ),
                new WorkoutPlanIdName( 3, "Yoga" ),
                new WorkoutPlanIdName( 4, "Low-impact aerobics" ),
                new WorkoutPlanIdName( 5, "Squatting and pelvic tilts" ),
                new WorkoutPlanIdName( 6, "step or elliptical machines" ),
                new WorkoutPlanIdName( 7, "jogging" ),

        });
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(this);

        //week2=findViewById(R.id.week2);
       // week3=findViewById(R.id.week3);
        //week4=findViewById(R.id.week4);
       // week5=findViewById(R.id.week5);
       // week6=findViewById(R.id.week6);
       // week7=findViewById(R.id.week7);
       // week8=findViewById(R.id.week8);


        fetchdata();

        adddietplan=findViewById(R.id.dietplan);
        consultdoc=findViewById(R.id.consultdoc);
        viewconsultreport=findViewById(R.id.viewconsultreport);
        addremainder=findViewById(R.id.remainderadd);
        viewworkout=findViewById(R.id.viewworkout);

        submitworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string_selectweek_spinner=selectweek.getSelectedItem().toString();
                string_complication=complication.getText().toString();
                string_week1=week1.getText().toString();
                WorkoutPlanIdName st = (WorkoutPlanIdName) spinner.getSelectedItem();
                String spid = getidOfSpinner("onClick", st);
                if(validateworkoutinfo())
                {
                    AddWorkoutData(spid);
                }
            }
        });


        adddietplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationPregnancy.this,Createdietplan.class);
                intent.putExtra("userid", Prefhelper.getInstance(InformationPregnancy.this).getUserid());
                startActivity(intent);
            }
        });

        viewworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationPregnancy.this,ViewWorkout.class);
                startActivity(intent);
                }
        });

        addremainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationPregnancy.this,Remainder.class);
                startActivity(intent);
            }
        });

        viewconsultreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationPregnancy.this,MyBookedappointment.class);
                startActivity(intent);
            }
        });

        consultdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationPregnancy.this,ConsultDoctor.class);
                startActivity(intent);
            }
        });



/*
       selectworkoutplan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                if(parent.getId() == R.id.workoutexcercise_spinner)
                {
// SpinnerValue_ArrayName - Name of the Array of Values used to populate spinner.
                    //String sText =selectworkoutplan.get(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }


    void AddWorkoutData(String spid)
    {

        OkHttpClient client = new OkHttpClient();
        Request request = AddData(spid);
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

                                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                                // Intent intent=new Intent(PregnancyReport.this,)
                            }
                            //progressDialog.dismiss();
                        } catch (Exception e) {
                            // progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                        }
                        // progressDialog.dismiss();
                    }
                });
            }
        });
    }


    private Request AddData(String spid)
    {
        JSONObject postdata = new JSONObject();
        try {

            postdata.put("user_id",Integer.parseInt(Prefhelper.getInstance(context).getUserid()));
            postdata.put("workout_week",string_selectweek_spinner);
            postdata.put("child_weight",string_week1);
            postdata.put("complications",string_complication);
            //postdata.put("workout_plan_id","1");
            postdata.put("workout_plan_id",spid);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", XAPI_KEY)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(BASE_URL1+"create_workout_plan")
                .post(body)
                .build();
    }


    private boolean validateworkoutinfo() {
        if (string_selectweek_spinner.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Week field empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if (string_week1.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "child weight field empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (string_complication.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Complication field empty", Toast.LENGTH_LONG).show();
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
                            pojoInformationPregnancy = gson.fromJson(body, PojoInformationPregnancy.class);
                            workoutPlanData = (ArrayList)pojoInformationPregnancy.getWorkoutPlanData();
                             Log.i("workoutplan data",workoutPlanData.toString());
                            listbaseAdapter = new CustomAdapterInformationPregnancy(InformationPregnancy.this, workoutPlanData);
                            listView.setAdapter(listbaseAdapter);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
//                            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
    private Request femalecycledetail() {
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", XAPI_KEY)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(BASE_URL1+"get_workout_plan/"+Integer.parseInt(Prefhelper.getInstance(context).getUserid()))
                .get()
                .build();
        return request;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        WorkoutPlanIdName st = (WorkoutPlanIdName) spinner.getSelectedItem();

        // Show it via a toast
       // toastState( "onItemSelected", st );
        getidOfSpinner( "onItemSelected", st );
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void toastState( String prefix, WorkoutPlanIdName st )
    {
        if ( st != null )
        {
            String desc = "Event: " + prefix + "\nstate: " + st.name+ "\nid: " + String.valueOf( st.id );
            //Toast.makeText(getApplicationContext(), desc, Toast.LENGTH_SHORT).show();
        }
    }

    public String getidOfSpinner( String prefix, WorkoutPlanIdName st )
    {
        if ( st != null )
        {
            String desc = String.valueOf( st.id );
           // Toast.makeText(getApplicationContext(), desc, Toast.LENGTH_SHORT).show();
            return desc;
        }
        return null;
    }



    public void CloseActivity(View view)
    {
        finish();
    }



}
