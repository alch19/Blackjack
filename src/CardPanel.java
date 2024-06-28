import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {
    private Card card;
    private boolean hidden;

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
            g.drawString(card.getRank() + " of " + card.getSuit(), 10, 20);
        }
    }
}
