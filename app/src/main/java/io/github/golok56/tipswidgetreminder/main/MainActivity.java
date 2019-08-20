package io.github.golok56.tipswidgetreminder.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.tipswidgetreminder.DailyReminder;
import io.github.golok56.tipswidgetreminder.PokemonCard;
import io.github.golok56.tipswidgetreminder.R;
import io.github.golok56.tipswidgetreminder.services.RetrofitApp;
import io.github.golok56.tipswidgetreminder.services.sqlite.CardHelper;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final int DAILY_REMINDER_ID = 56;

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

        setAlarm(DailyReminder.class, DAILY_REMINDER_ID, 7);
//        stopAlarm(DailyReminder.class, DAILY_REMINDER_ID);
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

    private void setAlarm(Class cls, int notificationId, int hour) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, cls);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,notificationId, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void stopAlarm(Class cls, int notificationId) {
        Intent intent = new Intent(this, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, 0);
        pendingIntent.cancel();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
