package blackjack.domain;

import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Money;
import blackjack.domain.participant.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameTest {
    Game game;

    @BeforeEach
    void setUp() {
        Player a = new Player("bob", new Money(3000));
        Player b = new Player("kak", new Money(2000));
        game = Game.of(Arrays.asList(a, b));
    }

    @Test
    void createGame() {
        assertThat(game.getPlayers()).hasSize(2);
    }

    @Test
    @DisplayName("카드 두장 셋업")
    void setUpTwoCards() {
        game.startRound();
        for (Player player : game.getPlayers()) {
            assertThat(player.getCards()).hasSize(2);
        }
        Dealer dealer = game.getDealer();
        assertThat(dealer.getCards()).hasSize(2);
    }

    @Test
    void dealerHitUntilStay() {
        game.startRound();
        game.playDealerTurn();
        assertFalse(game.getDealer()
                        .shouldDraw());
    }
}