package com.dwiromadon.gispesantren.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dwiromadon.gispesantren.R;

public class TentangAplikasi extends AppCompatActivity {


    Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_aplikasi);
        getSupportActionBar().hide();

        btnOK = (Button) findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TentangAplikasi.this, HomePengguna.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(TentangAplikasi.this, HomePengguna.class);
        startActivity(i);
        finish();
    }
}