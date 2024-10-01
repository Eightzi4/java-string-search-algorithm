public class BruteForceSearch {
    private String text;
    private String sample;

    public BruteForceSearch(String text, String sample) {
        this.text = text;
        this.sample = sample;
    }

    public boolean find() {
        int textCharIndex = 0;
        int finished = 0;
        while (finished == 0) {
            int sampleCharIndex = 0;
            while (true) {
                if (text.charAt(textCharIndex) == sample.charAt(sampleCharIndex)) {
                    ++textCharIndex;
                    ++sampleCharIndex;
                    if (sampleCharIndex == sample.length()) {
                        finished = 1;
                        break;
                    }
                    if (textCharIndex == text.length()) {
                        finished = -1;
                        break;
                    }
                } else {
                    ++textCharIndex;
                    if (textCharIndex == text.length()) {
                        finished = -1;
                    }
                    break;
                }
            }
        }
        if (finished == 1) {
            return true;
        }
        return false;
    }

    public boolean find2() {
        int textCharIndex = 0;
        int finished = 0;
        while (finished == 0) {
            int rememberedIndex = textCharIndex;
            int sampleCharIndex = 0;
            while (text.charAt(textCharIndex) == sample.charAt(sampleCharIndex)) {
                ++textCharIndex;
                ++sampleCharIndex;
                if (sampleCharIndex == sample.length()) {
                    finished = 1;
                    break;
                }
                if (textCharIndex == text.length()) {
                    finished = -1;
                    break;
                }
            }
            if (finished == 0) {
                textCharIndex = rememberedIndex + 1;
                if (textCharIndex == text.length()) {
                    finished = -1;
                }
            }
        }
        if (finished == 1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        BruteForceSearch bruteForceSearch = new BruteForceSearch("Insert the missing parts to complete the following \"short hand if...else\" statement:", "f...");
        System.out.println(bruteForceSearch.find());
        System.out.println(bruteForceSearch.find2());
    }
}
