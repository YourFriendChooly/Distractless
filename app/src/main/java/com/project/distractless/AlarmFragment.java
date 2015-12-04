package com.project.distractless;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AlarmFragment extends AppCompatActivity {

    public static int timeout = 4;
    private static SectionsPagerAdapter mSectionsPagerAdapter;
    static ArrayList<String> items;
    static int numComplete = 0;
    static ViewPager mViewPager;
    static FragmentManager fmanager;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.alarm_fragment);



        //Read files contained in the todolist text file.
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
            items.size();
        } catch (IOException e) {
            items = new ArrayList<String>();
        }

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /*
        Calling KioskMode and setting timer for kiosk de-activation.
         */
        Timer lockTimeout = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            PrefUtils.setKioskModeActive(false, getApplicationContext());
            }
        };
        lockTimeout.schedule(task, TimeUnit.HOURS.toMillis(timeout));
        startService(new Intent(this, KioskService.class));


        /*
        FloatingActionButton fab serves as an icon to mark a task as complete. The following
        fragmentManager will remove the fragment once it has been completed.
         */
        final int total = items.size();
        final Animation bounce = AnimationUtils.loadAnimation(this, R.anim.zoom);
        FloatingActionButton runFab = (FloatingActionButton) findViewById(R.id.runFab);
        runFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numComplete++;
                final ImageView thumb = (ImageView) findViewById(R.id.img_completed);
                thumb.setVisibility(View.VISIBLE);
                thumb.startAnimation(bounce);
                thumb.setVisibility(View.INVISIBLE);
                completedCheck(numComplete, total);
            }
        });
        fmanager = getSupportFragmentManager();

        bounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String itemsLeft = Integer.toString(items.size());
                Snackbar.make(mViewPager, "Good Job! Only " +itemsLeft+" Tasks Remaining!", Snackbar.LENGTH_SHORT).show();
                destroyFragment(mViewPager.getCurrentItem());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    //KIOSK CODE
    //------START------
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {/*
    Overriding onWindowFocusChanged and closing dialog screens using the intent manager will prevent
    the user from using the power button long press to shut down the app while the to-do list is
    running.
     */
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    @Override
    public void onBackPressed() {
        // nothing to do here
        // … really
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
    /*
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
    */
    //-------END-------
    //KIOSK CODE

    //DestroyFragment uses the Pager Adapter as well as a Fragment Transaction to remove
    //The currently active fragment, the item from the array,
    //and repopulate the remaining fragments in order.
    public void destroyFragment(int mViewPagerCurrentItem){
        ToDoFragment pf = new ToDoFragment();
        Fragment newFragment = pf.newInstance(mViewPagerCurrentItem);
        items.remove(mViewPagerCurrentItem);
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.replace(R.id.container, newFragment);
        mSectionsPagerAdapter.DestroyItem(mViewPagerCurrentItem);
        mSectionsPagerAdapter.notifyDataSetChanged();
        ft.commit();
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    //CompletedCheck determines if all of the items in the List have been completed using a counter
    //(completed) and a sentry (total.) This is also the transition point for the next activity.
    public void completedCheck(int completed, int total){
            if (completed == total){
                Intent completedIntent = new Intent(this, Pin.class);
                Pin.fromList = true;
                startActivity(completedIntent);
            }
    }
    /* CAN POTENTIALLY DELETE
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the todo_toolbar; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    END POTENTIALLY DELETE*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void DestroyItem(int posit){
            Object objectobject = this.instantiateItem(mViewPager, posit);
            if (objectobject != null)
                destroyItem(mViewPager, posit, objectobject);
         }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            ToDoFragment fragReturn = new ToDoFragment();
            return fragReturn.newInstance(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


        @Override
        public int getCount() {
            //Count of pages is equal to the number of items in the items array.
            int count = items.size();
            return count;
        }



    }


    public class ToDoFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "list_number";
        private static final String ARG_LIST_CONTENTS = "list_contents";
        private static final String ARG_FRAG_ID = "fragment_id";


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public ToDoFragment newInstance(int sectionNumber) {
            ArrayList<String> instanceItems = items;
            ToDoFragment fragment = new ToDoFragment();
            String sectionString = String.valueOf(sectionNumber+1);
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, sectionString);
            args.putString(ARG_LIST_CONTENTS, instanceItems.get(sectionNumber));
            fragment.setArguments(args);

            return fragment;
            //Link Data from list array here.


        }

        public ToDoFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
            TextView listNumber = (TextView) rootView.findViewById(R.id.section_label);
            TextView listContents = (TextView) rootView.findViewById(R.id.list_content);
            listNumber.setText(getArguments().getString(ARG_SECTION_NUMBER));
            listContents.setText(getArguments().getString(ARG_LIST_CONTENTS));
            //Activates KioskMode
            PrefUtils.setKioskModeActive(true, getApplicationContext());
            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();

            ActivityManager activityManager = (ActivityManager) getApplicationContext()
                    .getSystemService(Context.ACTIVITY_SERVICE);

            activityManager.moveTaskToFront(getTaskId(), 0);

        }
    }
}
