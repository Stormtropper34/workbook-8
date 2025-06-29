package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind", "root", "yearup")) {

            int userChoice = 0;
            do {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("4) Search for customer");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");

                if (scanner.hasNextInt()) {
                    userChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (userChoice) {
                        case 1:
                            displayAllProducts(connection);
                            break;
                        case 2:
                            displayAllCustomers(connection);
                            break;
                        case 3:
                            displayAllCategories(connection);
                            break;
                        case 4:
                            searchForCustomer(connection);
                            break;
                        case 0:
                            System.out.println("Exiting.. Adios!");
                            break;
                        default:
                            System.out.println("Try again. :P");
                    }
                } else {
                    System.out.println("Try again.");
                    scanner.nextLine();
                }
            } while (userChoice != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();

        }
    }

    private static void searchForCustomer(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Search for Customer that starts with: ");
        String searchCustomer = scanner.nextLine()+ "%";

        String query = "SELECT ContactName, CompanyName FROM Customers " +
                "WHERE ContactName LIKE ? ORDER BY ContactName";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, searchCustomer);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.printf("%-25s %-40s%n", "Contact Name", "Company Name");
                System.out.println("--------------------------------------------------------------");


                while (resultSet.next()) {
                    String contactName = resultSet.getString("ContactName");
                    String companyName = resultSet.getString("CompanyName");

                    System.out.printf("%-25s %-40s%n", contactName, companyName);
                }

            }
        } catch (SQLException e) {
            System.out.println("Error searching for customer:");
            e.printStackTrace();
        }
    }




    private static void displayAllProducts(Connection connection) {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.printf("%-4s %-40s %10s %10s%n", "ID", "Product Name", "Price", "Stock");
            System.out.println("--------------------------------------------------------------------------------");

            while (resultSet.next()) {
                int productId = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                double price = resultSet.getDouble("UnitPrice");
                int unitStock = resultSet.getInt("UnitsInStock");

                System.out.printf("%-4d %-40s %10.2f %10d%n", productId, productName, price, unitStock);
            }

        } catch (SQLException e) {
            System.out.println("Error displaying products:");
            e.printStackTrace();
        }
    }

    private static void displayAllCustomers(Connection connection) {
        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.printf("%-25s %-35s %-20s %-20s %-20s%n",
                    "Contact Name", "Company", "City", "Country", "Phone");
            System.out.println("-----------------------------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                String contactName = resultSet.getString("ContactName");
                String companyName = resultSet.getString("CompanyName");
                String city = resultSet.getString("City");
                String country = resultSet.getString("Country");
                String phoneNumber = resultSet.getString("Phone");

                System.out.printf("%-25s %-35s %-20s %-20s %-20s%n",
                        contactName, companyName, city, country, phoneNumber);
            }

        } catch (SQLException e) {
            System.out.println("Error displaying customers:");
            e.printStackTrace();
        }

    }

    private static void displayAllCategories(Connection connection){
        String query = "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.printf("%-4s %-40s%n", "ID", "Category Name");
            System.out.println("---------------------");

            while (resultSet.next()) {
                String categoryId = resultSet.getString("CategoryID");
                String categoryName = resultSet.getString("CategoryName");

                System.out.printf("%-4s %-40s%n", categoryId, categoryName);

            }

        } catch (SQLException e) {
            System.out.println("Error displaying categories");
            e.printStackTrace();

        }
    }
}
