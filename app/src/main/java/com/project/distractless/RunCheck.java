package com.project.distractless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by OBEY-YOSEMITE on 15-11-30.
 */
public class RunCheck extends Activity {

    public static boolean setRunAssistant = false;

    public static Intent ActivitySwitch(Context context, Class mainClass, Class altClass) {
        if (!setRunAssistant) {
            Intent intent = new Intent(context, mainClass);
            return intent;
        } else {
            Intent intent = new Intent(context, altClass);
            return intent;
        }

    }
}
