package com.app.feish.application.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.feish.application.R;

public class ListViewOfPregnnacyReport extends AppCompatActivity {
    TextView listpregnancyinfo,listworkoutinfo,listworkoutdetails,listadditionalworkoutdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_of_pregnnacy_report);
        listpregnancyinfo=findViewById(R.id.listpragnancydata);
        listworkoutinfo=findViewById(R.id.listworkout);
        listworkoutdetails=findViewById(R.id.listworkoutdetails);
        listadditionalworkoutdetails=findViewById(R.id.listadditionaldetailsworkout);

        listpregnancyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListViewOfPregnnacyReport.this,ListPregnancyInfo.class);
                startActivity(intent);
            }
        });

        listworkoutinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListViewOfPregnnacyReport.this,ListWorkout.class);
                startActivity(intent);
            }
        });

        listworkoutdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListViewOfPregnnacyReport.this,ListWorkoutDetails.class);
                startActivity(intent);
            }
        });

        listadditionalworkoutdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListViewOfPregnnacyReport.this,ListAdditionalWorkout.class);
                startActivity(intent);
            }
        });

    }
    public void CloseActivity(View view)
    {
        finish();
    }
}
