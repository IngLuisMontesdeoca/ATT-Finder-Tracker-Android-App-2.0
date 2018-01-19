package io.github.ingluismontesdeoca.location.lib.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.ingluismontesdeoca.location.R;

/**
 * Created by Aministrador on 31/08/2016.
 */
public class Properties {

    public Properties(){

    }

    public class mapProperties{
        //Propiedades del mapa
        //private LatLngBounds latLngBoundsForCameraTarget;
        private HashMap<String,Object> properties = new HashMap<String,Object>();

        public mapProperties(){
            this.properties.put("position", new LatLng(19.41281849460692, -99.13204990246524));
            this.properties.put("zoom",11 );
            this.properties.put("mapType", GoogleMap.MAP_TYPE_NORMAL);
            this.properties.put("mapToolBarEnabled",  true );
            this.properties.put("liteMode",false );
            this.properties.put("maxZoomPreference",19 );
            this.properties.put("minZoomPreference", 1 );
            this.properties.put("zoomControlsEnabled",true );
            this.properties.put("zoomGesturesEnabled", true );
            this.properties.put("compasEnabled",true );
            this.properties.put("ambientEnabled", true );
            this.properties.put("scrollGesturesEnabled", true );
            this.properties.put("tiltGesturesEnabled",true);
            this.properties.put("rotateGesturesEnabled",true );
            this.properties.put("zOrderOnTop", true );
        }

        public GoogleMapOptions getMapOptions(){
            return new GoogleMapOptions()
                    .camera( CameraPosition.fromLatLngZoom((LatLng) this.properties.get("position"),(int) this.properties.get("zoom")))
                    .mapType((int) this.properties.get("mapType"))
                    .mapToolbarEnabled((boolean) this.properties.get("mapToolBarEnabled"))
                    .liteMode((boolean) this.properties.get("liteMode"))
                    .maxZoomPreference((int) this.properties.get("maxZoomPreference"))
                    .minZoomPreference((int) this.properties.get("minZoomPreference"))
                    .zoomControlsEnabled((boolean) this.properties.get("zoomControlsEnabled"))
                    .zoomGesturesEnabled((boolean) this.properties.get("zoomGesturesEnabled"))
                    .compassEnabled((boolean) this.properties.get("compasEnabled"))
                    .ambientEnabled((boolean) this.properties.get("ambientEnabled"))
                    .scrollGesturesEnabled((boolean) this.properties.get("scrollGesturesEnabled"))
                    .tiltGesturesEnabled((boolean) this.properties.get("tiltGesturesEnabled"))
                    .rotateGesturesEnabled((boolean) this.properties.get("rotateGesturesEnabled"))
                    .zOrderOnTop((boolean) this.properties.get("zOrderOnTop"));
                    //.latLngBoundsForCameraTarget(this.latLngBoundsForCameraTarget)
        }

        public GoogleMapOptions getMapOptions(HashMap<String,Object> properties){
            this.validateMapOption(properties);
            return this.getMapOptions();
        }

        public void setMapOptions(GoogleMap map, HashMap<String,Object> properties){
            this.validateMapOption(properties);
            map.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom((LatLng) this.properties.get("position"),(int) this.properties.get("zoom"))));
            map.setMapType((int) this.properties.get("mapType"));
            map.getUiSettings().setMapToolbarEnabled((boolean) this.properties.get("mapToolBarEnabled"));
            //map.setLite setLiteMode((boolean) this.properties.get("liteMode"))
            map.setMaxZoomPreference((int) this.properties.get("maxZoomPreference"));
            map.setMinZoomPreference((int) this.properties.get("minZoomPreference"));
            map.getUiSettings().setZoomControlsEnabled((boolean) this.properties.get("zoomControlsEnabled"));
            map.getUiSettings().setZoomGesturesEnabled((boolean) this.properties.get("zoomGesturesEnabled"));
            map.getUiSettings().setCompassEnabled((boolean) this.properties.get("compasEnabled"));
            //map.getUiSettings().setAmbientEnabled((boolean) this.properties.get("ambientEnabled"))
            map.getUiSettings().setScrollGesturesEnabled((boolean) this.properties.get("scrollGesturesEnabled"));
            map.getUiSettings().setTiltGesturesEnabled((boolean) this.properties.get("tiltGesturesEnabled"));
            map.getUiSettings().setRotateGesturesEnabled((boolean) this.properties.get("rotateGesturesEnabled"));
            //map.getUiSettings().setZOrderOnTop((boolean) this.properties.get("zOrderOnTop"));
        }

        private void validateMapOption(HashMap<String,Object> options){
            for (HashMap.Entry<String, Object> entry : options.entrySet()) {
                if( this.properties.containsKey( entry.getKey() ) )
                    this.properties.put(entry.getKey(),entry.getValue());
            }
        }

    }

    //Propiedades de marca
    public class markerProperties{
        //Propiedades de marca default
        private HashMap<String,Object> properties = new HashMap<String,Object>();

        public markerProperties(){
            this.properties.put("snippet", "snippet");
            this.properties.put("title", "title");
            this.properties.put("position", new LatLng(19.41281849460692, -99.13204990246524));
            this.properties.put("anchorX", 0.50);
            this.properties.put("anchorY", 0.50);
            this.properties.put("anchorInfoWindowX", 0.50);
            this.properties.put("anchorInfoWindowY", 0.50);
            this.properties.put("draggable", false);
            this.properties.put("icon", BitmapDescriptorFactory.fromResource(R.drawable.ic_att));
            this.properties.put("alpha", 5);
            this.properties.put("flat", false);
            this.properties.put("rotation", 0);
            this.properties.put("visible", true);
            this.properties.put("zIndex", 100);
            this.properties.put("infoWindowOpen", 100);
        }

        //SETEAR PROPIEDADES DEFAULT
        public MarkerOptions getMarkerOptions(){
            try {
                return new MarkerOptions()
                        .position((LatLng) this.properties.get("position"))
                        .title(this.properties.get("title").toString())
                        .snippet(this.properties.get("snippet").toString())
                        .icon(BitmapDescriptorFactory.fromResource(Integer.parseInt(this.properties.get("icon").toString())))
                        .draggable((boolean) this.properties.get("draggable"))
                        .alpha((Integer) this.properties.get("alpha"))
                        .rotation((Integer) this.properties.get("rotation"))
                        .visible((boolean) this.properties.get("visible"))
                        .zIndex((Integer) this.properties.get("zIndex"));
            }catch(Exception e){
                return null;
            }
            //.anchor(this.anchorX,this.anchorY)
            //.infoWindowAnchor(this.anchorX,this.anchorY)
        }

        //SETEAR PROPIEDADES POR HASHMAP
        public MarkerOptions getMarkerOptions(HashMap<String,Object> properties){
            this.validateMarkerOption(properties);
            return this.getMarkerOptions();
        }

        public void setMarkerOptions(Marker marker, HashMap properties){
            this.validateMarkerOption(properties);
            marker.setPosition((LatLng) this.properties.get("position"));
            marker.setTitle(this.properties.get("title").toString());
            marker.setSnippet(this.properties.get("snippet").toString());
            marker.setIcon(BitmapDescriptorFactory.fromResource(Integer.parseInt(this.properties.get("icon").toString())));
            marker.setDraggable((boolean) this.properties.get("draggable"));
            marker.setAlpha((Integer) this.properties.get("alpha"));
            marker.setRotation((Integer) this.properties.get("rotation"));
            marker.setVisible((boolean) this.properties.get("visible"));
            marker.setZIndex((Integer) this.properties.get("zIndex"));
            if((boolean) this.properties.get("infoWindowOpen"))
                marker.showInfoWindow();
            else
                marker.hideInfoWindow();
            //.anchor(this.anchorX,this.anchorY)
            //.infoWindowAnchor(this.anchorX,this.anchorY)
        }

        private void validateMarkerOption(HashMap<String,Object> options){
            for (HashMap.Entry<String, Object> entry : options.entrySet()) {
                if( this.properties.containsKey( entry.getKey() ) )
                    this.properties.put(entry.getKey(),entry.getValue());
            }
        }
    }

    public class circleProperties{
        public LatLng center = new LatLng(19.41281849460692, -99.13204990246524);
        public double radio = 50;
        public float strokeWidth = 2;
        public float zIndex = 100;
        public int strokeColor = 1;
        public int fillColor = 1;
        public boolean visible = true;

        public CircleOptions getCircleOptions(HashMap<String,Object> properties){
            return new CircleOptions()
                    .center((LatLng) properties.get("center"))
                    .radius(Double.parseDouble(properties.get("radio").toString()))
                    .fillColor(Integer.parseInt(properties.get("fillColor").toString()))
                    .strokeColor(Integer.parseInt(properties.get("strokeColor").toString()))
                    //.visible(Boolean.parseBoolean(properties.get("visible").toString()))
                    .zIndex(Float.parseFloat(properties.get("zIndex").toString()))
                    .strokeWidth(Float.parseFloat(properties.get("strokeWidth").toString()));
        }

        public CircleOptions getCircleOptions(){
            return new CircleOptions()
                    .center(this.center)
                    .radius(this.radio)
                    .strokeColor(this.strokeColor)
                    .strokeWidth(this.strokeWidth)
                    .fillColor(this.fillColor)
                    .zIndex(this.zIndex)
                    .visible(this.visible);
        }

        public void setCircleOpetions(Circle circle, HashMap properties){
            circle.setCenter((LatLng) properties.get("center"));
            circle.setRadius(Double.parseDouble(properties.get("radio").toString()));
            circle.setFillColor(Integer.parseInt(properties.get("fillColor").toString()));
            circle.setStrokeColor(Integer.parseInt(properties.get("strokeColor").toString()));
            circle.setZIndex(Float.parseFloat(properties.get("zIndex").toString()));
            //circle.setVisible(Boolean.parseBoolean(properties.get("visible").toString()))
            circle.setStrokeWidth(Float.parseFloat(properties.get("strokeWidth").toString()));
        }
    }

    public class polylineProperties{
        public LatLng[] coords;
        public boolean clickable;
        public int color;
        public boolean godesic;
        public boolean visible;
        public float width;
        public float zIndex;

        public PolylineOptions getPolylineOptions(HashMap<String,Object> properties){
            LatLng[] coords =  LatLng[].class.cast(properties.get("coords"));
            PolylineOptions options = new PolylineOptions();

            for(int i=0; i<coords.length; i++)
                options.add(coords[i]);

            options.clickable(Boolean.parseBoolean(properties.get("clickable").toString()))
                    .geodesic(Boolean.parseBoolean(properties.get("geodesic").toString()))
                    //.visible(Boolean.parseBoolean(properties.get("visible").toString()))
                    .width(Float.parseFloat(properties.get("width").toString()))
                    .zIndex(Float.parseFloat(properties.get("zIndex").toString()))
                    .color(Integer.parseInt(properties.get("color").toString()));
            return  options;
        }

        public PolylineOptions getPolylineOptions(){
            PolylineOptions options = new PolylineOptions();

            for(int i=0; i<coords.length; i++)
                options.add(coords[i]);

            options.clickable(this.clickable)
                    .geodesic(this.godesic)
                    .visible(this.visible)
                    .width(this.width)
                    .zIndex(this.zIndex)
                    .color(this.color);
            return  options;
        }

        public void setPolylineOptions(Polyline polyline, HashMap<String, Object> properties){
            LatLng[] coords =  LatLng[].class.cast(properties.get("coords"));
            List<LatLng> p = new ArrayList<>();

            for(int i=0; i<coords.length; i++)
                p.add(coords[i]);

            polyline.setPoints(p);
            polyline.setClickable(Boolean.parseBoolean(properties.get("clickable").toString()));
            polyline.setGeodesic(Boolean.parseBoolean(properties.get("geodesic").toString()));
            //polyline.setVisible(Boolean.parseBoolean(properties.get("visible").toString()));
            polyline.setWidth(Float.parseFloat(properties.get("width").toString()));
            polyline.setZIndex(Float.parseFloat(properties.get("zIndex").toString()));
            polyline.setColor(Integer.parseInt(properties.get("color").toString()));
        }
    }

    public class polygonProperties{
        LatLng[] coords;
        public boolean clickable;
        public int strokeColor;
        public int fillColor;
        public boolean godesic;
        public boolean visible = true;
        public float strokeWidth;
        public float zIndex;

        public PolygonOptions getPolygonOptions(HashMap<String,Object> properties){
            LatLng[] coords =  LatLng[].class.cast(properties.get("coords"));
            PolygonOptions options = new PolygonOptions();

            for(int i=0; i<coords.length; i++)
                options.add(coords[i]);

            options.clickable(Boolean.parseBoolean(properties.get("clickable").toString()))
                    .geodesic(Boolean.parseBoolean(properties.get("geodesic").toString()))
                    //.visible(Boolean.parseBoolean(properties.get("visible").toString()))
                    .strokeWidth(Float.parseFloat(properties.get("strokeWidth").toString()))
                    .zIndex(Float.parseFloat(properties.get("zIndex").toString()))
                    .strokeColor(Integer.parseInt(properties.get("strokeColor").toString()))
                    .fillColor(Integer.parseInt(properties.get("fillColor").toString()));
            return  options;
        }

        public void setPolygonOptions(Polygon polygon, HashMap<String, Object> properties){
            LatLng[] coords =  LatLng[].class.cast(properties.get("coords"));
            List<LatLng> p = new ArrayList<>();

            for(int i=0; i<coords.length; i++)
                p.add(coords[i]);

            polygon.setClickable(Boolean.parseBoolean(properties.get("clickable").toString()));
            polygon.setGeodesic(Boolean.parseBoolean(properties.get("geodesic").toString()));
            polygon.setVisible(Boolean.parseBoolean(properties.get("visible").toString()));
            polygon.setStrokeWidth(Float.parseFloat(properties.get("strokeWidth").toString()));
            polygon.setZIndex(Float.parseFloat(properties.get("zIndex").toString()));
            polygon.setStrokeColor(Integer.parseInt(properties.get("strokeColor").toString()));
            polygon.setFillColor(Integer.parseInt(properties.get("fillColor").toString()));
        }

        public PolygonOptions PolygonOptions(){
            PolygonOptions options = new PolygonOptions();

            for(int i=0; i<coords.length; i++)
                options.add(coords[i]);

            options.clickable(this.clickable)
                    .geodesic(this.godesic)
                    .visible(this.visible)
                    .strokeWidth(this.strokeWidth)
                    .zIndex(this.zIndex)
                    .strokeColor(this.strokeColor)
                    .fillColor(this.fillColor);
            return  options;
        }
    }

}
