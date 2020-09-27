package com.app.feish.application.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feish.application.Connectiondetector;

import com.app.feish.application.Patient.MedicalHitoryp;
import com.app.feish.application.Patient.RegisterPatient;
import com.app.feish.application.R;
import com.app.feish.application.Remote.ApiUtils;
import com.app.feish.application.Remote.EncryptionDecryption;
import com.app.feish.application.model.doctormsglist;
import com.app.feish.application.sessiondata.Prefhelper;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.support.constraint.Constraints.TAG;
import static com.app.feish.application.fragment.ListFamilyhistory.JSON;

/**
 * Created by lenovo on 6/4/2016.
 */
public class AddTratement extends Fragment {
    // Store instance variables
  View view1;
    ViewGroup viewGroup;

    CalendarPickerView calendar_s,calendar_e;
    Button btn_s,btn_e;
    EditText et_startdate,et_enddate;
    ProgressDialog progressDialog;
    SimpleDateFormat DATE_FORMAT;
    EditText et_tname,et_desc;
    TextView btn_treate;
    ArrayList<doctormsglist> doctormsglists;
    ArrayList<doctormsglist> doctormsglistsTemp;
ProgressBar progressBar;
    Spinner spinner_app;
    EditText spinner_pname;
    RadioGroup radioGroup_iscured,radioGroup_isrunning;
    String str_trename="",str_desc="",str_prname="",str_iscured="",str_isrunning="",str_sdate="",str_edate="";
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    public static AddTratement newInstance(int page, String title) {
        AddTratement fragmentFirst = new AddTratement();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("Stringlist", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view1 = inflater.inflate(R.layout.fragment_treatment, container, false);
        doctormsglistsTemp= new ArrayList<>();
        doctormsglists= new ArrayList<>();
         progressDialog= new ProgressDialog(getActivity());
         progressDialog.setTitle("Loading.........");
        progressBar=view1.findViewById(R.id.progressBar);
        et_startdate=view1.findViewById(R.id.sdate);
        et_enddate=view1.findViewById(R.id.edate);
        et_tname=view1.findViewById(R.id.tre_name);
        btn_treate=view1.findViewById(R.id.btn_treatment);
        et_desc=view1.findViewById(R.id.remark);
        spinner_pname=view1.findViewById(R.id.proname_spinner);
        spinner_app=view1.findViewById(R.id.appo_spinner);
        radioGroup_iscured=view1.findViewById(R.id.radiocured);
        radioGroup_isrunning=view1.findViewById(R.id.radiorunning);
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
   //     getActivity().setTitle("CollegeName");

        et_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate_sdate();
               /* Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();*/

            }
        });
       /* mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                et_startdate.setText(date);
                str_sdate=date;
            }
        };*/





        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

        et_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate_end();
               /* Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();*/

            }
        });
       /* mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String datee = month + "/" + day + "/" + year;
                et_enddate.setText(datee);
                str_edate=datee;
            }
        };*/





        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


        radioGroup_isrunning.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioruy)
                {
                    str_isrunning="1";
                }
                else
                {
                    str_isrunning="0";
                }
            }
        });
        radioGroup_iscured.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radiocy)
                {
                    str_iscured="1";
                }
                else
                {
                    str_iscured="0";
                }
            }
        });
        btn_treate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_desc=et_desc.getText().toString();
                str_trename=et_tname.getText().toString();
                if(validateTreatment())
                {
                    fetchdata();
                    fetchdataMDB();
                    progressBar.setVisibility(View.VISIBLE);

                }

            }
        });
        fecthingtestlist();
        spinner_pname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //ViewGroup viewGroup =context. findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.search_spinner, viewGroup, false);
                EditText search_item=dialogView.findViewById(R.id.search_item);
                final ListView listItem=dialogView.findViewById(R.id.listItem);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                CustomAdapter_report customAdapter=new CustomAdapter_report(getContext(),doctormsglists);
                listItem.setAdapter(customAdapter);

                search_item.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strText = s.toString().trim();
                        if (strText.length() != 0) {
                            doctormsglistsTemp.clear();
                            for (int i = 0; i < doctormsglists.size(); i++) {
                                if (doctormsglists.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                                    doctormsglistsTemp.add(doctormsglists.get(i));
                                }
                            }
                            CustomAdapter_report arrayAdapter = new CustomAdapter_report( getContext(),doctormsglistsTemp);
                            listItem.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(),"No match found",Toast.LENGTH_LONG).show();
                            CustomAdapter_report arrayAdapter = new CustomAdapter_report( getContext(),doctormsglistsTemp);
                            listItem.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                        }

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        String strText = s.toString().trim();
                        if (strText.length() != 0) {
                            doctormsglistsTemp.clear();
                            for (int i = 0; i < doctormsglists.size(); i++) {
                                if (doctormsglists.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                                    doctormsglistsTemp.add(doctormsglists.get(i));
                                }
                            }
                            CustomAdapter_report arrayAdapter = new CustomAdapter_report( getContext(),doctormsglistsTemp);
                            listItem.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(),"No match found",Toast.LENGTH_LONG).show();
                            CustomAdapter_report arrayAdapter = new CustomAdapter_report( getContext(),doctormsglists);
                            listItem.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                        }

                    }
                });

                listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        alertDialog.dismiss();
//                        ethinicity.setText(arg0.getSelectedItem().toString());
                        str_prname= String.valueOf(doctormsglists.get(position).getId());
                        spinner_pname.setText(doctormsglists.get(position).getName());
                    }
                });
            }
        });

        return view1;
    }
    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
    void pickdate_sdate()
    {
        final Dialog dialog1 = new Dialog(getActivity());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.camgal);
        dialog1.setCanceledOnTouchOutside(false);
        final Calendar nextYear=Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        final  Calendar lastYear=Calendar.getInstance();
        lastYear.add(Calendar.YEAR,-1);
        btn_s=dialog1.findViewById(R.id.ok);
        calendar_s =  dialog1.findViewById(R.id.calendar_view);
        calendar_s.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withSelectedDate(new Date());
        calendar_s.setCustomDayView(new DefaultDayViewAdapter());
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
                    str_sdate=testdate;
                    dialog1.dismiss();

                }

            }
        });
        dialog1.show();

    }
    void pickdate_end()
    {
        final Dialog dialog1 = new Dialog(getActivity());
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
                    str_edate=testdate;
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
           // Toast.makeText(getActivity(), "Truye", Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(getActivity(), "Not Valid!!! Start Date is less than end date", Toast.LENGTH_LONG).show();
            return;
        }
    }



    private Request AddVitalSignApp()
    {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id",Integer.parseInt(Prefhelper.getInstance(getActivity()).getUserid()));
            postdata.put("description",str_desc);
            postdata.put("end_date",str_edate);
            postdata.put("start_date",str_sdate);
            postdata.put("name",str_trename);
            postdata.put("procedure_id",Integer.parseInt(str_prname));
            postdata.put("is_cured",Integer.parseInt(str_iscured));
            postdata.put("is_running",Integer.parseInt(str_isrunning));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url(ApiUtils.BASE_URL2+"addTreatment")
                .post(body)
                .build();
    }
    private void fetchdata()
    {
        OkHttpClient client = new OkHttpClient();
        Request request = AddVitalSignApp();
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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            if (jsonObject.getInt("Success") == 1) {
                                progressBar.setVisibility(View.GONE);
                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(getActivity());
                                }
                                builder.setTitle("Message")
                                        .setMessage(jsonObject.getString("message"))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                getActivity().finish();
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
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }

        });
    }

    private Request AddVitalSignAppMDB()
    {

        JSONObject postdatauser = new JSONObject();

        String encryptstartdate= EncryptionDecryption.encode(str_sdate);
        String encryptenddate= EncryptionDecryption.encode(str_edate);

        try {
            /*JSONObject postdatauser = new JSONObject();
            JSONObject postdatavalue = new JSONObject();
            JSONArray jsonArrayrecord = new JSONArray();
            JSONObject postdata = new JSONObject();
*/
            postdatauser.put("user_id",Integer.parseInt(Prefhelper.getInstance(getActivity()).getUserid()));
            postdatauser.put("description",str_desc);
            postdatauser.put("end_date",encryptenddate);
            postdatauser.put("start_date",encryptstartdate);
            postdatauser.put("name",str_trename);
            postdatauser.put("procedure_id",Integer.parseInt(str_prname));
            postdatauser.put("is_cured",Integer.parseInt(str_iscured));
            postdatauser.put("is_running",Integer.parseInt(str_isrunning));
            postdatauser.put("modified_by", Prefhelper.getInstance(getActivity()).getUserid());
            postdatauser.put("modified_at",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date()));
            postdatauser.put("source_type","mobile");
            postdatauser.put("deleted_flag","0");


         /*   postdata.put("Treatment", postdatauser);

            postdatavalue.put("value", postdata);

            jsonArrayrecord.put(postdatavalue);

            postdatamain.put("record", jsonArrayrecord);
            Log.d("register data", postdatamain.toString());*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    //    Log.d("medicaldata",postdatamain.toString());
        RequestBody body = RequestBody.create(JSON, postdatauser.toString());
        return new Request.Builder()
               /* .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")*/
                .url(ApiUtils.BASE_URLMAngoDB+"add/treatment")
                .post(body)
                .build();
    }
    private void fetchdataMDB()
    {
        OkHttpClient client = new OkHttpClient();
        Request request = AddVitalSignAppMDB();
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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), ""+body, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }

        });
    }
    private boolean validateTreatment(){
        if(str_desc.compareTo("")==0)
        {
            Toast.makeText(getActivity(),"Description field empty",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (str_edate.compareTo("")==0)
        {
            Toast.makeText(getActivity(),"End Date field empty",Toast.LENGTH_LONG).show();
            return false;
        }
        if(str_sdate.compareTo("")==0)
        {
            Toast.makeText(getActivity()," Start Date field empty",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (str_trename.compareTo("")==0)
        {
            Toast.makeText(getActivity(),"Treatement name field empty",Toast.LENGTH_LONG).show();
            return false;
        } if(str_prname.compareTo("")==0)
        {
            Toast.makeText(getActivity(),"Procedurename sign field empty",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (str_isrunning.compareTo("")==0)
        {
            Toast.makeText(getActivity()," Is Running field empty",Toast.LENGTH_LONG).show();
            return false;
        }else if (str_iscured.compareTo("")==0)
        {
            Toast.makeText(getActivity(),"Is Cured  field empty",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }
    private Request fetchingprocedurename() {
        JSONObject postdata = new JSONObject();
        RequestBody body = RequestBody.create(MedicalHitoryp.JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url(ApiUtils.BASE_URL2+"listprocedurename")
                .post(body)
                .build();

    }
    private void fecthingtestlist()
    {
        OkHttpClient client = new OkHttpClient();
        Request request = fetchingprocedurename();

        Log.i("", "onClick: "+request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }
            @Override
            public void onResponse(final Response response) throws IOException {

                final String body=response.body().string();
                // medicalconditionJSON(body);
                //Log.i("1234add", "onResponse: "+body);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject= new JSONObject(body);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for (int i = 0; i <jsonArray.length() ; i++)
                            {
                                JSONObject  jsonObject1= jsonArray.getJSONObject(i);
                                doctormsglists.add(new doctormsglist(jsonObject1.getInt("id"),jsonObject1.getString("name")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      /*  CustomAdapter_report customAdapter=new CustomAdapter_report(getActivity(),doctormsglists);
                        spinner_pname.setAdapter(customAdapter);
                        spinner_pname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                str_prname= String.valueOf(doctormsglists.get(position).getId());

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });*/
                    }
                });


            }

        });

    }
}