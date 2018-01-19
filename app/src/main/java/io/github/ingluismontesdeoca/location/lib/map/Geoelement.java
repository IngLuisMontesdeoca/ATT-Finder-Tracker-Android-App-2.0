package io.github.ingluismontesdeoca.location.lib.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;

import java.util.HashMap;

public class Geoelement{

    public GoogleMap map;
    public Properties.markerProperties mapMarkerProperties;
    public Properties.circleProperties mapCircleProperties;
    public Properties.polygonProperties mapPolygonProperties;
    public Properties.polylineProperties mapPolylineProperties;
    public HashMap Geoelements = new HashMap<String,Object>();

    public Geoelement(GoogleMap map){
        this.map = map;
        this.mapMarkerProperties = new Properties().new markerProperties();
        this.mapCircleProperties = new Properties().new circleProperties();
        this.mapPolygonProperties = new Properties().new polygonProperties();
        this.mapPolylineProperties = new Properties().new polylineProperties();
    }

    /*Creacion de geoelementos*/
    public Marker marker(HashMap options, boolean center){
        try{
            if( options != null )
                this.Geoelements.put("mk_"+options.get("id"),this.map.addMarker(this.mapMarkerProperties.getMarkerOptions(options)));
            else
                this.Geoelements.put("mk_"+options.get("id"),this.map.addMarker(this.mapMarkerProperties.getMarkerOptions()));
            if( center )
                this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(((Marker) this.Geoelements.get("mk_"+options.get("id"))).getPosition(),19));

            return (Marker) this.Geoelements.get("mk_"+options.get("id"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Circle circle(HashMap options, boolean center){
        try{
            if( options != null )
                this.Geoelements.put("cr_"+options.get("id"),this.map.addCircle(this.mapCircleProperties.getCircleOptions(options)));
            else
                this.Geoelements.put("cr_"+options.get("id"),this.map.addCircle(this.mapCircleProperties.getCircleOptions()));
            if( center )
                this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(( (Circle) this.Geoelements.get("cr_"+options.get("id"))).getCenter(),19));
            //Debug.mkToast("createing circle "+( (Circle) this.Geoelements.get("cr_"+options.get("id"))).getCenter(), this );
            return (Circle) this.Geoelements.get("cr_"+options.get("id"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Polyline polyline(HashMap options, boolean center){
        try{
            if( options != null )
                this.Geoelements.put("pl_"+options.get("id"),this.map.addPolyline(this.mapPolylineProperties.getPolylineOptions(options)));
            else
                this.Geoelements.put("pl_"+options.get("id"),this.map.addPolyline(this.mapPolylineProperties.getPolylineOptions()));
            return (Polyline) this.Geoelements.get("pl_"+options.get("id"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Polygon polygon(HashMap options, boolean center){
        try{
            if( options == null )
                this.Geoelements.put("pl_"+options.get("id"),this.map.addPolygon(this.mapPolygonProperties.getPolygonOptions(options)));
            else
                this.Geoelements.put("pl_"+options.get("id"),this.map.addPolygon(this.mapPolygonProperties.getPolygonOptions(options)));
            return (Polygon) this.Geoelements.get("pg_"+options.get("id"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /* Getter */
    public Marker getMarker(String id){
        try{
            if( this.Geoelements.containsKey("mk_"+id))
                return (Marker) this.Geoelements.get("mk_"+id);
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Circle getCircle(String id){
        try{
            return (Circle) this.Geoelements.get("cr_"+id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean existElement(String type,String id){
        return this.Geoelements.containsKey(type+"_"+id);
    }

    /* Setters */
    public void setProperties(HashMap options){
        try{
            switch(options.get("type").toString()) {
                case "mk":
                    this.setMarkerProperties(options);
                    break;
                case "cr":
                    break;
                case "pg":
                    this.setPolygonProperties(options);
                    break;
                case "pl":
                    this.setPolylineProperties(options);
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void centerElement(String type, String id){
        try{
            switch(type){
                case "mk":
                    this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(((Marker) this.Geoelements.get(type+"_"+id)).getPosition(),19));
                    break;
                case "pg":
                    break;
                case "pl":
                    break;
                case "cr":
                    this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(( (Circle) this.Geoelements.get(type+"_"+id)).getCenter(),19));
                    break;
            }
        }catch(Exception e){

        }
    }

    public void setMarkerProperties(HashMap options) {
        try{
            this.mapMarkerProperties.setMarkerOptions((Marker) this.Geoelements.get("mk_"+options.get("id")),options);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setCircleProperties(HashMap options) {
        try{
            this.mapCircleProperties.setCircleOpetions((Circle) this.Geoelements.get("cr_"+options.get("id")),options);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setPolylineProperties(HashMap options) {
        try{
            this.mapPolylineProperties.setPolylineOptions((Polyline) this.Geoelements.get("pl_"+options.get("id")),options);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setPolygonProperties(HashMap options) {
        try{
            this.mapPolygonProperties.setPolygonOptions((Polygon) this.Geoelements.get("pg_"+options.get("id")),options);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*Adicionales*/
    public void removeElement(String type,String id){
        try{
            if( this.existElement(type,id)) {
                ((Marker) this.Geoelements.get(type+"_" + id)).remove();
                this.Geoelements.remove(type+"_" + id);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void markerGroups(){        }
    public void tooltip(){        }
}
