import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;

public class StringSearchAlgorithmForm extends JFrame {

    private JTextArea textInput;
    private JButton forwardsButton;
    private JTextField sampleInput;
    private JButton backwardsButton;
    private JTextField message;
    private JPanel mainPanel;
    private JLabel stepCounter;
    private JButton startEndButton;
    private JButton toTheEndButton;
    private final Algorithm algorithm;
    private boolean running = false;
    private final DefaultHighlighter.DefaultHighlightPainter redPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
    private final DefaultHighlighter.DefaultHighlightPainter greenPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);

    public StringSearchAlgorithmForm() {
        setTitle("Hledání vzorku v textu");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(this.mainPanel);
        setVisible(true);

        algorithm = new Algorithm();

        startEndButton.addActionListener(_ -> {
            running = !running;
            if (running) {
                algorithm.setSample(sampleInput.getText());
                algorithm.setText(textInput.getText());
                if (!algorithm.validateInput()) {
                    JOptionPane.showMessageDialog(this, "Neplatný vstup!", "Varování", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                startEndButton.setText("Ukončit");
                algorithmStep(1);
            } else {
                algorithm.toBeginning();
                updateStepCounter(0);
                message.setText("");
                textInput.getHighlighter().removeAllHighlights();
                startEndButton.setText("Začít");
            }

            textInput.setEditable(!running);
            sampleInput.setEditable(!running);
            forwardsButton.setEnabled(running);
            backwardsButton.setEnabled(false);
        });

        backwardsButton.addActionListener(_ -> algorithmStep(-1));

        forwardsButton.addActionListener(_ -> algorithmStep(1));

        toTheEndButton.addActionListener(_ -> algorithmStep(2));
    }

    private void updateStepCounter(int stepsMade) {
        stepCounter.setText("Krok č. " + stepsMade);
    }

    private void algorithmStep(int stepDirection) {
        algorithm.step(stepDirection);
        updateStepCounter(algorithm.getStepsMade());
        message.setText(algorithm.getStepMessage());
        if (algorithm.getCurrentStep() == Algorithm.Step.END) {
            forwardsButton.setEnabled(false);
            toTheEndButton.setEnabled(false);
        } else {
            forwardsButton.setEnabled(true);
            toTheEndButton.setEnabled(true);
        }
        backwardsButton.setEnabled(algorithm.getStepsMade() != 1);
        updateTextHighlights(stepDirection);
    }

    private void updateTextHighlights(int stepDirection) {
        DefaultHighlighter highlighter = (DefaultHighlighter) textInput.getHighlighter();
        int charIndex = algorithm.getLastStepState().textIdx;
        Algorithm.Step previousStep = algorithm.getLastStepState().step;
        try {
            switch (stepDirection) {
                case -1:
                    if (previousStep == Algorithm.Step.CHARS_DO_MATCH || previousStep == Algorithm.Step.CHARS_DO_NOT_MATCH || previousStep == Algorithm.Step.VALID_INPUT_PROVIDED && highlighter.getHighlights().length > 0) {
                        Object[] highlights = highlighter.getHighlights();
                        highlighter.removeHighlight(highlights[highlights.length - 1]);
                    }
                    break;

                case 1:
                    switch (previousStep) {
                        case CHARS_DO_MATCH: {
                            highlighter.addHighlight(charIndex, charIndex + 1, greenPainter);
                            break;
                        }
                        case CHARS_DO_NOT_MATCH: {
                            highlighter.addHighlight(charIndex, charIndex + 1, redPainter);
                            break;
                        }
                    }
                    break;

                case 2:
                    highlighter.removeAllHighlights();
                    for (Algorithm.StepState stepState : algorithm.getStepHistory()) {
                        switch (stepState.step) {
                            case CHARS_DO_MATCH:
                                highlighter.addHighlight(stepState.textIdx, stepState.textIdx + 1, greenPainter);
                                break;
                            case CHARS_DO_NOT_MATCH:
                                highlighter.addHighlight(stepState.textIdx, stepState.textIdx + 1, redPainter);
                                break;
                        }
                    }
                    break;
            }
        } catch (BadLocationException e) {
            System.err.println(e.getMessage());
        }
    }
}
