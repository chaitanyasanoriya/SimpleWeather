package com.chaitanya.sanoriya.weather;

import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

/**
 * Created by WildFire on 19-12-2017.
 */

public class NetworkUtils
{
    private static String weather_base = "http://dataservice.accuweather.com/currentconditions/v1/202369";
    private static String api_key = "GAmHeCEOqJtl3Uf4j3OOVpKAbM7XCZf0";
    private static String param_api_key = "apikey";
    public static URL builderUrlWeather()
    {
        Uri builturi = Uri.parse(weather_base).buildUpon().appendQueryParameter(param_api_key,api_key).build();
        URL url = null;
        try
        {
            url = new URL(builturi.toString());
        }catch (Exception e)
        {

        }
        Log.i(TAG,"buildURLForWeather URL : "+url);
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException
    {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try
        {
            InputStream in = urlConnection.getInputStream();
            Scanner scan = new Scanner(in);
            scan.useDelimiter("\\A");
            boolean hasInput = scan.hasNext();
            if(hasInput)
            {
                return scan.next();
            }
            else
            {
                return null;
            }

        }finally
        {
            urlConnection.disconnect();
        }
    }
}
