public class AlgorithmDemonstration {
    private String text;
    private String sample;
    private Step currentStep;
    private int stepsMade;
    private int textIndex;
    private int sampleIndex;

    enum Step {
        INITIALIZED,
        SAMPLE_SEARCH,
        CHARS_DO_MATCH,
        CHARS_DO_NOT_MATCH,
        DONE
    }

    public AlgorithmDemonstration(String text, String sample) {
        this.text = text;
        this.sample = sample;
        this.currentStep = Step.INITIALIZED;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public void toBeggining() {

    }

    public void step() {
        switch (currentStep) {
            case INITIALIZED -> {
                break;
            }
            case SAMPLE_SEARCH -> {
                break;
            }
            case CHARS_DO_MATCH -> {
                break;
            }
            case CHARS_DO_NOT_MATCH -> {
                break;
            }
            case DONE -> {
                break;
            }
        }
    }
}