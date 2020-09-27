package com.app.feish.application.Patient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.feish.application.R;
//import com.app.feish.application.clinic.ClinicDashboard;
import com.app.feish.application.sessiondata.Prefhelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL1;
import static com.app.feish.application.Remote.ApiUtils.XAPI_KEY;
import static com.app.feish.application.fragment.ListFamilyhistory.JSON;

public class PregnancyReport extends AppCompatActivity {

    EditText startdate, enddate, commentforwoemen, complicationforwomen;
    ProgressDialog progressDialog;
    ListView pregnancylist;
    Button submit,storepregnancyinfo;
    Spinner spinner_time, Spinner_month,spinner_week;
    CalendarPickerView calendar_s, calendar_e;
    SimpleDateFormat DATE_FORMAT;
    Context context = this;
    Button btn_s, btn_e;
    String string_sdate = "", string_edate = "", string_desc = "",string_pregnancytime="",string_month="",string_week="";
    String user_id;
    private ArrayList<PregnancyDatum> pregnancyData;
    private PojoPregnancy pojoPregnancy;
    private CustomAdapterPregnancy listbaseAdapter;
    ImageView imageViewhelp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_report);
        pregnancylist=findViewById(R.id.listforPregnancyinfo);
        imageViewhelp2=findViewById(R.id.imageviewhelp2);
        pregnancyData=new ArrayList<>();


        fetchdata();



        storepregnancyinfo=findViewById(R.id.storepregnancyinfo);
        user_id = Prefhelper.getInstance(PregnancyReport.this).getUserid();
        startdate = findViewById(R.id.startdate);
        enddate = findViewById(R.id.enddate);
        commentforwoemen = findViewById(R.id.commentforwomen);
        complicationforwomen = findViewById(R.id.complicationforwomen);
        spinner_time = findViewById(R.id.time_spinner);
        Spinner_month = findViewById(R.id.month_spinner);
        spinner_week=findViewById(R.id.spinner_week);
        submit = findViewById(R.id.store);

       submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PregnancyReport.this, InformationPregnancy.class);
                startActivity(intent);
            }
        });


        DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate_sdate();
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate_end();
            }
        });



        storepregnancyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string_pregnancytime=spinner_time.getSelectedItem().toString();
                string_desc=commentforwoemen.getText().toString();
                string_sdate=startdate.getText().toString();
                string_edate=enddate.getText().toString();
                string_month=Spinner_month.getSelectedItem().toString();
                string_week=spinner_week.getSelectedItem().toString();

               // string_pregnancytime=spinner_time.getText().toString();
                //string_month=Spinner_month.getText().toString();
               // string_week=spinner_week.getText().toString();
              //  AddPregnancyData();

                try {
                    if(validatepregnancyinfo())
                    {
                        AddPregnancyData();
                        }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });



        imageViewhelp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(PregnancyReport.this);
                builder.setTitle("#Workout Details Is Next Section");
                builder.setMessage("In Workout Section You can track your workouts with time and date and track weight of child week wise");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //  Toast.makeText(getApplicationContext(), "OK was clicked", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(getApplicationContext(), android.R.string.no, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }
        });
        }

    void pickdate_sdate() {
        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.camgal);
        dialog1.setCanceledOnTouchOutside(false);
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        btn_s = (Button) dialog1.findViewById(R.id.ok);
        calendar_s = (CalendarPickerView) dialog1.findViewById(R.id.calendar_view);
        calendar_s.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withSelectedDate(new Date());
        calendar_s.setCustomDayView(new DefaultDayViewAdapter());
        Date today = new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime();
//        Date today = new Date();
        calendar_s.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar_s.init(today, nextYear.getTime())
                .withSelectedDate(today).inMode(CalendarPickerView.SelectionMode.SINGLE);
        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Date> mydates = (ArrayList<Date>) calendar_s.getSelectedDates();
                for (int i = 0; i < mydates.size(); i++) {
                    Date tempdate = mydates.get(i);
                    String testdate = DATE_FORMAT.format(tempdate);
                    startdate.setText(testdate);
                    dialog1.dismiss();
                    }
                    }
        });
        dialog1.show();
        }





    void pickdate_end() {
        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.camgal);
        dialog1.setCanceledOnTouchOutside(false);
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        btn_e = (Button) dialog1.findViewById(R.id.ok);
        calendar_e = (CalendarPickerView) dialog1.findViewById(R.id.calendar_view);
        calendar_e.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withSelectedDate(new Date());
        calendar_e.setCustomDayView(new DefaultDayViewAdapter());
        //Date today = new Date();
        Date today = new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime();
        calendar_e.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar_e.init(today, nextYear.getTime())
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
                ArrayList<Date> mydates = (ArrayList<Date>) calendar_e.getSelectedDates();
                for (int i = 0; i < mydates.size(); i++) {
                    Date tempdate = mydates.get(i);
                    String testdate = DATE_FORMAT.format(tempdate);
                    enddate.setText(testdate);
                    dialog1.dismiss();
                    }
            }
        });
        dialog1.show();
        }


        void datevalidation(Date date) throws ParseException
        {
            SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
            if (dfDate.parse(startdate.getText().toString()).before(date))
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
        if (dfDate.parse(startdate.getText().toString()).before(dfDate.parse(enddate.getText().toString())))
        {
            return true;
        }else
        {
        //    Toast.makeText(getApplicationContext(), "Not Valid!!! Start Date is less than end date", Toast.LENGTH_LONG).show();
            return false;
        }
    }



    void AddPregnancyData()
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
                                Intent intent=new Intent(PregnancyReport.this,InformationPregnancy.class);
                                startActivity(intent);


                                } else {

                                Toast.makeText(getApplicationContext(), "Data Added SuccesFully", Toast.LENGTH_SHORT).show();
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

    private Request AddData()
    {
        JSONObject postdata = new JSONObject();
        try {

            postdata.put("user_id",Integer.parseInt(Prefhelper.getInstance(context).getUserid()));
            postdata.put("pregnancy_time",string_pregnancytime);
            postdata.put("start_date",string_sdate);
            postdata.put("end_date",string_edate);
            postdata.put("month",string_month);
            postdata.put("week",string_week);
            postdata.put("comment",string_desc);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", XAPI_KEY)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(BASE_URL1+"add_pregnancy_data")
                .post(body)
                .build();
    }


    private boolean validatepregnancyinfo() throws ParseException {
        if (string_pregnancytime.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Pregnancy Time field empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if (string_sdate.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), " Start Date field empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (string_edate.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "End Date field empty", Toast.LENGTH_LONG).show();
            return false;

        } else if (string_month.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Month field empty", Toast.LENGTH_LONG).show();
            return false;

        } else if (string_week.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Week field empty", Toast.LENGTH_LONG).show();
            return false;

        } else if (string_desc.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Comment field empty", Toast.LENGTH_LONG).show();
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

                            pojoPregnancy = gson.fromJson(body, PojoPregnancy.class);
                            pregnancyData = (ArrayList)pojoPregnancy.getPregnancyData();
                            Log.i("menstrutaion data",pregnancyData.toString());
                            listbaseAdapter = new CustomAdapterPregnancy(PregnancyReport.this, pregnancyData);

                            pregnancylist.setAdapter(listbaseAdapter);

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
                .url(BASE_URL1+"pregnancy_data/"+Integer.parseInt(Prefhelper.getInstance(context).getUserid()))
                .get()
                .build();
        return request;
    }


    public void CloseActivity(View view)
    {
        finish();
    }

}




