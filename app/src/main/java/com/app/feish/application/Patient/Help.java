package com.app.feish.application.Patient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.feish.application.R;

public class Help extends AppCompatActivity {
    TextView healthhelp,vitalhelp,scanreporthelp,labresulthelp,medicalhistoryhelp,familyhistoryhelp,diethelp,
    treatmenthelp,remainderhelp,messahehelp,patientlikemehelp,blooddonarhelp,shehelp,bothelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        healthhelp=findViewById(R.id.healthhelp);
        vitalhelp=findViewById(R.id.vitalhelp);
        scanreporthelp=findViewById(R.id.scanreporthelp);
        labresulthelp=findViewById(R.id.labresulthelp);
        medicalhistoryhelp=findViewById(R.id.medicalhistoryhelp);
        familyhistoryhelp=findViewById(R.id.familyhistoryhelp);
        diethelp=findViewById(R.id.diethelp);
        treatmenthelp=findViewById(R.id.treatmenthelp);
        remainderhelp=findViewById(R.id.reaminderhelp);
        messahehelp=findViewById(R.id.messagehelp);
        patientlikemehelp=findViewById(R.id.patientlikemehelp);
        blooddonarhelp=findViewById(R.id.blooddonorhelp);
        shehelp=findViewById(R.id.shehelp);
        bothelp=findViewById(R.id.bothelp);


        healthhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,HealthHelpSection.class);
                startActivity(intent);
            }
        });


        vitalhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,VitalHelpSection.class);
                startActivity(intent);
            }
        });


        scanreporthelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,ScanReportHelpSection.class);
                startActivity(intent);
            }
        });

        labresulthelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,LabReportHelpSection.class);
                startActivity(intent);
            }
        });

        medicalhistoryhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,MedicalHistoryHelpSection.class);
                startActivity(intent);
            }
        });

        familyhistoryhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,FamilyHistoryHelpSection.class);
                startActivity(intent);
            }
        });

        diethelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,DietHelpSection.class);
                startActivity(intent);
            }
        });

        treatmenthelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,TreatmentHelpSection.class);
                startActivity(intent);
            }
        });

        remainderhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,RemainderHelpSection.class);
                startActivity(intent);
            }
        });

        messahehelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,MessageHelpSection.class);
                startActivity(intent);
            }
        });

        patientlikemehelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,PatientLikeMeHelpSection.class);
                startActivity(intent);
            }
        });

        blooddonarhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,BloodDonorHelpSection.class);
                startActivity(intent);
            }
        });

        shehelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,SheHelpSection.class);
                startActivity(intent);
            }
        });

        bothelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Help.this,BotHelpSection.class);
                startActivity(intent);
            }
        });
    }
}

