package blackjack.domain.participant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static blackjack.domain.FixtureCards.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BettingTest {

    private Money money;
    private Player player;
    private Dealer dealer;

    @BeforeEach
    void setUp() {
        money = new Money(1000);
        player = new Player("bob", money);
        dealer = new Dealer();
    }

    @Test
    void bettingIfBlackjack() {
        player.startRound(JACK_SPADES, ACE_CLUBS);
        dealer.startRound(NINE_SPADES, JACK_SPADES);
        dealer.stay();
        assertThat(player.revenue(dealer)).isEqualTo(1500);
    }

    @Test
    void bothBlackjack() {
        player.startRound(JACK_SPADES, ACE_CLUBS);
        dealer.startRound(JACK_SPADES, ACE_CLUBS);

        assertThat(player.revenue(dealer)).isEqualTo(0);
    }

    @Test
    void bettingIfHit() {
        player.startRound(JACK_SPADES, JACK_SPADES);
        dealer.startRound(JACK_SPADES, JACK_SPADES);
        dealer.stay();
        assertThatThrownBy(() -> player.revenue(dealer)).isInstanceOf(UnsupportedOperationException.class);
    }


    @Test
    void bettingIfStayWin() {
        player.startRound(JACK_SPADES, JACK_SPADES);
        player.stay();
        dealer.startRound(JACK_SPADES, NINE_SPADES);
        dealer.stay();
        assertThat(player.revenue(dealer)).isEqualTo(1000);
    }

    @Test
    void bettingIfStayDraw() {
        player.startRound(JACK_SPADES, JACK_SPADES);
        player.stay();
        dealer.startRound(JACK_SPADES, JACK_SPADES);
        dealer.stay();
        assertThat(player.revenue(dealer)).isEqualTo(0);
    }

    @Test
    void bettingIfStayLose() {
        player.startRound(JACK_SPADES, NINE_SPADES);
        player.stay();
        dealer.startRound(JACK_SPADES, JACK_SPADES);
        dealer.stay();
        assertThat(player.revenue(dealer)).isEqualTo(-1000);
    }

    @Test
    void bettingIfBust() {
        player.startRound(JACK_SPADES, JACK_SPADES);
        player.addCard(JACK_SPADES);
        dealer.startRound(JACK_SPADES, JACK_SPADES);
        dealer.stay();
        assertThat(player.revenue(dealer)).isEqualTo(-1000);
    }

    @Test
    void bothBust() {
        player.startRound(JACK_SPADES, JACK_SPADES);
        player.addCard(JACK_SPADES);
        dealer.startRound(JACK_SPADES, JACK_SPADES);
        dealer.addCard(TWO_DIAMONDS);
        assertThat(player.revenue(dealer)).isEqualTo(-1000);
    }

    @Test
    void playerHitDealerBlackjack() {
        player.startRound(JACK_SPADES, JACK_SPADES);
        player.stay();
        dealer.startRound(JACK_SPADES, ACE_CLUBS);
        assertThat(player.revenue(dealer)).isEqualTo(-1000);
    }
}