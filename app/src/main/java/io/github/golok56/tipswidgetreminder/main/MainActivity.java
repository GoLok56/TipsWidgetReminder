package io.github.golok56.tipswidgetreminder.main;

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

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.swl_main)
    SwipeRefreshLayout swlMain;
    @BindView(R.id.tv_main)
    TextView tvMain;

    private MainPresenter mPresenter;
    private MainAdapter mAdapter;

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
        swlMain.setRefreshing(false);
        mAdapter.updateData(pokemons);
        rvMain.setVisibility(View.VISIBLE);
        tvMain.setVisibility(View.GONE);
    }
}
