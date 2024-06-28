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
    private JLabel playerTotalValueLabel;
    private JLabel dealerTotalValueLabel;
    private int balance = 1000;
    private JLabel balanceLabel;
    private int bet;

    private boolean dealerCardRevealed = false;

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

        playerTotalValueLabel = new JLabel("Player Total: 0");
        dealerTotalValueLabel = new JLabel("Dealer Total: 0");

        handsPanel.add(playerHandPanel);
        handsPanel.add(dealerHandPanel);

        balanceLabel = new JLabel("Balance: " + balance);
        JPanel balancePanel = new JPanel();
        balancePanel.add(balanceLabel);

        playerHandPanel.add(playerTotalValueLabel, BorderLayout.SOUTH);
        dealerHandPanel.add(dealerTotalValueLabel, BorderLayout.SOUTH);

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

        playerTotalValueLabel = new JLabel("Player Total: 0");
        dealerTotalValueLabel = new JLabel("Dealer Total: 0");
        
        playerHandPanel.add(playerTotalValueLabel, BorderLayout.SOUTH);
        dealerHandPanel.add(dealerTotalValueLabel, BorderLayout.SOUTH);

        add(balancePanel, BorderLayout.NORTH);
        add(handsPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);

        startGame();
    }

    private void startGame() {
        String betInput = JOptionPane.showInputDialog(this, "How much would you like to bet?", "Place your bet (Balance: " + balance + ")", JOptionPane.PLAIN_MESSAGE);
        try {
            bet = Integer.parseInt(betInput);
            if (bet <= 0 || bet > balance) {
                JOptionPane.showMessageDialog(this, "Invalid bet amount. Please enter a value between 1 and your current balance.", "Invalid Bet", JOptionPane.ERROR_MESSAGE);
                startGame();
                return;
            }
        } catch (NumberFormatException e) {
            System.exit(0);
            startGame();
            return;
        }

        deck.shuffle();

        playerHand.clear();
        dealerHand.clear();

        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        Card secondDealerCard = deck.dealCard();
        secondDealerCard.setHidden(true);
        dealerHand.addCard(secondDealerCard);

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
            if (i == 1 && dealerCards.get(i).isHidden()) {
                cardPanel.setHidden(true);
            }
            dealerHandPanel.add(cardPanel);
        }

        playerTotalValueLabel.setText("Player Total: " + playerHand.getValue());
        dealerTotalValueLabel.setText("Dealer Total: " + (dealerCards.get(1).isHidden() ? dealerHand.getCards().get(0).getValue() : dealerHand.getValue()));

        playerHandPanel.add(playerTotalValueLabel);
        dealerHandPanel.add(dealerTotalValueLabel);

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
            CardPanel cardPanel = new CardPanel(card);
            if (card.isHidden()) {
                cardPanel.setHidden(false);
            }
            dealerHandPanel.add(cardPanel);
        }

        playerTotalValueLabel.setText("Player Total: " + playerHand.getValue());
        dealerTotalValueLabel.setText("Dealer Total: " + dealerHand.getValue());

        playerHandPanel.add(playerTotalValueLabel);
        dealerHandPanel.add(dealerTotalValueLabel);

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
            balance-=bet;
            balanceLabel.setText("Balance: " + balance);
            if(outOfMoney()) {
                int response = JOptionPane.showConfirmDialog(this, "Would you like to play again?", "Play Again", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    balance = 1000;
                    balanceLabel.setText("Balance: " + balance);
                    startGame();
                } else {
                    System.exit(0);
                }
            } else {
                resetGame();
            }
        }
    }

    private void playerStand() {
        dealerTurn();
    }

    private void dealerTurn() {
        dealerCardRevealed = false;

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dealerCardRevealed) {
                    revealDealerSecondCard();
                    dealerCardRevealed = true;
                } else if (dealerHand.getValue() < 17) {
                    dealerHand.addCard(deck.dealCard());
                    updateUI(1);
                } else {
                    ((Timer) e.getSource()).stop();
                    determineOutcome();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private void revealDealerSecondCard() {
        dealerHand.getCards().get(1).setHidden(false);
        updateUI();
    }

    private void determineOutcome() {
        int playerValue = playerHand.getValue();
        int dealerValue = dealerHand.getValue();

        String message;
        if (dealerHand.isBust() || (playerValue > dealerValue)) {
            message = "Player wins!";
            balance+=bet;
        } else if (playerValue < dealerValue) {
            message = "Dealer wins!";
            balance-=bet;
        } else {
            message = "It's a tie!";
        }
        balanceLabel.setText("Balance: " + balance);
        JOptionPane.showMessageDialog(this, message);
        if(outOfMoney()) {
            int response = JOptionPane.showConfirmDialog(this, "Would you like to play again?", "Play Again", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                balance = 1000;
                balanceLabel.setText("Balance: " + balance);
                startGame();
            } else {
                System.exit(0);
            }
        } else {
            resetGame();
        }
    }

    private void resetGame() {
        playerHand = new Hand();
        dealerHand = new Hand();
        startGame();
    }

    private boolean outOfMoney() {
        return balance<=0;
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
