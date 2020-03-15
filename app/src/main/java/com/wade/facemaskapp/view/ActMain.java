package com.wade.facemaskapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wade.facemaskapp.adapter.MaskRecyclerView;
import com.wade.facemaskapp.R;
import com.wade.facemaskapp.async.CSVMaskDataAsyncTask;
import com.wade.facemaskapp.async.HospitalDataAsyncTask;
import com.wade.facemaskapp.async.JSONMaskDataAsyncTask;
import com.wade.facemaskapp.bean.HospitalBean;
import com.wade.facemaskapp.bean.MaskBean;
import com.wade.facemaskapp.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

public class ActMain extends AppCompatActivity {
    private List<MaskBean> beanList;
    private List<MaskBean> maskBeanList;
    private List<MaskBean> searchList;
    private List<HospitalBean> hospitalBeanList;
    private RecyclerView mRecyclerView;
    private MaskRecyclerView adapter;
    private FloatingActionButton mFloatingActionButton;
    private BottomAppBar mBottomAppBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private View.OnClickListener mFloatingActionButton_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ActMain.this,R.style.DialogStyle);
            View v = LayoutInflater.from(ActMain.this).inflate(R.layout.dialog_search, null);
            builder.setView(v);

            final EditText mTxtSearch = v.findViewById(R.id.mTxtSearch);
            Button mBtnYes = v.findViewById(R.id.mBtnYes);
            Button mBtnNo = v.findViewById(R.id.mBtnNo);

            final AlertDialog dialog = builder.create();

            mBtnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchList = new ArrayList<>();

                    boolean findAddress = false;

                    for(int i = 0; i< beanList.size(); i++){
                        if(beanList.get(i).getAddress().contains(mTxtSearch.getText().toString())){
                            searchList.add(beanList.get(i));
                            findAddress = true;
                        }
                    }

                    if(!findAddress){
                        for(int i = 0; i< beanList.size(); i++){
                            if(beanList.get(i).getName().contains(mTxtSearch.getText().toString())){
                                searchList.add(beanList.get(i));
                            }
                        }
                    }

                    adapter.updateData(searchList);
                    mRecyclerView.scrollToPosition(0);
                    dialog.cancel();
                }
            });

            mBtnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });

            dialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getCSVMaskData();
        getHospitalData();
    }

    private void init(){
        beanList = new ArrayList<>();
        maskBeanList = new ArrayList<>();
        hospitalBeanList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == 1){
                    mBottomAppBar.setVisibility(View.GONE);
                    mFloatingActionButton.hide();
                }else if(newState == 0){
                    mBottomAppBar.setVisibility(View.VISIBLE);
                    mFloatingActionButton.show();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mFloatingActionButton = findViewById(R.id.mFloatingActionButton);
        mFloatingActionButton.setOnClickListener(mFloatingActionButton_click);
        mBottomAppBar = findViewById(R.id.mBottomAppBar);
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCSVMaskData();
                getHospitalData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        setSupportActionBar(mBottomAppBar);

        adapter = new MaskRecyclerView(maskBeanList,hospitalBeanList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getMaskData(){
        new JSONMaskDataAsyncTask(ActMain.this, new JSONMaskDataAsyncTask.IMaskData() {
            @Override
            public void getDataSuccess(String data) {
                JsonToObject(data);
                adapter.updateData(maskBeanList);
            }

            @Override
            public void getDataError(String data) {
            }
        }).execute(UrlUtil.MASKDATA_JSON_URL);
    }

    private void getCSVMaskData(){
        new CSVMaskDataAsyncTask(ActMain.this, new CSVMaskDataAsyncTask.ICSVMaskData() {
            @Override
            public void getDataSuccess(List<?> list) {
                list.remove(0);
                beanList.addAll((List<MaskBean>) list);
                adapter.updateData((List<MaskBean>) list);
            }

            @Override
            public void getDataError(String data) {

            }
        }).execute(UrlUtil.MASKDATA_CSV_URL);
    }

    private void getHospitalData(){
        new HospitalDataAsyncTask(ActMain.this,new HospitalDataAsyncTask.IHospitalData(){

            @Override
            public void getDataSuccess(List<?> list) {
                list.remove(0);
                hospitalBeanList.addAll((List<HospitalBean>) list);
            }

            @Override
            public void getDataError(String data) {

            }
        }).execute(UrlUtil.HOSPITALDATA_URL);
    }

    private void JsonToObject(String json){
        Gson gson = new Gson();
        maskBeanList = gson.fromJson(json, new TypeToken<List<MaskBean>>(){}.getType());
    }
}
