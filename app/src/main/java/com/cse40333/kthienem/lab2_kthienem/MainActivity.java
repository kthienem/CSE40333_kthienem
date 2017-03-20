package com.cse40333.kthienem.lab2_kthienem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> homeScore = new ArrayList<>();
    private ArrayList<String> awayScore = new ArrayList<>();
    private Team ND = new Team("Fighting Irish", "notre_dame", "March 1", "Notre Dame", "(21-5)");

    private ArrayList<Team> info = new ArrayList<>();
    private ListAdapter scheduleAdapter;
    private ListView scheduleListView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("ND Athletics");

        MyCsvFileReader reader = new MyCsvFileReader(getApplicationContext());
        info = reader.readCsvFile(R.raw.schedule);

        scheduleAdapter = new ScheduleAdapter(getBaseContext(), info);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();

        if (res_id == R.id.share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Basketball Matches");
            shareIntent.putExtra(Intent.EXTRA_TEXT, gameSchedule());
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
        if (res_id == R.id.sync) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), "Sync is not yet implemented", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView text = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            text.setTextColor(Color.WHITE);
            snackbar.setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar altSnackbar = Snackbar.make(findViewById(R.id.coordinator_layout), "Wait for the next few labs.", Snackbar.LENGTH_LONG);
                    View altSnackbarView = altSnackbar.getView();
                    TextView text = (TextView) altSnackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    text.setTextColor(Color.WHITE);
                    altSnackbar.show();
                }
            });
            snackbar.show();
        }
        if (res_id == R.id.settings) {
            registerForContextMenu(findViewById(R.id.scheduleListView));
            openContextMenu(findViewById(R.id.scheduleListView));
            unregisterForContextMenu(findViewById(R.id.scheduleListView));
        }
        return true;
    }

    public String gameSchedule() {
        StringBuilder gameString = new StringBuilder();

        for (Team team: info) {
            gameString.append(team.getTeamName() + ", ");
            gameString.append(team.getGameDate() + ", ");
            gameString.append("Purcell Pavilion\n");
        }
        gameString.deleteCharAt(gameString.length()-1);
        return gameString.toString();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.floating_contextual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int res_id = item.getItemId();

        if (res_id == R.id.womens) {
            //TODO: Implement in later lab
        }
        if (res_id == R.id.mens) {
            //TODO: Implement in later lab
        }
        if (res_id == R.id.away) {
            //TODO: Implement in later lab
        }
        if (res_id == R.id.home) {
            //TODO: Implement in later lab
        }
        return false;
    }
}
