import java.sql.*;
import java.util.*;

public class ProviderGateway {

    private static final String url = "jdbc:mysql://localhost:3306/MySQL80";
    private static final String user = "ProviderGW";
    private static final String password = "Password!";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static ProviderContainer ProviderReport(int date) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            int servicedate = date;
            rs = stmt.executeQuery("select servicedate from providerservice");

            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return;
    }

    public boolean add_or_update_provider(ProviderContainer pc) {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
        String add_me = "insert into provider.providerinfo (providername, providernumber, provideraddress, providercity, providerzip)" + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement Statement = con.prepareStatement(add_me);
        Statement.setString(1, pc.provider_name);
        Statement.setInt(2, pc.provider_number);
        Statement.setString(3, pc.provider_address);
        Statement.setString(4, pc.provider_city);
        Statement.setInt(5, pc.provider_zip);
        Statement.executeUpdate();
        return true;
    } catch (Exception e) {
        return false;
    }
}
}
