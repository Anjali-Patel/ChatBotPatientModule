package com.app.feish.application.Patient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;
import com.takisoft.datetimepicker.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL1;
import static com.app.feish.application.Remote.ApiUtils.XAPI_KEY;
import static com.app.feish.application.fragment.ListFamilyhistory.JSON;

public class ViewWorkout extends AppCompatActivity {
    Button additionalinfo, submitworkoutinfo;
    EditText startdatee, starttime, endtime, calories;
    com.takisoft.datetimepicker.widget.TimePicker timePicker;
    CalendarPickerView calendar_s, calendar_e;
    SimpleDateFormat DATE_FORMAT, DATE_FORMAT_HH;
    Button btn_s, btn_e;
    Button btn_time;
    String string_calories = "", string_sdateworkout = "", string_stime = "", string_etime = "";
    Context context = this;
    ListView listview;
    private ArrayList<WorkoutDetail> workoutDetails;
    private PojoViewWorkout pojoViewWorkout;
    private CustomAdapterViewWorkout customAdapterViewWorkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);
        workoutDetails=new ArrayList<>();
        fetchdata();
        calories = findViewById(R.id.calories);
        additionalinfo = findViewById(R.id.additionalinfo);
        starttime = findViewById(R.id.starttime);
        submitworkoutinfo = findViewById(R.id.submitworkoutinfo);
        endtime = findViewById(R.id.endtime);
        listview = findViewById(R.id.listforviewworkout);
        DATE_FORMAT_HH = new SimpleDateFormat("dd");
        startdatee = findViewById(R.id.startdatee);
        additionalinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewWorkout.this, AdditionalInfoWorkout.class);
                startActivity(intent);
            }
        });


        submitworkoutinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string_sdateworkout = startdatee.getText().toString();
                string_stime = starttime.getText().toString();
                string_etime = endtime.getText().toString();
                string_calories = calories.getText().toString();

                if (validateworkoutinfo()) {
                    Addworkoutinfo();
                }
            }
        });

        DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

        startdatee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate_sdate();
            }
        });

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate_time(0);
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickendtime(0);
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
        Date today = new Date();
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
                    startdatee.setText(testdate);
                    dialog1.dismiss();

                }

            }
        });
        dialog1.show();

    }

    void pickdate_time(final int flag) {
        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.timepickfer);
        dialog1.setCanceledOnTouchOutside(false);
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        btn_time = dialog1.findViewById(R.id.ok);
        timePicker = dialog1.findViewById(R.id.calendar_view);
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    if (String.valueOf(timePicker.getHour()).length() == 1) {
                        if (String.valueOf(timePicker.getMinute()).equals("0")) {
                            starttime.setText("0" + timePicker.getHour() + ":" + "00" + ":" + "00");

                        } else {
                            starttime.setText("0" + timePicker.getHour() + ":" + timePicker.getMinute() + ":" + "00");

                        }
                    } else {
                        if (String.valueOf(timePicker.getMinute()).equals("0")) {
                            starttime.setText(timePicker.getHour() + ":" + "00" + ":" + "00");

                        } else {
                            starttime.setText(timePicker.getHour() + ":" + timePicker.getMinute() + ":" + "00");

                        }
                    }
                }

                dialog1.dismiss();
            }
        });
        dialog1.show();

    }

    void pickendtime(final int flag) {
        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.timepickfer);
        dialog1.setCanceledOnTouchOutside(false);
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        btn_time = dialog1.findViewById(R.id.ok);
        timePicker = dialog1.findViewById(R.id.calendar_view);


        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    if (String.valueOf(timePicker.getHour()).length() == 1) {
                        if (String.valueOf(timePicker.getMinute()).equals("0")) {
                            endtime.setText("0" + timePicker.getHour() + ":" + "00" + ":" + "00");

                        } else {
                            endtime.setText("0" + timePicker.getHour() + ":" + timePicker.getMinute() + ":" + "00");

                        }
                    } else {
                        if (String.valueOf(timePicker.getMinute()).equals("0")) {
                            endtime.setText(timePicker.getHour() + ":" + "00" + ":" + "00");

                        } else {
                            endtime.setText(timePicker.getHour() + ":" + timePicker.getMinute() + ":" + "00");

                        }
                    }
                }

                dialog1.dismiss();
            }
        });
        dialog1.show();

    }


    void timevalidation(Time time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        if (sdf.parse(starttime.getText().toString()).before(time)) {
            // Toast.makeText(getApplicationContext(), "Truye", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Not Valid!!! Start Time is less than end time", Toast.LENGTH_LONG).show();
            return;
        }
    }


    void Addworkoutinfo() {

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
                            Toast.makeText(getApplicationContext(), "data not valid", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private Request AddData() {
        JSONObject postdata = new JSONObject();
        try {

            postdata.put("user_id", Integer.parseInt(Prefhelper.getInstance(context).getUserid()));
            postdata.put("workout_date", string_sdateworkout);
            postdata.put("start_time", string_stime);
            postdata.put("end_time", string_etime);
            postdata.put("calories", string_calories);
            postdata.put("workout_id", "1");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", XAPI_KEY)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(BASE_URL1+"create_workout_details")
                .post(body)
                .build();
    }


    private boolean validateworkoutinfo() {
        if (string_sdateworkout.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), " Date field empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if (string_stime.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), " Start Time field empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (string_etime.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "End Time field empty", Toast.LENGTH_LONG).show();
            return false;

        } else if (string_calories.compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Calories field empty", Toast.LENGTH_LONG).show();
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
                Log.i("response", body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson = new GsonBuilder().create();

                            pojoViewWorkout = gson.fromJson(body, PojoViewWorkout.class);
                            workoutDetails = (ArrayList) pojoViewWorkout.getWorkoutDetails();
                            // Log.i("menstrutaion data",menstruationData.toString());
                            customAdapterViewWorkout = new CustomAdapterViewWorkout(ViewWorkout.this, workoutDetails);

                            listview.setAdapter(customAdapterViewWorkout);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Data Not Added ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    private Request femalecycledetail() {
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", XAPI_KEY)
                .url(BASE_URL1+"get_workout_detail/" + Integer.parseInt(Prefhelper.getInstance(context).getUserid()))
                .get()
                .build();
        return request;
    }


    public void CloseActivity(View view) {
        finish();
    }


}











