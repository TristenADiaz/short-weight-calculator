package filters;

import gui.guiFrame;
import gui.guiTotes;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.util.Objects;

public class MenuBarListener implements MenuListener {
    private JFrame frame;
    public MenuBarListener(JFrame frame){
        this.frame = frame;
    }

    @Override
    public void menuSelected(MenuEvent e) {

        frame.dispose();
        if(Objects.equals(frame.getTitle(), "Manifest Calculator"))
            new guiFrame(new JFrame());
        else
            new guiTotes(new JFrame());
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        System.out.println("TEST");
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        System.out.println("REE");
    }

}
