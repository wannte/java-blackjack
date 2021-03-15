package blackjack.domain.participant;

public class Player extends Participant {
    private final Money money;

    public Player(String stringName, Money money) {
        this(new Name(stringName), money);
    }

    public Player(Name name, Money money) {
        super(name, 2);
        this.money = money;
    }

    public int revenue(Dealer dealer) {
        return money.multiply(profitRatio(dealer));
    }
}