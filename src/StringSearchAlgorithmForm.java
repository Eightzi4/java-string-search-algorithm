import javax.swing.*;

public class StringSearchAlgorithmForm extends JFrame {

    private JTextArea textInput;
    private JButton forwardsButton;
    private JTextField sampleInput;
    private JButton backwardsButton;
    private JTextField message;
    private JPanel mainPanel;
    private JLabel stepCounter;

    public StringSearchAlgorithmForm() {
        this.setTitle("Hledání vzorku v textu");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.mainPanel);
        this.setVisible(true);
    }
}
