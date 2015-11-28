package com.project.distractless;


import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class alarmFragment extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    static ArrayList<String> items;
    private static ViewPager mViewPager;
    static int numComplete = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_fragment);
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        final ToDoFragment pf = new ToDoFragment();

        /*
        FloatingActionButton fab serves as an icon to mark a task as complete. The following
        fragmentManager will remove the fragment once it has been completed.
         */
        FloatingActionButton runFab = (FloatingActionButton) findViewById(R.id.runFab);
        runFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mViewPagerCurrentItem = mViewPager.getCurrentItem();
                Fragment newFragment = pf.newInstance(mViewPagerCurrentItem);
                items.remove(mViewPagerCurrentItem);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, newFragment);

                mSectionsPagerAdapter.DestroyItem(mViewPagerCurrentItem);
                mSectionsPagerAdapter.notifyDataSetChanged();
                ft.commit();
                mViewPager.setAdapter(mSectionsPagerAdapter);
            }
        });

    }

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
                //TODO Create a FOR loop to rearrange the items in the list after an item has been removed. Possibly using ListIterator.;
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
            // Show 3 total pages.
            int count = items.size();
            return count;
        }



    }


    public static class ToDoFragment extends Fragment {
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
            String sectionString = String.valueOf(sectionNumber);
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, sectionString);
            args.putString(ARG_LIST_CONTENTS, instanceItems.get(sectionNumber));
            fragment.setArguments(args);
            int fragID = fragment.getId();
            args.putInt(ARG_FRAG_ID, fragID);
            String TAG = "Fragment";

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
            final TextView itemComplete = (TextView) rootView.findViewById(R.id.isCompleted);
            listNumber.setText(getArguments().getString(ARG_SECTION_NUMBER));
            listContents.setText(getArguments().getString(ARG_LIST_CONTENTS));

            return rootView;
        }
    }
}
