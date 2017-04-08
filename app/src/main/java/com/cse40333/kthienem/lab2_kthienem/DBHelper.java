package com.cse40333.kthienem.lab2_kthienem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Kris on 3/29/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "basketball.db";
    private static int DATABASE_VERSION = 1;
    private static String TABLE_TEAMS = "Teams";
    private static String TABLE_GAMES = "Games";
    private static String[] TEAM_COLS = {"id", "name", "logo", "school", "record"};
    private static String[] GAME_COLS = {"id", "date", "location", "home_team", "home_score", "away_team", "away_score"};
    private SQLiteDatabase mDB;

    public DBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_GAMES + " ("
                + GAME_COLS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GAME_COLS[1] + " TEXT, "
                + GAME_COLS[2] + " TEXT, "
                + GAME_COLS[3] + " TEXT, "
                + GAME_COLS[4] + " INTEGER, "
                + GAME_COLS[5] + " TEXT, "
                + GAME_COLS[6] + " INTEGER "
                + ");");

        db.execSQL("CREATE TABLE " + TABLE_TEAMS + " ("
                + TEAM_COLS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TEAM_COLS[1] + " TEXT, "
                + TEAM_COLS[2] + " TEXT, "
                + TEAM_COLS[3] + " TEXT, "
                + TEAM_COLS[4] + " TEXT "
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists " + TABLE_GAMES);
        db.execSQL("DROP TABLE if exists " + TABLE_TEAMS);
        onCreate(db);
    }

    public long insertTeam(Team team) {
        long ret = -1;

        mDB = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TEAM_COLS[1], team.getTeamName());
        contentValues.put(TEAM_COLS[2], team.getTeamLogo());
        contentValues.put(TEAM_COLS[3], team.getSchoolName());
        contentValues.put(TEAM_COLS[4], team.getRecord());

        ret = mDB.insert(TABLE_TEAMS, null, contentValues);
        mDB.close();

        if (ret > 0) {
            Log.d("Team_Insert", "Successfully inserted");
        } else {
            Log.d("Team_Insert", "Unsuccessful insert");
        }

        return ret;
    }

    public void deleteTeam() {

    }

    public long insertGame(Game game, long homeID, long visitorID) {
        mDB = getWritableDatabase();
        long ret = -1;

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(GAME_COLS[1], game.getDate());
            contentValues.put(GAME_COLS[2], game.getLocation());
            contentValues.put(GAME_COLS[3], homeID);
            contentValues.put(GAME_COLS[4], game.getHomeScore());
            contentValues.put(GAME_COLS[5], visitorID);
            contentValues.put(GAME_COLS[6], game.getVisitorScore());

            ret = mDB.insert(TABLE_GAMES, null, contentValues);

            mDB.close();

            if (ret > 0) {
                Log.d("Game_Insert", "Successfully inserted");
            } else {
                Log.d("Game_Insert", "Unsuccessful insert");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void deleteGame() {

    }

    public long getTeamID(Team team) {
        long id = -1;

        mDB = getReadableDatabase();
        try {
            Cursor cursor = mDB.rawQuery("SELECT * FROM " + TABLE_TEAMS +
                    " WHERE " + TEAM_COLS[1] + "=" + "'" + team.getTeamName() + "'", null);
            if (cursor != null) {
                cursor.moveToFirst();
                id = cursor.getLong(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    /*public long getGame(int id) {
        Game game;

        return game;
    }*/

    public String getTeams() {
        StringBuilder sb = new StringBuilder();
        mDB = getReadableDatabase();

        Cursor mCursor = mDB.query(TABLE_TEAMS, null, null, null, null, null, null);
//        Cursor mCursor = mDB.rawQuery("SELECT * FROM " + TABLE_TEAMS, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            while(!mCursor.isAfterLast()) {
                sb.append(mCursor.getString(mCursor.getColumnIndex(TEAM_COLS[1])));
                mCursor.moveToNext();
            }
            mCursor.close();
        }
        mDB.close();

        return sb.toString();
    }

    public String getGames() {
        StringBuilder sb = new StringBuilder();
        mDB = getReadableDatabase();

        Cursor mCursor = mDB.query(TABLE_GAMES, null, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                sb.append(mCursor.getString(mCursor.getColumnIndex(GAME_COLS[3])));
                sb.append(" ");
                sb.append(mCursor.getString(mCursor.getColumnIndex(GAME_COLS[5])));
                sb.append("\n");
                mCursor.moveToNext();
            }
            mCursor.close();
        }
        mDB.close();

        return sb.toString();
    }

    public Game getGame(long id) {
        Game game = new Game("", "", new Team("", "", "", ""), new Team("", "", "", ""), Game.gameType.HOME, 0, 0);
        mDB = getReadableDatabase();

        Cursor gameCursor = mDB.query(TABLE_GAMES, GAME_COLS, GAME_COLS[0] + "=" + (int) id, null, null, null, null);

        if (gameCursor == null) {
            Log.d("GET_GAME", "Entry does not exist for given id");
        } else {
            gameCursor.moveToFirst();
            long team1ID = gameCursor.getLong(gameCursor.getColumnIndex(GAME_COLS[3]));
            long team2ID = gameCursor.getLong(gameCursor.getColumnIndex(GAME_COLS[5]));

            Team team1 = getTeam(team1ID);
            Team team2 = getTeam(team2ID);

            game = new Game(gameCursor.getString(gameCursor.getColumnIndex(GAME_COLS[1])),
                    gameCursor.getString(gameCursor.getColumnIndex(GAME_COLS[2])),
                    team1, team2, Game.gameType.HOME,
                    gameCursor.getInt(gameCursor.getColumnIndex(GAME_COLS[4])),
                    gameCursor.getInt(gameCursor.getColumnIndex(GAME_COLS[6])));

            gameCursor.close();
        }
        mDB.close();

        return game;
    }

    public Team getTeam(long id) {
        Team team = new Team("", "", "", "");
        mDB = getReadableDatabase();

        Cursor teamCursor = mDB.query(TABLE_TEAMS, TEAM_COLS, TEAM_COLS[0] + "=" + (int) id, null, null, null, null);

        if (teamCursor == null) {
            Log.d("GET_TEAM", "Entry does not exist for given id");
        } else {
            teamCursor.moveToFirst();

            ArrayList<String> info = new ArrayList<>();
            info.add(teamCursor.getString(teamCursor.getColumnIndex(TEAM_COLS[1])));
            info.add(teamCursor.getString(teamCursor.getColumnIndex(TEAM_COLS[2])));
            info.add(teamCursor.getString(teamCursor.getColumnIndex(TEAM_COLS[3])));
            info.add(teamCursor.getString(teamCursor.getColumnIndex(TEAM_COLS[4])));

            team = new Team(info.get(0), info.get(1), info.get(2), info.get(3));
            teamCursor.close();
        }
        mDB.close();

        return team;
    }
}
