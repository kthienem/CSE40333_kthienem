package com.cse40333.kthienem.lab2_kthienem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kris on 2/15/2017.
 */

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Game Detail");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Long gameID = bundle.getLong("game");
        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        final Game game = dbHelper.getGame(gameID);

        //Set info of team 1
        Team team1Info = game.getHome();
        String team1Score = Integer.toString(game.getHomeScore());
        setTeam1Info(team1Info, team1Score);

        //Set info of team 2
        Team team2Info = game.getVisitor();
        String team2Score = Integer.toString(game.getVisitorScore());
        setTeam2Info(team2Info, team2Score);

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(game.getDate());

        TextView location = (TextView) findViewById(R.id.location);
        location.setText(game.getLocation());

        View.OnClickListener cameraButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                if(game.getType() == Game.gameType.HOME) {
                    intent.putExtra("id", dbHelper.getTeamID(game.getVisitor()));
                } else if (game.getType() == Game.gameType.AWAY) {
                    intent.putExtra("id", dbHelper.getTeamID(game.getHome()));
                }

                startActivity(intent);
            }
        };

        Button cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(cameraButtonClickListener);
    }

    private void setTeam1Info(Team info, String mScore) {
        TextView school = (TextView) findViewById(R.id.team1School);
        school.setText(info.getSchoolName());

        TextView team = (TextView) findViewById(R.id.team1Name);
        team.setText(info.getTeamName());

        TextView record = (TextView) findViewById(R.id.team1Record);
        record.setText(info.getRecord());

        TextView score = (TextView) findViewById(R.id.team1Score);
        score.setText(mScore);

        ImageView logo = (ImageView) findViewById(R.id.team1Logo);
        String mDrawableName = info.getTeamLogo();
        int resID = getApplicationContext().getResources().getIdentifier(mDrawableName, "drawable", getApplicationContext().getPackageName());
        logo.setImageResource(resID);
    }

    private void setTeam2Info(Team info, String mScore) {
        TextView school = (TextView) findViewById(R.id.team2School);
        school.setText(info.getSchoolName());

        TextView team = (TextView) findViewById(R.id.team2Name);
        team.setText(info.getTeamName());

        TextView record = (TextView) findViewById(R.id.team2Record);
        record.setText(info.getRecord());

        TextView score = (TextView) findViewById(R.id.team2Score);
        score.setText(mScore);

        ImageView logo = (ImageView) findViewById(R.id.team2Logo);
        String mDrawableName = info.getTeamLogo();
        int resID = getApplicationContext().getResources().getIdentifier(mDrawableName, "drawable", getApplicationContext().getPackageName());
        logo.setImageResource(resID);
    }
}
