import javax.swing.*;

public class ChooseFighter {
    private JButton szefButton;
    private JButton dietetykButton;
    private JButton dostawcaButton;
    private JButton kucharzButton;
    private JButton klientButton;
    JPanel panel1;

    public ChooseFighter() {
        szefButton.addActionListener(e -> Main.zmienContent(new WelcomeSzef().panel1));
        dietetykButton.addActionListener(e -> Main.zmienContent(new LogDietetyk().panel1));
        kucharzButton.addActionListener(e -> Main.zmienContent(new helloKucharz().panel1));
        dostawcaButton.addActionListener(e -> Main.zmienContent(new helloDostawca().panel1));
    }
}
