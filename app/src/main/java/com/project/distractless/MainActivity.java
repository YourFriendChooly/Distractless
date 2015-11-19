package com.project.distractless;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //String for passwordFile
    public static final String KEY = "KeyFile";
    String keyInstance;
    TextView keyView;
    TextView keyPrompt;
    String keyEntry = "";
    SharedPreferences keyStore;
    Boolean noKey;

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
        } else if (keyEntry.equals(keyInstance)){
            keyPrompt.setText("Match!");
            //TODO Code Transition to next Activity once keys match.
        }
    }

}
