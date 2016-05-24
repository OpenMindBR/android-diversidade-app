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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.Map;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.activities.DetailNucleusActivity;
import br.edu.ifce.engcomp.francis.diversidade.miscellaneous.NucleusOperations;
import br.edu.ifce.engcomp.francis.diversidade.model.AddressNucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.HourNucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.Nucleus;
import br.edu.ifce.engcomp.francis.diversidade.model.Service;

/**
 * A simple {@link Fragment} subclass.
 */
public class NucleusFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mapPlace;
    private MapView mapView;
    public NucleusOperations nucleusOperations = new NucleusOperations();
    Map<Integer, Nucleus> nucleusMap = new HashMap<>();
    private Map<String,Integer> mapNucleusMarker = new HashMap<>();
    private Map<Integer, String> mapNucleusMarkerAux = new HashMap<>();
    ProgressDialog progressDialog;
    public int flagMap = 0;

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
        if(nucleusOperations.isNetworkAvailable(getActivity())){
            verifyGPS();
            eventMarkers();
        }
        else {
            Toast.makeText(getActivity(), R.string.toast_verify_internet, Toast.LENGTH_LONG).show();
        }
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
            Toast.makeText(getActivity(), R.string.toast_verify_gps, Toast.LENGTH_LONG).show();
        }

        if(myPosition!=null){
            flagMap=1;
            mapPlace.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myPosition.latitude, myPosition.longitude), 13));

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
                        Toast.makeText(getActivity(), R.string.toast_verify_gps, Toast.LENGTH_LONG);
                    }

                }
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }else {
            Toast.makeText(getActivity(), R.string.toast_verify_gps, Toast.LENGTH_LONG);
        }
    }

    public void doRequest(String estado){
        // Instantiate the RequestQueue.
        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());
        String url ="http://diversidade-cloudsocial.rhcloud.com/api/v1/centers?state=";
        String urlSigla = "";
        String urlNucleusState = "";

        urlSigla = nucleusOperations.choseState(estado);

        urlNucleusState = url + urlSigla;

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_load));
        progressDialog.show();

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlNucleusState, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String responsible = jsonObject.getString("responsible");
                        String email = jsonObject.getString("email");
                        String phone = jsonObject.getString("phone");
                        String site = jsonObject.getString("website");

                        String address =  jsonObject.getString("address");
                        String numero = jsonObject.getString("street_number");
                        String bairro = jsonObject.getString("sublocality");
                        String cidade = jsonObject.getString("city");
                        String estado = jsonObject.getString("state");
                        String pais = jsonObject.getString("country");

                        double latitude = jsonObject.optDouble("latitude");
                        double longitude = jsonObject.optDouble("longitude");

                        JSONArray services = jsonObject.getJSONArray("services");
                        ArrayList<Service> servicesN = new ArrayList<>();
                        for(int k = 0; k<services.length(); k++){
                            JSONObject jsonObjectS = services.getJSONObject(k);
                            String title = jsonObjectS.getString("name");
                            String description = jsonObjectS.getString("description");

                            servicesN.add(new Service(title, description));
                        }

                        nucleusMap.put(i, new Nucleus(id, name, responsible, phone, email, site, latitude, longitude,
                                new AddressNucleus(address, numero, bairro, cidade, estado, pais), servicesN));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                progressDialog.dismiss();
                initMarkers();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.error_server, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map map = new HashMap();
                map.put("Accept", "application/json; charset=UTF-8");
                return map;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    public void initMarkers() {
        if(!nucleusMap.isEmpty()){
            for(int i = 0; i<nucleusMap.size(); i++){
                Marker marker =  mapPlace.addMarker(new MarkerOptions().position(new LatLng(nucleusMap.get(i).getLatitude(),
                        nucleusMap.get(i).getLongitude()))
                        .title(nucleusMap.get(i).getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                        .snippet(getResources().getString(R.string.snippet_marker_message)));

                mapNucleusMarker.put(marker.getId(), nucleusMap.get(i).getId());
                mapNucleusMarkerAux.put(nucleusMap.get(i).getId(), marker.getId());
            }
        }
        else {
            Toast.makeText(getActivity(), R.string.toast_any_nucleus, Toast.LENGTH_LONG).show();
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
                        getDetailNucleus(marker.getId());
                    } else {
                        position_1 = marker.getPosition();
                    }
                }
                return false;
            }
        });
    }

    public void getDetailNucleus(String idMarker) {
        if (mapNucleusMarker.get(idMarker) != null && nucleusMap!=null) {
            for (int i = 0; i < nucleusMap.size(); i++) {
                if (nucleusMap.get(i).getId() == mapNucleusMarker.get(idMarker)) {
                    Intent detailNucleusActivityIntent = new Intent(getActivity(), DetailNucleusActivity.class);
                    detailNucleusActivityIntent.putExtra("INFOS_NUCLEUS", nucleusMap.get(i));
                    startActivity(detailNucleusActivityIntent);
                }
            }
        }
    }

    LocationListener listenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(flagMap==0){
                initMap();
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
    };



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
