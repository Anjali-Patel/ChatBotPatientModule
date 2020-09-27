package com.app.feish.application.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.feish.application.R;

public class MenstruationReport extends AppCompatActivity {
    TextView menstruationlistdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menstruation_report);
        menstruationlistdata=findViewById(R.id.menstruationlistdata);
        menstruationlistdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MenstruationReport.this,ListMenstruation.class);
                startActivity(intent);
            }
        });
    }
    public void CloseActivity(View view)
    {
        finish();
    }
}
