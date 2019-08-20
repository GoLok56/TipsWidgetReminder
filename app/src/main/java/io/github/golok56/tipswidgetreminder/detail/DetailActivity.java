package io.github.golok56.tipswidgetreminder.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.tipswidgetreminder.PokemonCard;
import io.github.golok56.tipswidgetreminder.R;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    public static final String EXTRA_POKEMON_CARD = "extra:pokemon-card";

    @BindView(R.id.iv_detail_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_detail_name)
    TextView tvName;
    @BindView(R.id.tv_detail_rarity)
    TextView tvRarity;
    @BindView(R.id.tv_detail_series)
    TextView tvSeries;

    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        PokemonCard pokemonCard = getIntent().getParcelableExtra(EXTRA_POKEMON_CARD);
        mPresenter = new DetailPresenter(this);
        mPresenter.start(pokemonCard);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLogo(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .into(ivLogo);
    }

    @Override
    public void showName(String name) {
        tvName.setText(name);
    }

    @Override
    public void showRarity(String rarity) {
        tvRarity.setText(rarity);
    }

    @Override
    public void showSeries(String series) {
        tvSeries.setText(series);
    }
}
