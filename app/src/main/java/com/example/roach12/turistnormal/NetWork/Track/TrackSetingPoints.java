package com.example.roach12.turistnormal.NetWork.Track;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TrackSetingPoints extends AsyncTask<Void, Void, Void> {
    String json;
    protected String message;
    public TrackSetingPoints(String json){
        this.json = json;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("points", json)
                .build();
        Request request = new Request.Builder()
                .url("http://109.120.189.141:81/web/api/track/add-poin")
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
        super.onPostExecute(aVoid);
    }
}
