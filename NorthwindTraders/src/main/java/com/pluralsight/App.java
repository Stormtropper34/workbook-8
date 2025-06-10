package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {


        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "yearup");


//        Statement statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products");

            System.out.println("Id    Name                                     Price  Stock");
            System.out.println("---- ---------------------------------------- ------- ------");

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productId = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                double price = resultSet.getDouble("UnitPrice");
                int unitStock = resultSet.getInt("UnitsInStock");

                System.out.printf("%-4d %-40s %7.2f %6d%n", productId, productName, price, unitStock);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        resultSet.close();
        preparedStatement.close();
        connection.close();


    }
}
