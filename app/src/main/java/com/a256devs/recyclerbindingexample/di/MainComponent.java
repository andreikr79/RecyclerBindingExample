package com.a256devs.recyclerbindingexample.di;

import com.a256devs.recyclerbindingexample.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}