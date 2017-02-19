package com.cse40333.kthienem.lab2_kthienem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kris on 2/15/2017.
 */

public class DetailActivity extends AppCompatActivity {

    private Button cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Game Detail");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //Set info of team 1
        String[] team1Info = bundle.getStringArray("team1");
        setTeam1Info(team1Info);

        String[] team2Info = bundle.getStringArray("team2");
        setTeam2Info(team2Info);

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(bundle.getString("date"));

        TextView location = (TextView) findViewById(R.id.location);
        location.setText(bundle.getString("location"));

        View.OnClickListener cameraButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(cameraIntent);
            }
        };

        cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(cameraButtonClickListener);
    }

    private void setTeam1Info(String[] info) {
        TextView school = (TextView) findViewById(R.id.team1School);
        school.setText(info[0]);

        TextView team = (TextView) findViewById(R.id.team1Name);
        team.setText(info[1]);

        TextView record = (TextView) findViewById(R.id.team1Record);
        record.setText(info[2]);

        TextView score = (TextView) findViewById(R.id.team1Score);
        score.setText(info[3]);

        ImageView logo = (ImageView) findViewById(R.id.team1Logo);
        String mDrawableName = info[4];
        int resID = getApplicationContext().getResources().getIdentifier(mDrawableName, "drawable", getApplicationContext().getPackageName());
        logo.setImageResource(resID);
    }

    private void setTeam2Info(String[] info) {
        TextView school = (TextView) findViewById(R.id.team2School);
        school.setText(info[0]);

        TextView team = (TextView) findViewById(R.id.team2Name);
        team.setText(info[1]);

        TextView record = (TextView) findViewById(R.id.team2Record);
        record.setText(info[2]);

        TextView score = (TextView) findViewById(R.id.team2Score);
        score.setText(info[3]);

        ImageView logo = (ImageView) findViewById(R.id.team2Logo);
        String mDrawableName = info[4];
        int resID = getApplicationContext().getResources().getIdentifier(mDrawableName, "drawable", getApplicationContext().getPackageName());
        logo.setImageResource(resID);
    }
}
