import javax.swing.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    static String daneZBazy;
    static String polaczenieURL = "jdbc:mysql://localhost:3306/mydb2?user=root&password=admin";
     static JFrame frame = new JFrame("First");

    public static void main(String[] args) {
        przejrzyjDaneKlient(1);
        frame.setSize(500,500);
        frame.setContentPane(new ChooseFighter().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    static void zmienContent(JPanel panel){
        frame.setContentPane(panel);
        frame.pack();
        frame.setSize(500,500);
    }


    static void utworzPoloczenie(String query){
        Connection conn;
        try {
            conn = DriverManager.getConnection(polaczenieURL);
            System.out.println(conn);

            Class.forName("com.mysql.cj.jdbc.Driver");

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                wyswietlDaneZBazy(rs);
            }
            conn.close();
        } catch (ClassNotFoundException wyjatek) {
            System.out.println("Problem ze sterownikiem");
        } catch (SQLException e) {
            System.out.println("nie ok");
        }
    }

    static void wyswietlDaneZBazy(ResultSet rs){

        System.out.println("\n");
            for (int i=1; ;i++){
                try {
                    daneZBazy = rs.getString(i);
                }catch(SQLException e){
                    break;
                }

                System.out.println(daneZBazy + " ");
            }


    }

    static void przejrzyjDaneKlient(int idKlienta){
        String query = "CALL `mydb2`.`przejrzyjDaneKlient`(" + idKlienta + ");\n";
        utworzPoloczenie(query);
    }
}
