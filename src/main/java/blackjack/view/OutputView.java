package blackjack.view;

import blackjack.domain.card.Card;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Money;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;

import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String SET_UP_MESSAGE = "%s와 %s에게 2장의 나누었습니다." + LINE_SEPARATOR;
    public static final String CARD_INFO_MESSAGE = "%s카드: %s";
    public static final String DEALER_DRAW_MESSAGE = "딜러는 16이하라 한장의 카드를 더 받았습니다.";
    public static final String RESULT_PREFIX = " - 결과 : %d" + LINE_SEPARATOR;
    public static final String REVENUE_FORMAT = "%s: %d" + LINE_SEPARATOR;
    public static final String FINAL_REVENUE_HEADER = LINE_SEPARATOR + "## 최종 수익";

    private OutputView() {
    }

    public static void printSetup(Dealer dealer, List<Player> players) {
        String playerNames = players.stream()
                                    .map(Player::getName)
                                    .collect(Collectors.joining(", "));
        System.out.printf(SET_UP_MESSAGE, dealer.getName(), playerNames);

        printOpenCardInfo(dealer);
        for (Player player : players) {
            printOpenCardInfo(player);
        }
    }

    public static void printOpenCardInfo(Participant participant) {
        String cardInfo = cardsToString(participant.getOpenCard());
        System.out.printf(CARD_INFO_MESSAGE, participant.getName(), cardInfo);
        System.out.print(LINE_SEPARATOR);
    }

    public static void printCardInfo(Participant participant) {
        String cardInfo = cardsToString(participant.getCards());
        System.out.printf(CARD_INFO_MESSAGE, participant.getName(), cardInfo);
    }

    private static String cardsToString(List<Card> cards) {
        return cards.stream()
                    .map(card -> card.getNumber() + card.getShape())
                    .collect(Collectors.joining(", "));
    }

    public static void printCardInfoWithLineSeparator(Participant participant) {
        printCardInfo(participant);
        System.out.print(LINE_SEPARATOR);
    }

    public static void printDealerDrawMessage(int dealerDrawCount) {
        for (int i = 0; i < dealerDrawCount; i++) {
            System.out.println(DEALER_DRAW_MESSAGE);
        }
    }

    public static void printFinalCardInfo(Dealer dealer, List<Player> players) {
        printFinalCardInfo(dealer);
        for (Player player : players) {
            printFinalCardInfo(player);
        }
    }

    private static void printFinalCardInfo(Participant participant) {
        printCardInfo(participant);
        System.out.printf(RESULT_PREFIX, participant.cardResult());
    }

    public static void printFinalRevenueHeader() {
        System.out.println(FINAL_REVENUE_HEADER);
    }

    public static void printFinalRevenue(Participant participant, int revenue) {
        System.out.printf(REVENUE_FORMAT, participant.getName(), revenue);
    }

    public static void printMessage(String s) {
        System.out.println(s);
    }
}
