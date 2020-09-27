package com.app.feish.application.Patient;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feish.application.Adpter.Dignosticreport;
import com.app.feish.application.R;
import com.app.feish.application.Remote.ApiUtils;
import com.app.feish.application.fragment.ListFamilyhistory;
import com.app.feish.application.modelclassforapi.ContactService_getDetails;
import com.app.feish.application.sessiondata.Prefhelper;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.scanlibrary.PickImageFragment;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.app.feish.application.Patient.ConsultDoctor.JSON;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL2;
import static com.app.feish.application.Remote.ApiUtils.BASE_URL1;
import static com.app.feish.application.Remote.ApiUtils.XAPI_KEY;

public class VitalandReport extends AppCompatActivity {
    Toolbar toolbar;
    private static final int REQUEST_CODE = 99;
    private Button UploadButton;
    private Button ViewReportButton;
    private Button cameraButton;
    private ImageView scannedImageView;
   public  static String FilePath ;
   Context context=this;
   String encodedimage="";
  public final static String IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/scanSample";
TextView patient_name,patient_gen,patient_blood,reportstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readingtextvalue);
        patient_name=findViewById(R.id.patient_name);
        patient_gen=findViewById(R.id.patient_gen);

        patient_blood=findViewById(R.id.patient_blood);

        reportstatus=findViewById(R.id.reportstatus);

        ViewReportButton=findViewById(R.id.ViewButton);
        String flName=getIntent().getStringExtra("firstname") +" "+getIntent().getStringExtra("lastname");
        patient_name.setText(flName);
        if(getIntent().getStringExtra("gen").equalsIgnoreCase("1")){
            patient_gen.setText("Male");

        }else{
            patient_gen.setText("Female");

        }

        if(Integer.parseInt(getIntent().getStringExtra("bloodgroup"))==1){
            patient_blood.setText("A+");

        }else if(Integer.parseInt(getIntent().getStringExtra("bloodgroup"))==2){
            patient_blood.setText("A-");

        }else if(Integer.parseInt(getIntent().getStringExtra("bloodgroup"))==3){
            patient_blood.setText("B+");

        }else if(Integer.parseInt(getIntent().getStringExtra("bloodgroup"))==4){
            patient_blood.setText("B-");

        }else if(Integer.parseInt(getIntent().getStringExtra("bloodgroup"))==5){
            patient_blood.setText("AB+");

        }else if(Integer.parseInt(getIntent().getStringExtra("bloodgroup"))==6){
            patient_blood.setText("AB-");

        }else if(Integer.parseInt(getIntent().getStringExtra("bloodgroup"))==5){
            patient_blood.setText("O+");

        }else if(Integer.parseInt(getIntent().getStringExtra("bloodgroup"))==6){
            patient_blood.setText("O-");

        }


        ViewReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VitalandReport.this,ViewScanReport.class);
                startActivity(intent);
            }
        });


      /*  UploadButton = findViewById(R.id.UploadButton);

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* try
                {
                    base64();
                    uploadreport();


                }catch (Exception e)
                {
                    Toast.makeText(context, "Please Scan report First", Toast.LENGTH_SHORT).show();
                }
*//*



                }

                });*/

        init();
    }


    private void base64()
    {
        Bitmap bm = BitmapFactory.decodeFile(FilePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        encodedimage = Base64.encodeToString(b, Base64.DEFAULT);
        Toast.makeText(this,PickImageFragment.hello,Toast.LENGTH_LONG).show();
//        FilePath=encodedImage.toString();
      Log.i("555555555555:--      ",encodedimage); // Toast.makeText(this, ""+encodedImage, Toast.LENGTH_SHORT).show();

    }
    private void base643() throws FileNotFoundException
    {
        FileInputStream fis = new FileInputStream(new File(FilePath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Log.i("555555555555:--      ",encodedimage); // Toast.makeText(this, ""+encodedImage, Toast.LENGTH_SHORT).show();

    }

    private void init() {

        cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA));
        scannedImageView = (ImageView) findViewById(R.id.scannedImage);
    }

    private class ScanButtonClickListener implements View.OnClickListener {

        private int preference;

        public ScanButtonClickListener(int preference) {
            this.preference = preference;


            }

        public ScanButtonClickListener() {

        }

        @Override
        public void onClick(View v) {
            startScan(preference);
        }
    }

    protected void startScan(int preference) {
        Intent intent = new Intent(this, ScanActivity.class);
        if(Build.VERSION.SDK_INT>=24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        intent.putExtra("filepath",ScanConstants.SCANNED_RESULT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {



            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            FilePath=PickImageFragment.hello;
           Toast.makeText(this,PickImageFragment.hello,Toast.LENGTH_LONG).show();




            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                getContentResolver().delete(uri, null, null);
                scannedImageView.setImageBitmap(bitmap);
                base64();
                uploadreport();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap convertByteArrayToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void  uploadreport()
    {

            OkHttpClient client = new OkHttpClient();
            Request request = AddReport();
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
                                if (jsonObject.getString("message").equals("report uploaded successfully")) {

                                    Toast.makeText(context, "Report Added SuccesFully", Toast.LENGTH_SHORT).show();


                                } else {

                                    Toast.makeText(getApplicationContext(), "Report Added SuccesFully", Toast.LENGTH_SHORT).show();

                                    //listView.setVisibility(View.VISIBLE);
//                                     fetchdata();


                                }

                            } catch (Exception e) {

                                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            });
        }

        private Request AddReport()
        {
            JSONObject postdata = new JSONObject();
            try {
                // postdata.put("user_id",1227);
                postdata.put("user_id",Integer.parseInt(Prefhelper.getInstance(context).getUserid()));
                postdata.put("report",encodedimage);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(ListFamilyhistory.JSON, postdata.toString());
            return new Request.Builder()
                    .addHeader("X-Api-Key", XAPI_KEY)
                    .addHeader("Content-Type", "application/json")
                    .url(BASE_URL1+"addReport")
                    .post(body)
                    .build();
        }

    private Request profilePicrequest() {
        File sourceFile = new File(FilePath);

        final MediaType MEDIA_TYPE = FilePath.endsWith("jpg") ?
                MediaType.parse("image/png") : MediaType.parse("image/jpeg");
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("report", sourceFile.getName(), RequestBody.create(MEDIA_TYPE, sourceFile))
                .build();

        Request request = new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url(BASE_URL2+"addLabResult")
                .post(requestBody)
                .build();

        return request;
    }

public  void close(View view)
{
    finish();
}
}



