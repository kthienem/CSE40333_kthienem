package com.cse40333.kthienem.lab2_kthienem;

import java.io.Serializable;

/**
 * Created by Kris on 4/2/2017.
 */

public class Game implements Serializable {

    public enum gameType {
        HOME,
        AWAY
    }

    private String date;
    private String location;
    private Team home;
    private Team visitor;
    private int homeScore;
    private int visitorScore;
    private gameType type;

    public Game(String date, String loc, Team team1, Team team2, gameType type, int homeScore, int visitorScore) {
        this.date = date;
        this.location = loc;
        this.home = team1;
        this.visitor = team2;
        this.type = type;
        this.homeScore = homeScore;
        this.visitorScore = visitorScore;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getVisitor() {
        return visitor;
    }

    public void setVisitor(Team visitor) {
        this.visitor = visitor;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getVisitorScore() {
        return visitorScore;
    }

    public void setVisitorScore(int visitorScore) {
        this.visitorScore = visitorScore;
    }

    public gameType getType() {
        return type;
    }

    public void setType(gameType type) {
        this.type = type;
    }
}
