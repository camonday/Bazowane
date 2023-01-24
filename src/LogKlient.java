import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogKlient {
    JPanel panel1;
    private JTextField IDField;
    private JButton OKButton;
    private int ID_Klienta;

    public LogKlient() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ID_Klienta = Integer.parseInt(IDField.getText());
                Main.zmienContent(new HelloKlient(ID_Klienta).panel1);
            }
        });
    }
}
