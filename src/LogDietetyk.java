import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogDietetyk {
    JPanel panel1;
    private JTextField IDField;
    private JButton OKButton;
    private int ID_Dietetyka;

    public LogDietetyk() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ID_Dietetyka = Integer.parseInt(IDField.getText());
                Main.zmienContent(new HelloDietetyk(ID_Dietetyka).panel1);
            }
        });
    }
}
