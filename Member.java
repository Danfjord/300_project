import java.time.LocalDateTime;
import java.util.Scanner;

class Member {
    private Scanner intScan = new Scanner(System.in); // For reading in
    public Scanner strScan = new Scanner(System.in); // For reading in

    public String memberName;
    public int memberNumber;
    String serviceDate; // MM-DD-YYYY

    Member getAll(){ // gathers and returns all data of a member
        Member mem = new Member(); // Group data together

        System.out.println("Enter member name:");
        mem.memberName = strScan.next();

        System.out.println("Enter member number:");
        mem.memberNumber = intScan.nextInt();

        System.out.println("Enter date of service (MM-DD-YYYY)");
        mem.serviceDate = strScan.next();

        return mem;
    }








}
