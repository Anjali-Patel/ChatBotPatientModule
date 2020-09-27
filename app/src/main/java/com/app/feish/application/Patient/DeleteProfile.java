package com.app.feish.application.Patient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feish.application.LoginActivity;
import com.app.feish.application.R;
import com.app.feish.application.sessiondata.Prefhelper;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL1;
import static com.app.feish.application.Remote.ApiUtils.XAPI_KEY;
import static com.app.feish.application.fragment.ListFamilyhistory.JSON;

public class DeleteProfile extends AppCompatActivity {

    TextView delprofile;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile);
        delprofile=findViewById(R.id.delprofile);
        delprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DeleteData();
            }
        });
    }

    void DeleteData()
    {

        OkHttpClient client = new OkHttpClient();
        Request request = DelData();
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
                            if (jsonObject.getString("message").equals("profile update successfully")) {


                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(context);
                                }
                                builder.setTitle("Delete entry")
                                        .setMessage("Are you sure you want to Deactivate Profile?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                Toast.makeText(getApplicationContext(), "Profile Deactivate SuccesFully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(DeleteProfile.this, LoginActivity.class));
                                                Prefhelper.getInstance(DeleteProfile.this).setLoggedIn(false);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();





                              /*  Toast.makeText(getApplicationContext(), "Profile Deactivate SuccesFully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DeleteProfile.this, LoginActivity.class));
                                Prefhelper.getInstance(DeleteProfile.this).setLoggedIn(false);
                                finish();*/

                            } else {

                                Toast.makeText(getApplicationContext(), "Some Error Occour", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "data not valid" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private Request DelData()
    {
        JSONObject postdata = new JSONObject();
        try {

            postdata.put("user_id",Integer.parseInt(Prefhelper.getInstance(context).getUserid()));



        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", XAPI_KEY)
                .addHeader("Content-Type", "application/json")
                .url(BASE_URL1+"deactivateProfile")
                .post(body)
                .build();
    }


}
