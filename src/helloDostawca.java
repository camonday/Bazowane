import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class helloDostawca {
     JPanel panel1;
    private JPanel odpowiedzPane;
    private JTextField textField1;
    private JButton gdzieZawieźćPaczkęButton;
    private JLabel tresc;
    private String quest;
    private int ID;

    public helloDostawca(){
        tresc.setVisible(true);
        odpowiedzPane.setVisible(false);

        gdzieZawieźćPaczkęButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textField1.getText().isEmpty()){
                    ID = Integer.parseInt(textField1.getText());
                    quest = "CALL `mydb2`.`gdzieZawiezcPaczkeDostawca`(?);\n";
                    Poloczenie1Variable(quest, ID);
                    odpowiedzPane.setVisible(true);
                }
            }
        });
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

    void OdpowiedzDoOkienka(/*@NotNull*/ ResultSet rs){
        StringBuilder tekstB= new StringBuilder();
        for (int i=1; ;i++){
            try {
                tekstB.append("     ");
                tekstB.append(rs.getString(i));
            }catch(SQLException e){
                tresc.setText(tekstB.toString());
                break;
            }
        }
    }
}
