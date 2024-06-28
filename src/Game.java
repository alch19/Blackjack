import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class Game extends JFrame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private JPanel playerHandPanel;
    private JPanel dealerHandPanel;
    private JButton hitButton;
    private JButton standButton;

    public Game() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        
        setTitle("Blackjack");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel handsPanel = new JPanel();
        handsPanel.setLayout(new GridLayout(2, 1));

        playerHandPanel = new JPanel();
        playerHandPanel.setPreferredSize(new Dimension(600, 150));
        playerHandPanel.setLayout(new FlowLayout());
        playerHandPanel.setBorder(BorderFactory.createTitledBorder("Player Hand"));

        dealerHandPanel = new JPanel();
        dealerHandPanel.setPreferredSize(new Dimension(600, 150));
        dealerHandPanel.setLayout(new FlowLayout());
        dealerHandPanel.setBorder(BorderFactory.createTitledBorder("Dealer Hand"));

        handsPanel.add(playerHandPanel);
        handsPanel.add(dealerHandPanel);

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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        add(handsPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);

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
        playerHandPanel.removeAll();
        dealerHandPanel.removeAll();

        for (Card card : playerHand.getCards()) {
            playerHandPanel.add(new CardPanel(card));
        }

        List<Card> dealerCards = dealerHand.getCards();
        for (int i = 0; i < dealerCards.size(); i++) {
            CardPanel cardPanel = new CardPanel(dealerCards.get(i));
            if (i == 1) {
                cardPanel.setHidden(true);
            }
            dealerHandPanel.add(cardPanel);
        }

        playerHandPanel.revalidate();
        playerHandPanel.repaint();
        dealerHandPanel.revalidate();
        dealerHandPanel.repaint();
    }

    private void updateUI(int i) {
        playerHandPanel.removeAll();
        dealerHandPanel.removeAll();

        for (Card card : playerHand.getCards()) {
            playerHandPanel.add(new CardPanel(card));
        }

        for (Card card : dealerHand.getCards()) {
            dealerHandPanel.add(new CardPanel(card));
        }

        playerHandPanel.revalidate();
        playerHandPanel.repaint();
        dealerHandPanel.revalidate();
        dealerHandPanel.repaint();
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
