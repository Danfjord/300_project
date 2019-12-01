import java.sql.*;

public class MemberContainer {
    public String member_name;
    public int member_number;
    public String member_address;
    public String member_city;
    public int member_zip;

    public MemberContainer(ResultSet rs) throws Exception {
        this.member_name = rs.getString(1);
        this.member_number = rs.getInt(2);
        this.member_address = rs.getString(3);
        this.member_city = rs.getString(4);
        this.member_zip = rs.getInt(5);
    }
}
