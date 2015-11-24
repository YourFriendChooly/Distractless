package com.project.distractless;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TimePicker;
import java.util.Calendar;

/*
SetAlarm
Function: Allows the user to select a time for the app's "Lockdown" mode to run, and the maximum
elapsed time before the "Lockdown" mode is de-activated.
 */

public class setAlarm extends AppCompatActivity {

    //REQUEST_CODE is an arbitrary value, but necessary according to resources.
    public static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm);
        /*
        The following sets up the Intent structure to launch the To-Do fragment activity at the user
        specified time. Consists of a pending intent Class to make the intent known regardless of if
        the app is active or not.
         */
        final Intent intent = new Intent(getApplicationContext(), alarmFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setAction(Intent.ACTION_MAIN);
        final PendingIntent pendingIntent = PendingIntent.getActivity
                (getApplicationContext(), REQUEST_CODE, intent, 0);

        /*
        Class declarations:
        TimePicker timePicker for setting the Alarm
        AlarmManager alarmManager for controlling the alarm (utilizing the pendingIntent)
        Button setAlarm to confirm and commit the Alarm
         */
        final TimePicker timePicker = (TimePicker)
                findViewById(R.id.timePicker);
        final AlarmManager alarmManager = (AlarmManager)
                getSystemService(ALARM_SERVICE);
        final Button setAlarm = (Button) findViewById(R.id.b_setAlarm);

        /*
        Buttons onClickListener will change the background of the button, and parse the
        user-selected time to a format that can be understood by the alarmManager via the
        Calendar class and corresponding methods.
        alarmManager is set to RTC_WAKEUP which will wake the phone up if it is sleeping,
        and execute the pendingIntent to launch the To-Do Fragment activity.
         */
        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlarm.getBackground().setColorFilter
                        (getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                alarmManager.set
                        (AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                exitReveal();
            }

            /*
            Kiosk mode will be enabled while the To-Do fragment is running, effectivly locking out
            the phone from being used or interfaced with. The 'Timeout' functions to ensure at least
            some safeguard from abuse. It's maximum length is 4 hours.
             */
            //TODO Code the "Timeout" feature for the Kiosk Mode portion of the Application.
            //TODO Code a "New password after completion?" feature to change password at next config run.

        });}

    /*
    exitRevel creates a circular reveal animation for transition from activity to activity.
    it reads the screen size, and determines the center of the screen, for it's start and end
    parameters.
     */
    void exitReveal() {
        final View mTransition = findViewById(R.id.setAlarmLayout);
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
                startActivity(new Intent(setAlarm.this, ToDoList.class));
                //TODO Set boolean for next activity to determine if it's app first run.
            }
        });
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(550);
        anim.start();
    }
}

