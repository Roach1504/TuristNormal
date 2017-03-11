package com.example.roach12.turistnormal.NetWork.Track;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TrackStart extends AsyncTask<Void, Void, Void> {
    String userId;
    String trackId = "0";
    protected String message;

    public TrackStart(String userId){
        this.userId = userId;
    }
    // TODO: 08.03.2017 Запрос на получения нового ID трека

    public String getTrackId() {
        return trackId;
    }

    protected String getStatus(String key, String strJson) {
        JSONObject dataJsonObj = null;
        String secondName = "";
        try {
            dataJsonObj = new JSONObject(strJson);
            secondName = dataJsonObj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return secondName;
    }
    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("tourist_id", userId)
                .addEncoded("track_title", "")
                .build();
        Request request = new Request.Builder()
                .url("http://109.120.189.141:81/web/api/track/new")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build();

        okhttp3.Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        };
            callback.onResponse(call, response);
            message = response.body().string().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        if(message != null){
            trackId = getStatus("track_id",message);
            Log.e("Получил ID", "id="+trackId);
        }
        else {
            trackId = "false";
            Log.e("error", "Проблемы с соеденениям");
        }
        super.onPostExecute(aVoid);
    }
}
