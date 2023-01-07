import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeSzef {
    JPanel panel1;
    private JTextField idKlientaTextField;
    private JButton OKButton;
    private JPanel blad;
    private JPanel przyciski;
    private JLabel label_bledu;
    private JButton sprawdzStanKontaButton;
    private JButton dodajObowiazekZaplatyButton;
    private JButton dodajWplateButton;
    private JButton takButton;
    private JButton nieButton;
    private JPanel TestP;
    private JPanel Ile;
    private JTextField textField1;
    private JButton OKButton1;
    private int idKlient;
    private int kwota;
    String query;

    public WelcomeSzef() {
        Visibility();

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Visibility();
               idKlient = Integer.parseInt(idKlientaTextField.getText());
               System.out.println(idKlient);
                query = "CALL `mydb2`.`przejrzyjDaneKlient`(" + idKlient + ");\n";
                TestP.setVisible(true);
            }
        });
        nieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Visibility();
                blad.setVisible(true);
                label_bledu.setText("Ten uzytkownik nie istnieje");
            }
        });
        takButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Visibility();
                przyciski.setVisible(true);
            }
        });
        sprawdzStanKontaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                query = "CALL `mydb2`.`sprawdzStanKontaKlientSzef`(" + idKlient +");\n";
                Main.utworzPoloczenie(query);
            }
        });
        dodajWplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ile.setVisible(true);
                OKButton1.addActionListener(new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        kwota = Integer.parseInt(textField1.getText());
                        System.out.println(kwota);
                        query = "CALL `mydb2`.`dodajWplateSzef`(" + idKlient + "," + kwota +");\n";
                        Main.utworzPoloczenie(query);
                    }
                });

            }
        });

        dodajObowiazekZaplatyButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                Ile.setVisible(true);
                OKButton1.addActionListener(new ActionListener() {
                    @Override
                    public synchronized void actionPerformed(ActionEvent e) {
                        kwota = Integer.parseInt(textField1.getText());
                        System.out.println(kwota);
                        query = "CALL `mydb2`.`dodajWplateSzef`(" + idKlient + "," + kwota +");\n";
                        Main.utworzPoloczenie(query);
                    }
                });

            }
        });
    }

    void Visibility(){
        przyciski.setVisible(false);
        blad.setVisible(false);
        TestP.setVisible(false);
        Ile.setVisible(false);
    }
}
