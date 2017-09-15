package br.com.jgeniselli.catalogacaolem.common.form.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.com.jgeniselli.catalogacaolem.R;

@EActivity(R.layout.activity_coordinates_selection)
public class CoordinatesSelectionActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, LocationListener {

    private static final long UPDATE_INTERVAL = 1000;

    @ViewById
    TextView positionTextView;

    private final float MINIMAL_ACCURACY = (float) 15.0;
    private GoogleMap mMap;
    private boolean markerAdded = false;
    private LatLng latLng;
    private LatLng serviceLatLng;

    private LocationManager locationManager;
    private String provider;

    @AfterViews
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        startUpdatingLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerDragListener(this);

        if (latLng != null) {
            addMarker(googleMap, latLng);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_reset, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_reset: {
                if (locationManager != null) {
                    setLatLng(serviceLatLng);
                }
                break;
            }
            case R.id.action_save: {
                pushPositionToPreviousActivity(latLng);
                break;
            }
            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        setLatLng(marker.getPosition());
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    private void addMarker(GoogleMap map, LatLng position) {
        if (!markerAdded) {
            markerAdded = true;
            map.addMarker(new MarkerOptions().position(position).draggable(true));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 100));
        }
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;

        if (latLng != null) {
            positionTextView.setText(String.format("Lat: %02.12f\nLong: %02.12f", latLng.latitude, latLng.longitude));

            if (mMap != null) {
                addMarker(mMap, latLng);
            }
        } else {
            positionTextView.setText("");
        }
    }

    private void pushPositionToPreviousActivity(LatLng position) {
        Intent intent = new Intent();
        intent.putExtra("latitude", position.latitude);
        intent.putExtra("longitude", position.longitude);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.location_alert, Toast.LENGTH_LONG).show();
                return;
            }
        }
        startUpdatingLocation();
    }

    public void startUpdatingLocation() {
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        locationManager.requestLocationUpdates(provider, 1000, 15, this);

        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
        if (lastKnownLocation != null) {
            setLatLng(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
            serviceLatLng = latLng;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.getAccuracy() <= MINIMAL_ACCURACY && latLng == null) {
            setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
            serviceLatLng = latLng;
        } else {
            serviceLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
