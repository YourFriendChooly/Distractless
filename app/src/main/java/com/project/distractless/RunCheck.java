package com.project.distractless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by OBEY-YOSEMITE on 15-11-30.
 */
public class RunCheck extends Activity {
    public void ActivitySwitch(Context context, Class mainClass, Class altClass){
        if (Splash.setRunAssistant){
            Intent intent = new Intent(context, mainClass);
            startActivity(intent);
        } else { Intent intent = new Intent(context, altClass);
            startActivity(intent); }

    }
}
