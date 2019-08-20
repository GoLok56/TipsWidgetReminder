package io.github.golok56.tipswidgetreminder.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.tipswidgetreminder.PokemonCard;
import io.github.golok56.tipswidgetreminder.R;
import io.github.golok56.tipswidgetreminder.services.RetrofitApp;
import io.github.golok56.tipswidgetreminder.services.sqlite.CardHelper;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.swl_main)
    SwipeRefreshLayout swlMain;
    @BindView(R.id.tv_main)
    TextView tvMain;

    private MainPresenter mPresenter;
    private MainAdapter mAdapter;

    private CardHelper cardHelper;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new MainPresenter(this, RetrofitApp.getInstance().getPokemonCardService());
        mPresenter.fetchPokemonDataFromInternet();
        swlMain.setRefreshing(true);

        mAdapter = new MainAdapter(this);
        rvMain.setAdapter(mAdapter);
        swlMain.setOnRefreshListener(() -> mPresenter.fetchPokemonDataFromInternet());

        sharedPreferences = getSharedPreferences("sharedpref", Context.MODE_PRIVATE);
        cardHelper = CardHelper.getInstance(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dispose();
    }

    @Override
    public void showError(String message) {
        swlMain.setRefreshing(false);
        tvMain.setText(message);
        tvMain.setVisibility(View.VISIBLE);
        rvMain.setVisibility(View.GONE);
    }

    @Override
    public void showPokemonList(List<PokemonCard> pokemons) {
        mAdapter.updateData(pokemons);
        rvMain.setVisibility(View.VISIBLE);
        tvMain.setVisibility(View.GONE);

        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
        if (isFirstTime) {
            cardHelper.open();
            for (PokemonCard pokemonCard : pokemons) {
                cardHelper.insert(pokemonCard);
            }
            cardHelper.close();
            sharedPreferences.edit().putBoolean("isFirstTime", false).apply();
        }
        swlMain.setRefreshing(false);
    }
}
