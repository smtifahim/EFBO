package com.bryancapps.blackjack.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.bryancapps.blackjack.R;
import com.bryancapps.blackjack.models.Game;
import com.bryancapps.blackjack.models.GameState;
import com.bryancapps.blackjack.models.GameStatus;
import com.bryancapps.blackjack.models.Player;
import com.bryancapps.blackjack.models.PlayerState;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

//This class involves the following events w.r.t the EFBO.
//@EFBO: "Display Game Activity Screen" hasNextEvent "Display Player Money"
//@EFBO: "Display Game Activity Screen" hasNextEvent "Display Dealer Hand"
//@EFBO: "Display Game Activity Screen" hasNextEvent "Display Player Hand"
//@EFBO: "Display Game Activity Screen" hasNextEvent "Display Player Bet"
//@EFBO: "Display Game Activity Screen" hasNextEvent "Enable Increment Bet Button"
//@EFBO: "Enable IncrementBet Button" hasNextEvent "Click Increment Bet Button"

//@EFBO: "Click Increment Bet Button" hasNextEvent "Enable BET Button"
//@EFBO: "Click Increment Bet Button" hasNextEvent "Enable Decrement Bet Button"
//@EFBO: "Enable Decrement Bet Button" hasNextEvent "Click Decrement Bet Button"

//@EFBO: "Click Increment Bet Button" hasNextEvent "Display Updated Bet"
//@EFBO: "Click Decrement Bet Button" hasNextEvent "Display Updated Bet"

//@EFBO: "Click Increment Bet Button" isAlternateEventOf "Click Decrement Bet Button"
//@EFBO: "Click Increment Bet Button"  isAlternateEventOf "Click BET Button"
//@EFBO: "Enable BET button" hasNextEvent "Click BET button"

//@EFBO: "Click BET button" hasNextEvent "Flip Dealer First Card"
//@EFBO: "Click BET button" hasNextEvent "Flip Player Cards"
//@EFBO: "Flip Player Cards" hasNextEvent "Observe Game Status"

//@EFBO: "Enable HIT Button" hasNextEvent "Click HIT Button"
//@EFBO: "Click HIT Button" hasNextEvent "Observe Game Status"
//@EFBO: "Click HIT Button" hasNextEvent "Deal Additional Player Card"
//@EFBO: "Deal Additional Player Card" hasNextEvent "Observe Game Status"

//@EFBO: "Click HIT Button" isAlternateEventOf "Click STAY Button"
//@EFBO: "Click STAY Button" hasNextEvent "Flip Dealer Second Card"
//@EFBO: "Flip Dealer Second Card" hasNextEvent "Observe Game Status"

//@EFBO: "Display PLAY AGAIN Button" hasNextEvent "Click PLAY AGAIN Button"
//@EFBO: "Click PLAY AGAIN Button" hasNextEvent "Display Game Activity Screen"
//@EFBO: "Click PLAY AGAIN Button" hasNextEvent "Display Updated Player Money"

//@EFBO: GameActivityClass hasInteractionWith client-agent
public class GameActivity extends AppCompatActivity {
    private Game game;
    private GameFragmentPagerAdapter adapter;
    private ViewPager pager;
    private List<Disposable> disposables;

    //region Lifecycle


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //@EFBO: GameActivityClass isInterfaceOf "Display Game Activity Screen"
        setContentView(R.layout.activity_game);

        game = new Game();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final int defaultValue = getResources().getInteger(R.integer.saved_money_default);
        game.setMoney(sharedPref.getLong("money", defaultValue));

        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new GameFragmentPagerAdapter(getSupportFragmentManager());
        adapter.add(GameFragment.newInstance(game.newPlayer()));
        pager.setAdapter(adapter);

        subscribeToGameEvents(defaultValue);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        disposables.clear();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("money", game.money());
        editor.apply();
    }

    //endregion

    private void subscribeToGameEvents(int defaultMoney) {
        disposables = new ArrayList<>();
        Disposable listsOfPlayers = game.getObservable()
                .map(GameState::playerCount)
                .distinctUntilChanged()
                .map(count -> game.players())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showPlayers);
        Disposable noMoney = game.players().get(0).getObservable()
                .map(PlayerState::status)
                .filter(status -> status == GameStatus.BETTING && game.money() <= 0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> showMoneyDialog(defaultMoney));
        Collections.addAll(disposables, listsOfPlayers, noMoney);
    }

    private void showMoneyDialog(final int dollars) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle("Need more money?")
                .setMessage(String.format(Locale.US,
                        "Press OK to start over with another $%d", dollars))
                .setPositiveButton("OK", (dialog, which) -> game.setMoney(dollars))
                .show();
    }

    private void showPlayers(ImmutableList<Player> players) {
        // remove any fragments whose players were removed
        while (adapter.fragments.size() > players.size()) {
            adapter.fragments.remove(adapter.fragments.size() - 1);
        }
        // add fragments for any new players
        while (players.size() > adapter.fragments.size()) {
            addGameFragment(GameFragment.newInstance(players.get(adapter.fragments.size())));
        }
        adapter.notifyDataSetChanged();
    }

    public void resetGame() {
        pager.setCurrentItem(0);
        int count = adapter.getCount();
        for (int i = count - 1; i >= 1; i--) {
            adapter.remove(i);
        }
        game.resetForNewHand();
        adapter.notifyDataSetChanged();
    }

    private void addGameFragment(GameFragment fragment) {
        adapter.add(fragment);
    }

    //region FragmentPagerAdapter

    private class GameFragmentPagerAdapter extends FragmentPagerAdapter {
        private final List<GameFragment> fragments;

        GameFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            fragments = new ArrayList<>();
        }

        public void add(GameFragment fragment) {
            fragments.add(fragment);
            notifyDataSetChanged();
        }

        void remove(int index) {
            fragments.remove(index);
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof GameFragment) {
                int index = fragments.indexOf(object);
                if (index != -1) {
                    return index;
                }
            }
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    //endregion
}