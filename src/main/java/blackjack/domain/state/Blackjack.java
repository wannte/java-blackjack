package blackjack.domain.state;

public class Blackjack extends Finished {
    public Blackjack(Cards cards) {
        super(cards);
    }

    @Override
    public boolean isBlackjack() {
        return true;
    }

    @Override
    public boolean isBust() {
        return false;
    }

    @Override
    public double profitRatio(State opponent) {
        if (opponent.isBlackjack())
            return 0;
        return 1.5;
    }

}
