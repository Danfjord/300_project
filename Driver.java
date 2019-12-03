import java.util.*;
import static java.lang.System.exit;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


public class Driver {
   public static void main(String[] args)
   {
      LinkedList<billReport> reportList = new LinkedList<>(); // Linked list for member reports. Contains fees owed
      LinkedList<Provider> providerList = new LinkedList<>(); // Linked list of provider info and corresponding member info
      int[] managerID = new int[]{111, 555}; // Array of manager ID's

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

      LocalDateTime now = LocalDateTime.now(); // Getting current date/time

      Scanner in = new Scanner(System.in); // For reading in

      // List of services provided by ChocAn
      Service service1= new Service(15532, "Aerobics exercise session", 150);
      Service service2= new Service(27350, "Dietitian appointment", 100);
      Service service3= new Service(30098, "Weights class", 125);

      Provider prov = new Provider(); // Object of provider class, used to call methods
      Member mem = new Member(); // Member obj
      prov.buildProviderList(providerList); // Adds the providers

      System.out.println("Welcome provider! Please login with your provider credentials:");
      int providerNumber = prov.validateProvider(providerList); // Returns provider ID number and/or exits system if 3 invalid attempts were made to login
      System.out.println("Validated");



      boolean checkPermissions; // Used to get managers ID number for approval
      int response; // User response for the menu
      do {
         System.out.println("\nPlease select from menu:\n");
         System.out.println("0) Logout");
         System.out.println("1) Add a service to the bill");
         System.out.println("2) View all existing ChocAn member reports");
         System.out.println("3) Display all services");
         System.out.println("4) Remove a provider");
         System.out.println("5) View full provider report"); // Contains all provider information
         System.out.println("6) View summary provider report"); // Contains providerst to be paid, number of consultations each one had, and his/her total fee for the week

         response = in.nextInt(); // Read in menu response

         switch (response) {
            case 0:
               System.out.println("Logging out of system...");
               exit(0);
            case 1:
               System.out.println("Enter the number of the provider who conducted this service");
               providerNumber = prov.validateProvider(providerList);
               billReport report = prov.billChocAn(providerNumber); // This 'report' is the record to disk, as shown on page 2 of the project info.
               // Possibly Danford can write this out to a database?

               if (report == null) { // Error checking
                  System.out.println("Invalid service code");
                  break;
               }

               System.out.println("Bill report added!");
               reportList.add(report); // Adding it to the list of provider bills

               // Displaying what we just added
               System.out.println("\nReport summary:\n");
               System.out.println("Member name: " + report.memberName);
               System.out.println("Service Fee: " + report.serviceFees);
               System.out.println("Current date/time: " + formatter.format(now));
               System.out.println("Date service was provided: " + report.serviceDate);
               System.out.println("Provider number: " + report.providerNumber);
               System.out.println("Member number: " + report.memberNumber);
               System.out.println("Comments: " + report.comments);


               switch (providerNumber) { // Depending on which provider we logged in as, we're going to adjust the fees we are owed with each bill added
                  case 198345:
                     providerList = prov.updateProvider((int) report.serviceFees, 198345, providerList, report);
                     break;

                  case 114685:
                     providerList = prov.updateProvider((int) report.serviceFees, 114685, providerList, report);
                     break;

                  case 193751:
                     providerList = prov.updateProvider((int) report.serviceFees, 193751, providerList, report);
                     break;
                  default:
                     throw new IllegalStateException("Unexpected value: " + providerNumber);
               }
               System.out.println("Provider fees updated");


               break;

            case 2: // Viewing all member report bills

               if (reportList.isEmpty()){
                  System.out.println("No reports are available");
                  break;
               }

               System.out.println("\nAll member reports:\n");
               for (billReport i : reportList){
                  System.out.println("Service Fee: " + i.serviceFees);
                  System.out.println("Current date/time: " + formatter.format(now));
                  System.out.println("Date service was provided: " + i.serviceDate);
                  System.out.println("Provider number: " + i.providerNumber);
                  System.out.println("Member number: " + i.memberNumber);
                  System.out.println("Comments: " + i.comments + '\n');
               }
               break;

            case 3:
               // For service container, possibly make a method to return each object of service to "ServiceContainer"?
               System.out.println("Service code: " + service1.serviceCode);
               System.out.println("Service name: " + service1.serviceName);
               System.out.println("Service Fee: " + service1.serviceFees + '\n');

               System.out.println("Service code: " + service2.serviceCode);
               System.out.println("Service name: " + service2.serviceName);
               System.out.println("Service Fee: " + service2.serviceFees + '\n');

               System.out.println("Service code: " + service3.serviceCode);
               System.out.println("Service name: " + service3.serviceName);
               System.out.println("Service Fee: " + service3.serviceFees + '\n');
               break;

            case 4:
               int providerCode; // User entered in provider code, to search for and remove

               checkPermissions = prov.validateManager(managerID); // Getting manager permissions to view confidential information

               if (checkPermissions) // If true, that means that correct managerID was inputted
               {
                  System.out.println("Access granted");
                  System.out.println("Enter the provider code of whom you'd like to remove");
                  providerCode = in.nextInt();

                  providerList = prov.removeProvider(providerCode, providerList);

               }
               else
                   System.out.println("Invalid manager code. Permission denied");
               break;

            case 5:
               checkPermissions = prov.validateManager(managerID); // Getting manager permissions to view confidential information

               if (checkPermissions) {
                   System.out.println("Access granted");
                  if (providerList.isEmpty()) // If no list
                     System.out.println("No active providers are available");
                  else {
                     for (Provider i : providerList) { // For each node in provider LLL, output data
                        System.out.println("\nProvider name: " + i.providerName);
                        System.out.println("Provider ID number: " + i.providerNumber);
                        System.out.println("Provider street address: " + i.providerStreetAddress);
                        System.out.println("Provider city: " + i.providerCity);
                        System.out.println("Provider state: " + i.providerZipcode);
                        System.out.println("Provider fees owed: " + i.providerFeesOwed);

                        System.out.println("Date/Time: " + formatter.format(now));

                       if (i.memberInfo.memberName == null)
                          System.out.println("No members have been assisted by this provider");
                       else {
                          System.out.println("Date of service: " + i.memberInfo.serviceDate);
                          System.out.println("Member name: " + i.memberInfo.memberName);
                          System.out.println("Member number: " + i.memberInfo.memberNumber);
                          System.out.println("Service code: " + i.memberInfo.serviceCode);
                       }
                     }
                  }
               }
               else
                  System.out.println("Invalid manager code. Permission denied");

               break;

            case 6:
               checkPermissions = prov.validateManager(managerID);

               if (checkPermissions){
                  System.out.println("Access granted");
                  if (providerList.isEmpty())
                     System.out.println("No active providers are available");
                  else{
                     System.out.println("Summary report of accounts payable, for manager");
                     for (Provider i : providerList){
                         if (i.providedService) // If this provider has provided a service
                         {
                            System.out.println("Provider: " + i.providerName);
                            System.out.println("Providers total payment: " + i.providerFeesOwed);
                            System.out.println("Number of clients the provider has assisted: " + i.numberOfConsultations + '\n');
                         }
                     }
                  }
               }
               break;
            default:
               System.out.println("Invalid entry. Please try again\n");


         }
      } while (response != 0);
   }
}
