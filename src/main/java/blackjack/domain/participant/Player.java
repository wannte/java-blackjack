package blackjack.domain.participant;

public class Player extends Participant {
    private final Money money;
    private double profitRatio;

    public Player(String stringName, Money money) {
        this(new Name(stringName), money);
    }

    public Player(Name name, Money money) {
        super(name, 2);
        this.money = money;
    }

    public void updateProfitRatio(Dealer dealer) {
        if (isDrawWith(dealer)) {
            profitRatio = 0;
            return;
        }
        if (isDefeatedBy(dealer)) {
            profitRatio = -1;
            return;
        }
        profitRatio = profitRatio();
    }

    public boolean isDrawWith(Dealer dealer) {
        return isBothBlackjack(dealer) || stayAndHasSameCardResult(dealer);
    }

    private boolean isBothBlackjack(Dealer dealer) {
        return isBlackJack() && dealer.isBlackJack();
    }

    private boolean stayAndHasSameCardResult(Dealer dealer) {
        return isBothStay(dealer) && cardResult() == dealer.cardResult();
    }

    private boolean isDefeatedBy(Dealer dealer) {
        return isBust() || isBothStay(dealer) && cardResult() < dealer.cardResult() || isStay() && dealer.isBlackJack();
    }

    private boolean isBothStay(Dealer dealer) {
        return isStay() && dealer.isStay();
    }

    public double revenue() {
        return money.value() * profitRatio;
    }
}