package com.cse40333.kthienem.lab2_kthienem;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kris on 3/1/2017.
 */

public class MyCsvFileReader {
    private Context context;
    private Team ND = new Team("Fighting Irish", "notre_dame", "Notre Dame", "(21-5)");
    private ArrayList<Team> teams = new ArrayList<>();
    private ArrayList<Game> games = new ArrayList<>();

    public MyCsvFileReader(Context applicationContext) {
        this.context = applicationContext;
    }

    public void readCsvFile(int fileresource) {
//        ArrayList<Team> games = new ArrayList<>();
        InputStream fin = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;

        try {
            fin = context.getResources().openRawResource(fileresource);
            isr = new InputStreamReader(fin);
            reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(",");
                Team teamInfo = new Team(temp[0], temp[1], /*temp[2],*/ temp[3], temp[4]);
                teams.add(teamInfo);
                if (temp[5].equals("home")) {
                    Game gameInfo = new Game(temp[2], "Arena", ND, teamInfo, Game.gameType.HOME, 0, 0);
                    games.add(gameInfo);
                } else if (temp[5].equals("away")) {
                    Game gameInfo = new Game(temp[2], "Arena", teamInfo, ND, Game.gameType.AWAY, 0, 0);
                    games.add(gameInfo);
                } else {
                    Log.d("ERROR", "Invlaid argument in csv file");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
                if (fin != null) {
                    fin.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
    }

    public ArrayList<Team> getTeams(int fileResource) {
        if (this.teams.isEmpty() && this.games.isEmpty()) {
            readCsvFile(fileResource);
        }
        return this.teams;
    }

    public ArrayList<Game> getGames(int fileResource) {
        if (this.games.isEmpty() && this.teams.isEmpty()) {
            readCsvFile(fileResource);
        }

        return this.games;
    }
}
