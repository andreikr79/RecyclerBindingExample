package com.a256devs.recyclerbindingexample.api;

import com.a256devs.recyclerbindingexample.api.models.CompetitionModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface FootballApi {
    String BASE_URL = "http://api.football-data.org/";

    @GET("v1/competitions")
    Observable<ArrayList<CompetitionModel>> getCompetitions();
}
