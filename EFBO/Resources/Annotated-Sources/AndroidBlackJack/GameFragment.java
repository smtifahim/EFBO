package com.bryancapps.blackjack.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bryancapps.blackjack.R;
import com.bryancapps.blackjack.models.Card;
import com.bryancapps.blackjack.models.Game;
import com.bryancapps.blackjack.models.GameState;
import com.bryancapps.blackjack.models.GameStatus;
import com.bryancapps.blackjack.models.Player;
import com.bryancapps.blackjack.models.PlayerState;
import com.google.common.collect.ImmutableList;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@SuppressWarnings({"WeakerAccess", "unused"})




































public class GameFragment extends Fragment {

    //@EFBO: moneyTextView isInterfaceOf "Display Player Money"
    //@EFBO: moneyTextView isInterfaceOf "Display Updated Player Money"
    @BindView(R.id.text_money) TextView moneyTextView;

    //@EFBO: betTextView isInterfaceOf "Display Updated Bet"
    //@EFBO: betTextView isInterfaceOf "Display Player Bet"
    @BindView(R.id.text_bet) TextView betTextView;

    //@EFBO: playerScoreTextView isInterfaceOf "Display Player Score"
    @BindView(R.id.text_player_score) TextView playerScoreTextView;

    //@EFBO: dealerScoreTextView isInterfaceOf "Display Dealer Score"
    @BindView(R.id.text_dealer_score) TextView dealerScoreTextView;

    //@EFBO: handOverTextView isInterfaceOf "Display Final Outcome"
    @BindView(R.id.text_showdown_description) TextView handOverTextView;
    
    @BindView(R.id.text_bet_reminder) TextView bigBetView;

    //@EFBO: betButton isInterfaceOf "Click Bet Button"
    @BindView(R.id.button_bet) Button betButton;

    //@EFBO: incrementBetButton isInterfaceOf "Click Increment Bet Button"
    @BindView(R.id.button_increment_bet) Button incrementBetButton;

    //@EFBO: decrementBetButton isInterfaceOf "Click Decrement Bet Button"
    @BindView(R.id.button_decrement_bet) Button decrementBetButton;

    @BindView(R.id.button_double) Button doubleButton;

    @BindView(R.id.button_split) Button splitButton;

    //@EFBO: betDecisionView isInterfaceOf "Display Bet Decision"
    //@EFBO: betDecisionView isInterfaceOf "Hide Bet Decision"
    @BindView(R.id.layout_bet_decision) View betDecisionView;

    //@EFBO: hitAndStayView isInterfaceOf "Display HIT Button"
    //@EFBO: hitAndStayView isInterfaceOf "Hide HIT Button"
    //@EFBO: hitAndStayView isInterfaceOf "Display STAY Button"
    //@EFBO: hitAndStayView isInterfaceOf "Hide STAY Button"
    @BindView(R.id.layout_hitting_decision) View hitAndStayView;

    //@EFBO: playAgainView isInterfaceOf "Display PLAY AGAIN Button"
    //@EFBO: playAgainView isInterfaceOf "Hide PLAY AGAIN Button"
    @BindView(R.id.layout_play_again) View playAgainView;

    //@EFBO: waitingView isInterfaceOf "Display Waiting View"
    //@EFBO: waitingView isInterfaceOf "Hide Waiting View"
    @BindView(R.id.layout_waiting) View waitingView;

    //@EFBO: dealerHandView isInterfaceOf "Display Dealer Hand"
    @BindView(R.id.layout_dealer_hand) LinearLayout dealerHandView;

    //@EFBO: playerHandView isInterfaceOf "Display Player Hand"
    @BindView(R.id.layout_player_hand) LinearLayout playerHandView;

    private Unbinder unbinder;
    private CompositeDisposable disposable;
    private TransitionSet transitionSet;

    private Player player;
    private Game game;
    private NumberFormat currencyFormat;

    //region Lifecycle


    public static GameFragment newInstance(@NonNull Player player) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putSerializable("player", player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().getSerializable("player") != null) {
            player = (Player) getArguments().getSerializable("player");
            if (player == null) {
                throw new NullPointerException("Player from arguments bundle is null");
            }
            game = player.game();
        }

        transitionSet = new TransitionSet()
                .setOrdering(TransitionSet.ORDERING_SEQUENTIAL)
                .addTransition(new TransitionSet()
                        .setOrdering(TransitionSet.ORDERING_TOGETHER)
                        .addTransition(new CardFlip())
                        .addTransition(new ChangeBounds()))
                .addTransition(new Slide(Gravity.END));
        currencyFormat = NumberFormat.getCurrencyInstance();
        currencyFormat.setMaximumFractionDigits(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showMoney(game.money());
    }

    @Override
    public void onStart() {
        super.onStart();

        Disposable dealerHands = game.getObservable()
                .map(GameState::dealerCards)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showDealerCards);
        Disposable monies = game.getObservable()
                .map(GameState::money)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showMoney);
        Disposable playerHands = player.getObservable()
                .map(PlayerState::cards)
                .distinctUntilChanged()
                .filter(cards -> cards.size() != 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showPlayerCards);
        Disposable bets = player.getObservable()
                .map(PlayerState::bet)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showBet);
        Disposable statuses = player.getObservable()
                .map(PlayerState::status)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showDecisionView);
        disposable = new CompositeDisposable(dealerHands, monies, playerHands, bets, statuses);
    }

    @Override
    public void onStop() {
        super.onStop();
        disposable.dispose();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        disposable.dispose();
    }

    //endregion

    Player getPlayer() {
        return player;
    }

    //region Betting

    private long getPendingBet() {
        String text = betTextView.getText().toString();
        return Long.parseLong(text.substring(1, text.length()));
    }

    private void setPendingBet(long bet) {
        betTextView.setText(getString(R.string.current_bet, bet));

        long decrementAmount;
        if (bet < 100 && bet != 0) {
            decrementAmount = bet;
        } else {
            decrementAmount = 100;
        }
        decrementBetButton.setText(getString(R.string.decrement_bet, decrementAmount));
        decrementBetButton.setEnabled(bet != 0);
        betButton.setEnabled(bet != 0);

        long incrementAmount;
        if (bet > game.money() - 100 && bet != game.money()) {
            incrementAmount = bet;
        } else {
            incrementAmount = 100;
        }
        incrementBetButton.setText(getString(R.string.increment_bet, incrementAmount));
        incrementBetButton.setEnabled(bet != game.money());
    }

    //@EFBO: decrementBetButton hasInteractionWith user-agent
    @OnClick(R.id.button_decrement_bet)
    public void decrementBet() {
        long betDecrease;
        if (getPendingBet() < 100) {
            betDecrease = getPendingBet();
        } else {
            betDecrease = 100;
        }

        setPendingBet(getPendingBet() - betDecrease);
    }

    //@EFBO: incrementBetButton hasInteractionWith user-agent
    @OnClick(R.id.button_increment_bet)
    public void incrementBet() {
        long newBet;
        if (game.money() < 100 + getPendingBet()) {
            newBet = game.money();
        } else {
            newBet = getPendingBet() + 100;
        }

        setPendingBet(newBet);
    }

    //@EFBO: betButton hasInteractionWith user-agent
    @OnClick(R.id.button_bet)
    public void onBet() {
        player.initialBet(getPendingBet());
    }

    //endregion

    //region Hitting and Staying

    private void showBet(long bet) {
        bigBetView.setText(getString(R.string.current_bet, bet));
    }

    //@EFBO: hitButton hasInteractionWith user-agent
    @OnClick(R.id.button_hit)
    public void onHit() {
        player.hit();
    }

    //@EFBO: stayButton hasInteractionWith user-agent
    @OnClick(R.id.button_stay)
    public void onStay() {
        player.stay();
    }

    @OnClick(R.id.button_double)
    public void onDouble() {
        player.doubleHand();
    }

    @OnClick(R.id.button_split)
    public void onSplit() {
        player.split();
    }

    //@EFBO: setShowdownText hasInitialEvent "Evaluate Player Outcome"
    void setShowdownText() {
        Resources resources = getResources();
        long winnings = player.winnings();

        String text;

        //@EFBO: "Evaluate Player Outcome" hasInterface PlayerClass
        //@EFBO: server-agent interactsWith PlayerClass
        switch (player.outcome()) {
            //@EFBO: "Evaluate Player Outcome" hasNextEvent "Evaluation is PUSH"
            case PUSH:
                //@EFBO: client-agent interactsWith handOverTextView
                //@EFBO: "Evaluation is PUSH" hasNextEvent "Display PUSH Message"
                handOverTextView.setText(R.string.push);
                break;

            //@EFBO: "Evaluate Player Outcome" hasNextEvent "Evaluation is PLAYER_BLACKJACK"
            case PLAYER_BLACKJACK:
                text = String.format(resources.getString(R.string.player_blackjack), winnings - player.getBet());
                //@EFBO: "Evaluation is PLAYER_BLACKJACK" hasNextEvent "Display Player Blackjack Message"
                handOverTextView.setText(text);
                break;

            //@EFBO: "Evaluation is PLAYER_BLACKJACK" isAlternateEventOf "Evaluation is DEALER_BLACKJACK"
            //@EFBO: "Evaluate Player Outcome" hasNextEvent "Evaluation is DEALER_BLACKJACK"
            case DEALER_BLACKJACK:
                text = String.format(resources.getString(R.string.dealer_blackjack), player.getBet());
                //@EFBO: "Evaluation is DEALER_BLACKJACK" hasNextEvent "Display Player Lost Message"
                handOverTextView.setText(text);
                break;

            //@EFBO: "Evaluation is PLAYER_BLACKJACK" isAlternateEventOf "Evaluation is PLAYER_WIN"
            //@EFBO: "Evaluate Player Outcome" hasNextEvent "Evaluation is PLAYER_WIN"
            case PLAYER_WIN:
                text = String.format(resources.getString(R.string.player_wins), winnings - player.getBet());
                //@EFBO: "Evaluation is PLAYER_WIN" hasNextEvent "Display Player Won Message"
                handOverTextView.setText(text);
                break;

            //@EFBO: "Evaluation is DEALER_BUST" isAlternateEventOf "Evaluation is PLAYER_WIN"
            //@EFBO: "Evaluate Player Outcome" hasNextEvent "Evaluation is DEALER_BUST"
            case DEALER_BUST:
                text = String.format(resources.getString(R.string.dealer_busts), winnings - player.getBet());
                //@EFBO: "Evaluation is DEALER_BUST" hasNextEvent "Display Dealer Busted Message"
                handOverTextView.setText(text);
                break;

            //@EFBO: "Evaluation is DEALER_WIN" isAlternateEventOf "Evaluation is DEALER_BUST"
            //@EFBO: "Evaluate Player Outcome" hasNextEvent "Evaluation is DEALER_WIN"
            case DEALER_WIN:
                text = String.format(resources.getString(R.string.dealer_wins), player.getBet());
                //@EFBO: "Evaluation is DEALER_WIN" hasNextEvent "Display Player Lost Message"
                handOverTextView.setText(text);
                break;

            //@EFBO: "Evaluation is DEALER_WIN" isAlternateEventOf "Evaluation is PLAYER_BUST"
            //@EFBO: "Evaluate Player Outcome" hasNextEvent "Evaluation is PLAYER_BUST"
            case PLAYER_BUST:
                text = String.format(resources.getString(R.string.player_busts), player.getBet());
                //@EFBO: "Evaluation is DEALER_BUST" hasNextEvent "Display Over 21 Player Lost Message"
                handOverTextView.setText(text);
                break;
            case ERROR:
                handOverTextView.setText(R.string.hand_outcome_error);
                break;
        }
    }

    //endregion

    //region Hand Views

    private void showDealerCards(ImmutableList<Card> cards) {
        //@EFBO: client-agent interactsWith dealerHandView
        TransitionManager.beginDelayedTransition(dealerHandView, transitionSet);
        if (player.status() == GameStatus.BETTING) {
            dealerHandView.removeAllViews();
            cards = ImmutableList.of(Card.dealerBlank, Card.dealerBlank);
        }
        for (int i = 0; i < cards.size(); i++) {
            Card card;
            ImageView imageView;
            card = cards.get(i);
            if (i < dealerHandView.getChildCount()) {
                imageView = (ImageView) dealerHandView.getChildAt(i);
            } else {
                imageView = newImageViewForLayout(dealerHandView);
            }
            setCardForImageView(card, imageView);
        }
        //@EFBO: client-agent interactsWith dealerScoreTextView
        dealerScoreTextView.setText(String.valueOf(game.dealerScore()));
    }

    private void showPlayerCards(ImmutableList<Card> cards) {
        //@EFBO: client-agent interactsWith playerHandView
        TransitionManager.beginDelayedTransition(playerHandView, transitionSet);

        if (player.status() == GameStatus.BETTING) {
            cards = ImmutableList.of(Card.playerBlank, Card.playerBlank);
        }

        // remove any extra views
        if (playerHandView.getChildCount() > cards.size()) {
            int count = playerHandView.getChildCount() - cards.size();
            playerHandView.removeViews(cards.size(), count);
        }
        // set any existing views
        for (int i = 0; i < playerHandView.getChildCount(); i++) {
            ImageView cardImageView = (ImageView) playerHandView.getChildAt(i);
            setCardForImageView(cards.get(i), cardImageView);
        }
        // add any missing views
        for (int i = playerHandView.getChildCount(); i < cards.size(); i++) {
            setCardForImageView(cards.get(i), newImageViewForLayout(playerHandView));
        }
        for (int i = cards.size(); i < 2; i++) {
            setCardForImageView(Card.playerBlank, newImageViewForLayout(playerHandView));
        }
        //@EFBO: client-agent interactsWith playerScoreTextView
        playerScoreTextView.setText(String.valueOf(player.score()));
        
        doubleButton.setEnabled(player.isDoublable());
        splitButton.setEnabled(player.isSplittable());
    }

    private ImageView newImageViewForLayout(LinearLayout handView) {
        ImageView cardView = (ImageView) LayoutInflater.from(handView.getContext())
                .inflate(R.layout.card_item, handView, false);

        if (handView.getChildCount() == 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cardView.getLayoutParams());
            params.setMargins(0, 0, 0, 0);
            cardView.setLayoutParams(params);
        }

        handView.addView(cardView);
        return cardView;
    }

    private void setCardForImageView(Card card, ImageView imageView) {
        imageView.setImageResource(card.getImageID());
        imageView.setTag(card.toString());
    }

    //endregion

    private void showMoneyChange(double change) {
        if (change > 0) {
            moneyTextView.setText(String.format("%s\n+ %s", moneyTextView.getText(), currencyFormat.format(change)));
        }
    }

    private void showMoney(long money) {
        moneyTextView.setText(getString(R.string.your_money, money));
        incrementBetButton.setEnabled(money > 100);
    }

    //@EFBO: button_play_again hasInteractionWith user-agent
    @OnClick(R.id.button_play_again)
    public void playAgain() {
        if (GameActivity.class.isInstance(getActivity())) {
            GameActivity activity = (GameActivity) getActivity();
            activity.resetGame();
        }
    }

    //@EFBO: showDecisionView hasInitialEvent "Observe Game Status"
    //@EFBO: showDecisionView hasFinalEvent "Display Play Again Button"
    private void showDecisionView(GameStatus status) {
        //@EFBO: "Observe Game Status" hasNextEvent "Game Status is BETTING"
        if (status == GameStatus.BETTING) {
            //@EFBO: hitAndStayView hasInteractionWith client-agent
            //@EFBO: "Game Status is BETTING" hasNextEvent "Hide HIT Button"
            //@EFBO: "Game Status is BETTING" hasNextEvent "Hide STAY Button"
            hitAndStayView.setVisibility(View.GONE);

            //@EFBO: waitingView hasInteractionWith client-agent
            //@EFBO: "Game Status is BETTING" hasNextEvent "Hide Waiting View"
            waitingView.setVisibility(View.GONE);

            //@EFBO: playAgainView hasInteractionWith client-agent
            //@EFBO: "Game Status is BETTING" hasNextEvent "Hide PLAY AGAIN Button"
            playAgainView.setVisibility(View.GONE);

            //@EFBO: betDecisionView hasInteractionWith client-agent
            //@EFBO: "Game Status is BETTING" hasNextEvent "Display Bet Decision"
            betDecisionView.setVisibility(View.VISIBLE);
        }
        //@EFBO: "Game Status is BETTING" isAlternateEventOf "Game Status is HITTING"
        //@EFBO: "Observe Game Status" hasNextEvent "Game Status is HITTING"
        else if (status == GameStatus.HITTING) {

            //@EFBO: "Game Status is HITTING" hasNextEvent "Hide Bet Decision"
            betDecisionView.setVisibility(View.GONE);

            //@EFBO: "Game Status is HITTING" hasNextEvent "Hide Waiting View"
            waitingView.setVisibility(View.GONE);

            //@EFBO: "Game Status is HITTING" hasNextEvent "Hide PLAY AGAIN Button"
            playAgainView.setVisibility(View.GONE);

            //@EFBO: "Game Status is HITTING" hasNextEvent "Display HIT Button"
            //@EFBO: "Game Status is HITTING" hasNextEvent "Display STAY Button"
            hitAndStayView.setVisibility(View.VISIBLE);

            splitButton.setEnabled(player.isSplittable());
            doubleButton.setEnabled(player.isDoublable());
        }
        //@EFBO: "Game Status is WAITING" isAlternateEventOf "Game Status is HITTING"
        //@EFBO: "Observe Game Status" hasNextEvent "Game Status is WAITING"
        else if (status == GameStatus.WAITING) {

            //@EFBO: "Game Status is WAITING" hasNextEvent "Hide Bet Decision"
            betDecisionView.setVisibility(View.GONE);

            //@EFBO: "Game Status is WAITING" hasNextEvent "Hide HIT Button"
            //@EFBO: "Game Status is WAITING" hasNextEvent "Hide STAY Button"
            hitAndStayView.setVisibility(View.GONE);

            //@EFBO: "Game Status is WAITING" hasNextEvent "Hide PLAY AGAIN Button"
            playAgainView.setVisibility(View.GONE);

            //@EFBO: "Game Status is WAITING" hasNextEvent "Display Waiting View"
            waitingView.setVisibility(View.VISIBLE);
        }
        //@EFBO: "Game Status is WAITING" isAlternateEventOf "Game Status is SHOWDOWN"
        //@EFBO: "Observe Game Status" hasNextEvent "Game Status is SHOWDOWN"
        else if (status == GameStatus.SHOWDOWN) {
            //@EFBO: "Game Status is SHOWDOWN" hasNextEvent "Evaluate Player Outcome"
            setShowdownText();

            //@EFBO: "Game Status is SHOWDOWN" hasNextEvent "Hide Bet Decision"
            betDecisionView.setVisibility(View.GONE);

            //@EFBO: "Game Status is SHOWDOWN" hasNextEvent "Hide HIT Button"
            //@EFBO: "Game Status is SHOWDOWN" hasNextEvent "Hide STAY Button"
            hitAndStayView.setVisibility(View.GONE);

            //@EFBO: "Game Status is SHOWDOWN" hasNextEvent "Hide Waiting View"
            waitingView.setVisibility(View.GONE);

            //@EFBO: "Game Status is SHOWDOWN" hasNextEvent "Display PLAY AGAIN Button"
            playAgainView.setVisibility(View.VISIBLE);
        }
    }
}
