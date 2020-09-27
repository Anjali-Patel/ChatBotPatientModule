package com.app.feish.application.Patient;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.app.feish.application.Adpter.CustomAdapter_blooddonorlist;
import com.app.feish.application.Connectiondetector;
import com.app.feish.application.R;
import com.app.feish.application.Remote.ApiUtils;
import com.app.feish.application.model.bookedappointmentpatient;
import com.app.feish.application.modelclassforapi.Medicalcondition;
import com.app.feish.application.modelclassforapi.Medicalconditionlist;
import com.app.feish.application.sessiondata.Prefhelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.app.feish.application.Patient.MedicalHitoryp.JSON;

import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;

public class PatientLikeMe extends AppCompatActivity {
    ViewGroup viewGroup;

    Toolbar toolbar;
    String str_desc="";
    ListView listView;
    CustomAdapter_blooddonorlist customAdapter_blooddonorlist;
    ArrayList<bookedappointmentpatient> searchdoctorpojos;
    ArrayList<bookedappointmentpatient> searchdoctorpojosTemp;
    Context context=this;
    EditText spinner_mcon;
    LinearLayout linear_patient;
    EditText et_desc,search_patient;
    RelativeLayout relativeLayout;
    String str_mcon="";
    Medicalcondition  medicalcondition;
    List<Medicalconditionlist> medicalconditionlists;
    List<Medicalconditionlist> medicalconditionlistsTemp;

    ProgressBar progressBar;
    TextView textView;
    Connectiondetector connectiondetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_like_me);
        connectiondetector= new Connectiondetector(getApplicationContext());
        searchdoctorpojos= new ArrayList<>();
        searchdoctorpojosTemp= new ArrayList<>();
        medicalconditionlists= new ArrayList<>();
        medicalconditionlistsTemp= new ArrayList<>();
        linear_patient=findViewById(R.id.linear_patient);
        search_patient=findViewById(R.id.search_patient);
        relativeLayout=findViewById(R.id.search_go);

        initView();
        addingdata();
        spinner_mcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //ViewGroup viewGroup =context. findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.search_spinner, viewGroup, false);
                EditText search_item=dialogView.findViewById(R.id.search_item);
                final ListView listItem=dialogView.findViewById(R.id.listItem);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                CustomAdapter_plm customAdapter=new CustomAdapter_plm(context,medicalconditionlists);
                listItem.setAdapter(customAdapter);

                search_item.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strText = s.toString().trim();
                        if (strText.length() != 0) {
                            medicalconditionlistsTemp.clear();
                            for (int i = 0; i < medicalconditionlists.size(); i++) {
                                if (medicalconditionlists.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                                    medicalconditionlistsTemp.add(medicalconditionlists.get(i));
                                }
                            }
                            CustomAdapter_plm arrayAdapter = new CustomAdapter_plm( context,medicalconditionlistsTemp);
                            listItem.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context,"No match found",Toast.LENGTH_LONG).show();
                            CustomAdapter_plm arrayAdapter = new CustomAdapter_plm( context,medicalconditionlists);
                            listItem.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                        }

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        String strText = s.toString().trim();
                        if (strText.length() != 0) {
                            medicalconditionlistsTemp.clear();
                            for (int i = 0; i < medicalconditionlists.size(); i++) {
                                if (medicalconditionlists.get(i).getName().toLowerCase().startsWith(strText.toLowerCase())) {
                                    medicalconditionlistsTemp.add(medicalconditionlists.get(i));
                                }
                            }
                            CustomAdapter_plm arrayAdapter = new CustomAdapter_plm( context,medicalconditionlistsTemp);
                            listItem.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context,"No match found",Toast.LENGTH_LONG).show();
                            CustomAdapter_plm arrayAdapter = new CustomAdapter_plm( context,medicalconditionlists);
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
                        spinner_mcon.setText(medicalconditionlists.get(position).getName());
                        str_mcon= medicalconditionlists.get(position).getId();
                    }
                });
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_patient.setVisibility(View.VISIBLE);
                search_patient.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strText = s.toString().trim();
                        if (strText.length() != 0) {
                            searchdoctorpojosTemp.clear();
                            for (int i = 0; i < searchdoctorpojos.size(); i++) {
                                if (searchdoctorpojos.get(i).getDr_name().toLowerCase().startsWith(strText.toLowerCase())) {
                                    searchdoctorpojosTemp.add(searchdoctorpojos.get(i));
                                }
                            }
                            customAdapter_blooddonorlist= new CustomAdapter_blooddonorlist(context,searchdoctorpojosTemp);
                            listView.setAdapter(customAdapter_blooddonorlist);
                            customAdapter_blooddonorlist.notifyDataSetChanged();
                        } else {
                            Toast.makeText(PatientLikeMe.this,"No match found",Toast.LENGTH_LONG).show();
                            customAdapter_blooddonorlist= new CustomAdapter_blooddonorlist(context,searchdoctorpojos);
                            listView.setAdapter(customAdapter_blooddonorlist);
                            customAdapter_blooddonorlist.notifyDataSetChanged();
                        }

                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                        String strText = s.toString().trim();
                        if (strText.length() != 0) {
                            searchdoctorpojosTemp.clear();
                            for (int i = 0; i < searchdoctorpojos.size(); i++) {
                                if (searchdoctorpojos.get(i).getDr_name().toLowerCase().startsWith(strText.toLowerCase())) {
                                    searchdoctorpojosTemp.add(searchdoctorpojos.get(i));
                                }
                            }

                            customAdapter_blooddonorlist= new CustomAdapter_blooddonorlist(context,searchdoctorpojosTemp);
                            listView.setAdapter(customAdapter_blooddonorlist);
                            customAdapter_blooddonorlist.notifyDataSetChanged();
                        } else {
                            Toast.makeText(PatientLikeMe.this,"No match found",Toast.LENGTH_LONG).show();
                            customAdapter_blooddonorlist= new CustomAdapter_blooddonorlist(context,searchdoctorpojos);
                            listView.setAdapter(customAdapter_blooddonorlist);
                            customAdapter_blooddonorlist.notifyDataSetChanged();
                        }

                    }
                });
                if(searchdoctorpojos.size()>0) {
                    searchdoctorpojos.clear();
                    customAdapter_blooddonorlist.notifyDataSetChanged();
                }

                progressBar.setVisibility(View.VISIBLE);
                if(connectiondetector.isConnectingToInternet())
                fetchingplmdata();
                else
                    Toast.makeText(context, "No Internet!!", Toast.LENGTH_SHORT).show();
            }
        });
        customAdapter_blooddonorlist= new CustomAdapter_blooddonorlist(context,searchdoctorpojos);
        listView.setAdapter(customAdapter_blooddonorlist);

    }
    public void initView()
    {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Patient Like Me");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView=findViewById(R.id.list);
        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spinner_mcon=findViewById(R.id.mcondtion_spinner);
        et_desc=findViewById(R.id.mhdesc);
        textView=findViewById(R.id.msg);
        progressBar=findViewById(R.id.progressBar);


    }
    private Request fetchingmedicalcondition() {
        JSONObject postdata = new JSONObject();
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url(BASE_URL2+"listMedicalCondition")
                .post(body)
                .build();

    }
    public void medicalconditionJSON(String response) {
        Gson gson = new GsonBuilder().create();
        medicalcondition = gson.fromJson(response, Medicalcondition.class);
    }
    private void addingdata()
    {
        OkHttpClient client = new OkHttpClient();
        Request request = fetchingmedicalcondition();

        Log.i("", "onClick: "+request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }
            @Override
            public void onResponse(final Response response) throws IOException {

                final String body=response.body().string();
                medicalconditionJSON(body);
                Log.i("1234add", "onResponse: "+body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        medicalconditionlists=medicalcondition.getData();
                       /* CustomAdapter_plm customAdapter=new CustomAdapter_plm(context,medicalconditionlists);
                        spinner_mcon.setAdapter(customAdapter);
                        spinner_mcon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                str_mcon= medicalconditionlists.get(position).getId();
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
    private Request paientlikeme() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id",Prefhelper.getInstance(context).getUserid());
            postdata.put("conditions",str_mcon);
            postdata.put("description",str_desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url(BASE_URL2+"patientsLikeMe")
                .post(body)
                .build();

    }
    private void fetchingplmdata()
    {
        OkHttpClient client = new OkHttpClient();
        Request request = paientlikeme();

        Log.i("", "onClick: "+request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }
            @Override
            public void onResponse(final Response response) throws IOException {

                final String body=response.body().string();
                Log.i("1234adddata", "onResponse: "+body);
           //     medicalconditionJSON(body);

               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            JSONObject jsonObject= new JSONObject(body);
                            if(jsonObject.getString("message").equals("success"))
                            {

                                JSONArray jsonArray=jsonObject.getJSONArray("data");
                                if(jsonArray.length()>0)
                                {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        JSONObject jsonObject2 = jsonObject1.getJSONObject("MedicalHistory");
                                        JSONObject jsonObject3 = jsonObject1.getJSONObject("User");
                                        searchdoctorpojos.add(new bookedappointmentpatient(jsonObject3.getString("full_name"), jsonObject2.getString("description"), jsonObject3.getString("mobile"), jsonObject3.getString("email"),1,jsonObject3.getString("id")));
                                    }
                                    customAdapter_blooddonorlist = new CustomAdapter_blooddonorlist(context, searchdoctorpojos);
                                    listView.setVisibility(View.VISIBLE);
                                    listView.setAdapter(customAdapter_blooddonorlist);
                                    progressBar.setVisibility(View.GONE);
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    listView.setVisibility(View.GONE);
                                    textView.setText("No Result Found");
                                    Toast.makeText(context, "No Result Found", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

    }

}
class CustomAdapter_plm extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<Medicalconditionlist> medicalconditionlists;
    public CustomAdapter_plm(Context applicationContext, List<Medicalconditionlist> medicalconditionlists) {
        this.context = applicationContext;
        this.medicalconditionlists=medicalconditionlists;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return medicalconditionlists.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_item, null);
        TextView names = (TextView) view.findViewById(R.id.txt);
        names.setText(medicalconditionlists.get(i).getName());
        return view;
    }
}

