/*
This is the MemberGW class. It stands for gateway but I cannot help but read gonewild. It's named so because
I am already using Member too much.
This class is it's own object for report purposes. It contains:
    -A MemberContainer object of member info
    -An array of ServiceContainer objects that hold service info.
        --note that this is returning more information than you need for a member report.
get_member_info(int member_number);
    -This takes a member number and returns a MemberContainer object with the correct info.
new_or_update_member(MemberContainer m);
    -This takes a MemberContainer and will add or update the user information.
MakeMemberReport(int member_number);
    -This takes a member number and returns a MemberGW object with the needed info. It will give you
    more information than you need.
*/
import java.sql.*;

public class MemberGW extends Gateway {
    //SQL stuff that I hope to consolidate.
    private static final String url = "jdbc:mysql://localhost:3306/MySQL80";
    private static final String user = "ProviderGW";
    private static final String password = "Password!";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

//***********************************************************************
    //This is the MemberGW object contents.
    MemberContainer memcont;
    ServiceContainer[] sc;

    public MemberGW() {
        this.memcont = null;
        this.sc = null;
    }
//********************************************************************8

    //This takes a member number and returns a MemberContainer with the member-specific information.
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

    //This adds or updates member information. It takes a MemberContainer object and returns true or false.
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

    //This gets the member report info and returns it as a MemberGW object.
    public MemberGW MakeMemberReport(int membnum, Date date) throws Exception {
        MemberGW memb = new MemberGW();
        memb.memcont = get_member_info(membnum);
        memb.sc = Service.services_received_by_member(membnum, date);
        return memb;
    }
}