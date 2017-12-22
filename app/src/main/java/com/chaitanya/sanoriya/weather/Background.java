package com.chaitanya.sanoriya.weather;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by WildFire on 19-12-2017.
 */

public class Background implements Runnable
{
    MapsActivity ma;
    String loc;
    LatLng loc1 = null;
    @Override
    public void run()
    {
        //String str = "http://dataservice.accuweather.com/locations/v1/search?apikey=GAmHeCEOqJtl3Uf4j3OOVpKAbM7XCZf0&q=" + loc;
        //String str = "http://dataservice.accuweather.com/locations/v1/search?apikey=7aKdJ5IGCDmAgEYpfR6EiqqaZNqgKV9u&q=" + loc;
        String str = "http://dataservice.accuweather.com/locations/v1/search?apikey=UkRBjxxthQLxolM1ol8gXQyxNX95Hlnr&q=" + loc;
        GetCode code = new GetCode();
        try
        {
            code.setItems(Background.this,ma,loc,loc1);
            code.execute(str);
        } catch (Exception e)
        {
            Log.i("Background : ", "Error in Getting place code");
        }

    }

}
