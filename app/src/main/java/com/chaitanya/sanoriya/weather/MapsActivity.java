package com.chaitanya.sanoriya.weather;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private static final String TAG = MapsActivity.class.getSimpleName();
    protected GoogleMap mMap;
    protected EditText locate;
    private String location;
    private String user;
    private TextView user_text;
    private ArrayList<GetData> weatherArrayList = new ArrayList<>();
    protected String tempin;
    protected String cond;
    private LatLng loc;
    protected Marker m;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mapFragment.getMapAsync(this);
        locate = (EditText) findViewById(R.id.locate);
        user_text = (TextView) findViewById(R.id.user_text);
        locate.setVisibility(View.INVISIBLE);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        final Animation animationFadeIn_1 = AnimationUtils.loadAnimation(this, R.anim.fadein);
        final Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        Intent intent = getIntent();
        try
        {
            user = intent.getStringExtra("name");
            user_text.setText("Hello, " + user);
            user_text.startAnimation(animationFadeIn);
        } catch (Exception e)
        {
            user_text.setVisibility(View.INVISIBLE);
        }
        animationFadeIn.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                locate.startAnimation(animationFadeIn_1);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        animationFadeIn_1.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                user_text.startAnimation(animationFadeOut);
                locate.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        animationFadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                user_text.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        final Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        URL weatherUrl = NetworkUtils.builderUrlWeather();
        Log.i(TAG, "Maps : " + weatherUrl);
        locate.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    location = locate.getText().toString();
                    Background background = new Background();
                    background.loc = location;
                    background.ma = MapsActivity.this;
                    Thread t1 = new Thread(background);
                    t1.start();
                    List<Address> addressList = null;
                    if (location.isEmpty())
                    {
                    } else
                    {
                        Geocoder geocoder = new Geocoder(MapsActivity.this);
                        try
                        {
                            addressList = geocoder.getFromLocationName(location, 1);
                            Address address = addressList.get(0);
                            locate.startAnimation(animationFadeOut);
                            loc = new LatLng(address.getLatitude(), address.getLongitude());
                            try
                            {
                                Thread.sleep(750);
                            }
                            catch (Exception e)
                            {}
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                            while(t1.isAlive())
                            {
                                try
                                {
                                    Thread.sleep(500);
                                }
                                catch (Exception e)
                                {}
                            }
                            m = mMap.addMarker(new MarkerOptions().position(loc));
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        animationFadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                locate.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    public void stat()
    {
        Toast.makeText(this, "Problem with api key", Toast.LENGTH_LONG).show();
    }
}
