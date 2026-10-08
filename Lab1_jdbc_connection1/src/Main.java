import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/jdbc_db";
        String username = "hiruy";
        String password = "january62005";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            System.out.println("Connected successfully!");

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}