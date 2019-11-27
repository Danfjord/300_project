import java.sql.*;
import java.util.*;

public class ProviderGateway {
    public static placeholder ProviderReport(int date) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/MYSQL80", "ProviderGW", "Password!");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select servicedate from providerservice");

            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
