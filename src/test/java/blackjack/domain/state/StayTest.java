package blackjack.domain.state;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static blackjack.domain.FixtureCards.*;
import static org.assertj.core.api.Assertions.assertThat;

class StayTest {
    private static State blackjack = new Blackjack(new Cards(ACE_CLUBS, JACK_SPADES));
    private static State stay19 = new Stay(new Cards(NINE_SPADES, JACK_SPADES));
    private static State bust = new Bust(new Cards(JACK_SPADES, JACK_SPADES, JACK_SPADES));

    State state;

    private static Stream<State> opponents() {
        return Stream.of(blackjack, stay19, bust);
    }

    @Test
    void blackjackVersusStay() {
        state = new Blackjack(new Cards(ACE_CLUBS, JACK_SPADES));
        assertThat(state.profitRatio(stay19)).isEqualTo(1.5);
    }

    @Test
    void blackjackVersusBlackjack() {
        state = new Blackjack(new Cards(ACE_CLUBS, JACK_SPADES));
        assertThat(state.profitRatio(blackjack)).isEqualTo(0);
    }

    @Test
    void stayVersusBiggerStay() {
        state = new Stay(new Cards(TWO_DIAMONDS, JACK_SPADES));
        assertThat(state.profitRatio(stay19)).isEqualTo(-1);
    }

    @Test
    void stayVersusSameStay() {
        state = new Stay(new Cards(NINE_SPADES, JACK_SPADES));
        assertThat(state.profitRatio(stay19)).isEqualTo(0);
    }

    @Test
    void stayVersusSmallerStay() {
        state = new Stay(new Cards(JACK_SPADES, JACK_SPADES));
        assertThat(state.profitRatio(stay19)).isEqualTo(1);
    }

    @Test
    void stayVersusBlackjack() {
        state = new Stay(new Cards(JACK_SPADES, JACK_SPADES));
        assertThat(state.profitRatio(blackjack)).isEqualTo(-1);
    }

    @Test
    void stayVersusBust() {
        state = new Stay(new Cards(JACK_SPADES, JACK_SPADES));
        assertThat(state.profitRatio(bust)).isEqualTo(1);
    }

    @ParameterizedTest
    @MethodSource("opponents")
    void bust(State opponent) {
        state = new Bust(new Cards(JACK_SPADES, JACK_SPADES, JACK_SPADES));
        assertThat(state.profitRatio(opponent)).isEqualTo(-1);
    }
}