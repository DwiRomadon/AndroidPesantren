package com.dwiromadon.gispesantren.pengguna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dwiromadon.gispesantren.R;
import com.dwiromadon.gispesantren.server.BaseURL;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.vistrav.pop.Pop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailPonpes extends AppCompatActivity {

    Intent i, mapIntent;
    String arrGambar, iconGambar, namaPonpes, alamat, pemilik, noTelp, website, profile, info, pendidikan, ekskul, fasilitas, lat, longi;

    ArrayList gam = new ArrayList();

    CarouselView carouselView;
    CircleImageView imageIcon;

    TextView txtNamaPonpes, txtAlamat, txtPemilik, txtNotelp, txtWebsite, txtJudulContent, txtContent;
    Button btnProfileVisible, btnInfoVisible, btnPendidikanVisible, btnEkskull, btnFasilitas;

    private MapView mapView;

    String goolgeMap = "com.google.android.apps.maps"; // identitas package aplikasi google masps android
    Uri gmmIntentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiZHdpcjQiLCJhIjoiY2syeGZhOG0zMGFjMjNuczg1MXJ1M3ptayJ9.m4xcIOu0VAbXgcmXqHnWDQ");
        setContentView(R.layout.activity_detail_ponpes);
        getSupportActionBar().hide();

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

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        imageIcon    = (CircleImageView) findViewById(R.id.imageIcon);

        txtNamaPonpes   = (TextView) findViewById(R.id.txtNamaPesantren);
        txtAlamat       = (TextView) findViewById(R.id.txtAlamat);
        txtPemilik      = (TextView) findViewById(R.id.txtNamaPemilik);
        txtNotelp       = (TextView) findViewById(R.id.txtNomorTelp);
        txtWebsite      = (TextView) findViewById(R.id.txtWebsite);
        txtJudulContent = (TextView) findViewById(R.id.txtJudulContent);
        txtContent      = (TextView) findViewById(R.id.txtContent);

        btnProfileVisible       = (Button) findViewById(R.id.btnProfileVisible);
        btnInfoVisible          = (Button) findViewById(R.id.btnInfoVisible);
        btnPendidikanVisible    = (Button) findViewById(R.id.btnPendidikanVisible);
        btnEkskull              = (Button) findViewById(R.id.btnEkskull);
        btnFasilitas            = (Button) findViewById(R.id.btnFasilitas);

        Picasso.get().load(BaseURL.baseUrl + "gambar/" + iconGambar).fit().centerCrop().into(imageIcon);

        txtNamaPonpes.setText(namaPonpes);
        txtAlamat.setText(alamat);
        txtPemilik.setText(pemilik);
        txtNotelp.setText(noTelp);
        txtWebsite.setText(website);


        btnProfileVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnProfileVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.colorPrimary));
                btnProfileVisible.setTextColor(getResources().getColor(R.color.md_white_1000));

                btnInfoVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnInfoVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnPendidikanVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnPendidikanVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnEkskull.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnEkskull.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnFasilitas.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnFasilitas.setTextColor(getResources().getColor(R.color.colorPrimary));

                txtJudulContent.setText("Profil");
                txtContent.setText(profile);
            }
        });

        btnInfoVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnInfoVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.colorPrimary));
                btnInfoVisible.setTextColor(getResources().getColor(R.color.md_white_1000));

                btnProfileVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnProfileVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnPendidikanVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnPendidikanVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnEkskull.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnEkskull.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnFasilitas.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnFasilitas.setTextColor(getResources().getColor(R.color.colorPrimary));

                txtJudulContent.setText("Info Unggulan");
                txtContent.setText(info);
            }
        });

        btnPendidikanVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPendidikanVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.colorPrimary));
                btnPendidikanVisible.setTextColor(getResources().getColor(R.color.md_white_1000));

                btnProfileVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnProfileVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnInfoVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnInfoVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnEkskull.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnEkskull.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnFasilitas.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnFasilitas.setTextColor(getResources().getColor(R.color.colorPrimary));

                txtJudulContent.setText("Jenjang Pendidikan");
                txtContent.setText(pendidikan);
            }
        });

        btnEkskull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEkskull.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.colorPrimary));
                btnEkskull.setTextColor(getResources().getColor(R.color.md_white_1000));

                btnProfileVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnProfileVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnInfoVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnInfoVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnPendidikanVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnPendidikanVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnFasilitas.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnFasilitas.setTextColor(getResources().getColor(R.color.colorPrimary));

                txtJudulContent.setText("Ekstrakulikuler");
                txtContent.setText(ekskul);
            }
        });

        btnFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFasilitas.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.colorPrimary));
                btnFasilitas.setTextColor(getResources().getColor(R.color.md_white_1000));

                btnProfileVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnProfileVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnInfoVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnInfoVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnPendidikanVisible.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnPendidikanVisible.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnEkskull.setBackgroundColor(ContextCompat.getColor(DetailPonpes.this, R.color.md_white_1000));
                btnEkskull.setTextColor(getResources().getColor(R.color.colorPrimary));

                txtJudulContent.setText("Fasilitas");
                txtContent.setText(fasilitas);
            }
        });

        try {
            JSONArray arrayGambar = new JSONArray(arrGambar);
            for (int i = 0; i < arrayGambar.length(); i++) {
                gam.add(BaseURL.baseUrl + "gambar/" + arrayGambar.get(i).toString());
            }
            carouselView.setPageCount(gam.size());
            carouselView.setImageListener(imageListener);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final double lati = Double.parseDouble(lat);
        final double lon = Double.parseDouble(longi);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                        MarkerOptions marker = new MarkerOptions();
                        marker.setTitle(namaPonpes);
                        marker.setSnippet(alamat);
                        IconFactory iconFactory = IconFactory.getInstance(DetailPonpes.this);
                        Icon icon = iconFactory.fromResource((R.drawable.ic_pin));
                        marker.icon(icon);
                        marker.position(new LatLng(lati, lon));
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(lati, lon))
                                .zoom(13)
                                .tilt(20)
                                .build();
                        mapboxMap.addMarker(marker);
                        mapboxMap.setCameraPosition(position);

                        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                marker.hideInfoWindow();
                                double dlat =marker.getPosition().getLatitude();
                                double dlon =marker.getPosition().getLongitude();
                                final String alamat = marker.getSnippet();
                                final String nama = marker.getTitle();
                                final String slat = String.valueOf(dlat);
                                final String slon = String.valueOf(dlon);
                                Pop.on(DetailPonpes.this)
                                        .with()
                                        .title("Informasi")
                                        .cancelable(false)
                                        .layout(R.layout.detail_maps)
                                        .when(new Pop.Yah() {
                                            @Override
                                            public void clicked(DialogInterface dialog, View view) {

                                            }
                                        })
                                        .show(new Pop.View() { // assign value to view element
                                            @Override
                                            public void prepare(View view) {
                                                EditText edtNamPetsHop = (EditText) view.findViewById(R.id.edtPetshop);
                                                EditText edAlamat = (EditText) view.findViewById(R.id.edtAlamat);
                                                edtNamPetsHop.setText(nama);
                                                edAlamat.setText(alamat);

                                                Button goRoutes = (Button) view.findViewById(R.id.goRoutes);
                                                goRoutes.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        gmmIntentUri = Uri.parse("google.navigation:q=" + slat+","+slon);

                                                        // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
                                                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                                                        // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
                                                        mapIntent.setPackage(goolgeMap);

                                                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                            startActivity(mapIntent);
                                                        } else {
                                                            Toast.makeText(DetailPonpes.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                                                                    Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                return false;
                            }
                        });

                    }
                });

            }
        });

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Picasso.get().load(String.valueOf(gam.get(position))).fit().centerCrop().into(imageView);
            //imageView.setImageResource(sampleImages[position]);
        }
    };

    View.OnClickListener pauseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            carouselView.pauseCarousel();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
