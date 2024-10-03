// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        AlgorithmDemonstration algorithmDemonstration = new AlgorithmDemonstration();
        algorithmDemonstration.setSample("xyz");
        algorithmDemonstration.step();
        algorithmDemonstration.setText("cyx xy xyz");
        for (int i = 0; i < 24; i++) {
            algorithmDemonstration.step();
        }
    }
}