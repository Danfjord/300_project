import java.security.Provider;
import java.sql.*;
import java.util.Date;


public class Service {
    private static final String url = "jdbc:mysql://localhost:3306/MySQL80";
    private static final String user = "ProviderGW";
    private static final String password = "Password!";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public boolean add_service_rendered(ServiceContainer sc) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            String add_me = "insert into service.service (dateandtime, servicedate, providernumber, membernumber, servicecode, servicecomments, servicefee)" + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement Statement = con.prepareStatement(add_me);
            Statement.setInt(1, sc.datetime);
            Statement.setInt(2, sc.service_date);
            Statement.setInt(3, sc.provider_number);
            Statement.setInt(4, sc.member_number);
            Statement.setInt(5, sc.service_code);
            Statement.setString(6, sc.service_comments);
            Statement.setFloat(7, sc.service_fee);
            Statement.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //this gives the 7 days before the date given, if date == null, then last 7 days not including today.
    public static ServiceContainer[] services_done_by_provider(int providernum, Date thedate) throws Exception {
        ServiceContainer sc[] = new ServiceContainer[0];
        int i = 0;
        if (thedate == null) {
            thedate = new Date();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select providernumber from service.service where providernumber =  " + providernum + " and where servicedate > (thedate - interval 7 day)");
            while (rs.next()) {
                sc[i] = new ServiceContainer(rs);
            }
            return sc;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static ServiceContainer[] services_recieved_by_member(int memnum, Date thedate) throws Exception {
        ServiceContainer sc[] = new ServiceContainer[0];
        int i = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select membernumber from service.service where membernumber = " + memnum + " and where servicedate > (thedate - interval 7 day)");
            while (rs.next()) {
                sc[i] = new ServiceContainer(rs);
            }
            return sc;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
