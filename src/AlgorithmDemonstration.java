public class AlgorithmDemonstration {
    private String text = "";
    private String sample = "";
    private Step currentStep = Step.INITIALIZED;
    private int stepsMade = 0;
    private int textCharIndex = 0;
    private int savedTextCharIndex = 0;
    private int sampleCharIndex = 0;

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
                System.out.println("Class initialized, but input is not valid!");
                break;
            case VALID_INPUT_PROVIDED:
                System.out.println("Valid input has been provided!");
                currentStep = Step.CHARS_COMPARISON;
                break;
            case CHARS_COMPARISON:
                System.out.println("Comparing chars!");
                if (text.charAt(textCharIndex) == sample.charAt(sampleCharIndex)) {
                    currentStep = Step.CHARS_DO_MATCH;
                } else {
                    currentStep = Step.CHARS_DO_NOT_MATCH;
                }
                break;
            case CHARS_DO_MATCH:
                System.out.println("Chars do match!");
                ++textCharIndex;
                ++sampleCharIndex;
                if (savedTextCharIndex == -1) {
                    savedTextCharIndex = textCharIndex;
                }
                if (sampleCharIndex == sample.length()) {
                    currentStep = Step.DONE;
                    break;
                }
                if (textCharIndex == text.length()) {
                    currentStep = Step.DONE;
                    break;
                }
                currentStep = Step.CHARS_COMPARISON;
                break;
            case CHARS_DO_NOT_MATCH:
                System.out.println("Chars do not match!");
                if (savedTextCharIndex != -1) {
                    textCharIndex = savedTextCharIndex + 1;
                    System.out.println("Going back to " + (savedTextCharIndex + 1));
                    savedTextCharIndex = -1;
                }
                ++textCharIndex;
                sampleCharIndex = 0;
                if (textCharIndex == text.length()) {
                    currentStep = Step.DONE;
                    break;
                }
                currentStep = Step.CHARS_COMPARISON;
                break;
            case DONE:
                System.out.println("Search done!");
                break;
        }
    }

    public void validateInput() {
        if (!text.isEmpty() && !sample.isEmpty() && text.length() > sample.length()) {
            currentStep = Step.VALID_INPUT_PROVIDED;
            System.out.println("Input is valid!");
        } else {
            System.out.println("Input is not valid!");
        }
    }
}