import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseFighter {
    private JButton szefButton;
    private JButton dietetykButton;
    private JButton dostawcaButton;
    private JButton kucharzButton;
    private JButton klientButton;
    JPanel panel1;

    public ChooseFighter() {
        szefButton.addActionListener(e -> Main.zmienContent(new WelcomeSzef().panel1));

    }
}
