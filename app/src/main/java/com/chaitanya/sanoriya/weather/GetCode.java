package com.chaitanya.sanoriya.weather;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by WildFire on 19-12-2017.
 */

public class GetCode extends AsyncTask<String, Void, String>
{
    Background bg;
    MapsActivity ma;
    private String loc;
    private LatLng loc1;

    @Override
    protected String doInBackground(String... urls)
    {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try
        {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            int status = urlConnection.getResponseCode();
            if(status!=200)
            {
                ma.stat();
            }
            Log.i("Response Code GC : ", "" + status);
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();
            while (data != -1)
            {
                char current = (char) data;
                result += current;
                data = reader.read();
            }
            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (result.equals(""))
        {
            return null;
        } else
        {
            return result;
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        try
        {
            result = result.substring(1, result.length() - 1);
            System.out.println(result);
            JSONObject jsonObject = new JSONObject(result);
            String str = jsonObject.getString("Key");
            System.out.println(str);
            Log.i("Place code : ", str);
            //str = "http://dataservice.accuweather.com/currentconditions/v1/" + str + "?apikey=GAmHeCEOqJtl3Uf4j3OOVpKAbM7XCZf0";
            //str = "http://dataservice.accuweather.com/currentconditions/v1/" + str + "?apikey=7aKdJ5IGCDmAgEYpfR6EiqqaZNqgKV9u";
            str = "http://dataservice.accuweather.com/currentconditions/v1/" + str + "?apikey=UkRBjxxthQLxolM1ol8gXQyxNX95Hlnr";
            DownloadTask task = new DownloadTask();
            try
            {
                task.setMa(ma,loc,loc1);
                task.execute(str);
            } catch (Exception e)
            {
                Log.i("Background : ", "Error in Getting data");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setItems(Background bg, MapsActivity ma, String loc, LatLng loc1)
    {
        this.bg = bg;
        this.ma = ma;
        this.loc = loc;
        this.loc1 = loc1;
    }
}
