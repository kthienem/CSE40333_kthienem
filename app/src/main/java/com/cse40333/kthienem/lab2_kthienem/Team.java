package com.cse40333.kthienem.lab2_kthienem;

import java.io.Serializable;

/**
 * Created by Kris on 3/1/2017.
 */

public class Team implements Serializable {

    private String teamName;
    private String teamLogo;
    private String gameDate;
    private String schoolName;
    private String record;

    public Team(String team_name, String team_logo, String game_date, String school_name, String record) {
        this.teamName = team_name;
        this.teamLogo = team_logo;
        this.gameDate = game_date;
        this.schoolName = school_name;
        this.record = record;
    }

    public String getTeamName() { return teamName; }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
