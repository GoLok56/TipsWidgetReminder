package io.github.golok56.tipswidgetreminder.main;

import java.util.List;

import io.github.golok56.tipswidgetreminder.PokemonCard;

public interface MainContract {
    interface View {
        void showError(String message);
        void showPokemonList(List<PokemonCard> pokemons);
    }

    interface Presenter {
        void fetchPokemonDataFromInternet();
    }
}
