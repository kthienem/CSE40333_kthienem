package com.cse40333.kthienem.lab2_kthienem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String[]> info = new ArrayList<String[]>(){{
            String[] schools = {"Ohio State", "Florida State", "Wake Forest", "Boston College",
            "North Carolina State", "Georgia Tech", "North Virginia", "Chicago State"};

            String[] dates = {"Feb 9", "Feb 11", "Feb 12", "Feb 14", "Feb 18", "Feb 26", "March 4", "March 8"};

            String[] logos = {"ohio_state","fsu","wake_forest","boston_college","ncstate","georgia_tech","virginia","fsu"};

            for (int i = 0; i < schools.length; i++) {
                String[] temp = {schools[i], dates[i], logos[i]};
                add(temp);
            }
    }};
    private ListAdapter scheduleAdapter;
    private ListView scheduleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Schedule");

        scheduleAdapter = new ScheduleAdapter(this, info);
        scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);
    }
}
