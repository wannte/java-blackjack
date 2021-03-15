package blackjack.domain.state;

public class Bust extends Finished {
    public Bust(Cards cards) {
        super(cards);
    }

    @Override
    public boolean isBlackjack() {
        return false;
    }

    @Override
    public boolean isBust() {
        return true;
    }

    @Override
    public double profitRatio(State opponent) {
        return -1;
    }

    @Override
    public double profitRatio() {
        return -1;
    }
}
