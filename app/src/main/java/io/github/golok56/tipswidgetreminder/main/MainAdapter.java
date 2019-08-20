package io.github.golok56.tipswidgetreminder.main;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.tipswidgetreminder.PokemonCard;
import io.github.golok56.tipswidgetreminder.R;
import io.github.golok56.tipswidgetreminder.detail.DetailActivity;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Activity mActivity;
    private List<PokemonCard> pokemons = new ArrayList<>();

    MainAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.init(pokemons.get(position));
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    void updateData(List<PokemonCard> newPokemons) {
        pokemons.clear();
        pokemons.addAll(newPokemons);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_card_logo)
        ImageView ivCardLogo;
        @BindView(R.id.tv_card_name)
        TextView tvCardName;
        @BindView(R.id.tv_card_rarity)
        TextView tvCartRarity;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void init(PokemonCard pokemonCard) {
            Glide.with(itemView)
                    .load(pokemonCard.getImage())
                    .into(ivCardLogo);

            tvCardName.setText(pokemonCard.getName());
            tvCartRarity.setText(pokemonCard.getRarity());
            itemView.setOnClickListener(view -> showDetail(pokemonCard));
        }

        void showDetail(PokemonCard pokemonCard) {
            Intent intent = new Intent(mActivity, DetailActivity.class)
                    .putExtra(DetailActivity.EXTRA_POKEMON_CARD, pokemonCard);

            ActivityOptionsCompat activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            mActivity,
                            Pair.create(ivCardLogo, "logo"),
                            Pair.create(tvCardName, "name"),
                            Pair.create(tvCartRarity, "rarity")
                    );
            mActivity.startActivity(intent, activityOptionsCompat.toBundle());
        }
    }
}
