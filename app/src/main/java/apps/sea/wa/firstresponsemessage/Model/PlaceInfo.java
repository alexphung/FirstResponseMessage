package apps.sea.wa.firstresponsemessage.Model;

import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;

public class PlaceInfo {
    private String id;
    private CharSequence name;
    private CharSequence address;
    private CharSequence phoneNumber;
    private Uri websiteUri;
    private LatLng latLng;
    private CharSequence attributions;
    private LatLngBounds viewport;
    private Locale locale;
    private float rating;
    private List<Integer> placeTypes;
    private int priceLevel;

    public PlaceInfo() {
    }

    public PlaceInfo(Place mPlace){
        this.id = mPlace.getId();
        this.name = mPlace.getName();
        this.address = mPlace.getAddress();
        this.phoneNumber = mPlace.getPhoneNumber();
        this.websiteUri = mPlace.getWebsiteUri();
        this.latLng = mPlace.getLatLng();
        this.attributions = mPlace.getAttributions();
        this.viewport = mPlace.getViewport();
        this.locale = mPlace.getLocale();
        this.rating = mPlace.getRating();
        this.placeTypes = mPlace.getPlaceTypes();
        this.priceLevel = mPlace.getPriceLevel();
    }

    public PlaceInfo(String id, CharSequence name, CharSequence address, CharSequence phoneNumber,
                     Uri websiteUri, LatLng latLng, CharSequence attributions, LatLngBounds viewport,
                     Locale locale, float rating, List<Integer> placeTypes, int priceLevel) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.websiteUri = websiteUri;
        this.latLng = latLng;
        this.attributions = attributions;
        this.viewport = viewport;
        this.locale = locale;
        this.rating = rating;
        this.placeTypes = placeTypes;
        this.priceLevel = priceLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public CharSequence getAddress() {
        return address;
    }

    public void setAddress(CharSequence address) {
        this.address = address;
    }

    public CharSequence getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(CharSequence phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Uri getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public CharSequence getAttributions() {
        return attributions;
    }

    public void setAttributions(CharSequence attributions) {
        this.attributions = attributions;
    }

    public LatLngBounds getViewport() {
        return viewport;
    }

    public void setViewport(LatLngBounds viewport) {
        this.viewport = viewport;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Integer> getPlaceTypes() {
        return placeTypes;
    }

    public void setPlaceTypes(List<Integer> placeTypes) {
        this.placeTypes = placeTypes;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "id='" + id + '\'' +
                ", name='" + name+ '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", websiteUri=" + websiteUri +
                ", latLng=" + latLng +
                ", attributions=" + attributions +
                ", viewport=" + viewport +
                ", locale=" + locale +
                ", rating=" + rating +
                ", placeTypes=" + placeTypes +
                ", priceLevel=" + priceLevel +
                '}';
    }
}
