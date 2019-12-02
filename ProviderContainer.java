/*
This is the ProviderContainer object class. It is used with the Provider class and is the object that
the add_or_update_provider(ProviderConainer something) requires.
*/
import java.sql.*;

public class ProviderContainer {
    //SQL stuff that I hope to consolidate.
    private static final String url = "jdbc:mysql://localhost:3306/MySQL80";
    private static final String user = "ProviderGW";
    private static final String password = "Password!";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

//******************************************************
    //This is what a ProviderContainer....contains.
    public String provider_name;
    public int provider_number;
    public String provider_address;
    public String provider_city;
    public String provider_state;
    public int provider_zip;
    public int[] provider_services;

    public ProviderContainer(ResultSet rs) throws Exception{
            this.provider_name = rs.getString(1);
            this.provider_number = rs.getInt(2);
            this.provider_address = rs.getString(3);
            this.provider_city = rs.getString(4);
            this.provider_state = rs.getString(5);
            this.provider_zip = rs.getInt(6);

            //This is where the provider_services will be picked up
    }
//**********************************************************************************************8

    //This takes a provider number and returns the location-specific info as a providercontainer.
    public static ProviderContainer GetProviderInfo(int providernum) throws Exception{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select providernumber from provider.providerinfo where providernumber =  " + providernum);
            return new ProviderContainer(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
