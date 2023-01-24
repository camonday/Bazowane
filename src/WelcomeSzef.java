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
    private JPanel Ile;
    private JTextField textField1;
    private JButton OKButton1;
    private JLabel naglowek_komunikatu;
    private int idKlient;
    private int kwota;
    String query;
    boolean zmiana;

    public WelcomeSzef() {
        Visibility();

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Visibility();
               idKlient = Integer.parseInt(idKlientaTextField.getText());
                query = "CALL `mydb2`.`przejrzyjDaneKlient`(?);\n";

                zmiana = false;
                Poloczenie1Variable(query, idKlient);
                if(!zmiana){
                   tresc_komunikatu.setText("Ten uzytkownik nie istnieje");
                }else {
                    przyciski.setVisible(true);
                }
                komunikat.setVisible(true);
            }
        });
        sprawdzStanKontaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ile.setVisible(false);
                query = "CALL `mydb2`.`sprawdzStanKontaKlientSzef`(?);\n";
                Poloczenie1Variable(query, idKlient);
            }
        });
        dodajWplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ile.setVisible(true);
                query = "CALL `mydb2`.`dodajWplateSzef`(?,?);\n";
            }
        });

        dodajObowiazekZaplatyButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                Ile.setVisible(true);
                query = "CALL `mydb2`.`dodajObowiazekZaplatySzef`(?,?);\n";
            }
        });

        OKButton1.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                kwota = Integer.parseInt(textField1.getText());
                System.out.println(kwota);//
                Poloczenie2Variable(query,idKlient,kwota);
                naglowek_komunikatu.setText("obecny stan konta:");
                komunikat.setVisible(true);
            }
        });
    }

    void Visibility(){
        przyciski.setVisible(false);
        komunikat.setVisible(false);
        naglowek_komunikatu.setVisible(false);
        Ile.setVisible(false);
    }

    void Poloczenie2Variable(String query_, int var1, int var2){
        try {
            Connection conn = Main.otworzPoloczenie();

            try (PreparedStatement Query = conn.prepareStatement(query_)){
                conn.setAutoCommit(false);
                Query.setInt(1, var1);
                Query.setInt(2, var2);

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

    void Poloczenie1Variable(String query_, int var1){
        try {
            Connection conn = Main.otworzPoloczenie();

            try (PreparedStatement Query = conn.prepareStatement(query_)){
                conn.setAutoCommit(false);
                Query.setInt(1, var1);

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
                    zmiana = true;
                break;
            }
        }
    }
}
