package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameOverUI {
    public static void showGameOverUI(JFrame parentFrame, String message, Runnable playAgainAction, Runnable backToMenuAction) {
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameOverFrame.setSize(400, 300);
        gameOverFrame.setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(messageLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton playAgainButton = new JButton("Play Again");
        JButton backToMenuButton = new JButton("Back to Menu");

        playAgainButton.addActionListener((ActionEvent e) -> {
            playAgainAction.run();
            gameOverFrame.dispose();
        });

        backToMenuButton.addActionListener((ActionEvent e) -> {
            backToMenuAction.run();
            gameOverFrame.dispose();
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(backToMenuButton);

        gameOverFrame.add(panel, BorderLayout.CENTER);
        gameOverFrame.add(buttonPanel, BorderLayout.SOUTH);

        gameOverFrame.setVisible(true);
    }
}
