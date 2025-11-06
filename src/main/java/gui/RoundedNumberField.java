package gui;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class RoundedNumberField extends JFormattedTextField {
    private int radius;

    public RoundedNumberField(NumberFormatter formatter, int radius) {
        super(new DefaultFormatterFactory(formatter));
        this.radius = radius;
        setOpaque(false); // very important
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setHorizontalAlignment(JTextField.LEFT);
        setFont(new Font("Arial", Font.BOLD, 24));
        setBackground(new Color(230, 240, 255));
        setColumns(10);
        setEditable(true);
        // padding inside
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                setBackground(new Color(255, 255, 200)); // light yellow
            }
            public void focusLost(FocusEvent e) {
                setBackground(Color.WHITE);
            }
        });
        setForeground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint rounded background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0x667db6)); // border color
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);

        g2.dispose();
    }

}
