package com.app.feish.application.Patient;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.app.feish.application.R;

public class Welcome extends Activity {
    ImageView happy,sad,cool,cry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        happy=findViewById(R.id.happy);
        sad=findViewById(R.id.sad);
       cool=findViewById(R.id.cool);



     /*   WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 1000;
        params.width = 1000;
        params.y = -10;

        this.getWindow().setAttributes(params);
*/

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Welcome.this,PatientDashboard.class);
                startActivity(intent);
            }
        });


       sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Welcome.this,PatientDashboard.class);
                startActivity(intent);
            }
        });

        cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Welcome.this,PatientDashboard.class);
                startActivity(intent);
            }
        });
    }
}
