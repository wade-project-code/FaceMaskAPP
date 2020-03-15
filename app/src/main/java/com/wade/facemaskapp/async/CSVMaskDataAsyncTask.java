package com.wade.facemaskapp.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.wade.facemaskapp.R;
import com.wade.facemaskapp.bean.MaskBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Wade on 2020/3/14.
 */
public class CSVMaskDataAsyncTask extends AsyncTask<String, Integer, List<?>> {
    private HttpsURLConnection connection;
    private ICSVMaskData icsvMaskData;
    private Context mContext;
    private ProgressDialog dialog;
    private List<MaskBean> list;

    public interface ICSVMaskData{
        void getDataSuccess(List<?> list);
        void getDataError(String data);
    }

    public CSVMaskDataAsyncTask(Context mContext,ICSVMaskData icsvMaskData){
        this.mContext = mContext;
        this.icsvMaskData = icsvMaskData;
        list = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(mContext,"",mContext.getString(R.string.loading),true,false);
    }

    @Override
    protected List<?> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            if(connection.getResponseCode() != 200){
                icsvMaskData.getDataError(connection.getResponseMessage());
            }

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while((line = bufferedReader.readLine()) != null){
                String[] maskData = line.split(",");

                MaskBean bean = new MaskBean(
                        maskData[0], maskData[1], maskData[2],
                        maskData[3], maskData[4], maskData[5],
                        maskData[6]
                );

                list.add(bean);
            }

        } catch (Exception e) {
            icsvMaskData.getDataError(e.getMessage());
        } finally {
            connection.disconnect();
        }

        return list;
    }

    @Override
    protected void onPostExecute(List<?> objects) {
        super.onPostExecute(objects);
        dialog.dismiss();
        icsvMaskData.getDataSuccess(objects);
    }
}
