package blackjack.domain.state;

public class Stay extends Finished {
    public Stay(Cards cards) {
        super(cards);
    }

    @Override
    public boolean isBlackjack() {
        return false;
    }

    @Override
    public boolean isBust() {
        return false;
    }

    @Override
    public double profitRatio(State opponent) {
        if (opponent.isBlackjack())
            return -1;
        if (opponent.isBust())
            return 1;
        return Integer.compare(cards.calculateResult(), opponent.cards.calculateResult());
    }

    @Override
    public double profitRatio() {
        return 1;
    }
}
