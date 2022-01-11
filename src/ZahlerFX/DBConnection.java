package ZahlerFX;

import java.sql.*;

public class DBConnection {

    public DBConnection() {
        createConnection();
    }


    private void createConnection()  {
        try {
            // Get a connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zaehlerprojektdb", "root", "how2count");
            //Create statement
            Statement stmt = conn.createStatement();
            //Execute SQL query
            ResultSet rs = stmt.executeQuery("SELECT * FROM zaehler");
            //Process result set
            while (rs.next()) {
                System.out.println(rs.getString("count") + " , " + rs.getString("countingobject"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}