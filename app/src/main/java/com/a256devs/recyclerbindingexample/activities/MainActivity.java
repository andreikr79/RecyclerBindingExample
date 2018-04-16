package com.a256devs.recyclerbindingexample.activities;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.widget.Toast;

import com.a256devs.recyclerbindingexample.R;
import com.a256devs.recyclerbindingexample.BR;
import com.a256devs.recyclerbindingexample.api.FootballApi;
import com.a256devs.recyclerbindingexample.api.models.CompetitionModel;
import com.a256devs.recyclerbindingexample.binding.RecyclerBindingAdapterFiltered;
import com.a256devs.recyclerbindingexample.binding.RecyclerConfiguration;
import com.a256devs.recyclerbindingexample.databinding.ActivityMainBinding;
import com.a256devs.recyclerbindingexample.databinding.ItemCompetitionBinding;
import com.a256devs.recyclerbindingexample.di.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    ArrayList<CompetitionModel> competitions = new ArrayList<>();

    CompetitionModel cmpCompetition = new CompetitionModel();

    RecyclerConfiguration rvConfig = new RecyclerConfiguration();
    RecyclerBindingAdapterFiltered<CompetitionModel> rvAdapter;

    @Inject
    FootballApi footballApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getMainComponent().inject(this);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.swCompetition.setOnRefreshListener(this::getCompetitions);
        binding.swCompetition.setRefreshing(true);
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                cmpCompetition.setCompareString(str);
                if(rvAdapter!=null) rvAdapter.filter(cmpCompetition);
            }
        });
        getCompetitions();
    }

    private void getCompetitions() {
        Disposable disposable = footballApi.getCompetitions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(competitionModels -> {
                    binding.swCompetition.setRefreshing(false);
                    competitions = competitionModels;
                    initRV();
                }, throwable -> {
                    binding.swCompetition.setRefreshing(false);
                    Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCompositeDisposable.clear();
    }

    private void initRV() {
        if(rvAdapter==null) {
            rvConfig.setItemAnimator(new DefaultItemAnimator());
            rvConfig.setLayoutManager(new LinearLayoutManager(this));
            SparseArray<RecyclerBindingAdapterFiltered.OnItemClickListener<CompetitionModel>> listeners = new SparseArray<>();
            listeners.put(R.id.btnLeague, (position, item) -> {
                Toast.makeText(this, "Click Leage on position "+ String.valueOf(position)+ " for "+item.getCaption(), Toast.LENGTH_SHORT).show();
            });
            listeners.put(R.id.btnFixtures, (position, item) -> {
                Toast.makeText(this, "Click Fixtures on position "+ String.valueOf(position)+ " for "+item.getCaption(), Toast.LENGTH_SHORT).show();
            });
            listeners.put(R.id.btnTeams, (position, item) -> {
                Toast.makeText(this, "Click Teams on position "+ String.valueOf(position)+ " for "+item.getCaption(), Toast.LENGTH_SHORT).show();
            });
            rvAdapter = new RecyclerBindingAdapterFiltered<CompetitionModel>(R.layout.item_competition, BR.competition, competitions, 0, null, listeners);
            rvAdapter.setOnItemClickListener((position, item) -> {
                Toast.makeText(this, "Item in position "+String.valueOf(position)+" clicked", Toast.LENGTH_SHORT).show();
            });
            rvAdapter.setOnBindHolderListener((holder, position, item) -> {
                ItemCompetitionBinding bindingHolder = (ItemCompetitionBinding) (holder.getBinding());
                bindingHolder.btnMore.setOnClickListener(v-> {
                    bindingHolder.setAdditionalVisibility(!bindingHolder.getAdditionalVisibility());
                });
            });
            rvAdapter.setHasStableIds(true);
            rvAdapter.setFiltereditems(competitions, cmpCompetition);
            rvConfig.setAdapter(rvAdapter);
            binding.setRvConfig(rvConfig);
        } else {
            rvAdapter.setFiltereditems(competitions, cmpCompetition);
        }
    }

}
