package blackjack;

public class GameResult {
    public static String getResult(int playerValue, int dealerValue) {
        if (playerValue > 21) {
            return "You Lose!";
        } else if (dealerValue > 21) {
            return "You Win!";
        } else if (playerValue == dealerValue) {
            return "Tie!";
        } else if (playerValue > dealerValue) {
            return "You Win!";
        } else {
            return "You Lose!";
        }
    }
}
