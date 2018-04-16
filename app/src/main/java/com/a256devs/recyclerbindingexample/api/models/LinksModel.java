package com.a256devs.recyclerbindingexample.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinksModel {
    @SerializedName("self")
    @Expose
    private ReferenceModel self;
    @SerializedName("teams")
    @Expose
    private ReferenceModel teams;
    @SerializedName("fixtures")
    @Expose
    private ReferenceModel fixtures;
    @SerializedName("leagueTable")
    @Expose
    private ReferenceModel leagueTable;

    public ReferenceModel getSelf() {
        return self;
    }

    public void setSelf(ReferenceModel self) {
        this.self = self;
    }

    public ReferenceModel getTeams() {
        return teams;
    }

    public void setTeams(ReferenceModel teams) {
        this.teams = teams;
    }

    public ReferenceModel getFixtures() {
        return fixtures;
    }

    public void setFixtures(ReferenceModel fixtures) {
        this.fixtures = fixtures;
    }

    public ReferenceModel getLeagueTable() {
        return leagueTable;
    }

    public void setLeagueTable(ReferenceModel leagueTable) {
        this.leagueTable = leagueTable;
    }
}
