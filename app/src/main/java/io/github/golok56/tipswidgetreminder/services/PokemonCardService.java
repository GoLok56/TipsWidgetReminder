package io.github.golok56.tipswidgetreminder.services;

import io.github.golok56.tipswidgetreminder.PokemonCard;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonCardService {
    @GET("cards")
    Single<Response<PokemonCard.PokemonCardResponse>> getCards();
}
