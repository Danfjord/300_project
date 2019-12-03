import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.*;
import java.util.*;
import static java.lang.System.exit;

class Provider {
    private Scanner in = new Scanner(System.in); // For reading in integers
    Scanner readString = new Scanner(System.in); // For reading in strings // For some reason, if I use
    // scanner 'in' for both strings and ints, it doesn't work properly // For some reason, if I use

    String providerName;
    int providerNumber;
    String providerStreetAddress;
    String providerCity;
    String providerState;
    int providerZipcode;

    int providerFeesOwed;
    int numberOfConsultations;
    boolean providedService; // True for did provide service, false for otherwise

    billReport memberInfo = new billReport(); // Each provider will have a member object info


   // Provider Methods =====================================================
    public void buildProviderList(LinkedList<Provider> providers){ // Adds list of providers
        Provider p1 = new Provider();
        Provider p2 = new Provider();
        Provider p3 = new Provider();

        p1.providerName = ("Tim");
        p1.providerNumber = (198345);
        p1.providerStreetAddress = ("1824 SE 32st");
        p1.providerCity = ("Gresham");
        p1.providerState = ("OR");
        p1.providerZipcode = (97030);
        p1.providerFeesOwed = 0;
        p1.numberOfConsultations = 0;
        p1.providedService = false;

        p2.providerName = ("Theo");
        p2.providerNumber = (114685);
        p2.providerStreetAddress = ("1931 W Montgomery Ave");
        p2.providerCity = ("Portland");
        p2.providerState = ("OR");
        p2.providerZipcode = (97144);
        p2.providerFeesOwed = 0;
        p2.numberOfConsultations = 0;
        p2.providedService = false;

        p3.providerName = ("Cam");
        p3.providerNumber = (193751);
        p3.providerStreetAddress = ("9000 MLK Blvd");
        p3.providerCity = ("Portland");
        p3.providerState = ("OR");
        p3.providerZipcode = (97598);
        p3.providerFeesOwed = 0;
        p3.numberOfConsultations = 0;
        p3.providedService = false;


        providers.add(p1); // Adding 3 providers
        providers.add(p2);
        providers.add(p3);
    }

    billReport billChocAn(int providerNumber){ // Method to bill ChocAn after for a service
        System.out.println("Enter in service code:");
        int serviceCode = in.nextInt();
        billReport report = new billReport();  // All information will be stored in 'report'. The returned

        if (serviceCode == 15532) // Checking what kind of service it is
            report.serviceFees = 150;
        else if (serviceCode == 27350)
            report.serviceFees = 100;
        else if (serviceCode == 30098)
            report.serviceFees = 125;
        else // If the service code is invalid, return null. An error message will be printed back to provider
            return null;

        Member mem = new Member();

        mem = mem.getAll();
        // Set all data in report
        report.serviceDate = mem.serviceDate;
        report.memberNumber = mem.memberNumber;
        report.memberName = mem.memberName;
        report.providerNumber = providerNumber;
        report.serviceCode = serviceCode;

        System.out.println("Enter comments regarding this report. If no comments are needed, enter 'NA'");
        report.comments = readString.nextLine();
        return report; // Returning the whole report back to driver
    }



    private int checkID(int id, LinkedList<Provider> providerList) { // Takes in ID entered by provider from the "validateProvider" method. Returns true if ID number matches with a provider, returns false otherwise
        for (Provider i : providerList){ // Once we find an ID number, return it
            if (i.providerNumber == id)
                return i.providerNumber;
        }
        return 0; // Statement should not be reached
    }

    int validateProvider(LinkedList<Provider> providerList){
        int checkProv = 0;
        System.out.println("(3) Attempts left. Enter ID:");

        int i = 2;
        while (i >= 0) // Give 3 attempts to verify provider ID. After the 3rd attempt, lock system and exit
        {
            int id = in.nextInt(); // Store user input and pass to checkID method

            checkProv = checkID(id, providerList); // Method to check if provider number is valid or not. Return false if not matching, true if matching

            if (checkProv != 0) // If the ID is valid, return true
                return checkProv; // If we are able to return out of this method, then access has been granted
            else {
                if (i == 1)
                    System.out.println("Invalid Provider ID Number. (" + i + ") Attempt left. \nEnter ID:");
                else
                    System.out.println("Invalid Provider ID Number. (" + i + ") Attempts left. \nEnter ID:");
            }
            --i;
        }

        System.out.println("All attempts failed, locking system");
        exit(-1); return -1;
    }

    public int providerBalance(LinkedList<Integer> list){ // Goes through provider report LLL and returns sum of fees owed
        int sum = 0;
        for (int i : list)
            sum += i;
        return sum;
    }

    public boolean validateManager(int[] managerID){
        System.out.println("Manager authorization required. Please enter Manager ID code:");
        int id = in.nextInt();

        if (id == managerID[0] || id == managerID[1])
            return true;
        else
            return false;

    }


    public LinkedList<Provider> updateProvider(int fees, int providerID, LinkedList<Provider> providerList, billReport report){ // This method will alter the "feesOwed" field in the linked list. Because we have no pass by reference,
        // we need to make the return type that of a linkedList<Provider> and set our old linked list equal to the function call to actually change the value
        for (Provider i : providerList){
            if (providerID == i.providerNumber){ // If we have a match
                i.providerFeesOwed += fees; // Adjust fees
                i.memberInfo.serviceCode = report.serviceCode;
                i.memberInfo.memberNumber = report.memberNumber;
                i.memberInfo.memberName = report.memberName;
                i.memberInfo.serviceDate = report.serviceDate;
                ++i.numberOfConsultations; // Updating the number of consultations this provider has
                i.providedService = true; // Setting a flag that this provider provided a service
            }
        }
        return providerList; // return whole list
    }

    LinkedList<Provider> removeProvider(int providerID, LinkedList<Provider> providerList){
        for (Provider i : providerList){
            if (providerID == i.providerNumber) {
                providerList.remove(); // Removes if we're at a match
                System.out.println("Provider: " + providerID + " has been removed\n");
                return providerList;
            }
        }
        System.out.println("No providers with that number were found");
        return providerList;

    }










}

