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

    public String choseState(String state){
        String sigla = "";

        switch (state){
            case "Acre":
                sigla = "AC";
                break;
            case "Alagoas":
                sigla = "AL";
                break;
            case "Amapá":
                sigla = "AP";
                break;
            case "Amazonas":
                sigla = "AM";
                break;
            case "Bahia":
                sigla = "BA";
                break;
            case "Ceará":
                sigla = "CE";
                break;
            case "Distrito Federal":
                sigla = "DF";
                break;
            case "Espírito Santo":
                sigla = "ES";
                break;
            case "Goiás":
                sigla = "GO";
                break;
            case "Maranhão":
                sigla = "MA";
                break;
            case "Mato Grosso":
                sigla = "MT";
                break;
            case "Mato Grosso do Sul":
                sigla = "MS";
                break;
            case "Minas Gerais":
                sigla = "MG";
                break;
            case "Pará":
                sigla = "PA";
                break;
            case "Paraíba":
                sigla = "PB";
                break;
            case "Paraná":
                sigla = "PR";
                break;
            case "Pernambuco":
                sigla = "PE";
                break;
            case "Piauí":
                sigla = "PI";
                break;
            case "Rio de Janeiro":
                sigla = "RJ";
                break;
            case "Rio Grande do Norte":
                sigla = "RN";
                break;
            case "Rio Grande do Sul":
                sigla = "RS";
                break;
            case "Rondônia":
                sigla = "RO";
                break;
            case "Roraima":
                sigla = "RR";
                break;
            case "Santa Catarina":
                sigla = "SC";
                break;
            case "São Paulo":
                sigla = "SP";
                break;
            case "Sergipe":
                sigla = "SE";
                break;
            case "Tocatins":
                sigla = "TO";
                break;
        }

        return sigla;
    }
}
