package com.wade.facemaskapp.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.wade.facemaskapp.R;
import com.wade.facemaskapp.bean.HospitalBean;

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
public class HospitalDataAsyncTask extends AsyncTask<String,Integer,List<?>> {
    private HttpsURLConnection connection;
    private IHospitalData iHospitalData;
    private Context mContext;
    private ProgressDialog dialog;
    private List<HospitalBean> list;

    public interface IHospitalData {
        void getDataSuccess(List<?> list);
        void getDataError(String data);
    }

    public HospitalDataAsyncTask(Context mContext, IHospitalData iHospialData) {
        this.iHospitalData = iHospialData;
        this.mContext = mContext;
        list = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(mContext,"", mContext.getString(R.string.loading),true,false);
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
                iHospitalData.getDataError(connection.getResponseMessage());
            }

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while((line = bufferedReader.readLine()) != null){
                String[] HospitalData = line.split(",");

                HospitalBean bean = new HospitalBean(
                        HospitalData[0], HospitalData[1], HospitalData[2],
                        HospitalData[3], HospitalData[4], HospitalData[5],
                        HospitalData[6], HospitalData[7], HospitalData[8]
                );

                list.add(bean);
            }

        } catch (Exception e) {
            iHospitalData.getDataError(e.getMessage());
        } finally {
            connection.disconnect();
        }

        return list;
    }

    @Override
    protected void onPostExecute(List<?> objects) {
        super.onPostExecute(objects);
        dialog.dismiss();
        iHospitalData.getDataSuccess(objects);
    }
}
