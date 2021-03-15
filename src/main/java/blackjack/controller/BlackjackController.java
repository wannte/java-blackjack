package blackjack.controller;

import blackjack.domain.Game;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Money;
import blackjack.domain.participant.Name;
import blackjack.domain.participant.Player;
import blackjack.view.InputView;
import blackjack.view.OutputView;

import java.util.List;
import java.util.stream.Collectors;

public class BlackjackController {

    public static final String DEALER_NO_MORE_DRAW_MESSAGE = "딜러는 16초과라 더 이상 카드를 받지 않습니다.";

    public BlackjackController() {
    }

    public void start() {
        Game game = initGame();
        setUpTwoCards(game);
        askPlayersDrawCard(game);
        makeDealerDrawCard(game);
        showFinalResult(game);
        printFinalRevenue(game);
    }

    private Game initGame() {
        try {
            List<Name> playerNames = inputPlayerNames();
            return Game.of(createPlayersFrom(playerNames));
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            return initGame();
        }
    }

    private List<Name> inputPlayerNames() {
        try {
            return InputView.inputPlayerNames()
                            .stream()
                            .map(Name::new)
                            .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            return inputPlayerNames();
        }
    }

    private List<Player> createPlayersFrom(List<Name> playerNames) {
        return playerNames.stream()
                          .map(this::createPlayer)
                          .collect(Collectors.toList());
    }

    private Player createPlayer(Name playerName) {
        return new Player(playerName, askBettingMoney(playerName));
    }

    private Money askBettingMoney(Name playerName) {
        try {
            return new Money(InputView.inputBettingMoneyBy(playerName.toString()));
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            return askBettingMoney(playerName);
        }
    }

    private void setUpTwoCards(Game game) {
        game.startRound();
        OutputView.printSetup(game.getDealer(), game.getPlayers());
    }

    private void askPlayersDrawCard(Game game) {
        while (game.isNotEnd()) {
            Player player = game.getCurrentPlayer();
            askAndReflectReply(player, game);
            OutputView.printCardInfoWithLineSeparator(player);
        }
    }

    private void askAndReflectReply(Player player, Game game) {
        try {
            game.reflectInput(InputView.inputYesOrNo(player));
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            OutputView.printMessage(e.getMessage());
            askAndReflectReply(player, game);
        }
    }

    private void makeDealerDrawCard(Game game) {
        int dealerDrawCount = game.playDealerTurn();
        OutputView.printDealerDrawMessage(dealerDrawCount);
        OutputView.printMessage(DEALER_NO_MORE_DRAW_MESSAGE);
    }

    private void showFinalResult(Game game) {
        OutputView.printFinalCardInfo(game.getDealer(), game.getPlayers());
    }

    public void printFinalRevenue(Game game) {
        game.calculateGameResult();
        Dealer dealer = game.getDealer();
        List<Player> players = game.getPlayers();
        OutputView.printFinalRevenueHeader();
        OutputView.printFinalRevenue(dealer, game.dealerRevenue());
        for (Player player : players) {
            OutputView.printFinalRevenue(player, player.revenue());
        }
    }
}
