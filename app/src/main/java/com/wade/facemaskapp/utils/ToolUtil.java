package com.wade.facemaskapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.wade.facemaskapp.R;

/**
 * Created by Wade on 2020/3/14.
 */
public class ToolUtil {
    private static final String CONNECTIVITY_MANAGER = "ConnectivityManager";

    public static boolean isCheckNetwork(final Context mContext){
        ConnectivityManager CM = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = CM.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()){
            new AlertDialog.Builder(mContext)
                    .setTitle(mContext.getString(R.string.dialog_network_title_error))
                    .setMessage(mContext.getString(R.string.dialog_network_msg))
                    .setIcon(R.drawable.ic_cancel_24dp)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((Activity)(mContext)).finish();
                        }
                    })
                    .show();
            return false;
        }else{
            Log.e(CONNECTIVITY_MANAGER,String.valueOf(info.getTypeName()));     // 目前以何種方式連線 [WIFI]
            Log.e(CONNECTIVITY_MANAGER,String.valueOf(info.getState()));         // 目前連線狀態 [CONNECTED]
            Log.e(CONNECTIVITY_MANAGER,String.valueOf(info.isAvailable()));      // 目前網路是否可使用 [true]
            Log.e(CONNECTIVITY_MANAGER,String.valueOf(info.isConnected()));      // 網路是否已連接 [true]
            Log.e(CONNECTIVITY_MANAGER,String.valueOf(info.isConnectedOrConnecting()));  // 網路是否已連接 或 連線中 [true]
            Log.e(CONNECTIVITY_MANAGER,String.valueOf(info.isFailover()));      // 網路目前是否有問題 [false]
            Log.e(CONNECTIVITY_MANAGER,String.valueOf(info.isRoaming()));        // 網路目前是否在漫遊中 [false]
            return true;
        }
    }

    public static String getSalesType(String s){
        switch (s){
            case "1":
                return "台北業務組";
            case "2":
                return "北區業務組";
            case "3":
                return "中區業務組";
            case "4":
                return "南區業務組";
            case "5":
                return "高屏業務組";
            case "6":
                return "東區業務組";
            default:
                return "業務組";
        }
    }

    public static String getSpecialType(String s){
        switch (s){
            case "1":
                return "醫學中心";
            case "2":
                return "區域醫院";
            case "3":
                return "地區醫院";
            case "4":
                return "基層院所";
            default:
                return "醫院";
        }
    }

    public static String getState(String s){
        switch (s){
            case "0":
                return "開業";
            case "2":
                return "歇業";
            case "3":
                return "公告註銷";
            case "6":
                return "換區歇業";
            case "7":
                return "跨分區業務組遷出";
            case "9":
                return "變更負責人";
            case "A":
                return "終止合約(違約被停)";
            case "B":
                return "不續約(合約到期)";
            case "C":
                return "醫院自行暫停";
            default:
                return "";
        }
    }

}
