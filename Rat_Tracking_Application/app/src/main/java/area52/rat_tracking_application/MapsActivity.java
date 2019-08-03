package area52.rat_tracking_application;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.RatReportManager;
import area52.rat_tracking_application.model.ReportLocation;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at New York City location and move the camera
        LatLng NY = new LatLng(40.67, -73.94);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(NY));
        getRatReportFilteredList();
    }

    /**
     * A method to get and filter out the rat reports based on date
     *
     *
     */
    private void getRatReportFilteredList() {
        Date start= (Date)getIntent().getSerializableExtra("start");
        Date end = (Date)getIntent().getSerializableExtra("end");
        List<RatReport> rawReports = (ArrayList<RatReport>) RatReportManager.getInstance().getRatReportList();

        for (int i = 0; i < rawReports.size(); i++) {
            Date creationDate = rawReports.get(i).getCreationDate();
            if (creationDate.after(start)
                    && creationDate.before(end)) {
                ReportLocation loc = rawReports.get(i).getLocation();
                LatLng reportLoc = new LatLng(loc.getLatitude(), loc.getLongitude());
                mMap.addMarker(new MarkerOptions().position(reportLoc)
                        .title(rawReports.get(i).detailedReportString()));
            }
        }
    }
}
