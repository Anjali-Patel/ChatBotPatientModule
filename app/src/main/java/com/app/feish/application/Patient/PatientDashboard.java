package com.app.feish.application.Patient;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.app.feish.application.Connectiondetector;
import com.app.feish.application.LoginActivity;
import com.app.feish.application.R;
import com.app.feish.application.Remote.ApiUtils;
import com.app.feish.application.Remote.EncryptionDecryption;
import com.app.feish.application.fragment.Addfamilyreport;
import com.app.feish.application.modelclassforapi.ContactService_getDetails;
import com.app.feish.application.sessiondata.Prefhelper;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL1;

public class PatientDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView textView_name;
    boolean failed = false;
    int id;
    View v;
    CardView CardView_vitalreport,bloodonor;

    TextView pdra_name, pdra_email;
    Toolbar toolbar;
    Context context = this;
    CircleImageView imageView,circleImageView_pp;

    private static final int CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE = 101;
    private static final String TAG = "FloatingViewControl";


    public static ContactService_getDetails contactService_getDetails;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Connectiondetector connectiondetector;
    public static String userName = null;



    private void updatetkenid()
    {
        OkHttpClient client = new OkHttpClient();
        Request validation_request = update();
        client.newCall(validation_request).enqueue(new Callback() {
            @Override

            public void onFailure(Request request, IOException e) {

                Log.i("Activity", "onFailure: Fail");
            }

            @Override
            public void onResponse(final Response response) throws IOException {
              //  final String body=response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    //    Toast.makeText(context, ""+body, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private Request update() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id",Prefhelper.getInstance(context).getUserid());
            postdata.put("token", FirebaseInstanceId.getInstance().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(Addfamilyreport.JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url(BASE_URL2+"updateUserTokenIDfornotification")
                .post(body)
                .build();
    }

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bloodonor = findViewById(R.id.bloddonor);
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
//get hold of textview.
      TextView date  = (TextView) findViewById(R.id.datetext);
//set it as current date.
      date.setText(date_n);
      date .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(PatientDashboard.this,SetupProfile.class);
              intent.putExtra("firstname",contactService_getDetails.getData().getFirstName());
              intent.putExtra("lastname",contactService_getDetails.getData().getLastName());
              intent.putExtra("email",contactService_getDetails.getData().getIs_active().toString());

              String decryptphone2= EncryptionDecryption.decode(contactService_getDetails.getData().getMobile());

              intent.putExtra("mobile",decryptphone2);
              if(contactService_getDetails.getData().getBloodGroup()!=null)
                  intent.putExtra("bloodgroup",contactService_getDetails.getData().getBloodGroup().toString());
              else
                  intent.putExtra("bloodgroup","0");
              intent.putExtra("gen",contactService_getDetails.getData().getGender());
              if(contactService_getDetails.getData().getMaritalStatus()!=null)
                  intent.putExtra("m_status",contactService_getDetails.getData().getMaritalStatus().toString());
              else
                  intent.putExtra("m_status","0");

              if(contactService_getDetails.getData().getAvatar()!=null)
                  intent.putExtra("pic",contactService_getDetails.getData().getAvatar().toString());
              else
                  intent.putExtra("pic","");
              if(contactService_getDetails.getData().getIdentityId()!=null)
                  intent.putExtra("IdentityId",contactService_getDetails.getData().getIdentityId());
              else
                  intent.putExtra("IdentityId","");

              if(contactService_getDetails.getData().getEthnicityId()!=null)
                  intent.putExtra("ethn",contactService_getDetails.getData().getEthnicityId().toString());
              else
                  intent.putExtra("ethn","");
              if(contactService_getDetails.getData().getOccupationId()!=null)
                  intent.putExtra("occupation",contactService_getDetails.getData().getOccupationId().toString());
              else
                  intent.putExtra("occupation","");

              if(contactService_getDetails.getData().getIdentityType()!=null)
                  intent.putExtra("identitytype",contactService_getDetails.getData().getIdentityType().toString());
              else
                  intent.putExtra("identitytype","");

              if(contactService_getDetails.getData().getAddress()==null)
                  intent.putExtra("address","");
              else
                  intent.putExtra("address",contactService_getDetails.getData().getAddress().toString());

              if(contactService_getDetails.getData().getHeight()==null)
                  intent.putExtra("Height","");
              else
                  intent.putExtra("Height",contactService_getDetails.getData().getHeight().toString());

              if(contactService_getDetails.getData().getWeight()==null)
                  intent.putExtra("Weight","");
              else
                  intent.putExtra("Weight",contactService_getDetails.getData().getWeight().toString());
              startActivity(intent);
          }
      });

      //
      //
      //
      // showCustomFloatingView(getApplicationContext(), true);

   //  checkPermission();
        connectiondetector= new Connectiondetector(getApplicationContext());
        imageView=findViewById(R.id.profileppic);
    String a=  Prefhelper.getInstance(PatientDashboard.this).getProfile_pic();
      Glide.with(PatientDashboard.this).load( a).error(R.drawable.patienr).into(imageView);
      imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(PatientDashboard.this,SetupProfile.class);
              intent.putExtra("firstname",contactService_getDetails.getData().getFirstName());
              intent.putExtra("lastname",contactService_getDetails.getData().getLastName());
              intent.putExtra("email",contactService_getDetails.getData().getIs_active().toString());

              String decryptphone2= EncryptionDecryption.decode(contactService_getDetails.getData().getMobile());

              intent.putExtra("mobile",decryptphone2);
              if(contactService_getDetails.getData().getBloodGroup()!=null)
                  intent.putExtra("bloodgroup",contactService_getDetails.getData().getBloodGroup().toString());
              else
                  intent.putExtra("bloodgroup","0");
              intent.putExtra("gen",contactService_getDetails.getData().getGender());
              if(contactService_getDetails.getData().getMaritalStatus()!=null)
                  intent.putExtra("m_status",contactService_getDetails.getData().getMaritalStatus().toString());
              else
                  intent.putExtra("m_status","0");

              if(contactService_getDetails.getData().getAvatar()!=null)
                  intent.putExtra("pic",contactService_getDetails.getData().getAvatar().toString());
              else
                  intent.putExtra("pic","");
              if(contactService_getDetails.getData().getIdentityId()!=null)
                  intent.putExtra("IdentityId",contactService_getDetails.getData().getIdentityId());
              else
                  intent.putExtra("IdentityId","");

              if(contactService_getDetails.getData().getEthnicityId()!=null)
                  intent.putExtra("ethn",contactService_getDetails.getData().getEthnicityId().toString());
              else
                  intent.putExtra("ethn","");
              if(contactService_getDetails.getData().getOccupationId()!=null)
                  intent.putExtra("occupation",contactService_getDetails.getData().getOccupationId().toString());
              else
                  intent.putExtra("occupation","");

              if(contactService_getDetails.getData().getIdentityType()!=null)
                  intent.putExtra("identitytype",contactService_getDetails.getData().getIdentityType().toString());
              else
                  intent.putExtra("identitytype","");

              if(contactService_getDetails.getData().getAddress()==null)
                  intent.putExtra("address","");
              else
                  intent.putExtra("address",contactService_getDetails.getData().getAddress().toString());

              if(contactService_getDetails.getData().getHeight()==null)
                  intent.putExtra("Height","");
              else
                  intent.putExtra("Height",contactService_getDetails.getData().getHeight().toString());

              if(contactService_getDetails.getData().getWeight()==null)
                  intent.putExtra("Weight","");
              else
                  intent.putExtra("Weight",contactService_getDetails.getData().getWeight().toString());
              startActivity(intent);

          }
      });
        initView();
        if(connectiondetector.isConnectingToInternet())
        {
            updatetkenid();
            fetchingdata();
        }
        else
            Toast.makeText(context, "No Internet!!", Toast.LENGTH_SHORT).show();
  }

    @TargetApi(Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE) {
            showCustomFloatingView(getApplicationContext(), false);
        }
    }
    @SuppressLint("NewApi")
    private void showCustomFloatingView(Context context, boolean isShowOverlayPermission) {
        // API22以下かチェック
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            final Intent intent = new Intent(context, CustomFloatingViewService.class);
            ContextCompat.startForegroundService(context, intent);
            return;
        }

        // 他のアプリの上に表示できるかチェック
        if (Settings.canDrawOverlays(context)) {
            final Intent intent = new Intent(context, CustomFloatingViewService.class);
            ContextCompat.startForegroundService(context, intent);
            return;
        }
        // オーバレイパーミッションの表示
        if (isShowOverlayPermission) {
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            startActivityForResult(intent, CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE);
        }
        Toast.makeText(this, "hii", Toast.LENGTH_SHORT).show();
    }
    public void initView()
  {
      toolbar =findViewById(R.id.toolbar);
      toolbar.setTitleTextColor(Color.WHITE);
      toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
      setSupportActionBar(toolbar);
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });
      if(getSupportActionBar()!=null)
      getSupportActionBar().setTitle("DashBoard");
      DrawerLayout drawer =findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
              this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.addDrawerListener(toggle);
      toggle.syncState();

      NavigationView navigationView =findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);
      v=navigationView.getHeaderView(0);

      textView_name=findViewById(R.id.name);
      textView_name.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(PatientDashboard.this,SetupProfile.class);
              intent.putExtra("firstname",contactService_getDetails.getData().getFirstName());
              intent.putExtra("lastname",contactService_getDetails.getData().getLastName());
              intent.putExtra("email",contactService_getDetails.getData().getIs_active().toString());

              String decryptphone2= EncryptionDecryption.decode(contactService_getDetails.getData().getMobile());

              intent.putExtra("mobile",decryptphone2);
              if(contactService_getDetails.getData().getBloodGroup()!=null)
                  intent.putExtra("bloodgroup",contactService_getDetails.getData().getBloodGroup().toString());
              else
                  intent.putExtra("bloodgroup","0");
              intent.putExtra("gen",contactService_getDetails.getData().getGender());
              if(contactService_getDetails.getData().getMaritalStatus()!=null)
                  intent.putExtra("m_status",contactService_getDetails.getData().getMaritalStatus().toString());
              else
                  intent.putExtra("m_status","0");

              if(contactService_getDetails.getData().getAvatar()!=null)
                  intent.putExtra("pic",contactService_getDetails.getData().getAvatar().toString());
              else
                  intent.putExtra("pic","");
              if(contactService_getDetails.getData().getIdentityId()!=null)
                  intent.putExtra("IdentityId",contactService_getDetails.getData().getIdentityId());
              else
                  intent.putExtra("IdentityId","");

              if(contactService_getDetails.getData().getEthnicityId()!=null)
                  intent.putExtra("ethn",contactService_getDetails.getData().getEthnicityId().toString());
              else
                  intent.putExtra("ethn","");
              if(contactService_getDetails.getData().getOccupationId()!=null)
                  intent.putExtra("occupation",contactService_getDetails.getData().getOccupationId().toString());
              else
                  intent.putExtra("occupation","");

              if(contactService_getDetails.getData().getIdentityType()!=null)
                  intent.putExtra("identitytype",contactService_getDetails.getData().getIdentityType().toString());
              else
                  intent.putExtra("identitytype","");

              if(contactService_getDetails.getData().getAddress()==null)
                  intent.putExtra("address","");
              else
                  intent.putExtra("address",contactService_getDetails.getData().getAddress().toString());

              if(contactService_getDetails.getData().getHeight()==null)
                  intent.putExtra("Height","");
              else
                  intent.putExtra("Height",contactService_getDetails.getData().getHeight().toString());

              if(contactService_getDetails.getData().getWeight()==null)
                  intent.putExtra("Weight","");
              else
                  intent.putExtra("Weight",contactService_getDetails.getData().getWeight().toString());
              startActivity(intent);

          }
      });

      pdra_name=v.findViewById(R.id.pnamed);
      circleImageView_pp=v.findViewById(R.id.imageView_pp);
      Glide.with(PatientDashboard.this).load(Prefhelper.getInstance(PatientDashboard.this).getProfile_pic()).error(R.drawable.patienr).into(circleImageView_pp);
      pdra_email=v.findViewById(R.id.pemaild);
      CardView_vitalreport=findViewById(R.id.vitalreport);
      CardView_vitalreport.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(contactService_getDetails==null) {
                  Toast.makeText(PatientDashboard.this, "Data is not loaded", Toast.LENGTH_SHORT).show();
              }
              else {
                  Intent intent = new Intent(PatientDashboard.this, VitalandReport.class);
                //  Intent intent = new Intent(PatientDashboard.this, DemoScanner.class);
                  intent.putExtra("firstname",contactService_getDetails.getData().getFirstName());
                  intent.putExtra("lastname",contactService_getDetails.getData().getLastName());
                  if(contactService_getDetails.getData().getBloodGroup()!=null)
                      intent.putExtra("bloodgroup",contactService_getDetails.getData().getBloodGroup().toString());
                  else
                      intent.putExtra("bloodgroup","0");
                  intent.putExtra("gen",contactService_getDetails.getData().getGender());
                  intent.putExtra("data", contactService_getDetails);
                  startActivity(intent);
              }
          }
      });

  }

    private Request Patient_detail() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefhelper.getInstance(PatientDashboard.this).getUserid());
            //postdata.put("password",lpassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        return new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url(BASE_URL2+"getPatientdetails")
                .post(body)
                .build();
    }


    public void pdetailJSON(String response) {
        Gson gson = new GsonBuilder().create();
        try{
            contactService_getDetails = gson.fromJson(response, ContactService_getDetails.class);

        }catch(IllegalStateException | JsonSyntaxException exception){
            failed = true;
        }
    }
 private void fetchingdata()
  {
      OkHttpClient client = new OkHttpClient();
      Request request = Patient_detail();
      Log.i("", "onClick: "+request);
      client.newCall(request).enqueue(new Callback() {
          @Override
          public void onFailure(Request request, IOException e) {
              Log.i("Activity", "onFailure: Fail");
          }
          @Override
          public void onResponse(final Response response) throws IOException {
              String body=response.body().string();
              Log.i("", "onResponse: "+body);
              pdetailJSON(body);
              final String message = contactService_getDetails.getMessage();
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      //pb.setVisibility(View.GONE);
                      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                     if (message.equalsIgnoreCase("success")) {
                          Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();

                          String comname=contactService_getDetails.getData().getFirstName()+"  "+contactService_getDetails.getData().getLastName();
                          textView_name.setText(comname);

                         /* if(contactService_getDetails.getData().getAvatar()!=null) {

                              Picasso.with(context)
                                      .load(contactService_getDetails.getData().getAvatar().toString())
                                      //this is also optional if some error has occurred in downloading the image this image would be displayed
                                      .into(imageView);
                              Picasso.with(context)
                                      .load(contactService_getDetails.getData().getAvatar().toString())
                                      //this is also optional if some error has occurred in downloading the image this image would be displayed
                                      .into(circleImageView_pp);
                          }*/

                          pdra_name.setText(comname);
                          userName = comname;
                      /*  Intent intent=new Intent(PatientDashboard.this,ChatBot.class);
                          intent.putExtra("username", pdra_name.getText());
                          startActivity(intent);*/
                          pdra_email.setText(contactService_getDetails.getData().getIs_active().toString());
                      } else {
//                          Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                      }
                  }
              });
          }

      });

  }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.create();
            builder.setMessage("Do You Want to Exit ?");
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
            });
            builder.show();

       /* else {
            super.onBackPressed();
        }*/

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
         String appUrl="https://play.google.com/store/apps/details?id=com.app.feish.application&hl=en_IN";
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, appUrl);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Downloaad ");
            startActivity(shareIntent);
        } else if (id == R.id.nav_checkmsg) {
            startActivity(new Intent(PatientDashboard.this,PatientMessageActivity.class));


        } else if (id == R.id.nav_delete) {
            //startActivity(new Intent(PatientDashboard.this,DeleteProfile.class));

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(Html.fromHtml("<font color='#FF7F27'>Delete Profile</font>"));
            String alert1 = "<br>Are you sure you want to Delete Your Profile?<br>";
            String alert2 = "<br>Please be informed that all your data would be erased within 24 hours. <br> ";
            String alert3 = "<br>Please contact on support@feish.online for any assistance<br>" ;

            alert.setMessage(Html.fromHtml(alert1 +"\n"+ alert2 +"\n"+ alert3));
            //alert.setMessage("Are you sure you want to Deactivate Profile?.");

            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Profile Deactivate SuccesFully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PatientDashboard.this, LoginActivity.class));
                    Prefhelper.getInstance(PatientDashboard.this).setLoggedIn(false);
                    finish();
                }
            })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    })
                    .show();



        } else if (id == R.id.nav_report) {
            Intent intent=new Intent(PatientDashboard.this,Report.class);
            intent.putExtra("userid",Prefhelper.getInstance(PatientDashboard.this).getUserid());
            startActivity(intent);

        }/*else if (id == R.id.nav_preport) {
            Intent intent=new Intent(PatientDashboard.this,Allreports.class);
                    startActivity(intent);

        }*/ else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(context);
            }
            builder.setTitle("Delete entry")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            startActivity(new Intent(PatientDashboard.this, LoginActivity.class));
                            Prefhelper.getInstance(PatientDashboard.this).setLoggedIn(false);
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
        }

       /* } else if (id == R.id.nav_bookedappointment) {
                startActivity(new Intent(PatientDashboard.this,MyBookedappointment.class));
        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void OpenActivityC(View view)
    {
        if(contactService_getDetails==null) {
            Toast.makeText(PatientDashboard.this, "Data is not loaded", Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(new Intent(PatientDashboard.this, ConsultDoctor.class));
        }
    }

    public  void OpenPurchasePlan(View view)
    {
         startActivity(new Intent(PatientDashboard.this,PatientPurchasePlan.class));
    }
    public  void OpenActivityMsg(View view)
    {
        startActivity(new Intent(PatientDashboard.this,PatientMessageActivity.class));
    }

    public  void OpenActivityBlood(View view)
    {
        Intent intent=new Intent(PatientDashboard.this,BloodDonor.class);
        String a=contactService_getDetails.getData().getBloodGroup().toString();
        intent.putExtra("bloodgroup",a);
        startActivity(intent);
    }
    public  void OpenActivityHealth(View view)
    {
        startActivity(new Intent(PatientDashboard.this,HealthParameter.class));
    }
    public  void OpenActivityVitalSign(View view)
    {
        Intent intent=new Intent(PatientDashboard.this,VitalSigns.class);
        intent.putExtra("userid",Prefhelper.getInstance(PatientDashboard.this).getUserid());
        startActivity(intent);
    }
    public  void OpenActivityLabreport(View view)
    {
        Intent intent=new Intent(PatientDashboard.this,Report.class);
        intent.putExtra("userid",Prefhelper.getInstance(PatientDashboard.this).getUserid());
        startActivity(intent);
    }
    public  void OpenActivityMedicalHistory(View view)
    {
        Intent intent=new Intent(PatientDashboard.this,MedicalHitoryp.class);
        intent.putExtra("userid",Prefhelper.getInstance(PatientDashboard.this).getUserid());
        startActivity(intent);


    }
    public  void OpenActivityFamilyHistory(View view)
    {
        Intent intent=new Intent(PatientDashboard.this,FamilyHistory.class);
        intent.putExtra("userid",Prefhelper.getInstance(PatientDashboard.this).getUserid());
        startActivity(intent);
    }
    public  void OpenActivityRemainder(View view)
    {
        startActivity(new Intent(PatientDashboard.this,Remainder.class));
    }
    public  void OpenActivityFemale(View view)
    {
        Intent intent=new Intent(PatientDashboard.this,FemaleInfo.class);
        intent.putExtra("userid",Prefhelper.getInstance(PatientDashboard.this).getUserid());
        startActivity(intent);

    }

    public  void OpenActivityHelp(View view) {
        Intent intent = new Intent(PatientDashboard.this, Help.class);
        intent.putExtra("userid", Prefhelper.getInstance(PatientDashboard.this).getUserid());
        startActivity(intent);
    }

    public  void OpenActivityChatBot(View view)
    {
        Intent intent=new Intent(PatientDashboard.this,ChatBot.class);
        intent.putExtra("userid",Prefhelper.getInstance(PatientDashboard.this).getUserid());
        startActivity(intent);

    }


    public  void OpenActivityTreatment(View view)
    {
        startActivity(new Intent(PatientDashboard.this,Treatement.class));
    }
    public  void OpenActivityDietplan(View view)
    {
        Intent intent=new Intent(PatientDashboard.this,Createdietplan.class);
        intent.putExtra("userid",Prefhelper.getInstance(PatientDashboard.this).getUserid());
        startActivity(intent);
    }
    public  void OpenActivityLieme(View view)
    {
        startActivity(new Intent(PatientDashboard.this,PatientLikeMe.class));
    }


}
