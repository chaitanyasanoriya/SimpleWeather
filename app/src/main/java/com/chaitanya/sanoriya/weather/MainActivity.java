package com.chaitanya.sanoriya.weather;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private TextView textView_1;
    private EditText editText;
    private Button btn;
    private android.support.design.widget.TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_1 = (TextView) findViewById(R.id.textview_1);
        editText = (EditText) findViewById(R.id.editText);
        btn = (Button) findViewById(R.id.btn);
        textInputLayout = (android.support.design.widget.TextInputLayout) findViewById(R.id.text_input_layout);
        //textInputLayout.setVisibility(View.INVISIBLE);
        //btn.setVisibility(View.INVISIBLE);
        //editText.setVisibility(View.INVISIBLE);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String user = editText.getText().toString();
                if (user.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Username is empty",Toast.LENGTH_LONG).show();
                } else
                {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("name", user);
                    startActivity(intent);
                }
            }
        });
        /*final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        textView_1.startAnimation(animationFadeIn);
        animationFadeIn.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                textInputLayout.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });*/
    }

    /*protected void trans(View v)
    {
        String user = editText.getText().toString();
        if (user.isEmpty())
        {
            Toast.makeText(this, "Username is empty", Toast.LENGTH_LONG).show();
        } else
        {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("name", user);
            startActivity(intent);
        }
    }*/

}
