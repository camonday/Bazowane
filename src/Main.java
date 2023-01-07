import javax.swing.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    static String daneZBazy;
    static String polaczenieURL = "jdbc:mysql://localhost:3306/mydb2?user=root&password=admin";

    public static void main(String[] args) {


        int idKlienta =1;
        String query = "CALL `mydb2`.`przejrzyjDaneKlient`(" + idKlienta + ");\n";

        // utworz polaczenie



    }

    static void utworzPoloczenie(String query){
        Connection conn = null;
        try {
            System.out.println(conn);
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


}
