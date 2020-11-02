package com.dwiromadon.gispesantren.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dwiromadon.gispesantren.R;
import com.dwiromadon.gispesantren.admin.HomeAdmin;
import com.dwiromadon.gispesantren.pengguna.HomePengguna;

public class LoginActivity extends AppCompatActivity {

    LinearLayout skipLogin;
    Button button_signin;
    EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        skipLogin = (LinearLayout) findViewById(R.id.skipLogin);
        button_signin = (Button) findViewById(R.id.button_signin);
        edtUsername   = (EditText) findViewById(R.id.et_username);
        edtPassword   = (EditText) findViewById(R.id.et_password);

        skipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, HomePengguna.class);
                startActivity(i);
                finish();
            }
        });

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (userName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                }else if (password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                }else {
                    if (userName.equals("admin")){
                        if(password.equals("123456")){
                            Intent i = new Intent(LoginActivity.this, HomeAdmin.class);
                            startActivity(i);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Password Salah",
                                    Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Usename Salah",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(LoginActivity.this, HomePengguna.class);
        startActivity(i);
        finish();
    }
}