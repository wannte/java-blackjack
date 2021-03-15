package blackjack.domain.participant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    @Test
    void multiply() {
        Money money = new Money(1000);
        assertThat(money.multiply(1.5)).isEqualTo(1500);
    }
}