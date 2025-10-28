package filters;

import gui.guiTotes;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import math.WeightMath;

public class KeyInputHandler extends KeyAdapter {
    JLabel totelabel;
    JTextField totefield;
    WeightMath.Product currentProduct;
    boolean cardboard;

    public KeyInputHandler(JLabel totelabel, JTextField totefield, WeightMath.Product current, boolean cardboard) {
        this.totelabel = totelabel;
        this.totefield = totefield;
        this.currentProduct = current;
        this.cardboard = cardboard;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        // Check if the character is a digit
        if (!Character.isDigit(keyChar)) {
            // Consume the event to prevent non-digit input from appearing
            e.consume();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            int value = Integer.parseInt(totefield.getText().replaceAll(",", ""));
            cardboard = guiTotes.getIsCardboard();
            currentProduct = guiTotes.getCurrentProduct();
            totelabel.setText(Integer.toString(WeightMath.findToteWeight(value,cardboard,currentProduct)));
        }
    }
}
