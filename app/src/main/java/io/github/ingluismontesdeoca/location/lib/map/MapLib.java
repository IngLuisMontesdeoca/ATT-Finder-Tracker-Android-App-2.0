package io.github.ingluismontesdeoca.location.lib.map;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by Aministrador on 31/08/2016.
 */
public class MapLib {

    public GoogleMap map;
    public Geoelement geoelement;
    public Geocoder geocoder;
    public Properties.mapProperties mapProperties;
    public Context context;

    public MapLib(GoogleMap map,Context context) {
        this.map = map;
        this.context = context;
        this.mapInitOptions();
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public GoogleMap getMap() {
        return this.map;
    }

    public void setZoom(int level) {
        this.map.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    public void setCenter(LatLng c) {
        this.map.moveCamera(CameraUpdateFactory.newLatLng(c));
    }

    public void setMapProperties(HashMap properties) {
        try {

            if (ActivityCompat.checkSelfPermission(this.context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this.context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                this.map.setMyLocationEnabled((boolean) properties.get("myLocationEnabled"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mapInitOptions() {
        this.geoelement = new Geoelement(this.map);
        this.geocoder = new Geocoder(this.map,this.context);
        mapProperties = new Properties().new mapProperties();
    }


}
