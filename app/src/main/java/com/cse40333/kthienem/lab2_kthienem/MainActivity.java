package com.cse40333.kthienem.lab2_kthienem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] schools = {"Ohio State", "Florida State", "Wake Forest", "Boston College",
            "North Carolina State", "Georgia Tech", "North Virginia", "Chicago State"};
    private String[] teams = {"Buckeyes", "Seminoles", "Demon Deacons", "Eagles", "Wolf Pack", "Yellow Jackets", "Cavaliers", "Cougars"};
    private String[] dates = {"Feb 9", "Feb 11", "Feb 12", "Feb 14", "Feb 18", "Feb 26", "March 4", "March 8"};
    private String[] logos = {"ohio_state","fsu","wake_forest","boston_college","ncstate","georgia_tech","virginia","fsu"};
    private ArrayList<String> homeScore = new ArrayList<>();
    private ArrayList<String> awayScore = new ArrayList<>();
    private ArrayList<String> records = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();

    private ArrayList<String[]> info = new ArrayList<String[]>(){{
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

        Random r = new Random();
        for (int i = 0; i < schools.length; i++) {
            int temp = r.nextInt(100);
            homeScore.add(Integer.toString(temp));

            temp = r.nextInt(100);
            awayScore.add(Integer.toString(temp));

            int win = r.nextInt(27);
            int lose = 27 - win;
            records.add("(" + Integer.toString(win) + "-" + Integer.toString(lose) + ")");
        }

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle = addInfoToBundle(i);
//                bundle.putString("team1School", schools[i]);

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        scheduleListView.setOnItemClickListener(itemClickListener);
    }

    private Bundle addInfoToBundle(int id) {
        Bundle mBundle = new Bundle();

        String[] team1 = {schools[id], teams[id], records.get(id), homeScore.get(id), logos[id]};
        String[] team2 = {"Notre Dame", "Fighting Irish", "(21-5)", "78", "notre_dame"};

        mBundle.putStringArray("team1", team1);
        mBundle.putStringArray("team2", team2);

        mBundle.putString("date", "Saturday, " + dates[id] + ", 6:00 PM" );
        mBundle.putString("location", "Purcell Pavilion at the Joyce Center, Notre Dame, Indiana");

        return mBundle;
    }
}
