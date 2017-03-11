package com.example.roach12.turistnormal.trackWork;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import static com.vk.sdk.VKUIHelper.getApplicationContext;

public class TrackWork {
    Context context;

    public TrackWork(Context context) {
        this.context = context;
    }

    private String pointsX;
    private String pointsY;
    private String userId;
    private String trackId = "0";

    public boolean hasConnection() {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (wifiInfo != null && wifiInfo.isConnected()) {
                return true;
            }
            wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo != null && wifiInfo.isConnected()) {
                return true;
            }
            wifiInfo = cm.getActiveNetworkInfo();
            if (wifiInfo != null && wifiInfo.isConnected()) {

                return true;
            }
            return false;
        }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPointsXandY(String pointsX, String pointsY){
        this.pointsX = pointsX;
        this.pointsY = pointsY;
    }

    public String getUserId() {
        return userId;
    }

    public String getPointsX() {
        return pointsX;
    }

    public String getPointsY() {
        return pointsY;
    }

    public String getTrackId() {
        return trackId;
    }

    public void startWork(){
        if(hasConnection()){
            Log.e("TrackWork","True");
        }
        else{
            Log.e("TrackWork","false");
        }
    }
    public void stopTrack(){


        // TODO: 08.03.2017 сохранение точку с маркером прерывания
    }

}
