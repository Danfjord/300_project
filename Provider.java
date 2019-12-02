
/*
This is the Provider class. It uses a class-specific container that has:
- a ProviderContainer object of the info related to that provider.
- a ServiceContainer array of the services provided by the provider in question.
- a total_fee that is the amount owed to the provider
- a service_count ....
functions:
MakeProviderReport(int providernumber, Date date);
    -This provides a provider report as a 'Provider' object that is described above. The date given will provide a
    report for the previous 7 days.
add_or_update_provider(ProviderContainer pc);
    -This does what it says it does. It takes a ProviderContainer object and returns true or false.

*/

import java.sql.*;
import java.util.Date;

public class Provider {
    //this is for connecting to the database, I hope to consolidate them in the future.
    private static final String url = "jdbc:mysql://localhost:3306/MySQL80";
    private static final String user = "ProviderGW";
    private static final String password = "Password!";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
//****************************************************
    //This is what is in a 'Provider' container.
    ProviderContainer pinfo;
    ServiceContainer[] sc;
    float total_fee;
    int service_count = 0;

    public Provider() {
        this.sc = null;
        this.total_fee = 0;
        this.service_count = 0;
    }
//******************************************************

    //This makes a provider report, returns a Provider object. Provider.
    public Provider MakeProviderReport(int providernum, Date date) throws Exception {
        Provider pr = new Provider();
        ServiceContainer contain[] = Service.services_done_by_provider(providernum, date);
        while (sc[service_count] != null) {
            pr.total_fee += sc[service_count].service_fee;
        }
        pr.sc = contain;
        pr.service_count = service_count;
        pr.pinfo = ProviderContainer.GetProviderInfo(providernum);
        return pr;
    }

    //This adds or updates a provider, returns true or false as appropriate.
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
