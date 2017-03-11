package com.example.roach12.turistnormal.trackWork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.roach12.turistnormal.NetWork.Track.TrackSetingPoints;
import com.example.roach12.turistnormal.NetWork.Track.TrackStart;
import com.example.roach12.turistnormal.dateBase.CreatDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;


class WorkTimerTrack extends TimerTask{
    Context context;
    public WorkTimerTrack(Context mContext){
        this.context = mContext;
    }
    private boolean hasConnection() {
        if(context != null) {
            ConnectivityManager cm = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        else {
            return false;
        }
    }
    private String pointsX;
    private String pointsY;
    private String userId;
    private String trackId;
    protected SQLiteDatabase database;
    CreatDB dbHelper =  new CreatDB(context, "Baykal_DB", 1);

    protected String jsonArray(String trackId, String pointsX, String pointsY, String data,String marker){
        JSONObject pointServer = new JSONObject();
        try {
            pointServer.put("track_id", trackId);
            pointServer.put("x", pointsX);
            pointServer.put("y", pointsY);
            pointServer.put("date", data);
            pointServer.put("break", marker);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pointServer.toString();
    }

    private void savePoints(String data){
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreatDB.trackFildPoint_X, pointsX);
        contentValues.put(CreatDB.trackFildPoint_Y, pointsY);
        contentValues.put(CreatDB.trackFildTrackID, trackId);
        contentValues.put(CreatDB.trackFildDate, data);
        contentValues.put(CreatDB.trackFildMarker, "0");
        database.insert(CreatDB.TABLE_TREAKS, null, contentValues);
        Log.e("Coхранения точек в БД", pointsX +", "+ pointsY+", " + data+", "+ trackId);
        // TODO: 11.03.2017 Cохранение точек в бд
    }
    private void SetingPointsOutDB(String data) {
        JSONArray json = new JSONArray();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(dbHelper.TABLE_TREAKS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            Log.e("Отправка точек из базы","Точки есть");
            int keyX = cursor.getColumnIndex(CreatDB.trackFildPoint_X);
            int keyY = cursor.getColumnIndex(CreatDB.trackFildPoint_Y);
            int keyData = cursor.getColumnIndex(CreatDB.trackFildDate);
            int keyId = cursor.getColumnIndex(CreatDB.trackFildTrackID);
            int keyMarker = cursor.getColumnIndex(CreatDB.trackFildMarker);
            do {
                json.put(jsonArray(cursor.getString(keyId),cursor.getString(keyX),cursor.getString(keyY),cursor.getString(keyData),cursor.getString(keyMarker)));
            }
            while (cursor.moveToNext());
            TrackSetingPoints trackSetingPoints = new TrackSetingPoints(json.toString());
            trackSetingPoints.execute();
            // TODO: 08.03.2017 Отправка точек из базы данных, если точек нет то ни чего не делать
        }
        else{
            Log.e("Отправка точек из базы","Точек нет");
        }
    }

    @Override
    public void run() {
        TrackWork trackWork = new TrackWork(context);
        pointsX = trackWork.getPointsX();
        pointsY = trackWork.getPointsY();
        userId = trackWork.getUserId();
        trackId =trackWork.getTrackId();

        Calendar calendar = Calendar.getInstance();
        String dataMessage = calendar.getTime().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = dateFormat.format(new Date());

        if(hasConnection()){
            if(trackId.equals("0")){
                TrackStart trackStart = new TrackStart(userId);
                trackStart.execute();
                while (trackId.equals("0")){
                    trackId = trackStart.getTrackId();
                    // TODO: 11.03.2017 иду за id_track
                }
                if(!trackId.equals("false")){
                    JSONArray json = new JSONArray();
                    json.put(jsonArray(trackId, pointsX, pointsY, data,"0"));
                    TrackSetingPoints trackSetingPoints = new TrackSetingPoints(json.toString());
                    trackSetingPoints.execute();
                    SetingPointsOutDB(data);
                }
                else {
                    Log.e("Error", "Нет подключения к cерверу");
                }
            }
            else{
                JSONArray json = new JSONArray();
                json.put(jsonArray(trackId, pointsX, pointsY, data,"0"));
                if(!trackId.equals("false")){
                    TrackSetingPoints trackSetingPoints = new TrackSetingPoints(json.toString());
                    trackSetingPoints.execute();
                }
                else {
                    Log.e("Error", "не получил ID(Class WorkTimerTrack");
                }
            }
        }
        else{
            savePoints(data);
        }
    }
}
