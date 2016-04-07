package br.edu.ifce.engcomp.francis.diversidade.fragments;


import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NucleusFragment extends Fragment implements OnMapReadyCallback{

    private static GoogleMap mapPlace;
    public static GoogleMap getMapPlace() { return mapPlace;}

    private MapView mapView;

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

        return view;
    }

    void initMap(){
        mapPlace.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-3.7441914, -38.5380658), 15));

        verifyGPS();

        initMarkers();
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

    public void initMarkers(){
        Marker markerNucleus = mapPlace.addMarker(new MarkerOptions().position(new LatLng(-3.7297003, -38.5398319))
                .title("GRAB - Grupo de Resistência Asa Branca")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        Marker markerEvent = mapPlace.addMarker(new MarkerOptions().position(new LatLng(-3.7441914, -38.5380658))
                .title("Lançamento do app Diversidade - 15/05/2016")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
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
}
