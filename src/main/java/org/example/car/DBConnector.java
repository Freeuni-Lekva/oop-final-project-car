package org.example.car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {


    private static final String URL = "jdbc:mysql://localhost:3307/oopfinal";
    private static final String USER = "root";
    private static final String PASSWORD = "Lisemeitner1878$";

    //for tests:
//    private static final String URL = "jdbc:h2:mem:car_rental;DB_CLOSE_DELAY=-1";
//    private static final String USER = "sa";
//    private static final String PASSWORD = "";
    public static Connection getConnection() throws SQLException {
        try{
            //Class.forName("org.h2.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) throws SQLException {
        Connection con = DBConnector.getConnection();
    }
}
