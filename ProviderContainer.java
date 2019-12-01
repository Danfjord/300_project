import java.sql.*;


public class ProviderContainer {
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
}
