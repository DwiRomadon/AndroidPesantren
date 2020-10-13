package com.dwiromadon.gispesantren.pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dwiromadon.gispesantren.R;
import com.dwiromadon.gispesantren.users.LoginActivity;

public class HomePengguna extends AppCompatActivity {

    Button btnPesantren, cardMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pengguna);
        getSupportActionBar().hide();

        btnPesantren = (Button) findViewById(R.id.btnPesantren);
        cardMaps = (Button) findViewById(R.id.cardMaps);

        btnPesantren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePengguna.this, DataPesantrenPengguna.class);
                startActivity(i);
                finish();
            }
        });
        cardMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePengguna.this, Maps.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(HomePengguna.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}