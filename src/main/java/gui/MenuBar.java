package gui;

import filters.MenuBarListener;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.util.Objects;

public class MenuBar extends JMenuBar {
    JMenu remainder = new JMenu("Remainder");
    JMenu toteWeight = new JMenu("Manifest Calculator");

    public MenuBar(JFrame frame) {
        if(Objects.equals(frame.getTitle(), "Manifest Calculator")) {
            remainder.add(new JMenuItem("Open"));
            remainder.addMenuListener(new MenuBarListener(frame));
            this.add(remainder);
        } else{
            toteWeight.add(new JMenuItem("Open"));
            toteWeight.addMenuListener(new MenuBarListener(frame));
            this.add(toteWeight);
        }

    }
}