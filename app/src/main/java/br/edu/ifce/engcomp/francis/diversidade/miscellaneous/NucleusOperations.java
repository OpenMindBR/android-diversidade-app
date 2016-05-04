package br.edu.ifce.engcomp.francis.diversidade.miscellaneous;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.edu.ifce.engcomp.francis.diversidade.model.Nucleus;

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

    public void initMarkers(GoogleMap map, ArrayList<Nucleus> nucleus, Context context, Map<String, Integer> mapNucleusMarker,
                            Map<Integer, String> mapNucleusMarkerAux, ArrayList<Marker> markers) {
        if(!nucleus.isEmpty()){
            for(int i = 0; i<nucleus.size(); i++){
             Marker marker =  map.addMarker(new MarkerOptions().position(new LatLng(nucleus.get(i).getLatitude(), nucleus.get(i).getLongitude()))
                    .title(nucleus.get(i).getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                            .snippet("Clique mais uma vez para conhecer a organização."));

                mapNucleusMarker.put(marker.getId(), nucleus.get(i).getId());
                mapNucleusMarkerAux.put(nucleus.get(i).getId(), marker.getId());
                markers.add(marker);
            }
        }
        else {
            Toast.makeText(context, "Ainda não há núcleos cadastrados no seu estado.", Toast.LENGTH_LONG).show();
        }
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

     /*public void initTest() {
        teste1.setName("GRAB");
        teste1.setAddress(new AddressNucleus("R. Teresa Cristina", "1050", "Centro", "Fortaleza", "CE", "Brasil", "60015-141",
                -3.7297003, -38.5398319));
        teste1.setHour(new HourNucleus("Segunda-Sexta", "8h-18h"));
    }*/
}
