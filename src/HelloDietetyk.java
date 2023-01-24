import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloDietetyk {
    private int moje_id, klient_id;
    private String bufforek;
    private String quest;
    JPanel panel1;
    private JLabel imie;
    private JButton DaniaButton;
    private JTextField IDKlienta;
    private JButton sprawdzDaneKlientaButton;
    private JButton utwórzJadlospisButton;
    private JButton sprawdzJadlospisKlientaButton;
    private JButton dodajDoJadlospisuButton;
    private JButton OKButton;
    private JPanel KlientPane;
    private JPanel odpowiedzPane;
    private JLabel odpowiedzLabel;
    boolean zmiana;

    public HelloDietetyk(int id_dietetyka) {
        KlientPane.setVisible(false);
        odpowiedzPane.setVisible(false);

        moje_id = id_dietetyka;
        zmiana = false;
        quest = "select imie from mydb2.pracownicy where ID_Zawod = 1 and ID_Pracownik = ?;";
        Poloczenie1Variable(quest, moje_id);
        String moje_imie = bufforek;
        imie.setText("Witaj "+ moje_imie +"!");
        if(!zmiana){
            imie.setText("Podane ID nie należy do żadnego dietetyka. Dalsze korzystanie z aplikacji może prowadzić do błędów");
        }
        imie.setVisible(true);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //odczytaj id klienta
                klient_id = Integer.parseInt(IDKlienta.getText());
                // sprawdz czy to twoj klient
                zmiana = false;
                quest = "select imie from mydb2.klienci where ID_Klient = ? and ID_Dietetyk = ?;";
                Poloczenie2Variable(quest, klient_id, moje_id);
                if (zmiana){
                    bufforek = "Zajmujesz sie teraz: "+bufforek;
                    // wyswietl przyciski akcji
                    KlientPane.setVisible(true);
                }else{
                    bufforek = "to nie twój klient";
                }
                // wyswietl odpowiedz
                odpowiedzPane.setVisible(true);
                odpowiedzLabel.setText(bufforek);
                odpowiedzLabel.setVisible(true);
            }
        });


        DaniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quest = "CALL `mydb2`.`sprawdzDostepneDaniaDietetyk`();\n";
                Poloczenie0Variable(quest);
                odpowiedzPane.setVisible(true);
                odpowiedzLabel.setText(bufforek);
            }
        });
        sprawdzDaneKlientaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quest = "CALL `mydb2`.`sprawdzDaneKlientaDietetyk`(?, ?);\n";
                Poloczenie2Variable(quest, moje_id, klient_id);
                odpowiedzPane.setVisible(true);
                odpowiedzLabel.setText(bufforek);
            }
        });
        sprawdzJadlospisKlientaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Należy dodać date


                quest = "CALL `mydb2`.`sprawdzJadlospisKlientaDietetyk`(?, ?);\n";
               // Poloczenie2Variable(quest, klient_id, /*data*/);
                odpowiedzPane.setVisible(true);
                odpowiedzLabel.setText(bufforek);
            }
        });
        utwórzJadlospisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quest = "CALL `mydb2`.`utworzJadlospisDietetyk`(<{in ja int}>, <{klient int}>, <{dzien date}>, <{godzina time}>);\n";
                // Poloczenie4Variable(quest, mojeid, klient_id, /*data*/, godzina);
                odpowiedzPane.setVisible(true);
                odpowiedzLabel.setText(bufforek);
            }
        });
        dodajDoJadlospisuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quest = "CALL `mydb2`.`dodajDoJadlospisuDietetyk`(<{in ja int}>, <{klient int}>, <{jadlospis int}>, <{danie int}>, <{masax float}>);\n";
               // Poloczenie5Variable(quest, moje_id, klient_id...);
                odpowiedzPane.setVisible(true);
                odpowiedzLabel.setText(bufforek);
            }
        });
    }

    void Poloczenie0Variable(String query_){
        try {
            Connection conn = Main.otworzPoloczenie();

            try (PreparedStatement Query = conn.prepareStatement(query_)){
                conn.setAutoCommit(false);

                ResultSet rs = Query.executeQuery();
                conn.commit();
                while (rs.next()) {
                    OdpowiedzDoBufforka(rs);
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
                    OdpowiedzDoBufforka(rs);
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
                    OdpowiedzDoBufforka(rs);
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

    void OdpowiedzDoBufforka(@NotNull ResultSet rs){
        StringBuilder tekstB= new StringBuilder();
        for (int i=1; ;i++){
            try {
                tekstB.append("     ");
                tekstB.append(rs.getString(i));
            }catch(SQLException e){
                bufforek = (tekstB.toString());
                zmiana = true;
                break;
            }
        }
    }

}
