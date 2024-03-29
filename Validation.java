/*
This is the validation class. It's only role is to validate member ids.
It has a member number associated with a string describing the member status.
 */

import java.sql.*;

public class Validation {
    //SQL junk
    private static final String url = "jdbc:mysql://localhost:3306/MySQL80";
    private static final String user = "ProviderGW";
    private static final String password = "Password!";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    //Checks member status and returns whatever string is associated with the number.
    public String CheckStatus(int memnum) throws Exception {
        String nope = "User not found.";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select idnumber from validation.idnum where idnumber = " + memnum);

            if (rs != null) {
                return rs.getString(2);
            } else {
                return nope;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return nope;
    }

    //This adds or changes a member and/or their status.
    public boolean AddChangeMemberStatus(int memnum, String status) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            String addme = "insert into validation.idnum(idnumber, status)" + "VALUES(?, ?)";
            PreparedStatement Statement = con.prepareStatement(addme);
            Statement.setInt(1, memnum);
            Statement.setString(2, status);
            Statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
