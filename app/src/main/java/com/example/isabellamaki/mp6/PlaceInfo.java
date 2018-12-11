package com.example.isabellamaki.mp6;

import android.net.Uri;
import com.google.android.gms.maps.model.LatLng;

public class PlaceInfo {
    private String name;
    private String address;
    private String phoneNumber;
    private String id;
    private Uri websiteUri;
    private LatLng latlng;
    private float rating;

    public PlaceInfo(String name, String address, String phoneNumber, String id, Uri websiteUri,
                         LatLng latlng, float rating) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.websiteUri = websiteUri;
        this.latlng = latlng;
        this.rating = rating;
    }

    public PlaceInfo() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }

    public Uri getWebsiteUri() {
        return websiteUri;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "PlaceInfo {" + "name='" + name + '\'' + ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' + ", id='" + id + '\'' +
                ", websiteUri=" + websiteUri + ", latlng=" + latlng + ", rating=" + rating + '}';
    }
}
