package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {

    private final static QueryRunner queryRunner = new QueryRunner();
    private final static Connection conn = connection();



    @SneakyThrows
    private static Connection connection() {
        String url = System.getProperty("db");
        String user = System.getProperty("user");
        String password = System.getProperty("password");
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static String getStatusDebitCard() {
        return queryRunner.query(conn, "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1",
                new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getStatusCreditCard() {
        return queryRunner.query(conn, "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1",
                new ScalarHandler<>());
    }

    @SneakyThrows
    public static void deleteAllDB() {
        queryRunner.update(conn, "DELETE FROM credit_request_entity");
        queryRunner.update(conn, "DELETE FROM order_entity");
        queryRunner.update(conn, "DELETE FROM payment_entity");
    }
}

