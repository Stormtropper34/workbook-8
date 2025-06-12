package com.pluralsight;

import com.pluralsight.data.ProductDAO;
import com.pluralsight.model.Product;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        displayAllProductsScreen(dataSource);
        displayProductSearchScreen(dataSource);

    }

    private static void displayAllProductsScreen(BasicDataSource dataSource) {
        ProductDAO productDAO = new ProductDAO(dataSource);
        List<Product> products = productDAO.getAll();

        System.out.printf("%-4s %-40s %10s %10s%n","ID", "Product Name", "Price", "Stock");
        System.out.println("--------------------------------------------------------------------------------------");

        for (Product product:products) {
            System.out.printf("%-4s %-40s %10s %10s%n", product.getProductId(), product.getProductName(), product.getPrice(), product.getUnitStock());
        }
    }

    private static void displayProductSearchScreen(BasicDataSource dataSource) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search for products that start with: ");
        String searchTerm = scanner.nextLine() + "%";

        ProductDAO productDAO = new ProductDAO(dataSource);
        List<Product> products = productDAO.search(searchTerm);

        System.out.printf("%-4s %-40s %10s %10s%n","ID", "Product Name", "Price", "Stock");
        System.out.println("---------------------------------------------------------------------------------------");

        for (Product product:products) {
            System.out.printf("%-4s %-40s %10s %10s%n", product.getProductId(), product.getProductName(), product.getPrice(), product.getUnitStock());

        }
    }


}
