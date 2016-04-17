package br.edu.ifce.engcomp.francis.diversidade.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.activities.DetailNucleusActivity;
import br.edu.ifce.engcomp.francis.diversidade.miscellaneous.NucleusOperations;
import br.edu.ifce.engcomp.francis.diversidade.model.AddressNucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.HourNucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.Nucleus;

/**
 * A simple {@link Fragment} subclass.
 */
public class NucleusFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mapPlace;
    private MapView mapView;
    public NucleusOperations nucleusOperations = new NucleusOperations();
    Nucleus teste1 = new Nucleus();

    LocationListener listenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

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
    };


    public NucleusFragment() {
        // Required empty public constructor
    }

    public void initTest() {
        teste1.setName("GRAB");
        teste1.setAddress(new AddressNucleus("R. Teresa Cristina", "1050", "Centro", "Fortaleza", "CE", "Brasil", "60015-141",
                -3.7297003, -38.5398319));
        teste1.setHour(new HourNucleus("Segunda-Sexta", "8h-18h"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nucleus, container, false);

        mapView = (MapView) view.findViewById(R.id.map_nucleus);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        mapPlace = mapView.getMap();
        mapPlace.getUiSettings().setMyLocationButtonEnabled(true);
        mapPlace.getUiSettings().setMapToolbarEnabled(true);

        MapsInitializer.initialize(this.getActivity());

        initMap();
        initTest();

        return view;
    }

    void initMap(){
        mapPlace.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-3.7441914, -38.5380658), 15));

        verifyGPS();
        nucleusOperations.initMarkers(mapPlace);
        eventMarkers();
    }

    private void verifyGPS(){
        long timeUpdate = 3000;
        float distance = 0;

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeUpdate, distance, listenerGPS);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeUpdate, distance, listenerGPS);

        Location locationUser = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(locationUser == null){
            Toast.makeText(getActivity(), "Verifique a conexão do GPS!", Toast.LENGTH_LONG).show();

        }
    }

    private void eventMarkers() {
        mapPlace.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            private int tap = 0;
            private LatLng position_1 = new LatLng(0.0, 1.1);
            private LatLng position_2 = new LatLng(1.1, 0.0);

            @Override
            public boolean onMarkerClick(Marker marker) {
                tap = tap + 1;

                if (tap % 2 == 1 && !(position_1.equals(position_2))) {
                    position_1 = marker.getPosition();
                } else {
                    position_2 = marker.getPosition();
                    if (position_1.equals(position_2)) {
                        Intent intent = new Intent(getActivity(), DetailNucleusActivity.class);
                        intent.putExtra("INFOS_NUCLEUS", teste1);
                        startActivity(intent);
                    } else {
                        position_1 = marker.getPosition();
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
