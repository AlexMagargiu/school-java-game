package ui;

import blackjack.BlackJack;
import java.awt.*;
import javax.swing.*;
import wordle.Wordle;

public class GameSelectorUI {

    public static void launch() {
        JFrame frame = new JFrame("Select game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);  
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));  

        JButton blackjackButton = new JButton("Play Blackjack");
        JButton wordleButton = new JButton("Play Wordle"); 

        blackjackButton.addActionListener(e -> {
            new BlackJack().startGame();
            frame.dispose();  
        });

        wordleButton.addActionListener(e -> {
            new Wordle().startGame();
            frame.dispose();
        });

        panel.add(blackjackButton);
        panel.add(wordleButton);  
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}