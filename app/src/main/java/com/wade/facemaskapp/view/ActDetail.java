package com.wade.facemaskapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wade.facemaskapp.R;
import com.wade.facemaskapp.bean.HospitalBean;
import com.wade.facemaskapp.bean.MaskBean;
import com.wade.facemaskapp.utils.ToolUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ActDetail extends AppCompatActivity {
    private CardView mCardView;
    private TextView mTxtName,mTxtAdultMask,mTxtChildMask,mTxtAddress,mTxtTel,mTxtDateTime;
    private TextView mTxtSalesType,mTxtSpecialType,mTxtYear,mTxtWeek,mTxtRemark,mTxtState;
    private ImageView mImgMap,mImgTel,mImgPath;
    private TableLayout mTableLayout;
    private String[] weekArray = new String[]{" ","一","二","三","四","五","六","日"};
    private String[] timeArray = new String[]{" ","上午","下午","晚上"};
    private MaskBean maskBean;
    private HospitalBean hospitalBean;
    private int index = 0;

    private View.OnClickListener mImgMap_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActDetail.this, ActMap.class);
            intent.putExtra(MaskBean.MASK_BEAN,maskBean);
            startActivity(intent);
        }
    };

    private View.OnClickListener mImgTel_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:" + maskBean.getTel());
            intent.setData(uri);
            startActivity(intent);
        }
    };

    private View.OnClickListener mImgPath_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Geocoder geoCoder = new Geocoder(ActDetail.this, Locale.getDefault());

            try {
                List<Address> addressLocation = geoCoder.getFromLocationName(maskBean.getAddress(), 1);
                double latitude = addressLocation.get(0).getLatitude();
                double longitude = addressLocation.get(0).getLongitude();

                Intent intent = new Intent();
                Uri uri = Uri.parse("google.navigation:q=" +
                        latitude + "," + longitude +
                        "&mode=d");
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
    }

    private void init(){
        Intent intent = getIntent();
        maskBean = intent.getParcelableExtra(MaskBean.MASK_BEAN);
        hospitalBean = intent.getParcelableExtra(HospitalBean.HOSPITAL_BEAN);

        mTableLayout = findViewById(R.id.mTableLayout);
        mImgMap = findViewById(R.id.mImgMap);
        mImgTel = findViewById(R.id.mImgTel);
        mImgPath = findViewById(R.id.mImgPath);
        mCardView = findViewById(R.id.mCardView);
        mTxtName = findViewById(R.id.mTxtName);
        mTxtAdultMask = findViewById(R.id.mTxtAdultMask);
        mTxtChildMask = findViewById(R.id.mTxtChildMask);
        mTxtAddress = findViewById(R.id.mTxtAddress);
        mTxtTel = findViewById(R.id.mTxtTel);
        mTxtDateTime = findViewById(R.id.mTxtDateTime);
        mTxtSalesType = findViewById(R.id.mTxtSalesType);
        mTxtSpecialType = findViewById(R.id.mTxtSpecialType);
        mTxtYear = findViewById(R.id.mTxtYear);
        mTxtWeek = findViewById(R.id.mTxtWeek);
        mTxtRemark = findViewById(R.id.mTxtRemark);
        mTxtState = findViewById(R.id.mTxtState);

        mImgMap.setOnClickListener(mImgMap_click);
        mImgTel.setOnClickListener(mImgTel_click);
        mImgPath.setOnClickListener(mImgPath_click);


        mTxtName.setText(maskBean.getName());
        mTxtAdultMask.setText(maskBean.getAdultmask());
        mTxtChildMask.setText(maskBean.getChildmask());
        mTxtAddress.setText(maskBean.getAddress());
        mTxtTel.setText(maskBean.getTel());

        mTxtDateTime.setText(hospitalBean.getDatetime());
        mTxtSalesType.setText(ToolUtil.getSalesType(hospitalBean.getSalesType()));
        mTxtSpecialType.setText(ToolUtil.getSpecialType(hospitalBean.getSpecialType()));
        mTxtYear.setText(hospitalBean.getYear());
        mTxtRemark.setText(hospitalBean.getRemark());
        mTxtState.setText(ToolUtil.getState(hospitalBean.getState()));

        char[] weekData = hospitalBean.getWeek().toCharArray();

        for (int i=0; i<4; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j=0; j<8; j++){
                TextView textView = new TextView(this);
                LinearLayout.LayoutParams lp = new TableRow.LayoutParams(-1,-1,1.0f);
                lp.setMargins(1,1,1,1);
                textView.setLayoutParams(lp);

                if(i==0){
                    textView.setText(weekArray[j]);
                }else if(j==0){
                    textView.setText(timeArray[i]);
                }else{
                    if(weekData[index]=='N'){
                        textView.setText(R.string.hospital_week_n);
                        textView.setTextColor(Color.BLUE);
                    }else if(weekData[index]=='Y'){
                        textView.setText(R.string.hospital_week_y);
                        textView.setTextColor(Color.RED);
                    }
                    index++;
                }

                textView.setTextSize(16);
                textView.setGravity(Gravity.CENTER);
                tableRow.addView(textView);
            }
            mTableLayout.addView(tableRow);
        }
    }
}
