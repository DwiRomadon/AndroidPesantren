package com.dwiromadon.gispesantren.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dwiromadon.gispesantren.R;
import com.dwiromadon.gispesantren.adapter.AdapterPesantren;
import com.dwiromadon.gispesantren.model.ModelPesantren;
import com.dwiromadon.gispesantren.server.BaseURL;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DataPesantrenAdmin extends AppCompatActivity
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    ProgressDialog pDialog;

    AdapterPesantren adapter;
    ListView list;

    ArrayList<ModelPesantren> newsList = new ArrayList<ModelPesantren>();
    private RequestQueue mRequestQueue;

    int socketTimeout = 500000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pesantren_admin);

        getSupportActionBar().setTitle("Data Pesantren");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterPesantren(DataPesantrenAdmin.this, newsList);
        list.setAdapter(adapter);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); //
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DataPesantrenAdmin.this, HomeAdmin.class);
        startActivity(i);
        finish();
    }

    public void getAllPet(JSONObject jsonObject) {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.getJarak + "0", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelPesantren pesantren = new ModelPesantren();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaPesantren = jsonObject.getString("namaPesantren");
                                    final String notelp = jsonObject.getString("nomorTelp");
                                    final String arrGambar = jsonObject.getString("gambar");
                                    final String akreditasi = jsonObject.getString("akreditasi");
                                    final String lat = jsonObject.getString("latitude");
                                    final String lon = jsonObject.getString("longitude");
                                    final String jarak = jsonObject.getString("jarak");
                                    final String nomorNspp = jsonObject.getString("nomorNspp");
                                    final String website = jsonObject.getString("website");
                                    final String fasilitas = jsonObject.getString("fasilitas");
                                    final String gambarIcon = jsonObject.getString("gambar_icon");
                                    final String profile = jsonObject.getString("profile");
                                    final String info = jsonObject.getString("info");
                                    final String ekskul = jsonObject.getString("ekskul");
                                    final String pendidikan = jsonObject.getString("pendidikan");
                                    final String pemilik = jsonObject.getString("pemilik");

                                    JSONObject jobjJarak = new JSONObject(jarak);
                                    JSONArray arrayGambar = new JSONArray(arrGambar);
                                    String gambar = arrayGambar.get(0).toString();
                                    String jarakDistance = jobjJarak.getString("distance");
                                    String destination = jobjJarak.getString("destination");
                                    String duration = jobjJarak.getString("duration");

                                    pesantren.setDuration(duration);
                                    pesantren.setNamaPesantren(namaPesantren);
                                    pesantren.setAlamat(destination);
                                    pesantren.setNomorTelp(notelp);
                                    pesantren.setGambar(gambar);
                                    pesantren.setArrGambar(arrGambar);
                                    pesantren.set_id(_id);
                                    pesantren.setLati(lat);
                                    pesantren.setLongi(lon);
                                    pesantren.setJarak(jarakDistance);
                                    pesantren.setFasilitas(fasilitas);
                                    pesantren.setGambarIcon(gambarIcon);
                                    pesantren.setProfile(profile);
                                    pesantren.setInfo(info);
                                    pesantren.setEkskul(ekskul);
                                    pesantren.setPendidikan(pendidikan);
                                    pesantren.setWebsite(website);
                                    pesantren.setAkreditasi(akreditasi);
                                    pesantren.setNoNspp(nomorNspp);
                                    pesantren.setPemilik(pemilik);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                           Intent a = new Intent(DataPesantrenAdmin.this, EditHapusData.class);
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("gambar", newsList.get(position).getArrGambar());
                                            a.putExtra("gambar_icon", newsList.get(position).getGambarIcon());
                                            a.putExtra("namaPesantren", newsList.get(position).getNamaPesantren());
                                            a.putExtra("alamat", newsList.get(position).getAlamat());
                                            a.putExtra("website", newsList.get(position).getWebsite());
                                            a.putExtra("noTelp", newsList.get(position).getNomorTelp());
                                            a.putExtra("profile", newsList.get(position).getProfile());
                                            a.putExtra("info", newsList.get(position).getInfo());
                                            a.putExtra("pendidikan", newsList.get(position).getPendidikan());
                                            a.putExtra("ekskul", newsList.get(position).getEkskul());
                                            a.putExtra("fasilitas", newsList.get(position).getFasilitas());
                                            a.putExtra("latitude", newsList.get(position).getLati());
                                            a.putExtra("longitude", newsList.get(position).getLongi());
                                            a.putExtra("pemilik", newsList.get(position).getPemilik());
                                            a.putExtra("akreditasi", newsList.get(position).getAkreditasi());
                                            a.putExtra("nomorNspp", newsList.get(position).getNoNspp());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(pesantren);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        req.setRetryPolicy(policy);
        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    /**
     * If connected get lat and long
     *
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            Log.d("Lat Lon = ", String.valueOf(currentLatitude) + " " + String.valueOf(currentLongitude));

            try {
                newsList.clear();
                JSONObject jsonObj1=null;
                jsonObj1=new JSONObject();
                jsonObj1.put("latitude", String.valueOf(currentLatitude));
                jsonObj1.put("longitude", String.valueOf(currentLongitude));

                Log.d("Data = ", jsonObj1.toString());
                getAllPet(jsonObj1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }
}