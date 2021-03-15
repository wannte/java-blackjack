package blackjack.domain.state;

import org.junit.jupiter.api.Test;

import static blackjack.domain.FixtureCards.ACE_CLUBS;
import static blackjack.domain.FixtureCards.JACK_SPADES;
import static org.assertj.core.api.Assertions.assertThat;

class StartRoundTest {
    @Test
    void Blackjack() {
        State state = StartRound.draw(ACE_CLUBS, JACK_SPADES);
        assertThat(state).isInstanceOf(Blackjack.class);
    }

    @Test
    void Hit() {
        State state = StartRound.draw(JACK_SPADES, JACK_SPADES);
        assertThat(state).isInstanceOf(Hit.class);
    }
}