import org.jetbrains.annotations.NotNull;

import javax.swing.*;
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
