package com.project.distractless;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

public class setAlarm extends AppCompatActivity {

    public static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm);
        final Intent intent = new Intent(getApplicationContext(), alarmFragment.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setAction(Intent.ACTION_MAIN);

        final PendingIntent pendingIntent = PendingIntent.getActivity
                (getApplicationContext(), REQUEST_CODE, intent, 0);

        final TimePicker timePicker = (TimePicker)
                findViewById(R.id.timePicker);
        final AlarmManager alarmManager = (AlarmManager)
                getSystemService(ALARM_SERVICE);

        final Button setAlarm = (Button) findViewById(R.id.b_setAlarm);

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                setAlarm.getBackground().setColorFilter
                        (getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                alarmManager.set
                        (AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent); **/
                exitReveal();
            }
        });}

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
                mTransition.setVisibility(View.INVISIBLE);
                startActivity(new Intent(setAlarm.this, ToDoList.class));
                //TODO Code Next Activity Here, set boolean for next activity to determine if it's app first run.
            }
        });
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(550);
        anim.start();
    }
}

