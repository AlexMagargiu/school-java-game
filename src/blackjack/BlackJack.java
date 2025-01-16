package blackjack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import ui.GameOverUI;

public class BlackJack {
    private JFrame frame = new JFrame("Black Jack");
    private JPanel gamePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGame(g);
        }
    };
    private JPanel buttonPanel = new JPanel();
    private JButton hitButton = new JButton("Hit");
    private JButton stayButton = new JButton("Stay");

    private List<Card> deck = new ArrayList<>();
    private Hand dealerHand = new Hand();
    private Hand playerHand = new Hand();
    private Card hiddenCard;

    public void startGame() {
        setupDeck();
        resetGame();
        setupUI();
    }

    private void setupDeck() {
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"C", "D", "H", "S"};
        for (String suit : suits) {
            for (String value : values) {
                deck.add(new Card(value, suit));
            }
        }
        Collections.shuffle(deck);
    }

    private void resetGame() {
        dealerHand.reset();
        playerHand.reset();
        
        hiddenCard = deck.remove(deck.size() - 1);
        dealerHand.addCard(hiddenCard);
        
        Card visibleCard = deck.remove(deck.size() - 1);
        dealerHand.addCard(visibleCard);
        
        playerHand.addCard(deck.remove(deck.size() - 1));
        playerHand.addCard(deck.remove(deck.size() - 1));
        
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        
        gamePanel.repaint();
    }

    private void setupUI() {
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel, BorderLayout.CENTER);

        hitButton.setFocusable(false);
        stayButton.setFocusable(false);
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener((ActionEvent e) -> {
            playerHand.addCard(deck.remove(deck.size() - 1));
            if (playerHand.getHandValue() > 21) {
                hitButton.setEnabled(false);
            }
            gamePanel.repaint();
        });

        stayButton.addActionListener((ActionEvent e) -> {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
            while (dealerHand.getHandValue() < 17) {
                dealerHand.addCard(deck.remove(deck.size() - 1));
            }
            gamePanel.repaint();
            
            String result = GameResult.getResult(playerHand.getHandValue(), dealerHand.getHandValue());
            GameOverUI.showGameOverUI(frame, result, () -> resetGame(), () -> goBackToMenu());
        });

        frame.setVisible(true);
    }

    private void drawGame(Graphics g) {
        try {
            Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
            if (!stayButton.isEnabled()) {
                hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
            }
            g.drawImage(hiddenCardImg, 20, 20, 110, 154, null);
    
            for (int i = 1; i < dealerHand.getCards().size(); i++) {
                Card card = dealerHand.getCards().get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImg, 20 + i * 115, 20, 110, 154, null);
            }
    
            for (int i = 0; i < playerHand.getCards().size(); i++) {
                Card card = playerHand.getCards().get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImg, 20 + i * 115, 300, 110, 154, null);
            }
    
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            int displayedDealerValue;
            if (stayButton.isEnabled()) {
                displayedDealerValue = dealerHand.getHandValue() - hiddenCard.getValue();
            } else {
                displayedDealerValue = dealerHand.getHandValue();
            }
            g.drawString("Dealer: " + displayedDealerValue, 150, 230);
            g.drawString("Player: " + playerHand.getHandValue(), 150, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void goBackToMenu() {
        frame.dispose();
        ui.GameSelectorUI.launch();
    }
}