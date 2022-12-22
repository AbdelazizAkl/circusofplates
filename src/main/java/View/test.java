/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import eg.edu.alexu.csd.oop.game.*;
import eg.edu.alexu.csd.oop.game.GameEngine.GameController;
/**
 *
 * @author PC
 */
public class test {
    public static void main(String[] args) {
            final GameController gameController = GameEngine.start("zizo", new CircusOfPlates(800, 600));

    }
}
