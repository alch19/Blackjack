import java.util.Scanner;

public class Game {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private Scanner input;
    private double balance;

    public Game() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        input = new Scanner(System.in);
        balance = 100;
    }

    public void startGame() {
        
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
