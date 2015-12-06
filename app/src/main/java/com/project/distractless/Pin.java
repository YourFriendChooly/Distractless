package com.project.distractless;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;



public class Pin extends AppCompatActivity {

    //String for passwordFile
    public static boolean fromList = false;
    public static final String KEY = "KeyFile";
    String keyInstance;
    TextView keyView;
    TextView keyPrompt;
    String keyEntry = "";
    SharedPreferences keyStore;
    Boolean noKey;

    @Override
    protected void onResume() {
        super.onResume();
        if (fromList){
            keyPrompt.setText("Congratulations! Enter Your Key to Unlock Your Phone!");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Instantiations:
        keyPrompt will change it's output depending on if there is a valid key stored in memory,
        or depending on if the user key entered matched the stored key or not.
        keyView is used for feedback to display the number of digits (in password form) user enters.
        keyStore reads the saved key value.
        keyInstance will parse the string from keyStore, and if there is no value present a default
        value of A will be passed to the variable.
         */
        keyPrompt = (TextView) findViewById(R.id.keyPrompt);
        keyView = (TextView) findViewById(R.id.keyView);
        keyStore = getSharedPreferences(KEY, 0);
        keyInstance = keyStore.getString("keyValue", "a");

        //The following will adjust the keyPrompt output depending on if keyInstance pulls the
        //default value from keyStore or not.
        if (!fromList && keyInstance.equals("a")){
            keyPrompt.setText("Select A 6 Digit Key");
            noKey = true;
        } else if (fromList){
            keyPrompt.setText("Congratulations! Enter Key to Unlock Your Phone!");
            noKey = false;
        }
        else {
            keyPrompt.setText("Enter Your Key");
            noKey = false;
        }
    }

    /*
    Void numPad is used with a series of Case Statements for an OnClick return, which will allow
    us to easily code the return values for all of the numbers in the numberpad instead of having
    to code an on-click listener for each of the 10 buttons.
     */
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

    /*
    Void numData checks the value of the user-entered string after every button press, up to a
    maximum of 6 presses, and checks it against several conditions.
    noKey is a boolean assignment that looks for a default value assigned to the key in memory, if
    the default value is present then the value is replaced by the newly entered key.
     */
    public void numData (String num){
        keyEntry = keyEntry + num;
        keyView.setText(keyEntry);
        if (keyEntry.length() == 6 && noKey){
            keyPrompt.setText("Saved!");
            SharedPreferences.Editor keyEdit = keyStore.edit();
            keyEdit.putString("keyValue", keyEntry);
            keyEdit.commit();
            keyEntry = "";
            exitReveal();
        } else if (!fromList && keyEntry.equals(keyInstance)) {
            keyPrompt.setText("Match!");
            exitReveal();
        } else if (fromList && keyEntry.equals(keyInstance)){
            keyPrompt.setText("Focus Mode Deactivated!");
            PrefUtils.setKioskModeActive(false, getApplicationContext());
        } else if (keyEntry.length() == 6 && !keyEntry.equals(keyInstance)){
            keyPrompt.setText("Invalid Password");
            keyEntry = "";
            keyView.setText(keyEntry);
        }
    }

    /*
    exitRevel creates a circular reveal animation for transition from activity to activity.
    it reads the screen size, and determines the center of the screen, for it's start and end
    parameters.
     */
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
                //mTransition.setVisibility(View.INVISIBLE);
                Tutorial tut = new Tutorial();
                Intent intent = Tutorial.ActivitySwitch(Pin.this, SetAlarm.class, 1);
                startActivity(intent);
            }
        });
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(550);
        anim.start();
    }
}
