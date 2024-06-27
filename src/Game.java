import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private JTextArea playerHandText;
    private JTextArea dealerHandText;
    private JButton hitButton;
    private JButton standButton;

    public Game() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        
        setTitle("Blackjack");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        playerHandText = new JTextArea();
        playerHandText.setEditable(false);
        playerHandText.setFocusable(false); 
        dealerHandText = new JTextArea();
        dealerHandText.setEditable(false);
        dealerHandText.setFocusable(false); 

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerHit();
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerStand();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(hitButton);
        panel.add(standButton);

        add(new JScrollPane(playerHandText), BorderLayout.NORTH);
        add(new JScrollPane(dealerHandText), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        startGame();
    }

    private void startGame() {
        deck.shuffle();

        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        updateUI();
    }

    private void updateUI() {
        playerHandText.setText("Player Hand:\n" + playerHand + " (" +playerHand.getValue() + ")");
        dealerHandText.setText("Dealer Hand:\n" + dealerHand.getCards().get(0) + " and [hidden]");
    }

    private void updateUI(int i) {
        playerHandText.setText("Player Hand:\n" + playerHand + " (" +playerHand.getValue() + ")");
        dealerHandText.setText("Dealer Hand:\n" + dealerHand + " (" +dealerHand.getValue() + ")");
    }

    private void playerHit() {
        playerHand.addCard(deck.dealCard());
        updateUI();

        if (playerHand.isBust()) {
            JOptionPane.showMessageDialog(this, "Player busts! Dealer wins.");
            resetGame();
        }
    }

    private void playerStand() {
        dealerTurn();
        determineOutcome();
    }

    private void dealerTurn() {
        while (dealerHand.getValue() < 17) {
            dealerHand.addCard(deck.dealCard());
            updateUI(1);
        }
    }

    private void determineOutcome() {
        int playerValue = playerHand.getValue();
        int dealerValue = dealerHand.getValue();

        String message;
        if (dealerHand.isBust() || (playerValue > dealerValue)) {
            message = "Player wins!";
        } else if (playerValue < dealerValue) {
            message = "Dealer wins!";
        } else {
            message = "It's a tie!";
        }

        JOptionPane.showMessageDialog(this, message);
        resetGame();
    }

    private void resetGame() {
        playerHand = new Hand();
        dealerHand = new Hand();
        startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game().setVisible(true);
            }
        });
    }
}
