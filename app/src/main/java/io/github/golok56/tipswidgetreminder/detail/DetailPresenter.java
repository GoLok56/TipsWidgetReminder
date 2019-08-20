package io.github.golok56.tipswidgetreminder.detail;

import io.github.golok56.tipswidgetreminder.PokemonCard;

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View mView;
    public DetailPresenter(DetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void start(PokemonCard pokemonCard) {
        mView.showLogo(pokemonCard.getImage());
        mView.showName(pokemonCard.getName());
        mView.showRarity(pokemonCard.getRarity());
        mView.showSeries(pokemonCard.getSeries());
    }
}
