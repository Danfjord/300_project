import java.lang.reflect.Member;
import java.sql.*;

public class MemberGateway extends Gateway {


    private static final String url = "jdbc:mysql://localhost:3306/MySQL80";
    private static final String user = "ProviderGW";
    private static final String password = "Password!";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static MemberContainer get_member_info(int member_num) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select membernumber from member.memberinfo where membernumber = " + member_num);
            return new MemberContainer(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean new_or_update_member(MemberContainer mem) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            String add_me = "insert into member.memberinfo (membername, membernumber, memberaddress, membercity, memberzip)" + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement Statement = con.prepareStatement(add_me);
            Statement.setString(1, mem.member_name);
            Statement.setInt(2, mem.member_number);
            Statement.setString(3, mem.member_address);
            Statement.setString(4, mem.member_city);
            Statement.setInt(5, mem.member_zip);
            Statement.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}