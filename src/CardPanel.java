import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {
    private Card card;
    private boolean hidden;

    private static final Image HEARTS_ICON = new ImageIcon(("images/hearts.png")).getImage();
    private static final Image DIAMONDS_ICON = new ImageIcon(("images/diamonds.png")).getImage();
    private static final Image CLUBS_ICON = new ImageIcon(("images/clubs.png")).getImage();
    private static final Image SPADES_ICON = new ImageIcon(("images/spades.png")).getImage();

    private static final Font FONT = new Font("Arial", Font.BOLD, 18);

    public CardPanel(Card card) {
        this.card = card;
        this.hidden = false;
        setPreferredSize(new Dimension(100, 150));
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(FONT);
        if (hidden) {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth(), getHeight());
            g.drawString(card.getRank(), 7, 20);
            Image suitImage = getSuitImage(card.getSuit());
            if (suitImage != null) {
                int iconWidth = suitImage.getWidth(this);
                int iconHeight = suitImage.getHeight(this);
                int x = (getWidth() - iconWidth) / 2;
                int y = (getHeight() - iconHeight) / 2;
                g.drawImage(suitImage, x, y, this);
            }

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.translate(getWidth(), getHeight());
            g2d.rotate(Math.toRadians(180));
            g2d.drawString(card.getRank(), 7, 20);
            g2d.dispose();
        }
    }
    private Image getSuitImage(String suit) {
        switch (suit) {
            case "Hearts":
                return HEARTS_ICON;
            case "Diamonds":
                return DIAMONDS_ICON;
            case "Clubs":
                return CLUBS_ICON;
            case "Spades":
                return SPADES_ICON;
            default:
                return null;
        }
    }
}
