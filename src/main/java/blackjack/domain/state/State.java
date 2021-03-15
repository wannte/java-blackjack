package blackjack.domain.state;

import blackjack.domain.card.Card;

public abstract class State {
    protected Cards cards;

    public State(Cards cards) {
        this.cards = cards;
    }

    public Cards getCards() {
        return cards;
    }

    public abstract State draw(Card card);

    public abstract State stay();

    public abstract boolean isBlackjack();

    public abstract boolean isBust();

    public abstract boolean isFinished();

    public abstract double profitRatio(State opponent);
}
