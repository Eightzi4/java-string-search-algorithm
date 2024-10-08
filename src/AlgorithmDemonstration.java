public class AlgorithmDemonstration {
    private String text = "";
    private String sample = "";
    private Step currentStep = Step.INITIALIZED;
    private int stepsMade = 0;
    private int textCharIndex = 0;
    private int savedTextCharIndex = 0;
    private int sampleCharIndex = 0;
    private String[] stepsInfo = {
            "Input hasn't been entered or it is wrong.",
            "Provided input is valid, but the seach hasn't begun yet.", "Comparing characters text[textCharIndex] & sample[sampleCharIndex].",
            "Compared characters match.",
            "Compared characters don't match.",
            "Searching is done."
    };

    enum Step {
        INITIALIZED,
        VALID_INPUT_PROVIDED,
        CHARS_COMPARISON,
        CHARS_DO_MATCH,
        CHARS_DO_NOT_MATCH,
        DONE
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        validateInput();
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
        validateInput();
    }

    public void toBeginning() {
        stepsMade = 0;
        currentStep = Step.INITIALIZED;
        text = "";
        sample = "";
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
            case DONE:
                stepDone();
                break;
        }
        ++stepsMade;
    }

    private void stepInitialized() {
        printStepInfo("Class initialized, but input is not valid!");
    }

    private void stepValidInputProvided() {
        printStepInfo("text: " + text + " sample: " + sample);
        currentStep = Step.CHARS_COMPARISON;
    }

    private void stepCharsComparison() {
        printStepInfo("text[" + textCharIndex + "] = \"" + text.charAt(textCharIndex) + "\" sample[" + sampleCharIndex + "] = \"" + sample.charAt(sampleCharIndex) + "\"");
        if (text.charAt(textCharIndex) == sample.charAt(sampleCharIndex)) {
            currentStep = Step.CHARS_DO_MATCH;
        } else {
            currentStep = Step.CHARS_DO_NOT_MATCH;
        }
    }

    private void stepCharsDoMatch() {
        printStepInfo("\"" + text.charAt(textCharIndex) + "\" == \"" + sample.charAt(sampleCharIndex) + "\"");
        ++textCharIndex;
        ++sampleCharIndex;
        if (savedTextCharIndex == 0) {
            savedTextCharIndex = textCharIndex;
        }
        if (sampleCharIndex == sample.length()) {
            currentStep = Step.DONE;
            return;
        }
        if (textCharIndex == text.length()) {
            currentStep = Step.DONE;
            return;
        }
        currentStep = Step.CHARS_COMPARISON;
    }

    private void stepCharsDoNotMatch() {
        printStepInfo("\"" + text.charAt(textCharIndex) + "\" != \"" + sample.charAt(sampleCharIndex) + "\"");
        if (savedTextCharIndex != 0) {
            textCharIndex = savedTextCharIndex;
            savedTextCharIndex = 0;
        }
        ++textCharIndex;
        sampleCharIndex = 0;
        if (textCharIndex == text.length()) {
            currentStep = Step.DONE;
            return;
        }
        currentStep = Step.CHARS_COMPARISON;
    }

    private void stepDone() {
        printStepInfo("sample found at text[" + (savedTextCharIndex - 1) + "]..text[" + (savedTextCharIndex - 1 + sample.length()) + "]");
    }

    public void validateInput() {
        if (!text.isEmpty() && !sample.isEmpty() && text.length() > sample.length()) {
            currentStep = Step.VALID_INPUT_PROVIDED;
            System.out.println("Input is valid!");
        } else {
            System.out.println("Input is not valid!");
        }
    }

    private void printStepInfo(String message) {
        System.out.println("[" + stepsMade + "] " + message + " info: " + stepsInfo[currentStep.ordinal()]);
    }
}