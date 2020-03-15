package com.wade.facemaskapp.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wade.facemaskapp.async.CSVMaskDataAsyncTask;
import com.wade.facemaskapp.map.FaceMaskInfoWindowAdapter;
import com.wade.facemaskapp.R;
import com.wade.facemaskapp.bean.MaskBean;
import com.wade.facemaskapp.utils.UrlUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MaskBean maskBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getMaskAddress();
    }

    private void getMaskAddress(){
        Intent intent = getIntent();
        maskBean = intent.getParcelableExtra(MaskBean.MASK_BEAN);

        mMap.setInfoWindowAdapter(new FaceMaskInfoWindowAdapter(this));

        try{
            Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
            List<Address> addressLocation;
            addressLocation = geoCoder.getFromLocationName(maskBean.getAddress(), 1);
            double latitude = addressLocation.get(0).getLatitude();
            double longitude = addressLocation.get(0).getLongitude();

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(latitude,longitude))
                    .title(maskBean.getName())
                    .snippet(maskBean.getAddress() + "," +
                            maskBean.getTel() + "," +
                            maskBean.getDatetime() + "," +
                            maskBean.getAdultmask() + "," +
                            maskBean.getChildmask());
            mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),14));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
