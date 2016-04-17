package br.edu.ifce.engcomp.francis.diversidade.miscellaneous;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Bolsista on 12/04/2016.
 */
public class NucleusOperations {

    public LatLng getMyPosition(Location location) {
        float myPositionLatitude = (float) location.getLatitude();
        float myPositionLongitude = (float) location.getLongitude();
        LatLng myPosition = new LatLng(myPositionLatitude, myPositionLongitude);

        return myPosition;
    }

    public void initMarkers(GoogleMap map) {
        Marker markerNucleus = map.addMarker(new MarkerOptions().position(new LatLng(-3.7297003, -38.5398319))
                .title("GRAB - Grupo de Resistência Asa Branca")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        markerNucleus.setSnippet("Clique mais uma vez para conhecer a organização.");

        Marker markerEvent = map.addMarker(new MarkerOptions().position(new LatLng(-3.7441914, -38.5380658))
                .title("Lançamento do app Diversidade - 15/05/2016")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        markerEvent.setSnippet("Clique mais uma vez para conhecer o evento");
    }
}
