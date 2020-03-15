package com.wade.facemaskapp.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.wade.facemaskapp.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Wade on 2020/3/14.
 */
public class JSONMaskDataAsyncTask extends AsyncTask<String, Integer, String> {
    private HttpsURLConnection connection;
    private IMaskData iMaskData;
    private Context mContext;
    private ProgressDialog dialog;
    private StringBuilder sb = new StringBuilder();

    public interface IMaskData {
        void getDataSuccess(String data);
        void getDataError(String data);
    }

    public JSONMaskDataAsyncTask(Context mContext, IMaskData iMaskData){
        this.mContext = mContext;
        this.iMaskData = iMaskData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(mContext,"",mContext.getString(R.string.loading),true,false);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            connection = (HttpsURLConnection)url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            if(connection.getResponseCode() != 200){
                iMaskData.getDataError(connection.getResponseMessage());
            }

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;

            while((line = reader.readLine()) != null){
                sb.append(line);
            }

        } catch (Exception e) {
            iMaskData.getDataError(e.getMessage());
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        iMaskData.getDataSuccess(s);
    }
}
