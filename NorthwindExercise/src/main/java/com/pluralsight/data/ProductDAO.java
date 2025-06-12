package com.pluralsight.data;

import com.pluralsight.model.Product;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    BasicDataSource dataSource;

    public ProductDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getAll() {
        String sql = """
                select * 
                from products;
                """;
        List<Product> products = new ArrayList<>();

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {


            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int productId = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    double price = resultSet.getDouble("UnitPrice");
                    int unitStock = resultSet.getInt("UnitsInStock");
                    Product product = new Product(productId, productName, price, unitStock);
                    products.add(product);

                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving the products. Please try again");
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> search(String searchTerm) {
        String sql = """
                select *
                from products
                where ProductName like ?;
                """;
        List<Product> products = new ArrayList<>();

        try (
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, searchTerm);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    double price = resultSet.getDouble("UnitPrice");
                    int unitStock = resultSet.getInt("UnitsInStock");
                    Product product = new Product(productId, productName, price, unitStock);
                    products.add(product);
                }
            }


        } catch (SQLException e) {
            System.out.println("There was an error retrieving the products. Please try again or contact support.");
            e.printStackTrace();

        }
        return products;
    }
}

