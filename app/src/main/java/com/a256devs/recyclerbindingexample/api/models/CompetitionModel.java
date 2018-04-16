package com.a256devs.recyclerbindingexample.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionModel implements Comparable<CompetitionModel> {
    @SerializedName("_links")
    @Expose
    private LinksModel links;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("league")
    @Expose
    private String league;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("currentMatchday")
    @Expose
    private long currentMatchday;
    @SerializedName("numberOfMatchdays")
    @Expose
    private long numberOfMatchdays;
    @SerializedName("numberOfTeams")
    @Expose
    private long numberOfTeams;
    @SerializedName("numberOfGames")
    @Expose
    private long numberOfGames;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    private String compareString;

    public LinksModel getLinks() {
        return links;
    }

    public void setLinks(LinksModel links) {
        this.links = links;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getCurrentMatchday() {
        return currentMatchday;
    }

    public void setCurrentMatchday(long currentMatchday) {
        this.currentMatchday = currentMatchday;
    }

    public long getNumberOfMatchdays() {
        return numberOfMatchdays;
    }

    public void setNumberOfMatchdays(long numberOfMatchdays) {
        this.numberOfMatchdays = numberOfMatchdays;
    }

    public long getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(long numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public long getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(long numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public int compareTo(@NonNull CompetitionModel o) {
        return 0;
    }

    public String getCompareString() {
        return compareString;
    }

    public void setCompareString(String compareString) {
        this.compareString = compareString;
    }
}
