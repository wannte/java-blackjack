package blackjack.domain;

import blackjack.domain.card.Deck;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;

import java.util.Collections;
import java.util.List;

public class Game {
    public static final int BLACKJACK_NUMBER = 21;
    public static final String PLAYER_NUMBER_ERROR = "플레이어는 최소 1명 이상이여야 합니다.";
    public static final int NUMBER_OF_DECK = 4;

    private final Dealer dealer;
    private final List<Player> players;
    private final Deck deck;
    private int playerIndex = 0;

    private Game(List<Player> players) {
        this.dealer = new Dealer();
        this.players = players;
        this.deck = new Deck(NUMBER_OF_DECK);
    }

    public static Game of(List<Player> players) {
        if (players.isEmpty()) {
            throw new IllegalArgumentException(PLAYER_NUMBER_ERROR);
        }
        return new Game(players);
    }

    public void startRound() {
        drawTwoCard(dealer);
        for (Player player : players) {
            drawTwoCard(player);
        }
    }

    private void drawTwoCard(Participant participant) {
        participant.startRound(deck.draw(), deck.draw());
    }

    public Player getCurrentPlayer() {
        return players.get(playerIndex);
    }

    public void reflectInput(boolean willDraw) {
        Player player = getCurrentPlayer();
        if (willDraw) {
            drawCard(player);
            return;
        }
        if (player.isNotFinished()) {
            player.stay();
        }
        playerIndex += 1;
    }

    public boolean isNotEnd() {
        return playerIndex < players.size();
    }

    public void drawCard(Participant participant) {
        participant.addCard(deck.draw());
    }

    public int playDealerTurn() {
        int cnt = 0;
        while (dealer.shouldDraw()) {
            drawCard(dealer);
            cnt++;
        }
        if (dealer.isNotFinished()) {
            dealer.stay();
        }
        return cnt;
    }

    public int dealerRevenue() {
        return (-1) * totalPlayersRevenue();
    }

    private int totalPlayersRevenue() {
        return players.stream()
                      .mapToInt(this::getPlayerRevenue)
                      .sum();
    }

    public int getPlayerRevenue(Player player) {
        return player.revenue(dealer);
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Dealer getDealer() {
        return dealer;
    }

}