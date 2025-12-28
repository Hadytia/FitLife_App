package com.example.fitlife_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GettingStartedActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 100;

    private FusedLocationProviderClient fusedLocationClient;
    private TextView tvWelcome;
    private TextView tvLocation;
    private Button btnGetStarted;
    private ProgressBar progressBar;

    private String subDistrict = "";
    private String district = "";
    private String city = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        // Initialize views
        tvWelcome = findViewById(R.id.tv_welcome);
        tvLocation = findViewById(R.id.tv_location);
        btnGetStarted = findViewById(R.id.btn_get_started);
        progressBar = findViewById(R.id.progress_bar);

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Set welcome text
        tvWelcome.setText("Welcome to FitLife!");
        tvLocation.setText("Detecting your location...");
        btnGetStarted.setEnabled(false);

        // Check and request location permission
        if (checkLocationPermission()) {
            getLocation();
        } else {
            requestLocationPermission();
        }

        // Get Started button
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save location data and proceed to splash screen
                Intent intent = new Intent(GettingStartedActivity.this, SplashActivity.class);
                intent.putExtra("SUB_DISTRICT", subDistrict);
                intent.putExtra("DISTRICT", district);
                intent.putExtra("CITY", city);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST);
    }

    private void getLocation() {
        progressBar.setVisibility(View.VISIBLE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        progressBar.setVisibility(View.GONE);

                        if (location != null) {
                            // Get address from coordinates
                            getAddressFromLocation(location.getLatitude(), location.getLongitude());
                        } else {
                            // Location not available
                            tvLocation.setText("Location not available.\nPlease enable GPS and try again.");
                            btnGetStarted.setEnabled(true);
                            btnGetStarted.setText("Continue Anyway");
                        }
                    }
                });
    }

    private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                // Get sub-district (kecamatan)
                subDistrict = address.getSubLocality(); // Kecamatan
                if (subDistrict == null) subDistrict = "";

                // Get district (kabupaten/kota)
                district = address.getSubAdminArea(); // Kabupaten
                if (district == null) district = "";

                // Get city
                city = address.getLocality(); // Kota
                if (city == null) city = address.getAdminArea(); // Province as fallback
                if (city == null) city = "";

                // Display location
                String locationText = "";
                if (!subDistrict.isEmpty()) {
                    locationText = "📍 " + subDistrict;
                    if (!city.isEmpty()) {
                        locationText += ", " + city;
                    }
                } else if (!city.isEmpty()) {
                    locationText = "📍 " + city;
                } else {
                    locationText = "📍 Location detected";
                }

                tvLocation.setText(locationText);
                btnGetStarted.setEnabled(true);
                btnGetStarted.setText("Get Started");

            } else {
                tvLocation.setText("Unable to get address.\nBut you can continue.");
                btnGetStarted.setEnabled(true);
                btnGetStarted.setText("Get Started");
            }

        } catch (IOException e) {
            e.printStackTrace();
            tvLocation.setText("Error getting location.\nBut you can continue.");
            btnGetStarted.setEnabled(true);
            btnGetStarted.setText("Get Started");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                getLocation();
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied. Some features may not work.",
                        Toast.LENGTH_LONG).show();
                tvLocation.setText("Location permission denied.\nYou can still continue.");
                btnGetStarted.setEnabled(true);
                btnGetStarted.setText("Continue Anyway");
            }
        }
    }
}