import java.sql.*;

public class ServiceContainer {
    int datetime;
    int service_date;
    int provider_number;
    int member_number;
    int service_code;
    int service_fee;
    String service_comments;

    public ServiceContainer(ResultSet rs) throws Exception {
        this.datetime = rs.getInt(1);
        this.service_date = rs.getInt(2);
        this.provider_number = rs.getInt(3);
        this.member_number = rs.getInt(4);
        this.service_code = rs.getInt(5);
        this.service_fee = rs.getInt(7);
        this.service_comments = rs.getString(6);
    }

}
