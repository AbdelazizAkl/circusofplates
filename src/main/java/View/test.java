package View;

import eg.edu.alexu.csd.oop.game.*;
import eg.edu.alexu.csd.oop.game.GameEngine.GameController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class test {

    public static void main(String[] args) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem easyMenuItem = new JMenuItem("Easy");
        JMenuItem mediumMenuItem = new JMenuItem("Medium");
        JMenuItem hardMenuItem = new JMenuItem("Hard");

        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        menu.add(easyMenuItem);
        menu.add(mediumMenuItem);
        menu.add(hardMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menuBar.add(menu);
        final GameController gameController = GameEngine.start("Circus Of Plates", new CircusOfPlates(2, 7, 7, 2, 0), menuBar, Color.BLACK);
        easyMenuItem.addActionListener((ActionEvent e) -> {
            gameController.changeWorld(new CircusOfPlates(1, 4, 4, 1, 0));
        });
        mediumMenuItem.addActionListener((ActionEvent e) -> {
            gameController.changeWorld(new CircusOfPlates(2, 7, 7, 2, 0));
        });
        hardMenuItem.addActionListener((ActionEvent e) -> {
            gameController.changeWorld(new CircusOfPlates(3, 10, 10, 3, 1));
        });
        pauseMenuItem.addActionListener((ActionEvent e) -> {
            gameController.pause();
        });
        resumeMenuItem.addActionListener((ActionEvent e) -> {
            gameController.resume();
        });
    }

}
