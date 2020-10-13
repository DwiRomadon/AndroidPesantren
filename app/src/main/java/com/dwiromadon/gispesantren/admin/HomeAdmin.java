package com.dwiromadon.gispesantren.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dwiromadon.gispesantren.R;

public class HomeAdmin extends AppCompatActivity {

    Button cardInputData, btnDataPesantren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        getSupportActionBar().hide();

        cardInputData = (Button) findViewById(R.id.cardInputData);
        btnDataPesantren = (Button) findViewById(R.id.btnDataPesantren);

        cardInputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdmin.this, TambahData.class);
                startActivity(i);
                finish();
            }
        });

        btnDataPesantren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdmin.this, DataPesantrenAdmin.class);
                startActivity(i);
                finish();
            }
        });
    }
}
