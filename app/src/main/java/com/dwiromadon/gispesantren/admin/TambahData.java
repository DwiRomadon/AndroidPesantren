package com.dwiromadon.gispesantren.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dwiromadon.gispesantren.R;
import com.dwiromadon.gispesantren.server.BaseURL;
import com.dwiromadon.gispesantren.server.VolleyMultipart;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TambahData extends AppCompatActivity {

    EditText edtNamaPesantren, edtNomorTelp, edtAkreditasi, edtNoNspp, edtWebsite, edtLat, edtLon,
            edtProfil, edtInfo, edtEkskul, edtNamaPemilik, edtPendidikan, edtFasilitas;


    Button takeImg1, btnSubmit;
    ImageView imgChoose1;

    Bitmap bitmap1;
    private File destination1 = null;
    private InputStream inputStreamImg;
    private String imgPath1 = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    private RequestQueue mRequestQueue;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        getSupportActionBar().hide();


        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        takeImg1 = (Button) findViewById(R.id.btnTakeImage1);
        imgChoose1 = (ImageView) findViewById(R.id.gambar1);

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

        takeImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImage();
            }
        });

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaPesantren    = edtNamaPesantren.getText().toString();
                String noTelp           = edtNomorTelp.getText().toString();
                String lati             = edtLat.getText().toString();
                String longit           = edtLon.getText().toString();
                String akreditasi       = edtAkreditasi.getText().toString();
                String noNspp           = edtNoNspp.getText().toString();
                String web              = edtWebsite.getText().toString();
                String info             = edtInfo.getText().toString();
                String ekskull          = edtEkskul.getText().toString();
                String profile          = edtProfil.getText().toString();
                String pemilik          = edtNamaPemilik.getText().toString();
                String pendidikan       = edtPendidikan.getText().toString();
                String fasilitas        = edtFasilitas.getText().toString();

                if (namaPesantren.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Pesantren tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (pemilik.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Pemilik tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (noTelp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "No telphone tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (akreditasi.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Akreditasi tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (noNspp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nomor NSPP tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (pendidikan.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Pendidikan tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (fasilitas.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fasilitas tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (profile.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Profile tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (info.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Info tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (ekskull.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ekskul tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (web.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Alamat Website tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (lati.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Kordinat latitude tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if (longit.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Kordinat longitude tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {
                    inputData(bitmap1, namaPesantren, noTelp, akreditasi, noNspp, web,
                            lati, longit, profile, info, ekskull, pemilik, pendidikan, fasilitas);
                }
            }
        });
    }

    public void takeImage(){
        try {
            addPermission();
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahData.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap1 = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination1 = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination1.createNewFile();
                    fo = new FileOutputStream(destination1);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath1 = destination1.getAbsolutePath();
                imgChoose1.setImageBitmap(bitmap1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                imgPath1 = getRealPathFromURI(selectedImage);
                destination1 = new File(imgPath1.toString());
                imgChoose1.setImageBitmap(bitmap1);
                if (imgChoose1.getDrawable() != null) {
                    int newHeight = 300; // New height in pixels
                    int newWidth = 300;
                    imgChoose1.requestLayout();
                    imgChoose1.getLayoutParams().height = newHeight;
                    // Apply the new width for ImageView programmatically
                    imgChoose1.getLayoutParams().width = newWidth;
                    // Set the scale type for ImageView image scaling
                    imgChoose1.setScaleType(ImageView.ScaleType.FIT_XY);
                    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) imgChoose1.getLayoutParams();
                    marginParams.setMargins(0, 10, 0, 0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addPermission() {
        Dexter.withActivity(TambahData.this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(TambahData.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public byte[] getFileDataFromDrawable1(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void inputData(final Bitmap gmbr1, final String namaPes, final String noTep,
                           final String akreditasi, final String noNspp, final String web, final String lat,
                           final String longi, final String profile, final String info,
                           final String ekskul, final String pemilik, final String pendidikan, final String fasilitas) {
        pDialog.setMessage("Mohon Tunggu .........");
        showDialog();
        VolleyMultipart volleyMultipartRequest = new VolleyMultipart(Request.Method.POST, BaseURL.inputPesantren,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        mRequestQueue.getCache().clear();
                        hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            System.out.println("ress = " + jsonObject.toString());
                            String strMsg = jsonObject.getString("msg");
                            String id = jsonObject.getString("id");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), "Berhasil menginput data", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(TambahData.this, TambahLogo.class);
                                i.putExtra("_id", id);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(TambahData.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("namaPesantren", namaPes);
                params.put("nomorTelp", noTep);
                params.put("latitude", lat);
                params.put("longitude", longi);
                params.put("akreditasi", akreditasi);
                params.put("nomorNspp", noNspp);
                params.put("website", web);
                params.put("profile", profile);
                params.put("info", info);
                params.put("ekskul", ekskul);
                params.put("pemilik", pemilik);
                params.put("pendidikan", pendidikan);
                params.put("fasilitas", fasilitas);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename1 = System.currentTimeMillis();
                params.put("gambar", new VolleyMultipart.DataPart(imagename1 + ".jpg", getFileDataFromDrawable1(gmbr1)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue = Volley.newRequestQueue(TambahData.this);
        mRequestQueue.add(volleyMultipartRequest);
    }


    @Override
    public void onBackPressed(){
        Intent i = new Intent(TambahData.this, HomeAdmin.class);
        startActivity(i);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}