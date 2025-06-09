package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {


        Connection connection;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "yearup");


        Statement statement = connection.createStatement();

        String query = "SELECT ProductName FROM Products";
        System.out.println(query);

        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            String productName = results.getString("ProductName");
            System.out.println(productName);
        }

        results.close();
        statement.close();
        connection.close();


    }
}
