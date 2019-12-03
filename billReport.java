import java.time.LocalDateTime;

public class billReport {

     LocalDateTime date;
     String serviceDate;
     String memberName;
     int providerNumber; // 9 digits
     int memberNumber; // 5 digits
     int serviceCode; // Grabbed from service class
     float serviceFees;
     String comments;
}
