package io.github.golok56.tipswidgetreminder.main;

import java.util.List;

import io.github.golok56.tipswidgetreminder.BasePresenter;
import io.github.golok56.tipswidgetreminder.PokemonCard;
import io.github.golok56.tipswidgetreminder.services.PokemonCardService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainPresenter extends BasePresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private PokemonCardService mPokemonCardService;

    MainPresenter(MainContract.View mView, PokemonCardService mPokemonCardService) {
        this.mView = mView;
        this.mPokemonCardService = mPokemonCardService;
    }

    @Override
    public void fetchPokemonDataFromInternet() {
        add(mPokemonCardService.getCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pokemonCardResponseResponse -> {
                    if (pokemonCardResponseResponse.isSuccessful()) {
                        PokemonCard.PokemonCardResponse pokemonCardResponse =
                                pokemonCardResponseResponse.body();
                        if (pokemonCardResponse != null) {
                            List<PokemonCard> pokemonCards = pokemonCardResponse.getPokemonCards();
                            Timber.i("Jumlah kartu pokemon: %d", pokemonCards.size());

                            if (pokemonCards.size() != 0) {
                                mView.showPokemonList(pokemonCards);
                            } else {
                                mView.showError("Kartu yang ditemukan berjumlah 0");
                            }
                        } else {
                            mView.showError("Response null");
                        }
                    } else {
                        mView.showError("Terjadi kesalahan saat melakukan request HTTP " +
                                "dengan status " + pokemonCardResponseResponse.code());
                    }
                }, throwable -> {
                    Timber.i(throwable);
                    mView.showError(throwable.getMessage());
                }));
    }
}
