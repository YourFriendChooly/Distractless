package com.project.distractless;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //String for passwordFile
    public static final String KEY = "KeyFile";
    String keyInstance;
    TextView keyView;
    TextView keyPrompt;
    String keyEntry = "";
    SharedPreferences keyStore;
    Boolean noKey;
    Boolean firstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linking interface objects
        keyPrompt = (TextView) findViewById(R.id.keyPrompt);
        keyView = (TextView) findViewById(R.id.keyView);

        keyStore = getSharedPreferences(KEY, 0);
        //Get Stored Key
        keyInstance = keyStore.getString("keyValue", "a");
        if (keyInstance.equals("a")){
            keyPrompt.setText("Select A 6 Digit Key");
            noKey = true;
        } else {
            keyPrompt.setText("Enter Your Key");
            noKey = false;
        }
    }

    //Code for NumberPad
    public void numPad (View view){
        switch(view.getId()){
            case R.id.numPad1:
                numData("1");
                break;
            case R.id.numPad2:
                numData("2");
                break;
            case R.id.numPad3:
                numData("3");
                break;
            case R.id.numPad4:
                numData("4");
                break;
            case R.id.numPad5:
                numData("5");
                break;
            case R.id.numPad6:
                numData("6");
                break;
            case R.id.numPad7:
                numData("7");
                break;
            case R.id.numPad8:
                numData("8");
                break;
            case R.id.numPad9:
                numData("9");
                break;
            case R.id.numPad0:
                numData("0");
                break;
        }
    }

    //Adds values to key, check password, if conditions are met spit output.
    public void numData (String num){
        keyEntry = keyEntry + num;
        keyView.setText(keyEntry);
        if (keyEntry.length() == 6 && noKey){
            keyPrompt.setText("Saved!");
            SharedPreferences.Editor keyEdit = keyStore.edit();
            keyEdit.putString("keyValue", keyEntry);
            keyEdit.commit();
            keyEntry = "";
        } else if (keyEntry.equals(keyInstance)){
            keyPrompt.setText("Match!");
            exitReveal();

        } else if (keyEntry.length() == 6 && !keyEntry.equals(keyInstance)){
            keyPrompt.setText("Invalid Password");
            keyEntry = "";
            keyView.setText(keyEntry);
        }
    }
    //Exit transition animation circular reveal
    void exitReveal() {
        final View mTransition = findViewById(R.id.pinScreen);
        //Get Center of screen for clip
        int cx = mTransition.getMeasuredWidth()/2;
        int cy = mTransition.getMeasuredHeight()/2;

        //get final radius of clipping circle
        int finalRadius = Math.max(mTransition.getWidth(), mTransition.getHeight())/2;

        //Create an Animator for the view
        Animator anim =
                ViewAnimationUtils.createCircularReveal(mTransition, cx, cy, finalRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTransition.setVisibility(View.INVISIBLE);
                startActivity(new Intent(MainActivity.this, setAlarm.class));
                //TODO Code Next Activity Here, set boolean for next activity to determine if it's app first run.
            }
        });
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(550);
        anim.start();
    }
}
