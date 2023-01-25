import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloKlient {
    public JPanel panel1;
    private int moje_id;
    private boolean zmiana;
    private String bufforek, quest;
    private JLabel imie;

    private JLabel jadlospis;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton zmienDaneButton;
    private JButton jadłospisButton;
    private JButton mojeDaneButton;
    private JLabel tresc_komunikatu;

    public HelloKlient(int id_klienta) {
        moje_id=id_klienta;
        zmiana = false;
        quest = "select imie from mydb2.klienci where ID_Klient = ?;";
        Poloczenie1Variable(quest, moje_id);
        String moje_imie = bufforek;
        imie.setText("Witaj "+ moje_imie +"!");
        if(!zmiana){
            imie.setText("Podane ID nie należy do żadnego z klientów. Dalsze korzystanie z aplikacji może prowadzić do błędów");
        }
        imie.setVisible(true);

        zmienDaneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textField1.getText().isEmpty() && !textField2.getText().isEmpty() && !textField3.getText().isEmpty() && !textField4.getText().isEmpty() && !textField5.getText().isEmpty() && !textField6.getText().isEmpty()){
                    quest = "CALL `mydb2`.``zmienDaneKlient`(?,?,?,?,?,?,?);\n";
                    Poloczenie6Variable(quest, textField1.getText(), textField2.getText(), Integer.parseInt(textField3.getText()), Float.parseFloat(textField4.getText()), textField5.getText(), textField6.getText());
                }
            }
        });

        jadłospisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quest = "CALL `mydb2`.``sprawdzJadlospisKlient`(?);\n";
                Poloczenie0Variable(quest);
            }
        });

        mojeDaneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quest = "CALL `mydb2`.``przejrzyjDaneKlient`(?);\n";
                Poloczenie0Variable(quest);
            }
        });
    }

    void Poloczenie0Variable(String query_){
        try {
            Connection conn = Main.otworzPoloczenie();

            try (PreparedStatement Query = conn.prepareStatement(query_)){
                conn.setAutoCommit(false);
                Query.setInt(1, moje_id);

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

    void Poloczenie6Variable(String query_, String var1, String var2, int var3, float var4, String var5, String var6 ){
        try {
            Connection conn = Main.otworzPoloczenie();

            try (PreparedStatement Query = conn.prepareStatement(query_)){
                conn.setAutoCommit(false);
                Query.setInt(1, moje_id);
                Query.setString(2, var1);
                Query.setString(3, var2);
                Query.setInt(4, var3);
                Query.setFloat(5, var4);
                Query.setString(6, var5);
                Query.setString(7, var6);
                conn.commit();
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
    void OdpowiedzDoBufforka(/*@NotNull*/ ResultSet rs){
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
