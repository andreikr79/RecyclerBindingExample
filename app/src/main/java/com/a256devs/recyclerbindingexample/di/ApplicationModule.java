package com.a256devs.recyclerbindingexample.di;

import android.app.Application;
import android.content.Context;

import com.a256devs.recyclerbindingexample.api.FootballApi;
import com.a256devs.recyclerbindingexample.utils.Constants;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return application;
    }


    @Provides
    @Singleton
    public Context provideContext() {
        return application.getApplicationContext();
    }


    @Provides
    @Singleton
    public FootballApi provideFootballApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .create()))
                .baseUrl(FootballApi.BASE_URL)
                .build().create(FootballApi.class);
    }

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(Constants.CONNECTIONTIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Constants.READTIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(Constants.WRITETIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    return chain.proceed(original);
                });
        return builder.build();
    }

}


