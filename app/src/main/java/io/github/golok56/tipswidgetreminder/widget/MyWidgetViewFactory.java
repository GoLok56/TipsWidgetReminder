package io.github.golok56.tipswidgetreminder.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.github.golok56.tipswidgetreminder.PokemonCard;
import io.github.golok56.tipswidgetreminder.R;
import io.github.golok56.tipswidgetreminder.services.sqlite.CardHelper;

public class MyWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<PokemonCard> pokemonCards = new ArrayList<>();
    private CardHelper cardHelper;
    private Context context;

    public MyWidgetViewFactory(Context context) {
        this.context = context;
        cardHelper = CardHelper.getInstance(context);
    }

    @Override
    public void onCreate() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void onDataSetChanged() {
        cardHelper.open();
        pokemonCards = cardHelper.findAll();
        cardHelper.close();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return pokemonCards.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        try {
            Bitmap poster = Glide.with(context)
                    .asBitmap()
                    .load(pokemonCards.get(i).getImage())
                    .submit()
                    .get();
            remoteViews.setImageViewBitmap(R.id.iv_widget_item, poster);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Intent intent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.iv_widget_item, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
