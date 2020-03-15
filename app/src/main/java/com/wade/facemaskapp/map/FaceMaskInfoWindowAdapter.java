package com.wade.facemaskapp.map;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.wade.facemaskapp.R;

/**
 * Created by Wade on 2020/3/12.
 */
public class FaceMaskInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity mActivity;

    public FaceMaskInfoWindowAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.recyclerview_mask, null);

        CardView mCardView;
        TextView mTxtName,mTxtAdultMask,mTxtChildMask,mTxtAddress,mTxtTel,mTxtDateTime;
        String[] info = marker.getSnippet().split(",");

        mCardView = view.findViewById(R.id.mCardView);
        mTxtName = view.findViewById(R.id.mTxtName);
        mTxtAdultMask = view.findViewById(R.id.mTxtAdultMask);
        mTxtChildMask = view.findViewById(R.id.mTxtChildMask);
        mTxtAddress = view.findViewById(R.id.mTxtAddress);
        mTxtTel = view.findViewById(R.id.mTxtTel);
        mTxtDateTime = view.findViewById(R.id.mTxtDateTime);

        mTxtName.setText(marker.getTitle());
        mTxtAddress.setText(info[0]);
        mTxtTel.setText(info[1]);
        mTxtDateTime.setText(info[2]);
        mTxtAdultMask.setText(info[3]);
        mTxtChildMask.setText(info[4]);

        return view;
    }
}
