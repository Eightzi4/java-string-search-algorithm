import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;

public class AlgorithmDemonstration extends JFrame {
    private String text = "";
    private String sample = "";
    private int stepsMade = 0;
    private int textCharIndex = 0;
    private int savedTextCharIndex = 0;
    private int sampleCharIndex = 0;
    private Step currentStep = Step.INITIALIZED;
    private String currentStepInfo = "";
    private JPanel panelMain;
    private JTextField txtResult;
    private JButton btnNext;
    private JButton btnReset;
    private JButton btnPrev;
    private JTextField txtText;
    private JTextField txtSample;
    private JButton btnSearch;

    private String[] stepDescriptions = {
            "Input hasn't been entered or it is wrong.",
            "Provided input is valid, but the seach hasn't began yet.", "Comparing characters text[textCharIndex] & sample[sampleCharIndex].",
            "Compared characters match.",
            "Compared characters don't match.",
            "Searching sample in text is done.",
            "Searching sample in text is done."
    };

    private Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    private Highlighter.HighlightPainter painterGreen = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
    private Highlighter.HighlightPainter painterRed = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);

    public AlgorithmDemonstration() {
        setTitle("Algorithm Demonstration");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelMain);

        txtResult.setEditable(false);
        btnReset.addActionListener(e -> toBeginning());
        btnPrev.addActionListener(e -> back());
        btnNext.addActionListener(e -> next());
        btnSearch.addActionListener(e -> beginSearch());

    }

    private void next() {
        step();
        stepsMade++;
        display();
    }

    private void back() {
        if (stepsMade > 0) {
            reverseStep();
            stepsMade--;
            display();
        } else {
            System.out.println("Cannot go back further!");
        }
    }

    //eugh its messed up imma try to do something with it later *skull_emoji* *crying_emoji*
    private void reverseStep() {
        System.out.println("Reverse step: Before -> stepsMade = " + stepsMade + ", textCharIndex = " + textCharIndex + ", sampleCharIndex = " + sampleCharIndex);

        if (stepsMade > 0) {
            switch (currentStep) {
                case CHARS_DO_MATCH:
                    textCharIndex--;
                    sampleCharIndex--;
                    if (sampleCharIndex < 0) {
                        sampleCharIndex = sample.length() - 1;
                        textCharIndex--;
                    }
                    currentStep = Step.CHARS_COMPARISON;
                    break;
                case CHARS_DO_NOT_MATCH:
                    if (savedTextCharIndex != 0) {
                        textCharIndex = savedTextCharIndex - 1;
                        savedTextCharIndex = 0;
                    } else {
                        textCharIndex--;
                    }
                    currentStep = Step.CHARS_COMPARISON;
                    break;
                case CHARS_COMPARISON:
                    if (sampleCharIndex > 0) {
                        sampleCharIndex--;
                    } else if (textCharIndex > 0) {
                        textCharIndex--;
                    }
                    currentStep = Step.VALID_INPUT_PROVIDED;
                    break;
                case SAMPLE_FOUND:
                    textCharIndex = savedTextCharIndex - 1;
                    sampleCharIndex = sample.length() - 1;
                    currentStep = Step.CHARS_COMPARISON;
                    break;
                case SAMPLE_NOT_FOUND:
                    textCharIndex--;
                    currentStep = Step.CHARS_COMPARISON;
                    break;
                default:
                    break;
            }
            stepsMade--;
        }

        System.out.println("Reverse step: After -> stepsMade = " + stepsMade + ", textCharIndex = " + textCharIndex + ", sampleCharIndex = " + sampleCharIndex);
    }



    private enum Step {
        INITIALIZED,
        VALID_INPUT_PROVIDED,
        CHARS_COMPARISON,
        CHARS_DO_MATCH,
        CHARS_DO_NOT_MATCH,
        SAMPLE_FOUND,
        SAMPLE_NOT_FOUND
    }

    public String getText() {
        text = txtText.getText();
        return text;
    }

    /*public void setText(String text) {
        //this.text = text;
        text = txtText.getText();
        validateInput();
    }*/

    public String getSample() {
        sample = txtSample.getText();
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
        validateInput();
    }

    private void display(){
        txtResult.setText(getCurrentStepInfo());
        txtText.setText(getText());
        txtSample.setText(getSample());
        clearHighlights();
        switch (currentStep) {
            case CHARS_COMPARISON:
            case CHARS_DO_MATCH:
            case CHARS_DO_NOT_MATCH:
                highlightCurrentChars();
                break;
            case SAMPLE_FOUND:
                highlightSampleFound();
                break;
            case SAMPLE_NOT_FOUND:
                highlightSampleNotFound();
                break;
            default:
                break;
        }
    }

    public String getCurrentStepInfo() {
        return currentStepInfo;
    }

    public void toBeginning() {
        text = "";
        sample = "";
        stepsMade = 0;
        textCharIndex = 0;
        savedTextCharIndex = 0;
        sampleCharIndex = 0;
        currentStep = Step.INITIALIZED;
        currentStepInfo = "";
        txtResult.setText("");
        txtText.setText("");
        txtSample.setText("");
        clearHighlights();
    }

    public void step() {
        switch (currentStep) {
            case INITIALIZED:
                stepInitialized();
                break;
            case VALID_INPUT_PROVIDED:
                stepValidInputProvided();
                break;
            case CHARS_COMPARISON:
                stepCharsComparison();
                break;
            case CHARS_DO_MATCH:
                stepCharsDoMatch();
                break;
            case CHARS_DO_NOT_MATCH:
                stepCharsDoNotMatch();
                break;
            case SAMPLE_FOUND:
                sampleFound();
                break;
            case SAMPLE_NOT_FOUND:
                sampleNotFound();
                break;
        }
    }

    private void beginSearch(){
        display();
        validateInput();
    }

    private void stepInitialized() {
        formatStepDescription("Class initialized, but input is not valid!");
    }

    private void stepValidInputProvided() {
        formatStepDescription("text: " + text + " sample: " + sample);
        currentStep = Step.CHARS_COMPARISON;
    }

    private void stepCharsComparison() {
        if (textCharIndex >= text.length() || sampleCharIndex >= sample.length()) {
            currentStep = Step.SAMPLE_NOT_FOUND;
            return;
        }

        formatStepDescription("text[" + textCharIndex + "] = \"" + text.charAt(textCharIndex) + "\" sample[" + sampleCharIndex + "] = \"" + sample.charAt(sampleCharIndex) + "\"");

        if (text.charAt(textCharIndex) == sample.charAt(sampleCharIndex)) {
            currentStep = Step.CHARS_DO_MATCH;
        } else {
            currentStep = Step.CHARS_DO_NOT_MATCH;
        }
    }

    private void stepCharsDoMatch() {
        formatStepDescription("\"" + text.charAt(textCharIndex) + "\" == \"" + sample.charAt(sampleCharIndex) + "\"");
        if (sampleCharIndex == 0) {
            savedTextCharIndex = textCharIndex;
        }
        ++textCharIndex;
        ++sampleCharIndex;
        if (sampleCharIndex == sample.length()) {
            currentStep = Step.SAMPLE_FOUND;
            return;
        }
        if (textCharIndex == text.length()) {
            currentStep = Step.SAMPLE_NOT_FOUND;
            return;
        }
        currentStep = Step.CHARS_COMPARISON;
    }

    private void stepCharsDoNotMatch() {
        formatStepDescription("\"" + text.charAt(textCharIndex) + "\" != \"" + sample.charAt(sampleCharIndex) + "\"");
        if (savedTextCharIndex != 0) {
            textCharIndex = savedTextCharIndex;
            savedTextCharIndex = 0;
        }
        ++textCharIndex;
        sampleCharIndex = 0;
        if (textCharIndex == text.length()) {
            currentStep = Step.SAMPLE_NOT_FOUND;
            return;
        }
        currentStep = Step.CHARS_COMPARISON;
    }

    private void sampleFound() {
        formatStepDescription("sample found at text[" + (savedTextCharIndex - 1) + "]..text[" + (savedTextCharIndex - 1 + sample.length()) + "]");
        txtResult.setText("Sample found at index " + (savedTextCharIndex - 1) + "!");
    }

    private void sampleNotFound() {
        formatStepDescription("text does not contain searched sample");
        txtResult.setText("Sample not found!");

    }

    public void validateInput() {
        display();
        if (!text.isEmpty() && !sample.isEmpty() && text.length() > sample.length()) {
            currentStep = Step.VALID_INPUT_PROVIDED;
            System.out.println("Input is valid!");
        } else {
            System.out.println("Input is not valid!");
            JOptionPane.showMessageDialog(this, "Input is not valid!");
        }
    }

    private void formatStepDescription(String message) {
        currentStepInfo = "[" + stepsMade + "] " + message + ", description: " + stepDescriptions[currentStep.ordinal()];
    }

    private void highlightCurrentChars() {
        clearHighlights();
        try {
            if (textCharIndex >= 0 && textCharIndex < text.length()) {
                txtText.getHighlighter().addHighlight(textCharIndex, textCharIndex + 1, painter);
            }
            if (sampleCharIndex >= 0 && sampleCharIndex < sample.length()) {
                txtSample.getHighlighter().addHighlight(sampleCharIndex, sampleCharIndex + 1, painter);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void highlightSampleFound() {
        clearHighlights();
        try {
            int start = savedTextCharIndex;
            int end = start + sample.length();
            txtText.getHighlighter().addHighlight(start, end, painterGreen);
            txtSample.getHighlighter().addHighlight(0, sample.length(), painterGreen);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    private void highlightSampleNotFound() {
        clearHighlights();
        try {
            if (!text.isEmpty()) {
                txtText.getHighlighter().addHighlight(0, text.length(), painterRed);
            }
            if (!sample.isEmpty()) {
                txtSample.getHighlighter().addHighlight(0, sample.length(), painterRed);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void clearHighlights() {
        txtText.getHighlighter().removeAllHighlights();
        txtSample.getHighlighter().removeAllHighlights();
    }
}