public class AlgorithmDemonstration {
    private String text = "";
    private String sample = "";
    private int stepsMade = 0;
    private int textCharIndex = 0;
    private int savedTextCharIndex = 0;
    private int sampleCharIndex = 0;
    private Step currentStep = Step.INITIALIZED;
    private String currentStepInfo = "";

    private String[] stepDescriptions = {
            "Input hasn't been entered or it is wrong.",
            "Provided input is valid, but the seach hasn't began yet.", "Comparing characters text[textCharIndex] & sample[sampleCharIndex].",
            "Compared characters match.",
            "Compared characters don't match.",
            "Searching sample in text is done.",
            "Searching sample in text is done."
    };
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
        ++stepsMade;
    }

    private void stepInitialized() {
        formatStepDescription("Class initialized, but input is not valid!");
    }

    private void stepValidInputProvided() {
        formatStepDescription("text: " + text + " sample: " + sample);
        currentStep = Step.CHARS_COMPARISON;
    }

    private void stepCharsComparison() {
        formatStepDescription("text[" + textCharIndex + "] = \"" + text.charAt(textCharIndex) + "\" sample[" + sampleCharIndex + "] = \"" + sample.charAt(sampleCharIndex) + "\"");
        if (text.charAt(textCharIndex) == sample.charAt(sampleCharIndex)) {
            currentStep = Step.CHARS_DO_MATCH;
        } else {
            currentStep = Step.CHARS_DO_NOT_MATCH;
        }
    }

    private void stepCharsDoMatch() {
        formatStepDescription("\"" + text.charAt(textCharIndex) + "\" == \"" + sample.charAt(sampleCharIndex) + "\"");
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
    }

    private void sampleNotFound() {
        formatStepDescription("text does not contain searched sample");
    }

    public void validateInput() {
        if (!text.isEmpty() && !sample.isEmpty() && text.length() > sample.length()) {
            currentStep = Step.VALID_INPUT_PROVIDED;
            System.out.println("Input is valid!");
        } else {
            System.out.println("Input is not valid!");
        }
    }

    private void formatStepDescription(String message) {
        currentStepInfo = "[" + stepsMade + "] " + message + ", description: " + stepDescriptions[currentStep.ordinal()];
    }
}