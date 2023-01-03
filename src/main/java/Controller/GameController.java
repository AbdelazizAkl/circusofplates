/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import ObserverPattern.Observer;
import ObserverPattern.Subject;
import eg.edu.alexu.csd.oop.game.GameEngine;
import eg.edu.alexu.csd.oop.game.World;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Supplier;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author michael.said
 */
public final class GameController implements Subject{
    
    private final Supplier<World> gameSupplier;
    private JFrame gameFrame;
    private GameEngine.GameController gameController;
    private boolean visualState;
    private ArrayList<Observer> observers = new ArrayList<>();
    
    public GameController(Supplier<World> gameSupplier) {
        this.gameSupplier = gameSupplier;
        
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menuBar.add(menu);
        
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                gameController.changeWorld(new StarWar(800, 600));
                  gameFrame.dispose();
                  start();
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.pause();
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resume();
            }
        });
        
        return menuBar;
    }
    public void start() {
        JMenuBar menuBar = createMenuBar();
        World game = gameSupplier.get();
        this.gameController = GameEngine.start("Circus of Plates", game, menuBar, Color.BLACK);
        this.gameFrame = (JFrame) menuBar.getParent().getParent().getParent();
        this.gameFrame.setDefaultCloseOperation(JFrame. DO_NOTHING_ON_CLOSE); 
        this.gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(gameFrame, "Do you want to return to the menu?",
                        "Exit?", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    
                    setVisualState(false);
                    notifyAllObservers();
                    gameFrame.dispose();
                    //some code to return to game main window.
                }else{
                    
                }
                
            }
        });
    }

    public JFrame getGameFrame() {
        return gameFrame;
    }

    public GameEngine.GameController getGameController() {
        return gameController;
    }
        @Override
    public void setVisualState(boolean b) {
        this.visualState = b;
        gameFrame.setVisible(b);
        notifyAllObservers();
    }

    @Override
    public boolean getVisualState() {
        return visualState == true;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).updateGameFrame();
        }
    }

}
