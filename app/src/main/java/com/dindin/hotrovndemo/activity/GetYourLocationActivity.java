package com.dindin.hotrovndemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.dindin.hotrovndemo.R;
import com.dindin.hotrovndemo.databinding.ActivityGetYourLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GetYourLocationActivity extends AppCompatActivity implements OnMapReadyCallback{
    ActivityGetYourLocationBinding binding;

    FusedLocationProviderClient fusedLocationProviderClient;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_your_location);
        binding.btnBack.setOnClickListener(v -> finish());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(GetYourLocationActivity.this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

    }

    private void getMyLocation(GoogleMap googleMap) {
        if(googleMap == null) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(GetYourLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(GetYourLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Toast.makeText(getBaseContext(), "Successful", Toast.LENGTH_LONG).show();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    binding.btnDone.setOnClickListener(v -> {
                        Intent intent = new Intent(GetYourLocationActivity.this, CreateReliefNewsletterActivity.class);
                        intent.putExtra("lat", location.getLatitude());
                        intent.putExtra("long", location.getLongitude());
                        setResult(RESULT_OK, intent);
                        finish();
                    });

                    Drawable drawable = getResources().getDrawable(R.drawable.ic_pin_green);
                    googleMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromDrawable(drawable))));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(13).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        });
    }

    private Bitmap getMarkerBitmapFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getMyLocation(googleMap);
    }
}