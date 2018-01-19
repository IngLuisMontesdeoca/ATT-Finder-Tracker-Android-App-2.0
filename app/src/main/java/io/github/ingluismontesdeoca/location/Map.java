package io.github.ingluismontesdeoca.location;

import android.Manifest;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.ingluismontesdeoca.location.adapters.addressResultAdapter;
import io.github.ingluismontesdeoca.location.lib.debug.Debug;
import io.github.ingluismontesdeoca.location.lib.map.Geocoder;
import io.github.ingluismontesdeoca.location.lib.map.MapLib;
import io.github.ingluismontesdeoca.location.lib.map.Properties;
import io.github.ingluismontesdeoca.location.modelo.AddressResult;

public class Map extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ListView navigationOptions;
    private SupportMapFragment mapFragment;
    private FragmentTransaction fragmentTransaction;
    private MapLib mapLib;
    private ArrayList addressLine;
    private TextView txtInfo;
    ImageView _lb;
    ImageView lh;
    ImageView _lsat;
    ImageView _ltr;
    ImageView _lter;
    ImageView _lmap;
    ImageView _lv;
    FrameLayout _mapFragment;
    FrameLayout _streetView;
    LinearLayout _addressSearch;
    LinearLayout _toolbar;
    LatLng streetViewPosition;
    private FragmentTransaction fragmentStreetView;
    SupportStreetViewPanoramaFragment streetViewFragment;



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        initListView();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        loadMap();

        _lb = (ImageView) findViewById(R.id.action_position);
        lh = (ImageView) findViewById(R.id.action_home);
        _lsat = (ImageView) findViewById(R.id.action_layer_sat);
        _ltr = (ImageView) findViewById(R.id.action_layer_tr);
        _lter = (ImageView) findViewById(R.id.action_layer_ter);
        _lmap = (ImageView) findViewById(R.id.action_layer_map);
        _lv = (ImageView) findViewById(R.id.action_streetView);
        _mapFragment = (FrameLayout) findViewById(R.id.map);
        _streetView = (FrameLayout) findViewById(R.id.streetView);
        _addressSearch = (LinearLayout) findViewById(R.id.addressSearch);
        _toolbar = (LinearLayout) findViewById(R.id.toolbarMap);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /***
     * ACTION BAR
     *********************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location, menu);

        //Agregar listener a boton buscar
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        SearchView searchView = (SearchView) menu.findItem(R.id.action_busqueda).getActionView();
        searchView.setSearchableInfo(searchableInfo);
        searchView.setIconifiedByDefault(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            //Debug.mkToast("search",this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //Toast.makeText(this, "Searching by: "+ query, Toast.LENGTH_SHORT).show();
            this.searchAddress(query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String uri = intent.getDataString();
            //Toast.makeText(this, "Suggestion: "+ uri, Toast.LENGTH_SHORT).show();
        }
    }

    private void searchAddress(String add) {
        this.removeAddressResults();

        ArrayList<Geocoder.address> addresses = this.mapLib.geocoder.reverseGeocode(add, 10);
        addressLine = new ArrayList<AddressResult>();

        if (addresses.size() == 0) {
            Debug.mkToast("No se encontraror resultados|" + this.mapLib.geocoder.error, this);
        } else {
            for (int i = 0; i < addresses.size(); i++)
                addressLine.add(new AddressResult(this.mapLib.geocoder.formatAddress(addresses.get(i))));
        }

        addressResultAdapter _adapter = new addressResultAdapter(getApplicationContext(), addressLine);
        navigationOptions.setAdapter(_adapter);
        navigationOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchShowResults(position);
                drawer.closeDrawer(navigationOptions);
            }
        });
        drawer.openDrawer(navigationOptions);
    }

    private void searchShowResults(int position) {
        try {
            Geocoder.address add = this.mapLib.geocoder.getAddress(position);
            if (!this.mapLib.geoelement.existElement("mk", "geo" + position)) {
                HashMap properties = new HashMap<String, Object>();
                properties.put("position", add.position);
                properties.put("id", "geo" + position);
                properties.put("icon", R.drawable.ic_att);
                properties.put("title", this.mapLib.geocoder.formatAddress(add));
                properties.put("snippet", this.mapLib.geocoder.formatAddress(add));
                properties.put("draggable", false);
                properties.put("alpha", 5);
                properties.put("rotation", 0);
                properties.put("zIndex", 100);
                properties.put("infoWindowOpen", false);
                mapLib.geoelement.marker(properties, true);
            } else
                mapLib.geoelement.centerElement("mk", "geo" + position);
            setTxtInfo(this.mapLib.geocoder.formatAddress(add), add.position);
        } catch (Exception e) {

        }
    }

    private void removeAddressResults() {
        try {
            if (addressLine == null)
                return;
            if (addressLine.size() == 0)
                return;
            for (int i = 0; i < addressLine.size(); i++)
                this.mapLib.geoelement.removeElement("mk", "geo" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * NAVIGATION VIEW
     ****************************************************************************************/

    private void setTxtInfo(String txt, LatLng Position) {
        try {
            txtInfo.setText(txt);
            streetViewPosition = Position;
            findViewById(R.id.layAddress).setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Debug.printStack(e);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        loadInfo(item.getItemId());
        drawer.closeDrawer(navigationView);
        drawer.openDrawer(navigationOptions);
        return true;
    }


    private void initListView() {
        navigationOptions = (ListView) findViewById(R.id.nav_options);
    }

    private void loadPTNs() {
        ArrayList<String> dns = new ArrayList<String>();
        dns.add("5563022778");
        dns.add("5563022778");
        dns.add("5563022778");
        dns.add("5563022778");
        dns.add("5563022778");
        dns.add("5563022778");
        navigationOptions.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, dns));
    }

    private void loadPOIS() {
        ArrayList<String> dns = new ArrayList<String>();
        dns.add("POI 1");
        dns.add("POI 2");
        dns.add("POI 3");
        dns.add("POI 4");
        dns.add("POI 5");
        dns.add("POI 6");
        navigationOptions.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, dns));
    }

    private void loadGeofence() {
        ArrayList<String> dns = new ArrayList<String>();
        dns.add("GEO 1");
        dns.add("GEO 2");
        dns.add("GEO 3");
        dns.add("GEO 4");
        dns.add("GEO 5");
        dns.add("GEO 6");
        navigationOptions.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, dns));
    }

    private void loadInfo(int id) {
        switch (id) {
            case R.id.loc_equipos:
                loadPTNs();
                break;
            case R.id.geo_poi:
                loadPOIS();
                break;
            case R.id.geo_geoc:
                loadGeofence();
                break;
        }
    }

    /***
     * CARGA DEL MAPA
     ***************************************************************************************/

    private void loadMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Properties.mapProperties mapProperties = new Properties().new mapProperties();
        mapFragment = SupportMapFragment.newInstance(mapProperties.getMapOptions());
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mapFragment, "map");
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        this.mapLib = new MapLib(mMap, this.getApplicationContext());
        this.mapLib.map.getUiSettings().setCompassEnabled(true);
        this.mapLib.map.getUiSettings().setMyLocationButtonEnabled(true);
        this.getLocationServices();
        //MarkerClic
        mMap.setOnMarkerClickListener(this);
        //MarkerDrag
        mMap.setOnMarkerDragListener(this);
        //MapClic
        mMap.setOnMapClickListener(this);
        //MapLongClic
        mMap.setOnMapLongClickListener(this);
        initToolbar();
    }

    public void initToolbar() {
        try {
            _lb.setClickable(true);
            _lb.setOnClickListener(eventMapCtrl);
            lh.setClickable(true);
            lh.setOnClickListener(eventMapCtrl);
            _lsat.setClickable(true);
            _lsat.setOnClickListener(eventMapCtrl);
            _ltr.setClickable(true);
            _ltr.setOnClickListener(eventMapCtrl);
            _lter.setClickable(true);
            _lter.setOnClickListener(eventMapCtrl);
            _lmap.setClickable(true);
            _lmap.setOnClickListener(eventMapCtrl);
            _lv.setClickable(true);
            _lv.setOnClickListener(eventMapCtrl);
        } catch (Exception e) {

        }
    }

    public View.OnClickListener eventMapCtrl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.action_layer_map:
                        if (mapLib.map.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
                            return;
                        setMapView(GoogleMap.MAP_TYPE_NORMAL);
                        _lsat.setImageResource(R.drawable.ic_layer_sat);
                        _lter.setImageResource(R.drawable.ic_lay_ter);
                        _lmap.setImageResource(R.drawable.ic_lay_map_blue);
                        break;
                    case R.id.action_layer_ter:
                        if (mapLib.map.getMapType() == GoogleMap.MAP_TYPE_TERRAIN)
                            return;
                        setMapView(GoogleMap.MAP_TYPE_TERRAIN);
                        _lsat.setImageResource(R.drawable.ic_layer_sat);
                        _lter.setImageResource(R.drawable.ic_lay_ter_blue);
                        _lmap.setImageResource(R.drawable.ic_lay_map);
                        break;
                    case R.id.action_layer_sat:
                        if (mapLib.map.getMapType() == GoogleMap.MAP_TYPE_HYBRID)
                            return;
                        setMapView(GoogleMap.MAP_TYPE_HYBRID);
                        _lsat.setImageResource(R.drawable.ic_layer_sat_blue);
                        _lter.setImageResource(R.drawable.ic_lay_ter);
                        _lmap.setImageResource(R.drawable.ic_lay_map);
                        break;
                    /*
                    case R.drawable.ic_layer_bike:
                        break;
                    case R.drawable.ic_transit_light:
                        break;
                        */
                    case R.id.action_layer_tr:
                        if (mapLib.map.isTrafficEnabled()) {
                            mapLib.map.setTrafficEnabled(false);
                            _ltr.setImageResource(R.drawable.ic_traffic);
                            return;
                        }
                        mapLib.map.setTrafficEnabled(true);
                        _ltr.setImageResource(R.drawable.ic_traffic_blue);
                        break;
                    case R.id.action_home:
                        setInitZoom();
                        break;
                    case R.id.action_position:
                        getLocationServices();
                        break;
                    case R.id.action_streetView:
                        showStreetView();
                        break;
                }

            } catch (Exception e) {
                Log.i("MyTag", "Image button is pressed, visible in LogCat");
            }
        }
    };

    public void setInitZoom() {
        this.mapLib.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.41281849460692, -99.13204990246524), 11));
    }

    public void setMapView(int type) {
        this.mapLib.map.setMapType(type);
    }

    /******************
     * STREET VIEW
     *******************/
    public void showStreetView() {
        if (_mapFragment.getVisibility() == View.VISIBLE) {
            toggleLayouts(View.GONE);
            loadStreetView();
        } else {
            toggleLayouts(View.VISIBLE);
            closeStreetView();
        }
    }

    private void toggleLayouts(int visible) {
        _mapFragment.setVisibility(visible);
        _addressSearch.setVisibility(visible);
        _toolbar.setVisibility(visible);
    }

    private void closeStreetView(){
        _streetView.setVisibility(View.GONE);
        fragmentStreetView.detach(streetViewFragment);
        //fragmentStreetView.remove(streetViewFragment);
       // streetViewFragment = null;
       // streetViewPanorama = null;
    }



    public void loadStreetView() {
        if (streetViewPosition == null)
            return;
        _streetView.setVisibility(View.VISIBLE);
        if (streetViewFragment != null) {
            streetViewPanorama.setPosition(streetViewPosition);
            return;
        }
        makeStreetViewFragment();
    }

    private void makeStreetViewFragment() {
        StreetViewPanoramaOptions _so = new StreetViewPanoramaOptions().position(streetViewPosition);

        streetViewFragment = SupportStreetViewPanoramaFragment.newInstance(_so);
        fragmentStreetView = getSupportFragmentManager().beginTransaction();
        fragmentStreetView.add(R.id.streetView, streetViewFragment, "streetView");
        fragmentStreetView.commit();
        streetViewFragment.getStreetViewPanoramaAsync(callBackStreetView);
    }

    StreetViewPanorama streetViewPanorama;

    public OnStreetViewPanoramaReadyCallback callBackStreetView = new OnStreetViewPanoramaReadyCallback() {
        @Override
        public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
            streetViewPanorama = panorama;
            streetViewPanorama.setOnStreetViewPanoramaChangeListener(validateStreetView);
            //panorama.setPosition(streetViewPosition);
        }
    };

    public StreetViewPanorama.OnStreetViewPanoramaChangeListener validateStreetView = new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
        @Override
        public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
            if (streetViewPanoramaLocation == null || streetViewPanoramaLocation.links == null) {
                Toast.makeText(getApplicationContext(), "StreetView no disponible", Toast.LENGTH_SHORT).show();
                showStreetView();
            }
        }
    };

    /***
     * EVENTOS DEL MAPA
     ****************************************************************************************/

    @Override
    public void onMapClick(LatLng latLng) {
        this.mapLib.setCenter(latLng);
        //Debug.mkToast("Map Clic",Map.this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        this.mDoObjectsClicMap(latLng);
        //Debug.mkToast("Map Long Clic",Map.this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //Debug.mkToast("Marker Clic",Map.this);
        Snackbar.make(findViewById(R.id.drawer_layout), "CLIC MARCA", Snackbar.LENGTH_LONG);
        //this.openStreetView();
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {/*Debug.mkToast("onMarkerDragStart",Map.this);*/}

    @Override
    public void onMarkerDrag(Marker marker) {/*Debug.mkToast("onMarkerDrag",Map.this);*/}

    @Override
    public void onMarkerDragEnd(Marker marker) {/*Debug.mkToast("onMarkerDragEnd",Map.this);*/}


    private void openStreetView() {

    }

    private void mDoObjectsClicMap(LatLng latLng) {
        HashMap properties = new HashMap<String, Object>();
        properties.put("position", latLng);
        properties.put("id", "clic");
        properties.put("position", latLng);
        properties.put("icon", R.drawable.ic_att);
        properties.put("title", "[Dirección]");
        properties.put("snippet", "[Direccion]");
        properties.put("draggable", false);
        properties.put("alpha", 5);
        properties.put("rotation", 0);
        properties.put("zIndex", 100);
        if (this.mapLib.geoelement.existElement("mk", "clic"))
            this.mapLib.geoelement.setMarkerProperties(properties);
        else
            mapLib.geoelement.marker(properties, true);
        this.setLocationAddress("clic", latLng);
    }

    private void setLocationAddress(String id, LatLng position) {
        ArrayList<Geocoder.address> addresses = this.mapLib.geocoder.geocode(position, 1);
        HashMap properties = new HashMap<String, Object>();
        properties.put("id", id);
        if (addresses.size() == 0) {
            properties.put("title", "Sin información");
            properties.put("snippet", "Sin información");
            setTxtInfo("Dirección desconocida", position);
        } else {
            properties.put("title", addresses.get(0).addressLine);
            properties.put("snippet", addresses.get(0).addressLine);
            properties.put("infoWindowOpen", false);
            setTxtInfo(this.mapLib.geocoder.formatAddress(addresses.get(0)), position);
        }
        mapLib.geoelement.setMarkerProperties(properties);
    }

    /***
     * GEOLOCATION
     ****************************************************************************************/
    private void getLocationServices() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected())
                requestLocation();
            else
                mGoogleApiClient.connect();
            return;
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Debug.mkToast("Error onConnectionFailed - Map", Map.this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Debug.mkToast("onConnectionSuspended - Map", Location.this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocation();
    }

    public void requestLocation() {
        if (checkPermission())
            return;
        _lb.setImageResource(R.drawable.ic_location_light_blue);
        getLocation(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
    }

    private void getLocation(Location mMarkerPosition) {
        if (mMarkerPosition != null) {
            Toast.makeText(Map.this, mMarkerPosition.getLongitude() + "," + mMarkerPosition.getLatitude(), Toast.LENGTH_SHORT).show();
            this.mDoObjectsGeolocation(mMarkerPosition);
        } else
            Toast.makeText(Map.this, "UNABLE TO GET LOCATION", Toast.LENGTH_SHORT).show();
        //Debug.mkToast("onConnected - Map", Map.this);
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return true;
        }
        return false;
    }

    private void mDoObjectsGeolocation(Location mMarkerPosition) {
        this.mDoMarkerGeolocation(mMarkerPosition);
        this.mDoCircleGeolocation(mMarkerPosition);
    }


    private void mDoMarkerGeolocation(Location mMarkerPosition) {
        /*
        LatLng sydney = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney") );
        Properties.markerProperties mapMarkerProperties = new Properties().new markerProperties();
        mapMarkerProperties.position = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mapMarkerProperties.icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_att);
        */
        //SETEANDO PROPIEDADES POR MEDIO DE HASHMAP
        HashMap properties = new HashMap<String, Object>();
        properties.put("position", new LatLng(mMarkerPosition.getLatitude(), mMarkerPosition.getLongitude()));
        properties.put("icon", R.drawable.ic_att);
        properties.put("title", "Tu Ubicación");
        properties.put("snippet", "Tu Ubicación");
        properties.put("draggable", false);
        properties.put("alpha", 5);
        properties.put("rotation", 0);
        properties.put("zIndex", 100);
        properties.put("id", "pos");
        properties.put("type", "mk");
        if (this.mapLib.geoelement.existElement("mk", "pos")) {
            this.mapLib.geoelement.setProperties(properties);
            this.mapLib.geoelement.centerElement("mk", "pos");
        } else
            mapLib.geoelement.marker(properties, true);
        this.setLocationAddress("pos", new LatLng(mMarkerPosition.getLatitude(), mMarkerPosition.getLongitude()));
    }

    private void mDoCircleGeolocation(Location mMarkerPosition) {
        //SETEANDO PROPIEDADES POR MEDIO DE HASHMAP
        HashMap properties = new HashMap<String, Object>();
        properties.put("center", new LatLng(mMarkerPosition.getLatitude(), mMarkerPosition.getLongitude()));
        properties.put("radio", mMarkerPosition.getAccuracy());
        properties.put("fillColor", R.color.map_geo_fill);
        properties.put("strokeColor", Color.BLACK);
        properties.put("strokeWidth", 3);
        properties.put("zIndex", 100);
        properties.put("id", "pos");
        properties.put("type", "cr");
        if (this.mapLib.geoelement.existElement("cr", "pos"))
            this.mapLib.geoelement.setProperties(properties);
        else
            mapLib.geoelement.circle(properties, true);
        // Debug.mkToast("createing circle "+ this.mapLib.geoelement.existElement("cr","geo") , this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://io.github.ingluismontesdeoca.location/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://io.github.ingluismontesdeoca.location/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /*** GEOLOCATION ****************************************************************************************/
}
