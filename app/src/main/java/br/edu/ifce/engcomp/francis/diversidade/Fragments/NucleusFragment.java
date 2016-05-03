package br.edu.ifce.engcomp.francis.diversidade.Fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.activities.DetailNucleusActivity;
import br.edu.ifce.engcomp.francis.diversidade.miscellaneous.NucleusOperations;
import br.edu.ifce.engcomp.francis.diversidade.model.AddressNucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.HourNucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.Nucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.Service;
import br.edu.ifce.engcomp.francis.diversidade.model.TextBlog;

/**
 * A simple {@link Fragment} subclass.
 */
public class NucleusFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mapPlace;
    private MapView mapView;
    public NucleusOperations nucleusOperations = new NucleusOperations();
    public HashMap<Integer, Nucleus> nucleusHashMap = new HashMap<>();
    ProgressDialog progressDialog;

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


    //Requisitar núcleos do estado
    public void doRequest(String estado){
        ArrayList<Nucleus> nucleusArrayList = new ArrayList<>();
        // Instantiate the RequestQueue.
        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());
        String url ="http://diversidade-cloudsocial.rhcloud.com/api/v1/centers?state=";
        String urlSigla = "";
        String urlNucleusState = "";

        urlSigla = nucleusOperations.choseState(estado);

        urlNucleusState = url + urlSigla;

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Carregando...");
        progressDialog.show();

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlNucleusState, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("RESPONSE", response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String address =  jsonObject.getString("address");
                        double latitude = jsonObject.optDouble("latitude");
                        double longitude = jsonObject.optDouble("longitude");
                        JSONArray hours =  jsonObject.getJSONArray("operating_hours");
                        JSONArray services = jsonObject.getJSONArray("services");

                        nucleusHashMap.put(i, new Nucleus(id, name, latitude, longitude));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                progressDialog.dismiss();
                nucleusOperations.initMarkers(mapPlace, nucleusHashMap, getActivity());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Perdão, o servidor não respondeu!", Toast.LENGTH_SHORT).show();
                Log.i("RESPONSE", error.getCause().toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
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
        //nucleusOperations.initMarkers(mapPlace, nucleusHashMap, getActivity());
        //eventMarkers();
    }

    private void verifyGPS(){
        long timeUpdate = 0;
        float distance = 0;
        LatLng myPosition = null;

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeUpdate, distance, listenerGPS);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeUpdate, distance, listenerGPS);

        Location locationUser = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationUser2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(locationUser!=null){
            myPosition = new LatLng((float) locationUser.getLatitude(), (float) locationUser.getLongitude());
        }
        else if(locationUser2!=null){
            myPosition = new LatLng((float) locationUser2.getLatitude(), (float) locationUser2.getLongitude());
        }
        else{
            Toast.makeText(getActivity(), "Verifique a conexão do GPS!", Toast.LENGTH_LONG).show();
        }

        if(myPosition!=null){
            Geocoder geocoderCity = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses;
            String state = null;

            try {
                addresses = geocoderCity.getFromLocation(myPosition.latitude, myPosition.longitude, 1);
                if (addresses.size() > 0) {
                    state = addresses.get(0).getAdminArea();

                    if (state!=null){
                        doRequest(state);
                    }
                    else {
                        Toast.makeText(getActivity(), "Verifique a conexão do GPS!", Toast.LENGTH_LONG);
                    }

                }
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }else {
            Toast.makeText(getActivity(), "Verifique a conexão do GPS!", Toast.LENGTH_LONG);
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
                        //intent.putExtra("INFOS_NUCLEUS", teste1);
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
