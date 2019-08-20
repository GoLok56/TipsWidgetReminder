package io.github.golok56.tipswidgetreminder.services;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApp {
    private static final String BASE_URL = "https://api.pokemontcg.io/v1/";

    private static RetrofitApp sInstance;
    public static RetrofitApp getInstance() {
        if (sInstance == null) sInstance = new RetrofitApp();
        return sInstance;
    }

    private PokemonCardService mPokemonCardService;
    private RetrofitApp() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                .build();
        mPokemonCardService = mRetrofit.create(PokemonCardService.class);
    }

    public PokemonCardService getPokemonCardService() {
        return mPokemonCardService;
    }
}
