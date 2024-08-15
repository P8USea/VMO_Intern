package com.example.apartmentmanagement;

import com.example.apartmentmanagement.*;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.repository.*;
import com.example.apartmentmanagement.service.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseQuery {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/apartment_management";
        String user = "root";
        String password = "29102018";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM apartments";
            ResultSet resultSet = statement.executeQuery(query);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


