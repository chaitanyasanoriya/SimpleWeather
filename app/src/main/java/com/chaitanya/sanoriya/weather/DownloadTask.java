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

public class DownloadTask extends AsyncTask<String, Void,String>
{
    private String loc;
    private LatLng loc1;

    public void setMa(MapsActivity ma, String loc, LatLng loc1)
    {
        this.ma = ma;
        this.loc = loc;
        this.loc1 = loc1;
    }

    private MapsActivity ma;

    @Override
    protected String doInBackground(String ... urls)
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
            Log.i("Response Code DT : ",""+status);
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();
            while(data !=-1)
            {
                char current = (char) data;
                result +=current;
                data = reader.read();
            }
            return result;
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        try
        {
            result = result.substring(1,result.length()-1);
            JSONObject jsonObject = new JSONObject(result);
            JSONObject temp = new JSONObject(jsonObject.getString("Temperature"));
            JSONObject tempm = new JSONObject(temp.getString("Metric"));
            String str = tempm.getString("Value");
            Log.i("temp : ",str);
            ma.tempin = str;
            String cond;
            cond = jsonObject.getString("WeatherText");
            ma.cond = cond;
            ma.locate.setText(str);
            while(ma.m==null)
            {
                try
                {
                    Thread.sleep(100);
                    Log.i("Ma.m : ","stuck");
                }
                catch (Exception e)
                {}
            }
            ma.m.setTitle(loc+" : "+cond+" : "+str+" degrees");
            ma.m.showInfoWindow();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
