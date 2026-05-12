package ui;

import exception.WordNotFoundException;
import model.UserWord;
import service.DictionaryService;

import javax.swing.*;
import java.awt.*;

public class DictionaryFrame
        extends JFrame {

    private JTextField englishField;

    private JTextField russianField;

    private JButton translateButton;

    private JButton addButton;

    private JButton clearButton;

    private JLabel resultLabel;

    private JLabel countLabel;

    private JTextArea historyArea;

    private DictionaryService service;

    public DictionaryFrame() {

        service = new DictionaryService();

        setTitle("Electronic Dictionary");

        setSize(600, 500);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(12, 1)
        );

        JLabel englishLabel =
                new JLabel("English word:");

        englishField = new JTextField();

        JLabel russianLabel =
                new JLabel("Russian translation:");

        russianField = new JTextField();

        translateButton =
                new JButton("Translate");

        addButton =
                new JButton("Add Word");

        clearButton =
                new JButton("Clear");

        resultLabel =
                new JLabel(
                        "Result will appear here"
                );

        countLabel =
                new JLabel(
                        "Total words: "
                                + service.getDictionarySize()
                );

        historyArea = new JTextArea();

        historyArea.setEditable(false);

        translateButton.addActionListener(
                e -> translateWord()
        );

        addButton.addActionListener(
                e -> addWord()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        panel.add(englishLabel);

        panel.add(englishField);

        panel.add(russianLabel);

        panel.add(russianField);

        panel.add(translateButton);

        panel.add(addButton);

        panel.add(clearButton);

        panel.add(resultLabel);

        panel.add(countLabel);

        panel.add(new JLabel("History:"));

        panel.add(new JScrollPane(historyArea));

        add(panel);
    }

    private void translateWord() {

        try {

            String word =
                    englishField.getText();

            String translation =
                    service.search(word);

            resultLabel.setText(
                    "Translation: "
                            + translation
            );

            updateHistory();

        } catch (
                WordNotFoundException e
        ) {

            resultLabel.setText(
                    e.getMessage()
            );

        } finally {

            englishField.setText("");
        }
    }

    private void addWord() {

        String english =
                englishField.getText()
                        .trim();

        String russian =
                russianField.getText()
                        .trim();

        if (english.isEmpty()
                || russian.isEmpty()) {

            resultLabel.setText(
                    "Fill all fields"
            );

            return;
        }

        UserWord userWord =
                new UserWord(
                        english,
                        russian
                );

        service.addWord(userWord);

        resultLabel.setText(
                "Word added successfully"
        );

        countLabel.setText(
                "Total words: "
                        + service.getDictionarySize()
        );

        englishField.setText("");

        russianField.setText("");
    }

    private void clearFields() {

        englishField.setText("");

        russianField.setText("");

        resultLabel.setText(
                "Fields cleared"
        );
    }

    private void updateHistory() {

        historyArea.setText("");

        for (String word
                : service.getHistory()) {

            historyArea.append(
                    word + "\n"
            );
        }
    }
    protected void dictinary() {
        System.out.println("DICTINARY");
    }
}