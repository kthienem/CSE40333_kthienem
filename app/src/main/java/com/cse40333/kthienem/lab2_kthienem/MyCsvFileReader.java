package com.cse40333.kthienem.lab2_kthienem;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Kris on 3/1/2017.
 */

public class MyCsvFileReader {
    private Context context;

    public MyCsvFileReader(Context applicationContext) {
        this.context = applicationContext;
    }

    public ArrayList<Team> readCsvFile(int fileresource) {
        ArrayList<Team> games = new ArrayList<>();
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
                Team teamInfo = new Team(temp[0], temp[1], temp[2], temp[3], temp[4]);
                games.add(teamInfo);
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

        return games;
    }
}
