package by.it.sorokoee.jd03_01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateAdmin {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection connection =
                        DriverManager.getConnection(CN.URL_DB, CN.USER_DB, CN.PASSWORD_DB);
        ) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `users`(`login`, `password`, `email`, `fk_roles`) "+
                            "VALUES ('Admin','dfsfs','admin@gmail.ru',1);");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
