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

    private ArrayList<String> homeScore = new ArrayList<>();
    private ArrayList<String> awayScore = new ArrayList<>();
    private Team ND = new Team("Fighting Irish", "notre_dame", "March 1", "Notre Dame", "(21-5)");

    private ArrayList<Team> info = new ArrayList<>();
    private ListAdapter scheduleAdapter;
    private ListView scheduleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Schedule");

        MyCsvFileReader reader = new MyCsvFileReader(getApplicationContext());
        info = reader.readCsvFile(R.raw.schedule);

        scheduleAdapter = new ScheduleAdapter(this, info);
        scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);

        Random r = new Random();
        for (int i = 0; i < info.size(); i++) {
            int temp = r.nextInt(100);
            homeScore.add(Integer.toString(temp));

            temp = r.nextInt(100);
            awayScore.add(Integer.toString(temp));
        }

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = addInfoToBundle(i);

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        scheduleListView.setOnItemClickListener(itemClickListener);
    }

    private Bundle addInfoToBundle(int id) {
        Bundle mBundle = new Bundle();

        mBundle.putSerializable("team1", info.get(id));
        mBundle.putSerializable("team2", ND);

        mBundle.putString("date", "Saturday, " + info.get(id).getGameDate() + ", 6:00 PM" );
        mBundle.putString("location", "Purcell Pavilion at the Joyce Center, Notre Dame, Indiana");
        mBundle.putString("score1", awayScore.get(id));
        mBundle.putString("score2", homeScore.get(id));

        return mBundle;
    }
}
