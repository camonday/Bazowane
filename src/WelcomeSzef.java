import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WelcomeSzef {
    JPanel panel1;
    private JTextField idKlientaTextField;
    private JButton OKButton;
    private JPanel komunikat;
    private JPanel przyciski;
    private JLabel tresc_komunikatu;
    private JButton sprawdzStanKontaButton;
    private JButton dodajObowiazekZaplatyButton;
    private JButton dodajWplateButton;
    private JButton takButton;
    private JButton nieButton;
    private JPanel TestP;
    private JPanel Ile;
    private JTextField textField1;
    private JButton OKButton1;
    private JLabel naglowek_komunikatu;
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
                komunikat.setVisible(true);
                tresc_komunikatu.setText("Ten uzytkownik nie istnieje");
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
                        System.out.println(kwota);//"CALL `mydb2`.`dodajWplateSzef`(?,?);\n"
                        PoloczenieKlientKwota("CALL `mydb2`.`zapakujDanieKucharz`(?, ?);");
                        naglowek_komunikatu.setText("masa      czy_Zapakowane      ID_Jadlospis      ID_Danie");
                        komunikat.setVisible(true);
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
        komunikat.setVisible(false);
        TestP.setVisible(false);
        Ile.setVisible(false);
    }

    void PoloczenieKlientKwota(String query_){
        try {
            Connection conn = Main.otworzPoloczenie();

            try (PreparedStatement Query = conn.prepareStatement(query_)){
                conn.setAutoCommit(false);
                Query.setInt(1, idKlient);
                Query.setInt(2, kwota);
                ResultSet rs = Query.executeQuery();
                conn.commit();
                while (rs.next()) {
                    OdpowiedzDoOkienka(rs);
                }
            }catch (SQLException e) {
                e.printStackTrace();
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }

            Main.zamknijPoloczenie(conn);
        } catch (SQLException e) {
            System.out.println("Nie ok");
        } catch (ClassNotFoundException e) {
            System.out.println("Problem ze sterownikiem");
        }


    }

    void OdpowiedzDoOkienka(@NotNull ResultSet rs){
        StringBuilder tekstB= new StringBuilder();
        for (int i=1; ;i++){
            try {
                tekstB.append("     ");
                tekstB.append(rs.getString(i));
            }catch(SQLException e){
                tresc_komunikatu.setText(tekstB.toString());
                break;
            }
        }
    }
}
