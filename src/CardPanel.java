import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {
    private Card card;
    private boolean hidden;

    private static final ImageIcon HEARTS_ICON = new ImageIcon("../images/hearts.png");
    private static final ImageIcon DIAMONDS_ICON = new ImageIcon("../images/diamonds.png");
    private static final ImageIcon CLUBS_ICON = new ImageIcon("../images/clubs.png");
    private static final ImageIcon SPADES_ICON = new ImageIcon("../images/spades.png");

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
        if (hidden) {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth(), getHeight());
            g.drawString(card.getRank(), 10, 20);
            ImageIcon suitIcon = getSuitIcon(card.getSuit());
            if (suitIcon != null) {
                int iconWidth = suitIcon.getIconWidth();
                int iconHeight = suitIcon.getIconHeight();
                int x = (getWidth() - iconWidth) / 2;
                int y = (getHeight() - iconHeight) / 2;
                suitIcon.paintIcon(this, g, x, y);
            }
        }
    }
    private ImageIcon getSuitIcon(String suit) {
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
