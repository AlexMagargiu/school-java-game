package ui;

import blackjack.Blackjack;
import chromedinosaur.ChromeDinosaur;
import java.awt.*;
import javax.swing.*;
import spaceinvaders.SpaceInvaders;

public class GameSelectorUI {

    public static void launch() {
        JFrame frame = new JFrame("Select game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10)); 

        JButton blackjackButton = new JButton("Play Blackjack");
        JButton chromeDinosaurGameButton = new JButton("Play Dinosaur Game");
        JButton spaceInvadersButton = new JButton("Play Space Invaders");

        blackjackButton.addActionListener(e -> Blackjack.startGame());
        chromeDinosaurGameButton.addActionListener(e -> ChromeDinosaur.startGame());
        spaceInvadersButton.addActionListener(e -> SpaceInvaders.startGame());

        panel.add(blackjackButton);
        panel.add(chromeDinosaurGameButton);
        panel.add(spaceInvadersButton);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
