package com.dwiromadon.gispesantren.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dwiromadon.gispesantren.R;
import com.dwiromadon.gispesantren.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pendidikan extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    String _id;
    Intent i;

    EditText edtPendidikan;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendidikan);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        i = getIntent();
        _id = i.getStringExtra("_id");

        edtPendidikan = (EditText) findViewById(R.id.edtPendidikan);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fasi = edtPendidikan.getText().toString();

                try {
                    JSONObject jsonObj1=null;
                    JSONArray array=new JSONArray();
                    jsonObj1=new JSONObject();
                    array.put(new JSONObject().put("nama", fasi));
                    jsonObj1.put("pendidikan", array);

                    Log.d("Data = ", jsonObj1.toString());
                    pendidikan(jsonObj1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void pendidikan(JSONObject datas){
        pDialog.setMessage("Mohon Tunggu .........");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.updatePesantren+ _id, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Pendidikan.this);
                                builder1.setMessage("Ingin menambah Pendidikan ? ");
                                builder1.setCancelable(true);
                                builder1.setPositiveButton(
                                        "Ya",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                edtPendidikan.setText(null);
                                                dialog.cancel();
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "Tidak",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent i = new Intent(Pendidikan.this, Fasilitas.class);
                                                i.putExtra("_id", _id);
                                                startActivity(i);
                                                finish();
                                            }
                                        });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        mRequestQueue.add(req);
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "Data Harus Dilengkapi Dahulu", Toast.LENGTH_LONG).show();
    }

    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}