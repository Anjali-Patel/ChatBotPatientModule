package com.app.feish.application.Patient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feish.application.R;
import com.app.feish.application.sessiondata.Prefhelper;

public class FemaleInfo extends AppCompatActivity {
    TextView female,pragnancy,femalereport,femalecyclereport,pregnancyreport;
    String userid="";
    Context context=this;
    ImageView imageviewforhelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_female_info);
        female = findViewById(R.id.femalecycle);
        pragnancy = findViewById(R.id.pragnancy);
        femalecyclereport = findViewById(R.id.femalecyclereport);
        pregnancyreport = findViewById(R.id.pregnancyreport);
        imageviewforhelp=findViewById(R.id.imageviewhelp);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FemaleInfo.this, Female.class);
                startActivity(intent);
            }
        });
        pragnancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FemaleInfo.this, PregnancyReport.class);
                startActivity(intent);

            }
        });
        pregnancyreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FemaleInfo.this, ListViewOfPregnnacyReport.class);
                startActivity(intent);
            }
        });

        femalecyclereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FemaleInfo.this, MenstruationReport.class);
                startActivity(intent);
            }
        });



        imageviewforhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(FemaleInfo.this);
                builder.setTitle("##Instructions For Using She Section");
                builder.setMessage("1.Add Pregnancy:: Add your pregnancy information(months and weeks).." +
                        " 2.Add Menstruation: Track menstruation date wise " +
                        "3. View part Is for to view what data you saved in add part");
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

    public void CloseActivity(View view)
    {
        finish();
    }
}
