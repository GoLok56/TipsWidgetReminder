package io.github.golok56.tipswidgetreminder.detail;

import io.github.golok56.tipswidgetreminder.PokemonCard;

public interface DetailContract {
    interface View {
        void showLogo(String imageUrl);
        void showName(String name);
        void showRarity(String rarity);
        void showSeries(String series);
    }

    interface Presenter {
        void start(PokemonCard pokemonCard);
    }
}
