import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class pan_db_app {

    // Database credentials
    final static String HOSTNAME = "bush0037-sql-server.database.windows.net";
    final static String DBNAME = "pan-db";
    final static String USERNAME = "bush0037";
    final static String PASSWORD = "*********";

    // Database connection string
    final static String URL = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
            HOSTNAME, DBNAME, USERNAME, PASSWORD);

    // User input prompt//
    final static String PROMPT = 
            "\nPlease select one of the options below: \n" +
            "1) Enter a new team into the database \n" +
            "2) Enter a new client into the database and associate him or her with one or more teams \n" +
            "3) Enter a new volunteer into the database and associate him or her with one or more teams \n" +
            "4) Enter the number of hours a volunteer worked this month for a particular team \n" +
            "5) Enter a new employee into the database and associate him or her with one or more teams \n" +
            "6) Enter an expense charged by an employee \n" +
            "7) Enter a new organization and associate it to one or more PAN teams \n" +
            "8) Enter a new donor and associate him or her with several donations \n" +
            "9) Enter a new organization and associate it with several donations \n" +
            "10) Retrieve the name and phone number of the doctor of a particular client \n" +
            "11) Retrieve the total amount of expenses charged by each employee for a particular period of time \n" +
            "12) Retrieve the list of volunteers that are members of teams that support a particular client \n" +
            "13) Retrieve the names and contact information of the clients that are supported by teams sponsored by an organization whose name starts with a letter between B and K \n" +
            "14) Retrieve the name and total amount donated by donors that are also employees \n" +
            "15) Retrieve the names of all teams that were founded after a particular date \n" +
            "16) Increase the salary by 10% of all employees to whom more than one team must report \n" +
            "17) Delete all clients who do not have health insurance and whose value of importance for transportation is less than 5 \n" +
            "18) Enter new teams from a data file \n" +
            "19) Write a file consisting of names and mailing addresses of all people on the mailing list \n" +
            "20) Quit \n";

    public static void main(String[] args) throws SQLException {

        System.out.println("WELCOME TO THE PATIENT ASSISTANT NETWORK DATABASE SYSTEM");

        final Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input
        String option = ""; // Initialize user option selection as nothing
        while (!option.equals("20")) { // As user for options until option 4 is selected
            System.out.println(PROMPT); // Print the available options
            option = sc.next(); // Read in the user option selection
            
            switch (option) { // Switch between different options
	            case "1": // Insert a new faculty member
	            	break;
	                
	                
	            case "2": // Insert a new faculty option
	                break;
	             
	                
	            case "3":
	                break;
	                
	            
	            case "4": // Insert a new faculty member
	            	break;
	                
	                
	            case "5": // Insert a new faculty option
	                break;
	             
	                
	            case "6":
	                break;
	                
	            
	            case "7": // Insert a new faculty member
	            	break;
	                
	                
	            case "8": // Insert a new faculty option
	                break;
	             
	                
	            case "9":
	                break;
	                
	            
	            case "10": // Insert a new faculty member
	            	break;
	                
	            
	            case "11": // Insert a new faculty member
	            	break;
	                
	                
	            case "12": // Insert a new faculty option
	                break;
	             
	                
	            case "13":
	                break;
	                
	            
	            case "14": // Insert a new faculty member
	            	break;
	                
	                
	            case "15": // Insert a new faculty option
	                break;
	             
	                
	            case "16":
	                break;
	                
	            
	            case "17": // Insert a new faculty member
	            	break;
	                
	                
	            case "18": // Insert a new faculty option
	                break;
	             
	                
	            case "19":
	                break;
	                
	            
	            case "20": // Do nothing, the while loop will terminate upon the next iteration
	                System.out.println("Exiting Application");
	                break;
	                
	                
	            default: // Unrecognized option, re-prompt the user for the correct one
	                System.out.println(String.format(
	                    "Unrecognized option: %s\n" + 
	                    "Please try again!", 
	                    option));
	                break;
            }
        }
        sc.close(); // Close the scanner before exiting the application
        
    }
    
}
