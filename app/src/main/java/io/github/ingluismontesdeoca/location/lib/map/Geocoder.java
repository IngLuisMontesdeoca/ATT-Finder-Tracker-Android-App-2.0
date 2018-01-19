package io.github.ingluismontesdeoca.location.lib.map;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Aministrador on 29/09/2016.
 */
public class Geocoder {

    private GoogleMap map;
    private android.location.Geocoder geocoder;
    public String error = null;
    public ArrayList<address> addresses;

    public Geocoder(GoogleMap map, Context context){
        this.map = map;
        this.geocoder = new android.location.Geocoder(context, Locale.getDefault());
    }

    public ArrayList<address> geocode(LatLng position,int maxResults){
        ArrayList<address> addresses = new ArrayList<>();
        List<Address> addressList = null;

        try{
            addressList = this.geocoder.getFromLocation(position.latitude,position.longitude,maxResults);
        }catch(IOException ioException) {
            this.error = "IOException "+ioException.getMessage();
        } catch (IllegalArgumentException illegalArgumentException) {
            this.error = "IllegalArgumentException " + illegalArgumentException.getMessage();
        }

        if (addressList != null && addressList.size()  > 0) {
            for(int i = 0; i < addressList.size(); i++) {
                addresses.add(new address(addressList.get(i)));
            }
        }
        return addresses;
    }

    public address getAddress(int position){
        try{
            return this.addresses.get(position);
        }catch(Exception e){
            return null;
        }
    }


    public ArrayList<address> reverseGeocode(String locationName,int maxResults){
        ArrayList<address> addresses = new ArrayList<>();
        List<Address> addressList = null;

        try{
            addressList = this.geocoder.getFromLocationName(locationName,maxResults);
        }catch(IOException ioException) {
            this.error = "IOException "+ioException.getMessage();
        } catch (IllegalArgumentException illegalArgumentException) {
            this.error = "IllegalArgumentException " + illegalArgumentException.getMessage();
        }

        if (addressList != null && addressList.size()  > 0) {
            for(int i = 0; i < addressList.size(); i++) {
                addresses.add(new address(addressList.get(i)));
            }
        }
        this.addresses = addresses;
        return addresses;
    }

    public class address{
        public String countryCode;
        public String countryName;
        public String postalCode;
        public String locality;
        public String phone;
        public String subAdminArea;
        public String sublocality;
        public String subThoroughfare;
        public String thoroughfare;
        public String url;
        public String premises;
        public String featureName;
        public Bundle extras;
        public String adminArea;
        public LatLng position;
        public String addressLine;
        public ArrayList<String> addressFragments = new ArrayList<String>();

        public address( Address address ) {
            this.countryCode = address.getCountryCode();
            this.countryName = address.getCountryName();
            this.postalCode = address.getPostalCode();
            this.locality = address.getLocality();
            this.phone = address.getPhone();
            this.adminArea = address.getAdminArea();
            this.subAdminArea = address.getSubAdminArea();
            this.featureName = address.getFeatureName();
            this.sublocality = address.getSubLocality();
            this.subThoroughfare = address.getSubThoroughfare();
            this.thoroughfare = address.getThoroughfare();
            this.url = address.getUrl();
            this.premises = address.getPremises();
            this.extras = address.getExtras();

            if( address.hasLatitude() && address.hasLongitude())
                this.position = new LatLng(address.getLatitude(), address.getLongitude());

            if( address.getMaxAddressLineIndex() > 0){
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                    addressFragments.add(address.getAddressLine(i));
                this.addressLine = TextUtils.join(", ", addressFragments);
            }
        }
    }

    public String formatAddress(address address){
        try{
            if(address.addressLine != null)
                return address.addressLine;

            String ad = "";
            if(address.featureName != null)
                ad += address.featureName + ",";
            if(address.sublocality != null)
                ad += address.sublocality + ",";
            if(address.postalCode != null)
                ad += address.postalCode + ",";
            if(address.adminArea != null)
                ad += address.adminArea + ",";
            if(address.countryName != null)
                ad += address.countryName + ",";

            return ad;
        }catch (Exception e){
            return null;
        }
    }

}
