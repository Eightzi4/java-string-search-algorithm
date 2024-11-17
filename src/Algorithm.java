import java.util.ArrayList;

public class Algorithm {
    private String text = "";
    private String sample = "";
    private int stepsMade = 0;
    private int textCharIndex = 0;
    private int savedTextCharIndex = 0;
    private int sampleCharIndex = 0;
    private Step currentStep = Step.INITIALIZED;
    private String stepMessage = "";
    private final ArrayList<StepState> stepHistory = new ArrayList<>();

    public static class StepState {
        Step step;
        String message;
        int textIdx;
        int savedTextIdx;
        int sampleIdx;

        private StepState(Step step, String message, int textIdx, int savedTextIdx, int sampleIdx) {
            this.step = step;
            this.message = message;
            this.textIdx = textIdx;
            this.savedTextIdx = savedTextIdx;
            this.sampleIdx = sampleIdx;
        }
    }

    public enum Step {
        INITIALIZED,
        VALID_INPUT_PROVIDED,
        CHARS_COMPARISON,
        CHARS_DO_MATCH,
        CHARS_DO_NOT_MATCH,
        SAMPLE_FOUND,
        SAMPLE_NOT_FOUND,
        END
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public int getStepsMade() {
        return stepsMade;
    }

    public Step getCurrentStep() {
        return currentStep;
    }

    public ArrayList<StepState> getStepHistory() {
        return stepHistory;
    }

    public StepState getLastStepState() {
        return stepHistory.getLast();
    }

    public String getStepMessage() {
        return stepMessage;
    }

    public void toBeginning() {
        text = "";
        sample = "";
        stepsMade = 0;
        textCharIndex = 0;
        savedTextCharIndex = 0;
        sampleCharIndex = 0;
        currentStep = Step.INITIALIZED;
        stepMessage = "";
        stepHistory.clear();
    }

    private void saveCurrentState() {
        stepHistory.add(new StepState(
                currentStep,
                stepMessage,
                textCharIndex,
                savedTextCharIndex,
                sampleCharIndex
        ));
    }

    private void restorePreviousState() {
        if (!stepHistory.isEmpty()) {
            StepState previousState = stepHistory.removeLast();
            currentStep = previousState.step;
            stepMessage = previousState.message;
            textCharIndex = previousState.textIdx;
            savedTextCharIndex = previousState.savedTextIdx;
            sampleCharIndex = previousState.sampleIdx;
        }
    }

    public void step(int stepDirection) {
        switch (stepDirection) {
            case -1:
                if (stepsMade > 0) {
                    stepsMade += stepDirection;
                    restorePreviousState();
                }
                break;
            case 1:
                saveCurrentState();
                stepsMade += stepDirection;
                performAndSwitchStep();
                break;
            case 2:
                while (currentStep != Step.END) {
                    saveCurrentState();
                    ++stepsMade;
                    performAndSwitchStep();
                }
                break;
        }
    }

    private void performAndSwitchStep() {
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
            case END:
                break;
        }
    }

    private void stepInitialized() {
        stepMessage = "Algoritmus čeká na platný vzorek a text.";
    }

    private void stepValidInputProvided() {
        stepMessage = "Algoritmus připraven, text i vzorek je platný.";
        currentStep = Step.CHARS_COMPARISON;
    }

    private void stepCharsComparison() {
        stepMessage = "Porovnávání " + (textCharIndex + 1) + ". znaku textu s " + (sampleCharIndex + 1) + ". znakem vzorku.";
        if (text.charAt(textCharIndex) == sample.charAt(sampleCharIndex)) {
            currentStep = Step.CHARS_DO_MATCH;
        } else {
            currentStep = Step.CHARS_DO_NOT_MATCH;
        }
    }

    private void stepCharsDoMatch() {
        stepMessage = "Znaky \"" + text.charAt(textCharIndex) + "\" a \"" + sample.charAt(sampleCharIndex) + "\" se shodují. (" + (sampleCharIndex + 1) + "/" + sample.length() + ")";
        ++textCharIndex;
        ++sampleCharIndex;
        if (savedTextCharIndex == 0) {
            savedTextCharIndex = textCharIndex;
        }
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
        stepMessage = "Znaky \"" + text.charAt(textCharIndex) + "\" a \"" + sample.charAt(sampleCharIndex) + "\" se neshodují. (" + sampleCharIndex + "/" + sample.length() + ")";
        if (savedTextCharIndex != 0) {
            textCharIndex = savedTextCharIndex;
            savedTextCharIndex = 0;
        } else {
            ++textCharIndex;
            if (textCharIndex == text.length()) {
                currentStep = Step.SAMPLE_NOT_FOUND;
                return;
            }
        }
        sampleCharIndex = 0;
        currentStep = Step.CHARS_COMPARISON;
    }

    private void sampleFound() {
        stepMessage = "Vzorek nalezen na pozici " + savedTextCharIndex + "-" + (savedTextCharIndex + sample.length() - 1) + ".";
        currentStep = Step.END;
    }

    private void sampleNotFound() {
        stepMessage = "Vzorek v textu nebyl nalezen.";
        currentStep = Step.END;
    }

    public boolean validateInput() {
        if (!text.isEmpty() && !sample.isEmpty() && text.length() > sample.length()) {
            currentStep = Step.VALID_INPUT_PROVIDED;
            return true;
        }
        return false;
    }
}