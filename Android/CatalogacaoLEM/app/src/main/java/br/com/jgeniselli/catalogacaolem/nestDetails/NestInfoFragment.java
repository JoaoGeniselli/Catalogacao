package br.com.jgeniselli.catalogacaolem.nestDetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import br.com.jgeniselli.catalogacaolem.R;
import br.com.jgeniselli.catalogacaolem.common.models.AntNest;

/**
 * Created by jgeniselli on 07/09/17.
 */

@EFragment(R.layout.fragment_nest_info)
public class NestInfoFragment extends Fragment implements OnMapReadyCallback {

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    Toolbar toolbar;

    private Context context;
    private AntNest nest;

    private GoogleMap map;

    public void setup(Context context, AntNest nest) {
        this.context = context;
        this.nest = nest;
    }

    @AfterViews
    public void afterViews() {
        ((Activity)getContext()).setTitle(R.string.title_activity_nest_dashboard);
        ((AppCompatActivity)getContext()).setSupportActionBar(toolbar);

        if (context != null && nest != null) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            NestInfoLineAdapter adapter = new NestInfoLineAdapter(context, nest);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);

            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (nest!= null && nest.getBeginingPoint() != null && nest.getEndingPoint() != null) {
            LatLng beginingPoint;
            LatLng endingPoint;

            beginingPoint = new LatLng(
                    nest.getBeginingPoint().getLatitude(),
                    nest.getBeginingPoint().getLongitude());
            map.addMarker(new MarkerOptions().position(beginingPoint)
                    .title(context.getString(R.string.initial_point)));

            endingPoint = new LatLng(
                    nest.getEndingPoint().getLatitude(),
                    nest.getEndingPoint().getLongitude());
            map.addMarker(new MarkerOptions().position(endingPoint)
                    .title(context.getString(R.string.final_point)));

            Location beginingPosition = new Location("");
            beginingPosition.setLatitude(beginingPoint.latitude);
            beginingPosition.setLongitude(beginingPoint.longitude);

            Location endingPosition = new Location("");
            endingPosition.setLatitude(endingPoint.latitude);
            endingPosition.setLongitude(endingPoint.longitude);

            float distanceInMeters = beginingPosition.distanceTo(endingPosition) * 10;
            ArrayList points = new ArrayList();
            points.add(beginingPoint);
            points.add(endingPoint);
            LatLng centroid = computeCentroid(points);

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(centroid, 15));
        }
    }

    private LatLng computeCentroid(List<LatLng> points) {
        double latitude = 0;
        double longitude = 0;
        int n = points.size();

        for (LatLng point : points) {
            latitude += point.latitude;
            longitude += point.longitude;
        }

        return new LatLng(latitude/n, longitude/n);
    }
}
