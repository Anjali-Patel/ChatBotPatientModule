package com.app.feish.application.Patient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feish.application.Adpter.CustomAdapter_cycle;
import com.app.feish.application.Adpter.CustomAdapter_reportp;
import com.app.feish.application.R;
import com.app.feish.application.Remote.ApiUtils;
//import com.app.feish.application.fragment.CustomAdapter_listtreatement;
//import com.app.feish.application.clinic.ClinicDashboard;
import com.app.feish.application.clinic.ClinicDashboardDetails;
import com.app.feish.application.clinic.ClinicDetails;
import com.app.feish.application.clinic.DoctorList;
import com.app.feish.application.clinic.DoctorSummary;
import com.app.feish.application.clinic.ListbaseAdapter;
import com.app.feish.application.model.searchdoctorpojo;
import com.app.feish.application.model.vitalsignlist;
import com.app.feish.application.modelclassforapi.PatientBookedappointment;
import com.app.feish.application.sessiondata.Prefhelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL1;
import static com.app.feish.application.Remote.ApiUtils.XAPI_KEY;
import static com.app.feish.application.fragment.ListFamilyhistory.JSON;

public class Female extends AppCompatActivity {
    ListView listViewforfemale;
    Context context=this;
    CustomAdapter_cycle customAdapter_vitalsigns;
    CalendarPickerView calendar_s,calendar_e;
    Button btn_s,btn_e;
    EditText et_startdate,et_enddate;
    ProgressDialog progressDialog;
    private  MenstruationDatum menstruationDatum;
    SimpleDateFormat DATE_FORMAT;
    EditText commentcycle;
    String user_id;
    Button addcycle;
    String string_sdate="",string_edate="",string_desc="";
    Toolbar toolbar;
    private ArrayList<MenstruationDatum> menstruationData;
    private PojoFemaleCycle pojoFemaleCycles;
    private CustomAdapterFemaleCycle listbaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_female);
        menstruationData=new ArrayList<>();
        fetchdata();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        commentcycle=findViewById(R.id.commnetcycle);
        toolbar.setTitle("She Section");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        addcycle=findViewById(R.id.addcycle);
        setSupportActionBar(toolbar);
        listViewforfemale = findViewById(R.id.listforfemale);
        progressDialog= new ProgressDialog(getApplicationContext());
        progressDialog.setTitle("Loading.........");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /////////////
        /////////////
      /*  searchDoctorLists.add(new searchdoctorpojo("", "", ""));
        searchDoctorLists.add(new searchdoctorpojo("", "", ""));
        searchDoctorLists.add(new searchdoctorpojo("", "", ""));
        searchDoctorLists.add(new searchdoctorpojo("", "", ""));
        customAdapter_vitalsigns = new CustomAdapter_cycle(context, searchDoctorLists);*/
     //  listView.setAdapter(customAdapter_vitalsigns);
        user_id = Prefhelper.getInstance(Female.this).getUserid();
        et_startdate = findViewById(R.id.sdate);
        et_enddate = findViewById(R.id.edate);
        DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

        //     getActivity().setTitle("CollegeName");

        et_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate_sdate();
            }
        });
        et_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate_end();
            }
        });

        addcycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                string_desc=commentcycle.getText().toString();
                string_sdate=et_startdate.getText().toString();
                string_edate=et_enddate.getText().toString();
                try {
                    if(validateFemale())
                    {
                        AddFemaleCycle();

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });
    }
    void pickdate_sdate()
    {
        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.camgal);
        dialog1.setCanceledOnTouchOutside(false);
        final Calendar nextYear=Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        final  Calendar lastYear=Calendar.getInstance();
        lastYear.add(Calendar.YEAR,-1);
        btn_s=(Button)dialog1.findViewById(R.id.ok);
        calendar_s = (CalendarPickerView) dialog1.findViewById(R.id.calendar_view);
        calendar_s.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withSelectedDate(new Date());
        calendar_s.setCustomDayView(new DefaultDayViewAdapter());
        //Date today =new Date();
        Date today = new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime();
        calendar_s.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar_s.init(today,nextYear.getTime())
                .withSelectedDate(today).inMode(CalendarPickerView.SelectionMode.SINGLE);
        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Date> mydates = (ArrayList<Date>)calendar_s.getSelectedDates();
                for (int i = 0; i <mydates.size() ; i++) {
                    Date tempdate = mydates.get(i);
                    String testdate = DATE_FORMAT.format(tempdate);
                    et_startdate.setText(testdate);
                    dialog1.dismiss();

                }

            }
        });
        dialog1.show();

    }
    void pickdate_end()
    {
        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.camgal);
        dialog1.setCanceledOnTouchOutside(false);
        final Calendar nextYear=Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        final  Calendar lastYear=Calendar.getInstance();
        lastYear.add(Calendar.YEAR,-1);
        btn_e=(Button)dialog1.findViewById(R.id.ok);
        calendar_e = (CalendarPickerView) dialog1.findViewById(R.id.calendar_view);
        calendar_e.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withSelectedDate(new Date());
        calendar_e.setCustomDayView(new DefaultDayViewAdapter());
       // Date today =new Date();
        Date today = new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime();



        calendar_e.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar_e.init(today,nextYear.getTime())
                .withSelectedDate(today).inMode(CalendarPickerView.SelectionMode.SINGLE);

        calendar_e.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
//                Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_LONG).show();
                try {
                    datevalidation(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
        btn_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Date> mydates = (ArrayList<Date>)calendar_e.getSelectedDates();
                for (int i = 0; i <mydates.size() ; i++) {
                    Date tempdate = mydates.get(i);
                    String testdate = DATE_FORMAT.format(tempdate);
                    et_enddate.setText(testdate);
                    dialog1.dismiss();

                }

            }
        });
        dialog1.show();

    }

    void datevalidation(Date date) throws ParseException
    {
        SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
        if (dfDate.parse(et_startdate.getText().toString()).before(date))
        {
            // Toast.makeText(getApplicationContext(), "Truye", Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(getApplicationContext(), "Not Valid!!! Start Date is less than end date", Toast.LENGTH_LONG).show();
            return;
        }
    }

    boolean datevalidation() throws ParseException
    {
        SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
        if (dfDate.parse(et_startdate.getText().toString()).before(dfDate.parse(et_enddate.getText().toString())))
        {
            return true;
        }else
        {
            //    Toast.makeText(getApplicationContext(), "Not Valid!!! Start Date is less than end date", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    void AddFemaleCycle()
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
                            if (jsonObject.getString("message").equals("Add_Succesfully")) {

                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(getApplicationContext(), android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(getApplicationContext());
                                }
                                builder.setTitle("message")
                                        .setMessage(jsonObject.getString("message"))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete

                                            }
                                        })

                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })

                                        // .setIcon(android.R.drawable.m)

                                        .show();
                                builder.setCancelable(false);


                            } else {

                                Toast.makeText(getApplicationContext(), "Data Added SuccesFully", Toast.LENGTH_SHORT).show();

                                //listView.setVisibility(View.VISIBLE);
                                fetchdata();


                            }
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        }
                });
            }

        });
    }

    private Request AddData()
    {
        JSONObject postdata = new JSONObject();
        try {
           // postdata.put("user_id",1227);
            postdata.put("user_id",Integer.parseInt(Prefhelper.getInstance(context).getUserid()));
            postdata.put("comment",string_desc);
            postdata.put("end_date",string_edate);
            postdata.put("start_date",string_sdate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key",XAPI_KEY )
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(BASE_URL1+"add_menstruation_data")
                .post(body)
                .build();
    }

    private boolean validateFemale() throws ParseException{
        if(string_desc.compareTo("")==0)
        {
            Toast.makeText(getApplicationContext(),"Description field empty",Toast.LENGTH_LONG).show();
            return false;
        }

        if(string_sdate.compareTo("")==0)
        {
            Toast.makeText(getApplicationContext()," Start Date field empty",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (string_edate.compareTo("")==0)
        {
            Toast.makeText(getApplicationContext(),"End Date field empty",Toast.LENGTH_LONG).show();
            return false;

        } else if (datevalidation() != true){
            Toast.makeText(getApplicationContext(), "Not Valid!!! Start Date is less than end date", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
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
/*                            JsonReader reader = gson.newJsonReader(new StringReader(body));
                            reader.setLenient(true);
                            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);*/
                            pojoFemaleCycles = gson.fromJson(body, PojoFemaleCycle.class);
                            menstruationData = (ArrayList)pojoFemaleCycles.getMenstruationData();
                            Log.i("menstrutaion data",menstruationData.toString());
                            listbaseAdapter = new CustomAdapterFemaleCycle(Female.this, menstruationData);

                            listViewforfemale.setAdapter(listbaseAdapter);

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
                .url(BASE_URL1+"menstruation_data/"+Integer.parseInt(Prefhelper.getInstance(context).getUserid()))
                .get()
                .build();
        return request;
    }

}
