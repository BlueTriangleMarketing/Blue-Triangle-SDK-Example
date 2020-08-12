package com.example.testbluetrianglesdk;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;
import com.bluetriangle.analytics.Timer;

public class MovieDataBaseRunner extends AsyncTask<String, String, JSONObject> {
    public AsyncResponse delegate = null;//Call back interface
    private Timer timer;

    @Override
    protected JSONObject doInBackground(String... strings) {
        final Timer timer = new Timer("Async Next Page", "Android Traffic 3").start();
        String responseJson = "";
        try {
            URL url = new URL("https://movie-database-imdb-alternative.p.rapidapi.com/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-rapidapi-host","movie-database-imdb-alternative.p.rapidapi.com");
            connection.setRequestProperty("x-rapidapi-key","6c926d38d2mshcb9db754e1dd50cp11a347jsn0e5ac212038b");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("useQueryString","true");
            connection.setDoOutput(true);
            String jsonInputString = "{\"page\": \"1\",\"r\": \"json\",\"s\": \"Avengers Endgame\"}";

            Log.i("JsonString",jsonInputString);
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            Log.i("STATUS", String.valueOf(connection.getResponseCode()));
            Log.i("MSG" , connection.getResponseMessage());
            String responseLine = null;
            StringBuilder response = new StringBuilder();

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine);
                }
                Log.i("ResponseBody" ,response.toString());
            }

            responseJson = response.toString();

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            Log.i("error" , Objects.requireNonNull(e.getMessage()));
        }

        JSONObject responseObject = null;
        try {
            responseObject = new JSONObject(responseJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        timer.end().submit();
        return responseObject;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        delegate.processFinish(result);

    }

}
