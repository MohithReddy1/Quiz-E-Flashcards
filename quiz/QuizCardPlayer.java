package quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class QuizCardPlayer {
    private JTextArea question;
    private JTextArea typeIn;
    private JTextArea answer;
    private HashSet<QuizCard> cardHashSet;
    private QuizCard currentCard;
    private JFrame frame;
    private JButton nextButton;
    private boolean isShowAnswer;

    public void go() {
        frame = new JFrame("Quiz Card Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("Arial", Font.BOLD, 14);
        JLabel questionLabel = new JLabel("Question:");
        question = new JTextArea("Hint: First Load card set from File!!", 8, 40);
        question.setFont(bigFont);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setEditable(false);
        question.setBackground(Color.LIGHT_GRAY);
        JScrollPane qQuestionScroller = new JScrollPane(question);
        qQuestionScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qQuestionScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JLabel typeInLabel = new JLabel("Type in your answer:");
        typeIn = new JTextArea(8, 40);
        typeIn.setFont(bigFont);
        typeIn.setLineWrap(true);
        typeIn.setWrapStyleWord(true);
        typeIn.setEditable(true);
        JScrollPane qTypeInScroller = new JScrollPane(typeIn);
        qTypeInScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qTypeInScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JLabel answerLabel = new JLabel("Correct Answer:");
        answer = new JTextArea(8, 40);
        answer.setFont(bigFont);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setEditable(false);
        answer.setBackground(Color.LIGHT_GRAY);
        JScrollPane qAnswerScroller = new JScrollPane(answer);
        qAnswerScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qAnswerScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        nextButton = new JButton("Show Question");
        mainPanel.add(questionLabel);
        mainPanel.add(qQuestionScroller);
        mainPanel.add(typeInLabel);
        mainPanel.add(qTypeInScroller);
        mainPanel.add(answerLabel);
        mainPanel.add(qAnswerScroller);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load Card Set");
        loadMenuItem.addActionListener(new OpenMenuListener());
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(530, 590);
        frame.setVisible(true);
    }

    public class NextCardListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            if (isShowAnswer) {
                answer.setText(currentCard.getAnswer());
                nextButton.setText("Next Card");
                isShowAnswer = false;
            } else {
                cardHashSet.remove(currentCard);
                checkCards();
            }
        }
    }

    public void checkCards() {
        if (!cardHashSet.isEmpty()) {
            nextButton.setEnabled(true);
            typeIn.setText("");
            answer.setText("");
            showNextCard();
        } else {
            question.setText("That was the last card.");
            typeIn.setText("");
            answer.setText("");
            nextButton.setEnabled(false);
        }
    }

    public class OpenMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }
    }

    private void loadFile(File file) {
        cardHashSet = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        checkCards();
    }

    private void makeCard(String lineToParse) {
        StringTokenizer parser = new StringTokenizer(lineToParse, "/");
        if (parser.hasMoreTokens()) {
            QuizCard card = new QuizCard(parser.nextToken(), parser.nextToken());
            cardHashSet.add(card);
        }
    }

    private void showNextCard() {
        currentCard = (QuizCard) cardHashSet.toArray()[(int) (Math.random() * cardHashSet.size())];
        question.setText(currentCard.getQuestion());
        nextButton.setText("Show Answer");
        isShowAnswer = true;
    }
}