package wordle;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import ui.GameOverUI;
import ui.GameSelectorUI;

public class Wordle {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final JTextField inputField;
    private final List<JTextField[]> gridRows;
    private final String targetWord;
    private int currentRow;
    private static final int MAX_TRIES = 6;
    private static final int WORD_LENGTH = 5;
    private static final List<String> words = loadWords();

    private static List<String> loadWords() {
        List<String> wordList = new ArrayList<>();
        try {
            String path = "src/wordle/WordList.txt";
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toUpperCase();
                if (line.length() == WORD_LENGTH) {
                    wordList.add(line);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Error loading word list: " + e.getMessage());
            wordList.add("WORLD");
            wordList.add("HELLO");
            wordList.add("SPACE");
        }
        return wordList;
    }

    public Wordle() {
        frame = new JFrame("Wordle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        
        mainPanel = new JPanel(new GridLayout(6, 5, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.BOLD, 20));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        
        JButton submitButton = new JButton("Submit");
        
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        
        gridRows = new ArrayList<>();
        
        if (words.isEmpty()) {
            throw new RuntimeException("No words available for the game");
        }
        Random random = new Random();
        targetWord = words.get(random.nextInt(words.size()));
        
        setupGrid();
        setupInputHandlers(submitButton);
        
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
    }

    private void setupGrid() {
        for (int row = 0; row < MAX_TRIES; row++) {
            JTextField[] textFields = new JTextField[WORD_LENGTH];
            for (int col = 0; col < WORD_LENGTH; col++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(new Font("Arial", Font.BOLD, 20));
                field.setEditable(false);
                field.setBackground(Color.WHITE);
                textFields[col] = field;
                mainPanel.add(field);
            }
            gridRows.add(textFields);
        }
    }

    private void setupInputHandlers(JButton submitButton) {
        ActionListener submitAction = e -> submitGuess();
        submitButton.addActionListener(submitAction);
        inputField.addActionListener(submitAction);
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = inputField.getText();
                if (text.length() >= WORD_LENGTH && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                } else if (Character.isLetter(e.getKeyChar())) {
                    e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                } else if (!Character.isISOControl(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }

    private void submitGuess() {
        String guess = inputField.getText().toUpperCase();
        if (guess.length() != WORD_LENGTH) {
            JOptionPane.showMessageDialog(frame, 
                "Please enter a " + WORD_LENGTH + "-letter word!", 
                "Invalid Input", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (currentRow >= MAX_TRIES) return;
        
        JTextField[] currentRowFields = gridRows.get(currentRow);
        for (int i = 0; i < WORD_LENGTH; i++) {
            currentRowFields[i].setText(String.valueOf(guess.charAt(i)));
        }
        
        boolean won = guess.equals(targetWord);
        
        for (int i = 0; i < WORD_LENGTH; i++) {
            JTextField field = currentRowFields[i];
            char guessChar = guess.charAt(i);
            
            if (guessChar == targetWord.charAt(i)) {
                field.setBackground(Color.GREEN);
            } else if (targetWord.indexOf(guessChar) >= 0) {
                field.setBackground(Color.YELLOW);
            } else {
                field.setBackground(Color.GRAY);
            }
        }
        
        currentRow++;
        inputField.setText("");

        if (won) {
            showGameOver("Congratulations! You won!");
        } else if (currentRow >= MAX_TRIES) {
            showGameOver("Game Over! The word was: " + targetWord);
        }
    }

    private void showGameOver(String message) {
        GameOverUI.showGameOverUI(
            frame,
            message,
            () -> {
                frame.dispose();
                new Wordle().startGame();
            },
            () -> {
                frame.dispose();
                GameSelectorUI.launch();
            }
        );
    }

    public void startGame() {
        frame.setVisible(true);
        inputField.requestFocusInWindow();
    }
}