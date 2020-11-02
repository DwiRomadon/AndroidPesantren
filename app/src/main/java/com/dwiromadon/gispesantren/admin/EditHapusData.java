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

public class EditHapusData extends AppCompatActivity {

    Intent i;
    String arrGambar, iconGambar, namaPonpes, alamat, pemilik, noTelp, website, profile,
            info, pendidikan, ekskul, fasilitas, lat, longi, akreditasi, nomorNspp, _id;

    EditText edtNamaPesantren, edtNomorTelp, edtAkreditasi, edtNoNspp, edtWebsite, edtLat, edtLon,
            edtProfil, edtInfo, edtEkskul, edtNamaPemilik, edtPendidikan, edtFasilitas;

    Button btnUbahData, editGambar, btnHapus;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hapus_data);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        i = getIntent();
        arrGambar = i.getStringExtra("gambar");
        iconGambar = i.getStringExtra("gambar_icon");
        namaPonpes = i.getStringExtra("namaPesantren");
        alamat = i.getStringExtra("alamat");
        pemilik = i.getStringExtra("pemilik");
        noTelp = i.getStringExtra("noTelp");
        website = i.getStringExtra("website");
        profile = i.getStringExtra("profile");
        info = i.getStringExtra("info");
        pendidikan = i.getStringExtra("pendidikan");
        ekskul = i.getStringExtra("ekskul");
        fasilitas = i.getStringExtra("fasilitas");
        lat = i.getStringExtra("latitude");
        longi = i.getStringExtra("longitude");
        akreditasi = i.getStringExtra("akreditasi");
        nomorNspp = i.getStringExtra("nomorNspp");
        _id = i.getStringExtra("_id");

        edtNamaPesantren = (EditText) findViewById(R.id.et_namaPesantre);
        edtNomorTelp = (EditText) findViewById(R.id.et_notelp);
        edtAkreditasi = (EditText) findViewById(R.id.et_akreditasi);
        edtNoNspp = (EditText) findViewById(R.id.et_nonspp);
        edtWebsite = (EditText) findViewById(R.id.et_website);
        edtLat = (EditText) findViewById(R.id.et_lat);
        edtLon = (EditText) findViewById(R.id.et_longi);
        edtInfo = (EditText) findViewById(R.id.edt_info);
        edtProfil = (EditText) findViewById(R.id.et_profile);
        edtEkskul = (EditText) findViewById(R.id.edt_ekskul);
        edtNamaPemilik = (EditText) findViewById(R.id.et_namaPemilik);
        edtPendidikan = (EditText) findViewById(R.id.et_pendidikan);
        edtFasilitas = (EditText) findViewById(R.id.et_fasilitas);

        edtNamaPesantren.setText(namaPonpes);
        edtNomorTelp.setText(noTelp);
        edtAkreditasi.setText(akreditasi);
        edtNamaPemilik.setText(pemilik);
        edtNoNspp.setText(nomorNspp);
        edtWebsite.setText(website);
        edtLat.setText(lat);
        edtLon.setText(longi);
        edtInfo.setText(info);
        edtProfil.setText(profile);
        edtEkskul.setText(ekskul);
        edtPendidikan.setText(pendidikan);
        edtFasilitas.setText(fasilitas);

        btnUbahData = (Button) findViewById(R.id.btnUbahData);
        editGambar = (Button) findViewById(R.id.editGambar);
        btnHapus = (Button) findViewById(R.id.btnHapus);


        btnUbahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNamaPesantren    = edtNamaPesantren.getText().toString();
                String strNoTelp           = edtNomorTelp.getText().toString();
                String strLati             = edtLat.getText().toString();
                String strLongit           = edtLon.getText().toString();
                String strAkreditasi       = edtAkreditasi.getText().toString();
                String strNoNspp           = edtNoNspp.getText().toString();
                String strWeb              = edtWebsite.getText().toString();
                String strInfo             = edtInfo.getText().toString();
                String strEkskull          = edtEkskul.getText().toString();
                String strProfile          = edtProfil.getText().toString();
                String strPemilik          = edtNamaPemilik.getText().toString();
                String strPendidikan       = edtPendidikan.getText().toString();
                String strFasilitas        = edtFasilitas.getText().toString();

                if (strNamaPesantren.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Pesantren tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strPemilik.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Pemilik tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (strNoTelp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "No telphone tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strAkreditasi.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Akreditasi tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strNoNspp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nomor NSPP tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strPendidikan.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Pendidikan tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strFasilitas.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fasilitas tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strProfile.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Profile tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strInfo.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Info tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strEkskull.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ekskul tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strWeb.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Alamat Website tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (strLati.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Kordinat latitude tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (strLongit.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Kordinat longitude tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {
                    try {

                        JSONObject params=null;
                        JSONArray array=new JSONArray();
                        params  =new JSONObject();
                        params.put("namaPesantren", strNamaPesantren);
                        params.put("nomorTelp", strNoTelp);
                        params.put("latitude", strLati);
                        params.put("longitude", strLongit);
                        params.put("akreditasi", strAkreditasi);
                        params.put("nomorNspp", strNoNspp);
                        params.put("website", strWeb);
                        params.put("profile", strProfile);
                        params.put("info", strInfo);
                        params.put("ekskul", strEkskull);
                        params.put("pemilik", strPemilik);
                        params.put("pendidikan", strPendidikan);
                        params.put("fasilitas", strFasilitas);
                        Log.d("Data = ", params.toString());
                        ubahData(params);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditHapusData.this);
                builder1.setMessage("Ingin Menghapus Pesantren " + namaPonpes + " ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                hapusData();
                            }
                        });

                builder1.setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        editGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(EditHapusData.this, EditGambar.class);
                a.putExtra("_id", _id);
                a.putExtra("gambar", arrGambar);
                a.putExtra("gambar_icon", iconGambar);
                startActivity(a);
            }
        });

    }

    public void ubahData(JSONObject datas){
        pDialog.setMessage("Mohon Tunggu .........");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.ubahDataPesantren+ _id, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
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

    public void hapusData(){
        pDialog.setMessage("Mohon Tunggu .........");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, BaseURL.hapusPesantren+ _id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent a = new Intent(EditHapusData.this, DataPesantrenAdmin.class);
                                startActivity(a);
                                finish();
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