package com.project.distractless;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Calendar;

/*
SetAlarm
Function: Allows the user to select a time for the app's "Lockdown" mode to run, and the maximum
elapsed time before the "Lockdown" mode is de-activated.
 */

public class SetAlarm extends AppCompatActivity {

    //REQUEST_CODE is an arbitrary value, but necessary according to resources.
    public static final int REQUEST_CODE = 0;
    public static boolean runNow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm);

        /*
        Class declarations:
        TimePicker timePicker for setting the Alarm
        AlarmManager alarmManager for controlling the alarm (utilizing the pendingIntent)
        Button SetAlarm to confirm and commit the Alarm
         */
        final TimePicker timePicker = (TimePicker)
                findViewById(R.id.timePicker);
        final AlarmManager alarmManager = (AlarmManager)
                getSystemService(ALARM_SERVICE);
        final Button bRunNow = (Button)
                findViewById(R.id.b_RunNow);

        /*
        Kiosk mode will be enabled while the To-Do fragment is running, effectivly locking out
        the phone from being used or interfaced with. The 'Timeout' functions to ensure at least
        some safeguard from abuse. It's maximum length is 4 hours.
        */
        final EditText focusTimeout = (EditText) findViewById(R.id.t_focusTimeout);
        /*Create Toolbar,
        Forward click will change the background of the button, and parse the
        user-selected time to a format that can be understood by the alarmManager via the
        Calendar class and corresponding methods.
        alarmManager is set to RTC_WAKEUP which will wake the phone up if it is sleeping,
        and execute the pendingIntent to launch the To-Do Fragment activity.
         */
        Toolbar setAlarmbar = (Toolbar) findViewById(R.id.alarm_toolbar);
        setAlarmbar.setTitle("Set Start Time");
        setAlarmbar.inflateMenu(R.menu.todo_toolbar);
        setAlarmbar.findViewById(R.id.forward)
                .setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  setAlarm(timePicker, alarmManager, runNow);
                  if (focusTimeout != null)
                  AlarmFragment.timeout = Integer.parseInt(focusTimeout.getText().toString());
                  Tutorial rc = new Tutorial();
                  Intent intent = rc.ActivitySwitch(SetAlarm.this, ToDoList.class, 2);
                  startActivity(intent);
              }
          });

        /*
        Button to select "Run Now" will create an alert dialog to confirm that the user is requesting
        that the to-do list be activated once it has been created and confirmed.
         */
        bRunNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AlertDialog.Builder usrConfirm = new AlertDialog.Builder(SetAlarm.this);
                usrConfirm.setTitle("Confirm Selection");
                usrConfirm.setMessage("You've selected to run the list in focus mode as soon as it" +
                        " has been created, is that right?");
                usrConfirm.setNegativeButton("Whoops!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean runNow = false;
                    }
                });
                usrConfirm.setPositiveButton("I know what I clicked!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean runNow = true;
                    }
                });
                usrConfirm.create();
            }
        });

    }



    /*
    exitRevel creates a circular reveal animation for transition from activity to activity.
    it reads the screen size, and determines the center of the screen, for it's start and end
    parameters.
     */

    public void setAlarm(TimePicker timePicker, AlarmManager alarmManager, boolean runNow){
         /*
        The following sets up the Intent structure to launch the To-Do fragment activity at the user
        specified time. Consists of a pending intent Class to make the intent known regardless of if
        the app is active or not. If boolean runNow is true, no alarm will be set.
         */

        if (!runNow){
        final Intent intent = new Intent(getApplicationContext(), AlarmFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setAction(Intent.ACTION_MAIN);
        final PendingIntent pendingIntent = PendingIntent.getActivity
                (getApplicationContext(), REQUEST_CODE, intent, 0);

        Calendar runTime = Calendar.getInstance();
        runTime.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        runTime.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        alarmManager.set
                (AlarmManager.RTC_WAKEUP, runTime.getTimeInMillis(), pendingIntent);}
        else{
            ToDoList.runNow = true;
        }
    }



    public void runCheck(){
        startActivity(new Intent(SetAlarm.this, ToDoList.class));
    }

}


