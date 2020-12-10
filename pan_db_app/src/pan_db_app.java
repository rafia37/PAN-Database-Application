import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;

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
	            case "1": // Insert a new team
	            	
	            	
	            	//Predefining potential null variables
	            	//-------------------------------------
	            	String ssn = "1234567890";
	            	String report_date = "1900-01-01 00:00:00";
	            	String report_des = "unavailable";
	            	Timestamp rd = Timestamp.valueOf(report_date);
	            	
	            	
	            	// Collect values for every record from user inputs
	            	//--------------------------------------------------
	            	
                    System.out.println("Please enter team name:");
                    sc.nextLine();
                    String tname = sc.nextLine(); // Read in the user input of faculty ID

                    System.out.println("Please enter team type:");
                    String ttype = sc.nextLine(); // Read in user input of faculty name (white-spaces allowed).
                    
                    System.out.println("Please enter the date this team was formed (yyyy-mm-dd hh:mm:ss):");
                    String tdate = sc.nextLine();
                    Timestamp td = Timestamp.valueOf(tdate);
                    
                    System.out.println("Do you have the employee (team supervisor) information? [y/n]");
                    String einfo = sc.nextLine();
                    
                    
                    if (einfo=="y") {
	                    System.out.println("Please enter integer Employee SSN (If known):");
	                    ssn = sc.nextLine(); 
	                    
	                    System.out.println("Please enter report date (If available):");
	                    report_date = sc.nextLine(); // Read in user input of faculty name (white-spaces allowed).
	                    rd = Timestamp.valueOf(report_date);
	                    
	                    System.out.println("Please enter brief (max 100 characters) report description (If available):");
	                    report_des = sc.nextLine();
                    }
                    
                    
                    
                    //Connecting to database and performing query
                    //---------------------------------------------
                    
                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                    	
                    	// Prepare the stored procedure call
                    	final PreparedStatement statement = connection.prepareCall("{call query1(?, ?, ?, ?, ?, ?)}");
            			
            			// Set the parameters
            			statement.setString(1, tname);
                        statement.setString(2, ttype);
                        statement.setTimestamp(3, td);
                        if (einfo.equals("y")) {
	                        statement.setString(4, ssn);
	                        statement.setTimestamp(5, rd);
	                        statement.setString(6, report_des); 
                        } else {
	                        statement.setNull(4, Types.NULL);
	                        statement.setNull(5, Types.NULL);
	                        statement.setNull(6, Types.NULL);
	                    }
                	
                                             
                        // Call stored procedure
            			System.out.println("Inserting new row into team..");
            			statement.execute();
            			System.out.println("1 new row inserted \n");
            			
                    }

	            	break;
	                
	                
	            case "2": // Insert a new client and associate with teams
	            	
	            	String ssnP1; //Client ssn
	            	
	            	System.out.println("Does the client already exist in the person database? [y/n] :");
	            	sc.nextLine();
	            	String pExists = sc.nextLine();
	            	
	            	if (pExists.equals("n")) {
		            	
		            	//////////////////////Creating Person//////////////////////////
		            	//Predefining variables for later use
		            	//------------------------------------
		            	String name;
		            	String bday;
		            	String race;
		            	String gender;
		            	String profession;
		            	String mailing_add;
		            	String email;
		            	String home_pn;
		            	String work_pn;
		            	String cell_pn;
		            	int mailing_list;
		            	String company;
		            	
		            	
		            	// Collect values for every record from user inputs
		            	//--------------------------------------------------
		            	
		                System.out.println("Please enter person ssn:");
		                ssnP1 = sc.nextLine();
		                
		                System.out.println("Please enter person name:");
		                name = sc.nextLine(); 
		                
		                System.out.println("Please enter date of birth (yyyy-mm-dd hh:mm:ss):");
		                bday = sc.nextLine();  //2001-12-23 00:00:00
		                Timestamp dob = Timestamp.valueOf(bday);
		                
		                System.out.println("Please enter race:");
		                race = sc.nextLine();
		                
		                System.out.println("Please enter gender:");
		                gender = sc.nextLine();
		                
		                System.out.println("Please enter profession:");
		                profession = sc.nextLine();
		                
		                System.out.println("Please enter mailing address:");
		                mailing_add = sc.nextLine();
		                
		                System.out.println("Please enter email:");
		                email = sc.nextLine();
		                
		                System.out.println("Please enter home phone number:");
		                home_pn = sc.nextLine();
		                
		                System.out.println("Please enter work phone number:");
		                work_pn = sc.nextLine();
		                
		                System.out.println("Please enter cell phone number:");
		                cell_pn = sc.nextLine();
		                
		                System.out.println("Please enter whether is person is in the mailing list or not \n" + "Possible values are 1 for yes, 0 for no and 2 for unknown:");
		                mailing_list = sc.nextInt();
		                sc.nextLine();
		                
		                System.out.println("Please enter the affiliated company name (na if no affiliation):");
		                company = sc.nextLine();
		            
		                
		                
		                //Connecting to database and performing query
		                //---------------------------------------------
		                
		                System.out.println("Connecting to the database...");
		                // Get a database connection and prepare a query statement
		                try (final Connection connection = DriverManager.getConnection(URL)) {
		                	
		                	// Prepare the stored procedure call
		                	final PreparedStatement stmt_person = connection.prepareCall("{call insert_person(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		        			
		        			// Set the parameters
		                	stmt_person.setString(1, ssnP1);
		                	stmt_person.setString(2, name);
		                	stmt_person.setTimestamp(3, dob);  //2001-12-23 
		                	stmt_person.setString(4, race);
		                	stmt_person.setString(5, gender);
		                	stmt_person.setString(6, profession);
		                	stmt_person.setString(7, mailing_add);
		                	stmt_person.setString(8, email);
		                	stmt_person.setString(9, home_pn);
		                	stmt_person.setString(10, work_pn);
		                	stmt_person.setString(11, cell_pn);
		                    
		                    //Potential null variables
		                    if (mailing_list == 2) {
		                    	stmt_person.setNull(12, Types.NULL); 
		                	} else {
		                		stmt_person.setInt(12,  mailing_list);
		                	}
		                    
		                    if (company.equals("na")) {
		                    	stmt_person.setNull(13, Types.NULL); 
		                	} else {
		                		stmt_person.setString(13,  company);
		                	}
		            	
		                                         
		                    // Call stored procedures
		        			System.out.println("Inserting new row into person..");
		        			stmt_person.execute();
		        			System.out.println("1 new row inserted \n");
		        			
		        			
		        			///////////////////////Emergency Contacts/////////////////////
		        			System.out.println("How many emergency contacts for this person?:");
			                int nEC = sc.nextInt();
			                sc.nextLine();
			                
		        			// Prepare the stored procedure call
		                	final PreparedStatement stmt_ec = connection.prepareCall("{call insert_EC(?, ?, ?, ?, ?, ?, ?, ?)}");
		                	while (nEC>0) {
		                		
		                		System.out.println("Enter Information for Emergency Contact #"+nEC);
		                		
		                		System.out.println("Please enter EC name:");
		    	                name = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC relationship:");
		    	                String relationship = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC mailing address(na is acceptable):");
		    	                mailing_add = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC email:");
		    	                email = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC home phone number:");
		    	                home_pn = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC work phone number(na is acceptable):");
		    	                work_pn = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC cell phone number(na is acceptable):");
		    	                cell_pn = sc.nextLine();
			        			
			        			// Set the parameters
			                	stmt_ec.setString(1, ssnP1);
			                	stmt_ec.setString(2, name);
			                	stmt_ec.setString(3, relationship);
			                	stmt_ec.setString(5, email);
			                	stmt_ec.setString(6, home_pn);
			                    
			                    //Potential null variables
			                	if (mailing_add.equals("na")) {stmt_ec.setNull(4, Types.NULL);} else {stmt_ec.setString(4,  mailing_add);}
			                    if (work_pn.equals("na")) {stmt_ec.setNull(7, Types.NULL);} else {stmt_ec.setString(7,  work_pn);}
			                    if (cell_pn.equals("na")) {stmt_ec.setNull(8, Types.NULL); } else {stmt_ec.setString(8,  cell_pn);}
			            	
			                                         
			                    // Call stored procedures
			        			System.out.println("Inserting new row into emergency contact.");
			        			stmt_ec.execute();
			        			System.out.println("1 new row inserted \n");
		        				
		        				nEC--;
		        			}
		        			
		                }
	            	} else {
	            		System.out.println("Please enter client ssn:");
		                ssnP1 = sc.nextLine();
	            	}
	                
	                
	                
	                //////////////////////Creating Client//////////////////////////
	            	
	            	// Collect values for every record from user inputs
	            	//--------------------------------------------------
	                
	                System.out.println("Please enter client specific information:");
	                System.out.println("Please enter doctor name:");
	                String doc_name = sc.nextLine();
	                
	                System.out.println("Please enter doctor phone number:");
	                String doc_pn = sc.nextLine(); 
	                
	                System.out.println("Please enter attorney name:");
	                String att_name = sc.nextLine();
	                
	                System.out.println("Please enter attorney phone number:");
	                String att_pn = sc.nextLine();
	                
	                System.out.println("Please enter date of first assignment to PAN (yyyy-mm-dd hh:mm:ss):");
	                String asn_date = sc.nextLine();  //2001-12-23 00:00:00
	                Timestamp ad = Timestamp.valueOf(asn_date);
	                
	                
	                //Connecting to database and performing query
	                //---------------------------------------------
	                
	                System.out.println("Connecting to the database...");
	                // Get a database connection and prepare a query statement
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                	
	                	// Prepare the stored procedure call
	                	final PreparedStatement stmt_client = connection.prepareCall("{call query2a(?, ?, ?, ?, ?, ?)}");
	        			
	        			// Set the parameters
	                	stmt_client.setString(1, ssnP1);
	                	stmt_client.setString(2, doc_name); 
	                	stmt_client.setString(3, doc_pn);
	                	stmt_client.setString(4, att_name);
	                	stmt_client.setString(5, att_pn);
	                	stmt_client.setTimestamp(6, ad);  //2001-12-23
	                	
	                	
	                    // Call stored procedures
	        			System.out.println("Inserting new row into client..");
	        			stmt_client.execute();
	        			System.out.println("1 new row inserted \n");
	        			
	        			
	        			///////////////////////Insurance Providers/////////////////////
	        			System.out.println("How many insurance policies for this client?:");
		                int nIP = sc.nextInt();
		                sc.nextLine();
		                
		                //predefining variables for later use
		                String polID;
		                String proID;
		                String proAdd;
		                String ip_type;
		                
	        			// Prepare the stored procedure call
	                	final PreparedStatement stmt_ip = connection.prepareCall("{call insert_ip(?, ?, ?, ?, ?)}");
	                	while (nIP>0) {
	                		
	                		System.out.println("Enter Information for Insurance policy #"+nIP);
	                		
	                		System.out.println("Please enter policy ID:");
	    	                polID = sc.nextLine();
	    	                
	    	                System.out.println("Please enter provider ID:");
	    	                proID= sc.nextLine();
	    	                
	    	                System.out.println("Please enter provider address:");
	    	                proAdd= sc.nextLine();
	    	                
	    	                System.out.println("Please enter insurance type:");
	    	                ip_type = sc.nextLine();
	    	                
	    	                
		        			// Set the parameters
		                	stmt_ip.setString(1, ssnP1);
		                	stmt_ip.setString(2, polID);
		                	stmt_ip.setString(3, proID);
		                	stmt_ip.setString(4, proAdd);
		                	stmt_ip.setString(5, ip_type);
		                                             
		                    // Call stored procedures
		        			System.out.println("Inserting new row into insurance provider.");
		        			stmt_ip.execute();
		        			System.out.println("1 new row inserted \n");
	        				
	        				nIP--;
	        			}
	                	
	                	
	                	///////////////////////Needs Table/////////////////////
	        			System.out.println("How many needs for this client?:");
		                int nn = sc.nextInt();
		                sc.nextLine();
		                
		                //predefining variables for later use
		                String need;
		                int imp;
		                
	        			// Prepare the stored procedure call
	                	final PreparedStatement stmt_need = connection.prepareCall("{call insert_needs(?, ?, ?)}");
	                	while (nn>0) {
	                		
	                		System.out.println("Enter Information for client needs #"+nn);
	                		
	    	                System.out.println("Please enter need name:");
	    	                need= sc.nextLine();
	    	                
	    	                System.out.println("Please enter importance value of need (scale of 10):");
	    	                imp = sc.nextInt();
	    	                sc.nextLine();
	    	                
	    	                
		        			// Set the parameters
		                	stmt_need.setString(1, ssnP1);
		                	stmt_need.setString(2, need);
		                	stmt_need.setInt(3, imp);
		                                             
		                    // Call stored procedures
		        			System.out.println("Inserting new row into needs table.");
		        			stmt_need.execute();
		        			System.out.println("1 new row inserted \n");
	        				
	        				nn--;
	        			}
	                	
	                	
	                	
	                	///////////////////////Client-Team Association/////////////////////
	        			System.out.println("How many teams do you want to associate with this client?:");
		                int nt = sc.nextInt();
		                sc.nextLine();
		                
		                //predefining variables for later use
		                String team_name;
		                int active;
		                
	        			// Prepare the stored procedure call
	                	final PreparedStatement stmt_serve = connection.prepareCall("{call query2b(?, ?, ?)}");
	                	while (nt>0) {
	                		
	                		System.out.println("Enter Information for client-team #"+nt);
	                		
	    	                System.out.println("Please enter team name:");
	    	                team_name= sc.nextLine();
	    	                
	    	                System.out.println("Please enter active status of this client [0/1]:");
	    	                active = sc.nextInt();
	    	                sc.nextLine();
	    	                
	    	                
		        			// Set the parameters
	    	                stmt_serve.setString(1, team_name);
		                	stmt_serve.setString(2, ssnP1);
		                	stmt_serve.setInt(3, active);
		                                             
		                    // Call stored procedures
		        			System.out.println("Inserting new row into serves table.");
		        			stmt_serve.execute();
		        			System.out.println("1 new row inserted \n");
	        				
	        				nt--;
	        			}
        			
	                }
	                
	                break;
	             
	                
	            case "3": // Insert a new volunteer and associate with teams
	            	
	            	String ssnP2; //Volunteer ssn
	            	
	            	System.out.println("Does the Volunteer already exist in the person database? [y/n] :");
	            	sc.nextLine();
	            	String vExists = sc.nextLine();
	            	
	            	if (vExists.equals("n")) {
		            	
		            	//////////////////////Creating Person//////////////////////////
		            	//Predefining variables for later use
		            	//------------------------------------
		            	String name;
		            	String bday;
		            	String race;
		            	String gender;
		            	String profession;
		            	String mailing_add;
		            	String email;
		            	String home_pn;
		            	String work_pn;
		            	String cell_pn;
		            	int mailing_list;
		            	String company;
		            	
		            	
		            	// Collect values for every record from user inputs
		            	//--------------------------------------------------
		            	
		                System.out.println("Please enter person ssn:");
		                ssnP2 = sc.nextLine();
		                
		                System.out.println("Please enter person name:");
		                name = sc.nextLine(); 
		                
		                System.out.println("Please enter date of birth (yyyy-mm-dd hh:mm:ss):");
		                bday = sc.nextLine();  //2001-12-23 00:00:00
		                Timestamp dob = Timestamp.valueOf(bday);
		                
		                System.out.println("Please enter race:");
		                race = sc.nextLine();
		                
		                System.out.println("Please enter gender:");
		                gender = sc.nextLine();
		                
		                System.out.println("Please enter profession:");
		                profession = sc.nextLine();
		                
		                System.out.println("Please enter mailing address:");
		                mailing_add = sc.nextLine();
		                
		                System.out.println("Please enter email:");
		                email = sc.nextLine();
		                
		                System.out.println("Please enter home phone number:");
		                home_pn = sc.nextLine();
		                
		                System.out.println("Please enter work phone number:");
		                work_pn = sc.nextLine();
		                
		                System.out.println("Please enter cell phone number:");
		                cell_pn = sc.nextLine();
		                
		                System.out.println("Please enter whether is person is in the mailing list or not \n" + "Possible values are 1 for yes, 0 for no and 2 for unknown:");
		                mailing_list = sc.nextInt();
		                sc.nextLine();
		                
		                System.out.println("Please enter the affiliated company name (na if no affiliation):");
		                company = sc.nextLine();
		            
		                
		                
		                //Connecting to database and performing query
		                //---------------------------------------------
		                
		                System.out.println("Connecting to the database...");
		                // Get a database connection and prepare a query statement
		                try (final Connection connection = DriverManager.getConnection(URL)) {
		                	
		                	// Prepare the stored procedure call
		                	final PreparedStatement stmt_person = connection.prepareCall("{call insert_person(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		        			
		        			// Set the parameters
		                	stmt_person.setString(1, ssnP2);
		                	stmt_person.setString(2, name);
		                	stmt_person.setTimestamp(3, dob);  //2001-12-23 
		                	stmt_person.setString(4, race);
		                	stmt_person.setString(5, gender);
		                	stmt_person.setString(6, profession);
		                	stmt_person.setString(7, mailing_add);
		                	stmt_person.setString(8, email);
		                	stmt_person.setString(9, home_pn);
		                	stmt_person.setString(10, work_pn);
		                	stmt_person.setString(11, cell_pn);
		                    
		                    //Potential null variables
		                    if (mailing_list == 2) {
		                    	stmt_person.setNull(12, Types.NULL); 
		                	} else {
		                		stmt_person.setInt(12,  mailing_list);
		                	}
		                    
		                    if (company.equals("na")) {
		                    	stmt_person.setNull(13, Types.NULL); 
		                	} else {
		                		stmt_person.setString(13,  company);
		                	}
		            	
		                                         
		                    // Call stored procedures
		        			System.out.println("Inserting new row into person..");
		        			stmt_person.execute();
		        			System.out.println("1 new row inserted \n");
		        			
		        			
		        			///////////////////////Emergency Contacts/////////////////////
		        			System.out.println("How many emergency contacts for this person?:");
			                int nEC = sc.nextInt();
			                sc.nextLine();
			                
		        			// Prepare the stored procedure call
		                	final PreparedStatement stmt_ec = connection.prepareCall("{call insert_EC(?, ?, ?, ?, ?, ?, ?, ?)}");
		                	while (nEC>0) {
		                		
		                		System.out.println("Enter Information for Emergency Contact #"+nEC);
		                		
		                		System.out.println("Please enter EC name:");
		    	                name = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC relationship:");
		    	                String relationship = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC mailing address(na is acceptable):");
		    	                mailing_add = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC email:");
		    	                email = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC home phone number:");
		    	                home_pn = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC work phone number(na is acceptable):");
		    	                work_pn = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC cell phone number(na is acceptable):");
		    	                cell_pn = sc.nextLine();
			        			
			        			// Set the parameters
			                	stmt_ec.setString(1, ssnP2);
			                	stmt_ec.setString(2, name);
			                	stmt_ec.setString(3, relationship);
			                	stmt_ec.setString(5, email);
			                	stmt_ec.setString(6, home_pn);
			                    
			                    //Potential null variables
			                	if (mailing_add.equals("na")) {stmt_ec.setNull(4, Types.NULL);} else {stmt_ec.setString(4,  mailing_add);}
			                    if (work_pn.equals("na")) {stmt_ec.setNull(7, Types.NULL);} else {stmt_ec.setString(7,  work_pn);}
			                    if (cell_pn.equals("na")) {stmt_ec.setNull(8, Types.NULL); } else {stmt_ec.setString(8,  cell_pn);}
			            	
			                                         
			                    // Call stored procedures
			        			System.out.println("Inserting new row into emergency contact.");
			        			stmt_ec.execute();
			        			System.out.println("1 new row inserted \n");
		        				
		        				nEC--;
		        			}
		        			
		                }
	            	} else {
	            		System.out.println("Please enter volunteer ssn:");
		                ssnP2 = sc.nextLine();
	            	}
	                
	                
	                
	                //////////////////////Creating Volunteer//////////////////////////
	            	
	            	// Collect values for every record from user inputs
	            	//--------------------------------------------------
	                
	                System.out.println("Please enter Volunteer specific information:");
	                System.out.println("Please enter joining date:");
	                String j_date = sc.nextLine();
	                Timestamp jd = Timestamp.valueOf(j_date);
	                
	                System.out.println("Please enter most recent training date:");
	                String tr_date = sc.nextLine();
	                Timestamp trd = Timestamp.valueOf(tr_date);
	                
	                System.out.println("Please enter most recent training location:");
	                String tr_loc = sc.nextLine();
	                
	                
	                //Connecting to database and performing query
	                //---------------------------------------------
	                
	                System.out.println("Connecting to the database...");
	                // Get a database connection and prepare a query statement
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                	
	                	// Prepare the stored procedure call
	                	final PreparedStatement stmt_vlnt = connection.prepareCall("{call query3a(?, ?, ?, ?)}");
	        			
	        			// Set the parameters
	                	stmt_vlnt.setString(1, ssnP2);
	                	stmt_vlnt.setTimestamp(2, jd);  
	                	stmt_vlnt.setTimestamp(3, trd);  
	                	stmt_vlnt.setString(4, tr_loc); 
	                	
	                	
	                    // Call stored procedures
	        			System.out.println("Inserting new row into volunteer..");
	        			stmt_vlnt.execute();
	        			System.out.println("1 new row inserted \n");
	        			
	        			///////////////////////Volunteer-Team Association/////////////////////
	        			System.out.println("How many teams do you want to associate with this volunteer?:");
		                int nt = sc.nextInt();
		                sc.nextLine();
		                
		                //predefining variables for later use
		                String team_name;
		                int active;
		                float hours;
		                int leader;
		                
	        			// Prepare the stored procedure call
	                	final PreparedStatement stmt_work = connection.prepareCall("{call query3b(?, ?, ?, ?, ?)}");
	                	while (nt>0) {
	                		
	                		System.out.println("Enter Information for volunteer-team #"+nt);
	                		
	    	                System.out.println("Please enter team name:");
	    	                team_name= sc.nextLine();
	    	                
	    	                System.out.println("Please enter active status of this volunteer [0/1]:");
	    	                active = sc.nextInt();
	    	                sc.nextLine();
	    	                
	    	                System.out.println("Please enter the number of hours worked for this team this month:");
	    	                hours = sc.nextFloat();
	    	                sc.nextLine();
	    	                
	    	                System.out.println("Is this volunteer the leader of this team? [0/1]:");
	    	                leader = sc.nextInt();
	    	                sc.nextLine();
	    	                
	    	                
		        			// Set the parameters
	    	                stmt_work.setString(1, team_name);
	    	                stmt_work.setString(2, ssnP2);
	    	                stmt_work.setInt(3, active);
	    	                stmt_work.setFloat(4, hours);
	    	                stmt_work.setInt(5, leader);
		                                             
		                    // Call stored procedures
		        			System.out.println("Inserting new row into work table.");
		        			stmt_work.execute();
		        			System.out.println("1 new row inserted \n");
	        				
	        				nt--;
	        			}
        			
	                }
	                
	                break;
	                
	            
	            case "4": // Number of hours a volunteer worked
	            	//Collecting parameters from user input
	                System.out.println("Please enter the team name for which hours need to be updated:");
	                sc.nextLine();
	                String team_name= sc.nextLine();
	                
	                System.out.println("Please enter Volunteer SSN:");
	                String ssnP3= sc.nextLine();
	                
	                System.out.println("Please enter the number of hours worked for this team this month:");
	                float hours = sc.nextFloat();
	                sc.nextLine();
	                
                	try (final Connection connection = DriverManager.getConnection(URL)) {
                		final PreparedStatement stmt_q4 = connection.prepareCall("{call query4(?, ?, ?)}");
                		
	        			// Set the parameters
    	                stmt_q4.setString(1, team_name);
    	                stmt_q4.setString(2, ssnP3);
    	                stmt_q4.setFloat(3, hours);
    	                                         
	                    // Call stored procedures
	        			System.out.println("Updating hours in work table.");
	        			stmt_q4.execute();
	        			System.out.println("Hours updated \n");
        				
        			}
            	
	            	break;
	                
	                
	            case "5": // Insert a new employee and associate with teams
	            	
	            	String ssnP4; //Employee ssn
	            	
	            	System.out.println("Does the Employee already exist in the person database? [y/n] :");
	            	sc.nextLine();
	            	String eExists = sc.nextLine();
	            	
	            	if (eExists.equals("n")) {
		            	
		            	//////////////////////Creating Person//////////////////////////
		            	//Predefining variables for later use
		            	//------------------------------------
		            	String name;
		            	String bday;
		            	String race;
		            	String gender;
		            	String profession;
		            	String mailing_add;
		            	String email;
		            	String home_pn;
		            	String work_pn;
		            	String cell_pn;
		            	int mailing_list;
		            	String company;
		            	
		            	
		            	// Collect values for every record from user inputs
		            	//--------------------------------------------------
		            	
		                System.out.println("Please enter person ssn:");
		                ssnP4 = sc.nextLine();
		                
		                System.out.println("Please enter person name:");
		                name = sc.nextLine(); 
		                
		                System.out.println("Please enter date of birth (yyyy-mm-dd hh:mm:ss):");
		                bday = sc.nextLine();  //2001-12-23 00:00:00
		                Timestamp dob = Timestamp.valueOf(bday);
		                
		                System.out.println("Please enter race:");
		                race = sc.nextLine();
		                
		                System.out.println("Please enter gender:");
		                gender = sc.nextLine();
		                
		                System.out.println("Please enter profession:");
		                profession = sc.nextLine();
		                
		                System.out.println("Please enter mailing address:");
		                mailing_add = sc.nextLine();
		                
		                System.out.println("Please enter email:");
		                email = sc.nextLine();
		                
		                System.out.println("Please enter home phone number:");
		                home_pn = sc.nextLine();
		                
		                System.out.println("Please enter work phone number:");
		                work_pn = sc.nextLine();
		                
		                System.out.println("Please enter cell phone number:");
		                cell_pn = sc.nextLine();
		                
		                System.out.println("Please enter whether is person is in the mailing list or not \n" + "Possible values are 1 for yes, 0 for no and 2 for unknown:");
		                mailing_list = sc.nextInt();
		                sc.nextLine();
		                
		                System.out.println("Please enter the affiliated company name (na if no affiliation):");
		                company = sc.nextLine();
		            
		                
		                
		                //Connecting to database and performing query
		                //---------------------------------------------
		                
		                System.out.println("Connecting to the database...");
		                // Get a database connection and prepare a query statement
		                try (final Connection connection = DriverManager.getConnection(URL)) {
		                	
		                	// Prepare the stored procedure call
		                	final PreparedStatement stmt_person = connection.prepareCall("{call insert_person(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		        			
		        			// Set the parameters
		                	stmt_person.setString(1, ssnP4);
		                	stmt_person.setString(2, name);
		                	stmt_person.setTimestamp(3, dob);  //2001-12-23 
		                	stmt_person.setString(4, race);
		                	stmt_person.setString(5, gender);
		                	stmt_person.setString(6, profession);
		                	stmt_person.setString(7, mailing_add);
		                	stmt_person.setString(8, email);
		                	stmt_person.setString(9, home_pn);
		                	stmt_person.setString(10, work_pn);
		                	stmt_person.setString(11, cell_pn);
		                    
		                    //Potential null variables
		                    if (mailing_list == 2) {
		                    	stmt_person.setNull(12, Types.NULL); 
		                	} else {
		                		stmt_person.setInt(12,  mailing_list);
		                	}
		                    
		                    if (company.equals("na")) {
		                    	stmt_person.setNull(13, Types.NULL); 
		                	} else {
		                		stmt_person.setString(13,  company);
		                	}
		            	
		                                         
		                    // Call stored procedures
		        			System.out.println("Inserting new row into person..");
		        			stmt_person.execute();
		        			System.out.println("1 new row inserted \n");
		        			
		        			
		        			///////////////////////Emergency Contacts/////////////////////
		        			System.out.println("How many emergency contacts for this person?:");
			                int nEC = sc.nextInt();
			                sc.nextLine();
			                
		        			// Prepare the stored procedure call
		                	final PreparedStatement stmt_ec = connection.prepareCall("{call insert_EC(?, ?, ?, ?, ?, ?, ?, ?)}");
		                	while (nEC>0) {
		                		
		                		System.out.println("Enter Information for Emergency Contact #"+nEC);
		                		
		                		System.out.println("Please enter EC name:");
		    	                name = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC relationship:");
		    	                String relationship = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC mailing address(na is acceptable):");
		    	                mailing_add = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC email:");
		    	                email = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC home phone number:");
		    	                home_pn = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC work phone number(na is acceptable):");
		    	                work_pn = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC cell phone number(na is acceptable):");
		    	                cell_pn = sc.nextLine();
			        			
			        			// Set the parameters
			                	stmt_ec.setString(1, ssnP4);
			                	stmt_ec.setString(2, name);
			                	stmt_ec.setString(3, relationship);
			                	stmt_ec.setString(5, email);
			                	stmt_ec.setString(6, home_pn);
			                    
			                    //Potential null variables
			                	if (mailing_add.equals("na")) {stmt_ec.setNull(4, Types.NULL);} else {stmt_ec.setString(4,  mailing_add);}
			                    if (work_pn.equals("na")) {stmt_ec.setNull(7, Types.NULL);} else {stmt_ec.setString(7,  work_pn);}
			                    if (cell_pn.equals("na")) {stmt_ec.setNull(8, Types.NULL); } else {stmt_ec.setString(8,  cell_pn);}
			            	
			                                         
			                    // Call stored procedures
			        			System.out.println("Inserting new row into emergency contact.");
			        			stmt_ec.execute();
			        			System.out.println("1 new row inserted \n");
		        				
		        				nEC--;
		        			}
		        			
		                }
	            	} else {
	            		System.out.println("Please enter employee ssn:");
		                ssnP4 = sc.nextLine();
	            	}
	                
	                
	                
	                //////////////////////Creating Employee//////////////////////////
	            	
	            	// Collect values for every record from user inputs
	            	//--------------------------------------------------
	                
	                System.out.println("Please enter Employee specific information:");
	                System.out.println("Please enter salary:");
	                float salary = sc.nextFloat();
	                sc.nextLine();
	                
	                System.out.println("Please enter marital status:");
	                String mStatus = sc.nextLine();
	                
	                System.out.println("Please enter hiring date:");
	                String h_date = sc.nextLine();
	                Timestamp hd = Timestamp.valueOf(h_date);
	                
	                
	                //Connecting to database and performing query
	                //---------------------------------------------
	                
	                System.out.println("Connecting to the database...");
	                // Get a database connection and prepare a query statement
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                	
	                	// Prepare the stored procedure call
	                	final PreparedStatement stmt_q5a = connection.prepareCall("{call query5a(?, ?, ?, ?)}");
	        			
	        			// Set the parameters
	                	stmt_q5a.setString(1, ssnP4);
	                	stmt_q5a.setFloat(2, salary);  
	                	stmt_q5a.setString(3, mStatus); 
	                	stmt_q5a.setTimestamp(4, hd);  
	                	
	                	
	                	
	                    // Call stored procedures
	        			System.out.println("Inserting new row into Employee..");
	        			stmt_q5a.execute();
	        			System.out.println("1 new row inserted \n");
	        			
	        			///////////////////////Employee-Team Association/////////////////////
	        			System.out.println("How many teams do you want to associate with this volunteer?:");
		                int nt = sc.nextInt();
		                sc.nextLine();
		                
		                //predefining variables for later use
		                String tm_name;
		                String tm_type;
		                String fDate;
		                String rDate;
		                String rDes;
		                
	        			// Prepare the stored procedure call
	                	final PreparedStatement stmt_q5b = connection.prepareCall("{call query5b(?, ?, ?, ?, ?, ?)}");
	                	while (nt>0) {
	                		
	                		System.out.println("Enter Information for employee-team #"+nt);
	                		
	    	                System.out.println("Please enter team name:");
	    	                tm_name= sc.nextLine();
	    	                
	    	                System.out.println("Please enter team type (not needed if team already exists):");
	    	                tm_type = sc.nextLine();
	    	                
	    	                System.out.println("Please enter date formed (not needed if team already exists)");
	    	                fDate= sc.nextLine();
	    	                Timestamp fd = Timestamp.valueOf(fDate);
	    	                
	    	                System.out.println("Please enter report date (na is acceptable):");
	    	                rDate= sc.nextLine();
	    	                Timestamp repD = Timestamp.valueOf(rDate);
	    	                
	    	                System.out.println("Please enter report description (na is acceptable):");
	    	                rDes = sc.nextLine();
	    	                
	    	                
		        			// Set the parameters
	    	                stmt_q5b.setString(1, tm_name);
	    	                stmt_q5b.setString(2, tm_type);
	    	                stmt_q5b.setTimestamp(3, fd);
	    	                stmt_q5b.setString(4, ssnP4);
	    	                stmt_q5b.setTimestamp(5, repD);
	    	                stmt_q5b.setString(6, rDes);
		                                             
		                    // Call stored procedures
		        			System.out.println("Inserting new row into team table.");
		        			stmt_q5b.execute();
		        			System.out.println("1 new row inserted \n");
	        				
	        				nt--;
	        			}
        			
	                }
	                
	                break;
	             
	                
	            case "6": //Enter an employee expense
	            	//Collecting parameters from user input
	                System.out.println("Please enter Employee SSN:");
	                sc.nextLine();
	                String ssnP5= sc.nextLine();
	                
	                System.out.println("Please enter date of charge:");
	                String charge_date = sc.nextLine();
	                Timestamp cd = Timestamp.valueOf(charge_date);
	                
	                System.out.println("Please enter the amount of expense:");
	                float amount = sc.nextFloat();
	                sc.nextLine();
	                
	                System.out.println("Please enter description of expense:");
	                String cDes= sc.nextLine();
	                
                	try (final Connection connection = DriverManager.getConnection(URL)) {
                		final PreparedStatement stmt_q6 = connection.prepareCall("{call query6(?, ?, ?, ?)}");
                		
	        			// Set the parameters
    	                stmt_q6.setString(1, ssnP5);
    	                stmt_q6.setTimestamp(2, cd);
    	                stmt_q6.setFloat(3, amount);
    	                stmt_q6.setString(4, cDes);
    	                
	                    // Call stored procedures
	        			System.out.println("Inserting row in expenses table.");
	        			stmt_q6.execute();
	        			System.out.println("1 row inserted \n");
        				
        			}
	                break;
	                
	            
	            case "7": // Insert a new organization and associate it with pan teams
	            	//////////////////////Creating Organization//////////////////////////
	            	
	            	// Collect values for every record from user inputs
	            	//--------------------------------------------------
	                
	                System.out.println("Please enter organization name:");
	                sc.nextLine();
	                String org_name = sc.nextLine();

	                System.out.println("Please enter address:");
	                String address = sc.nextLine();

	                System.out.println("Please enter phone number:");
	                String org_pn = sc.nextLine();

	                System.out.println("Please enter name of the contact person:");
	                String contact = sc.nextLine();

	                System.out.println("Please enter business type (na if not a business):");
	                String btype = sc.nextLine();

	                System.out.println("Please enter business size (na if not a business):");
	                String bsize = sc.nextLine();

	                System.out.println("Please enter business website (na if not a business):");
	                String website = sc.nextLine();

	                System.out.println("Please enter religious affiliation if a church (na if not a church):");
	                String church = sc.nextLine();
	                
	                
	                //Connecting to database and performing query
	                //---------------------------------------------
	                
	                System.out.println("Connecting to the database...");
	                // Get a database connection and prepare a query statement
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                	
	                	// Prepare the stored procedure call
	                	final PreparedStatement stmt_q7a = connection.prepareCall("{call query7a(?, ?, ?, ?, ?, ?, ?, ?)}");
	        			
	        			// Set the parameters
	                	stmt_q7a.setString(1, org_name);
	                	stmt_q7a.setString(2, address);
	                	stmt_q7a.setString(3, org_pn);
	                	stmt_q7a.setString(4, contact);
	                	stmt_q7a.setString(5, btype);
	                	stmt_q7a.setString(6, bsize);
	                	stmt_q7a.setString(7, website);
	                	stmt_q7a.setString(8, church);
	                	
	                	
	                    // Call stored procedures
	        			System.out.println("Inserting new row into External Organization table..");
	        			stmt_q7a.execute();
	        			System.out.println("1 new row inserted \n");
	        			
	        			///////////////////////External Organization Association/////////////////////
	        			System.out.println("How many teams does this organization sponsor?:");
		                int nt = sc.nextInt();
		                sc.nextLine();
		                
		                //predefining variables for later use
		                String name_team;
		                
	        			// Prepare the stored procedure call
	                	final PreparedStatement stmt_q7b = connection.prepareCall("{call query7b(?, ?)}");
	                	while (nt>0) {
	                		
	                		System.out.println("Enter Information for organization-team #"+nt);
	                		
	    	                System.out.println("Please enter team name:");
	    	                name_team= sc.nextLine();
	    	                
		        			// Set the parameters
	    	                stmt_q7b.setString(1, org_name);
	    	                stmt_q7b.setString(2, name_team);
	    	                                         
		                    // Call stored procedures
		        			System.out.println("Inserting new row into sponsors table.");
		        			stmt_q7b.execute();
		        			System.out.println("1 new row inserted \n");
	        				
	        				nt--;
	        			}
      			
	                }
	            
	            	break;
	                
	                
	            case "8": // Insert a new employee and associate with teams
	            	
	            	String ssnP7; //Employee ssn
	            	
	            	System.out.println("Does the Donor already exist in the person database? [y/n] :");
	            	sc.nextLine();
	            	String dExists = sc.nextLine();
	            	
	            	if (dExists.equals("n")) {
		            	
		            	//////////////////////Creating Person//////////////////////////
		            	//Predefining variables for later use
		            	//------------------------------------
		            	String name;
		            	String bday;
		            	String race;
		            	String gender;
		            	String profession;
		            	String mailing_add;
		            	String email;
		            	String home_pn;
		            	String work_pn;
		            	String cell_pn;
		            	int mailing_list;
		            	String company;
		            	
		            	
		            	// Collect values for every record from user inputs
		            	//--------------------------------------------------
		            	
		                System.out.println("Please enter person ssn:");
		                ssnP7 = sc.nextLine();
		                
		                System.out.println("Please enter person name:");
		                name = sc.nextLine(); 
		                
		                System.out.println("Please enter date of birth (yyyy-mm-dd hh:mm:ss):");
		                bday = sc.nextLine();  //2001-12-23 00:00:00
		                Timestamp dob = Timestamp.valueOf(bday);
		                
		                System.out.println("Please enter race:");
		                race = sc.nextLine();
		                
		                System.out.println("Please enter gender:");
		                gender = sc.nextLine();
		                
		                System.out.println("Please enter profession:");
		                profession = sc.nextLine();
		                
		                System.out.println("Please enter mailing address:");
		                mailing_add = sc.nextLine();
		                
		                System.out.println("Please enter email:");
		                email = sc.nextLine();
		                
		                System.out.println("Please enter home phone number:");
		                home_pn = sc.nextLine();
		                
		                System.out.println("Please enter work phone number:");
		                work_pn = sc.nextLine();
		                
		                System.out.println("Please enter cell phone number:");
		                cell_pn = sc.nextLine();
		                
		                System.out.println("Please enter whether is person is in the mailing list or not \n" + "Possible values are 1 for yes, 0 for no and 2 for unknown:");
		                mailing_list = sc.nextInt();
		                sc.nextLine();
		                
		                System.out.println("Please enter the affiliated company name (na if no affiliation):");
		                company = sc.nextLine();
		            
		                
		                
		                //Connecting to database and performing query
		                //---------------------------------------------
		                
		                System.out.println("Connecting to the database...");
		                // Get a database connection and prepare a query statement
		                try (final Connection connection = DriverManager.getConnection(URL)) {
		                	
		                	// Prepare the stored procedure call
		                	final PreparedStatement stmt_person = connection.prepareCall("{call insert_person(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		        			
		        			// Set the parameters
		                	stmt_person.setString(1, ssnP7);
		                	stmt_person.setString(2, name);
		                	stmt_person.setTimestamp(3, dob);  //2001-12-23 
		                	stmt_person.setString(4, race);
		                	stmt_person.setString(5, gender);
		                	stmt_person.setString(6, profession);
		                	stmt_person.setString(7, mailing_add);
		                	stmt_person.setString(8, email);
		                	stmt_person.setString(9, home_pn);
		                	stmt_person.setString(10, work_pn);
		                	stmt_person.setString(11, cell_pn);
		                    
		                    //Potential null variables
		                    if (mailing_list == 2) {
		                    	stmt_person.setNull(12, Types.NULL); 
		                	} else {
		                		stmt_person.setInt(12,  mailing_list);
		                	}
		                    
		                    if (company.equals("na")) {
		                    	stmt_person.setNull(13, Types.NULL); 
		                	} else {
		                		stmt_person.setString(13,  company);
		                	}
		            	
		                                         
		                    // Call stored procedures
		        			System.out.println("Inserting new row into person..");
		        			stmt_person.execute();
		        			System.out.println("1 new row inserted \n");
		        			
		        			
		        			///////////////////////Emergency Contacts/////////////////////
		        			System.out.println("How many emergency contacts for this person?:");
			                int nEC = sc.nextInt();
			                sc.nextLine();
			                
		        			// Prepare the stored procedure call
		                	final PreparedStatement stmt_ec = connection.prepareCall("{call insert_EC(?, ?, ?, ?, ?, ?, ?, ?)}");
		                	while (nEC>0) {
		                		
		                		System.out.println("Enter Information for Emergency Contact #"+nEC);
		                		
		                		System.out.println("Please enter EC name:");
		    	                name = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC relationship:");
		    	                String relationship = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC mailing address(na is acceptable):");
		    	                mailing_add = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC email:");
		    	                email = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC home phone number:");
		    	                home_pn = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC work phone number(na is acceptable):");
		    	                work_pn = sc.nextLine();
		    	                
		    	                System.out.println("Please enter EC cell phone number(na is acceptable):");
		    	                cell_pn = sc.nextLine();
			        			
			        			// Set the parameters
			                	stmt_ec.setString(1, ssnP7);
			                	stmt_ec.setString(2, name);
			                	stmt_ec.setString(3, relationship);
			                	stmt_ec.setString(5, email);
			                	stmt_ec.setString(6, home_pn);
			                    
			                    //Potential null variables
			                	if (mailing_add.equals("na")) {stmt_ec.setNull(4, Types.NULL);} else {stmt_ec.setString(4,  mailing_add);}
			                    if (work_pn.equals("na")) {stmt_ec.setNull(7, Types.NULL);} else {stmt_ec.setString(7,  work_pn);}
			                    if (cell_pn.equals("na")) {stmt_ec.setNull(8, Types.NULL); } else {stmt_ec.setString(8,  cell_pn);}
			            	
			                                         
			                    // Call stored procedures
			        			System.out.println("Inserting new row into emergency contact.");
			        			stmt_ec.execute();
			        			System.out.println("1 new row inserted \n");
		        				
		        				nEC--;
		        			}
		        			
		                }
	            	} else {
	            		System.out.println("Please enter donor ssn:");
		                ssnP7 = sc.nextLine();
	            	}
	                
	                
	                
	                //////////////////////Creating Donor//////////////////////////
	            	
	            	// Collect values for every record from user inputs
	            	//--------------------------------------------------
	                
	                System.out.println("Please enter Donor specific information:");
	                
	                System.out.println("Please write about the inspiration or motivation behind your donation");
	                String dDes = sc.nextLine();
	                
	                
	                //Connecting to database and performing query
	                //---------------------------------------------
	                
	                System.out.println("Connecting to the database...");
	                // Get a database connection and prepare a query statement
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                	
	                	// Prepare the stored procedure call
	                	final PreparedStatement stmt_q8a = connection.prepareCall("{call query8a(?, ?)}");
	        			
	        			// Set the parameters
	                	stmt_q8a.setString(1, ssnP7);
	                	stmt_q8a.setString(2, dDes);  
	                	
	                	
	                    // Call stored procedures
	        			System.out.println("Inserting new row into Donor..");
	        			stmt_q8a.execute();
	        			System.out.println("1 new row inserted \n");
	        			
	        			///////////////////////Creating Donations/////////////////////
	        			System.out.println("How many Donations would you like to make?:");
		                int nt = sc.nextInt();
		                sc.nextLine();
		                
		                //predefining variables for later use
		                int donID;
		                String donDate;
		                float donAmount;
		                String dtype;
		                String cmpn;
		                String check;
		                String cc_num;
		                String cc_type;
		                String cc_date;
		                int anon;
		                
	        			// Prepare the stored procedure call
	                	final PreparedStatement stmt_q8b = connection.prepareCall("{call query8b(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
	                	final PreparedStatement stmt_q8c = connection.prepareCall("{call query8c(?, ?)}");
	                	while (nt>0) {
	                		
	                		System.out.println("Enter Information for donation #"+nt);
	                		
	    	                System.out.println("Please enter donation id:");
	    	                donID= sc.nextInt();
	    	                sc.nextLine();
	    	                
	    	                System.out.println("Please enter date of donation");
	    	                donDate= sc.nextLine();
	    	                Timestamp dDate = Timestamp.valueOf(donDate);
	    	                
	    	                System.out.println("Please enter amount of donation:");
	    	                donAmount = sc.nextFloat();
	    	                sc.nextLine();
	    	                
	    	                System.out.println("Please enter donation type:");
	    	                dtype = sc.nextLine();
	    	                
	    	                System.out.println("Please enter campaign name if applicable (na if not):");
	    	                cmpn = sc.nextLine();
	    	                
	    	                System.out.println("Please enter check number if you paid by check (na if you didn't)");
	    	                check = sc.nextLine();
	    	                
	    	                System.out.println("Please enter credit card number if you paid with credit card (na if you didn't)");
	    	                cc_num = sc.nextLine();
	    	                
	    	                System.out.println("Please enter credit card type if you paid with credit card (na if you didn't)");
	    	                cc_type = sc.nextLine();
	    	                
	    	                System.out.println("Please enter credit card expiration date if you paid with credit card (na if you didn't)");
	    	                cc_date = sc.nextLine();
	    	                
	    	                System.out.println("Please enter whether you want this donation to be anonymous or not [0/1]:");
	    	                anon = sc.nextInt();
	    	                sc.nextLine();
	    	                
	    	                
		        			// Set the parameters
	    	                stmt_q8b.setInt(1, donID);
	    	                stmt_q8b.setTimestamp(2, dDate);
	    	                stmt_q8b.setFloat(3, donAmount);
	    	                stmt_q8b.setString(4, dtype);
	    	                stmt_q8b.setString(5, cmpn);
	    	                stmt_q8b.setString(6, check);
	    	                stmt_q8b.setString(7, cc_num);
	    	                stmt_q8b.setString(8, cc_type);
	    	                stmt_q8b.setString(9, cc_date);
	    	                stmt_q8b.setInt(10, anon);
	    	                
	    	                // Set parameters for associating
	    	                stmt_q8c.setString(1, ssnP7);
	    	                stmt_q8c.setInt(2, donID);
	    	                
		                    // Call stored procedures
		        			System.out.println("Inserting new rows into donation and donate tables.");
		        			stmt_q8b.execute();
		        			stmt_q8c.execute();
		        			System.out.println("2 new rows inserted \n");
	        				
	        				nt--;
	        			}
        			
	                }
	                
	                break;
	             
	                
	            case "9": 
	            	//////////////////////Creating Organization//////////////////////////
	            	
	            	// Collect values for every record from user inputs
	            	//--------------------------------------------------
	                
	                System.out.println("Please enter organization name:");
	                sc.nextLine();
	                org_name = sc.nextLine();

	                System.out.println("Please enter address:");
	                address = sc.nextLine();

	                System.out.println("Please enter phone number:");
	                org_pn = sc.nextLine();

	                System.out.println("Please enter name of the contact person:");
	                contact = sc.nextLine();

	                System.out.println("Please enter business type (na if not a business):");
	                btype = sc.nextLine();

	                System.out.println("Please enter business size (na if not a business):");
	                bsize = sc.nextLine();

	                System.out.println("Please enter business website (na if not a business):");
	                website = sc.nextLine();

	                System.out.println("Please enter religious affiliation if a church (na if not a church):");
	                church = sc.nextLine();
	                
	                
	                //Connecting to database and performing query
	                //---------------------------------------------
	                
	                System.out.println("Connecting to the database...");
	                // Get a database connection and prepare a query statement
	                try (final Connection connection = DriverManager.getConnection(URL)) {
	                	
	                	// Prepare the stored procedure call
	                	final PreparedStatement stmt_q7a = connection.prepareCall("{call query7a(?, ?, ?, ?, ?, ?, ?, ?)}");
	        			
	        			// Set the parameters
	                	stmt_q7a.setString(1, org_name);
	                	stmt_q7a.setString(2, address);
	                	stmt_q7a.setString(3, org_pn);
	                	stmt_q7a.setString(4, contact);
	                	stmt_q7a.setString(5, btype);
	                	stmt_q7a.setString(6, bsize);
	                	stmt_q7a.setString(7, website);
	                	stmt_q7a.setString(8, church);
	                	
	                	
	                    // Call stored procedures
	        			System.out.println("Inserting new row into External Organization table..");
	        			stmt_q7a.execute();
	        			System.out.println("1 new row inserted \n");
	        			
	        			System.out.println("How many Donations would you like to make?:");
		                int nt = sc.nextInt();
		                sc.nextLine();
		                
		                //predefining variables for later use
		                int donID;
		                String donDate;
		                float donAmount;
		                String dtype;
		                String cmpn;
		                String check;
		                String cc_num;
		                String cc_type;
		                String cc_date;
		                int anon;
		                
		                final PreparedStatement stmt_q8b = connection.prepareCall("{call query8b(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		                final PreparedStatement stmt_q9 = connection.prepareCall("{call query9(?, ?)}");
	                	while (nt>0) {
	                		System.out.println("Enter Information for donation #"+nt);
	                		
	    	                System.out.println("Please enter donation id:");
	    	                donID= sc.nextInt();
	    	                sc.nextLine();
	    	                
	    	                System.out.println("Please enter date of donation");
	    	                donDate= sc.nextLine();
	    	                Timestamp dDate = Timestamp.valueOf(donDate);
	    	                
	    	                System.out.println("Please enter amount of donation:");
	    	                donAmount = sc.nextFloat();
	    	                sc.nextLine();
	    	                
	    	                System.out.println("Please enter donation type:");
	    	                dtype = sc.nextLine();
	    	                
	    	                System.out.println("Please enter campaign name if applicable (na if not):");
	    	                cmpn = sc.nextLine();
	    	                
	    	                System.out.println("Please enter check number if you paid by check (na if you didn't)");
	    	                check = sc.nextLine();
	    	                
	    	                System.out.println("Please enter credit card number if you paid with credit card (na if you didn't)");
	    	                cc_num = sc.nextLine();
	    	                
	    	                System.out.println("Please enter credit card type if you paid with credit card (na if you didn't)");
	    	                cc_type = sc.nextLine();
	    	                
	    	                System.out.println("Please enter credit card expiration date if you paid with credit card (na if you didn't)");
	    	                cc_date = sc.nextLine();
	    	                
	    	                System.out.println("Please enter whether you want this donation to be anonymous or not [0/1]:");
	    	                anon = sc.nextInt();
	    	                sc.nextLine();
	    	                
	    	                
		        			// Set the parameters
	    	                stmt_q8b.setInt(1, donID);
	    	                stmt_q8b.setTimestamp(2, dDate);
	    	                stmt_q8b.setFloat(3, donAmount);
	    	                stmt_q8b.setString(4, dtype);
	    	                stmt_q8b.setString(5, cmpn);
	    	                stmt_q8b.setString(6, check);
	    	                stmt_q8b.setString(7, cc_num);
	    	                stmt_q8b.setString(8, cc_type);
	    	                stmt_q8b.setString(9, cc_date);
	    	                stmt_q8b.setInt(10, anon);
	    	                
	    	                // Set parameters for associating
	    	                stmt_q9.setString(1, org_name);
	    	                stmt_q9.setInt(2, donID);
	    	                
		                    // Call stored procedures
		        			System.out.println("Inserting new rows into donation and donate tables.");
		        			stmt_q8b.execute();
		        			stmt_q9.execute();
		        			System.out.println("2 new rows inserted \n");
	        				
	                		nt--;
	                	}
	                		
	                	
	                }
	                break;
	                
	            
	            case "10": //name and phone number of the doctor of a particular client
	            	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        
                        //prepare query statement
                        final PreparedStatement statement = connection.prepareCall("{call query10(?)}");
                        
                        //set parameters
                        System.out.println("Please enter client ssn:");
                        sc.nextLine();
                        String ssnP9 = sc.nextLine();
                        statement.setString(1, ssnP9);
                        
                        final ResultSet resultSet = statement.executeQuery();
                        
                        System.out.println("name and phone number of the doctor of this client:");
                        System.out.println("Doctor Name | Phone Number");

                        // Unpack the tuples returned by the database and print them out to the user
                        while (resultSet.next()) {
                            System.out.println(String.format("%s | %s",
                                resultSet.getString(1),
                                resultSet.getString(2)));
                        }
                        
                    }
	            	break;
	                
	            
	            case "11":  //total amount of expenses charged by each employee for a particular period of time
	            	
	            	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        
                        //prepare query statement
                        final PreparedStatement statement = connection.prepareCall("{call query11(?, ?, ?)}");
                        
                        //collect parameters from user input
                        System.out.println("Please enter employee ssn:");
                        sc.nextLine();
                        String ssnP10 = sc.nextLine();
                        
                        System.out.println("Please enter start date of period");
    	                String start = sc.nextLine();
    	                Timestamp sd = Timestamp.valueOf(start);
    	                
    	                System.out.println("Please enter end date of period");
    	                String end = sc.nextLine();
    	                Timestamp ed = Timestamp.valueOf(end);
                        
    	                //set parameters
                        statement.setString(1, ssnP10);
                        statement.setTimestamp(2, sd);
                        statement.setTimestamp(3, ed);
                        
                        final ResultSet resultSet = statement.executeQuery();

                        // Unpack the tuples returned by the database and print them out to the user
                        while (resultSet.next()) {
                            System.out.println(String.format("Expense charged by this employee \n" + "during the given period is $ %s",
                                resultSet.getString(1)));
                        }
                        
                    }
	            	
	            	break;
	                
	                
	            case "12": //list of volunteers that are members of teams that support a particular client 
	            	
	            	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        
                        //prepare query statement
                        final PreparedStatement statement = connection.prepareCall("{call query12(?)}");
                        
                        //set parameters
                        System.out.println("Please enter client ssn:");
                        sc.nextLine();
                        String ssnP11 = sc.nextLine();
                        statement.setString(1, ssnP11);
                        
                        final ResultSet resultSet = statement.executeQuery();
                        
                        System.out.println("List of volunteers who support this particular client:");
                        System.out.println("SSN        |   Volunteer Name  |     Date of birth     |   Race   | Gender |   Profession   |      Mailing_address     |   Email_address   |  Home_phone_number |  Work_phone_number | Cell_phone_number | Mailing_list | Company_name");

                        // Unpack the tuples returned by the database and print them out to the user
                        while (resultSet.next()) {
                            System.out.println(String.format("%s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s" ,
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8),
                                resultSet.getString(9),
                                resultSet.getString(10),
                                resultSet.getString(11),
                                resultSet.getString(12),
                                resultSet.getString(13)));
                        }
                        
                    }
	            	
	                break;
	             
	                
	            case "13": // names and contact information of the clients that are supported by teams
	            			//sponsored by an organization whose name starts with a letter between B and K
	            	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        
                        //prepare query statement
                        final PreparedStatement statement = connection.prepareCall("{call query13}");
                        
                        //Execute query
                        final ResultSet resultSet = statement.executeQuery();
                        
                        
                        System.out.println(" Names and contact information of the clients that are supported by teams \n"
                        		+ "sponsored by an organization whose name starts with a letter between B and K:");
                        System.out.println("Client_Name | Mailing_address | Email_address | Home_phone_number | Work_phone_number | Cell_phone_number");

                        // Unpack the tuples returned by the database and print them out to the user
                        while (resultSet.next()) {
                            System.out.println(String.format("%s | %s | %s | %s | %s | %s",
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)));
                        }
                        
                    }
	                break;
	                
	            
	            case "14": 
	            	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        
                        //prepare query statement
                        final PreparedStatement statement = connection.prepareCall("{call query14}");
                        
                        //Execute query
                        final ResultSet resultSet = statement.executeQuery();
                        
                        
                        System.out.println("Name, total amount donated and anonimity of donors that are also employees:");
                        System.out.println("Employee_Name | Total_Donation | Anonymity");

                        // Unpack the tuples returned by the database and print them out to the user
                        while (resultSet.next()) {
                            System.out.println(String.format("%s | %s | %s",
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3)));
                        }
                        
                    }
	            	break;
	                
	                
	            case "15": // Insert a new faculty option
	            	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        
                        //prepare query statement
                        final PreparedStatement statement = connection.prepareCall("{call query15(?)}");
                        
                        //set parameters
                        System.out.println("Please enter the date:");
                        sc.nextLine();
                        String after_date = sc.nextLine();
                        Timestamp aDate = Timestamp.valueOf(after_date);
                        statement.setTimestamp(1, aDate);
                        
                        final ResultSet resultSet = statement.executeQuery();
                        
                        System.out.println("Teams which were founded after the given date:");

                        // Unpack the tuples returned by the database and print them out to the user
                        while (resultSet.next()) {
                            System.out.println(String.format("%s",
                                resultSet.getString(1)));
                        }
                        
                    }
	                break;
	             
	                
	            case "16":
	            	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        
                        //prepare query statement
                        final PreparedStatement statement = connection.prepareCall("{call query16}");
                        
                        //Execute query
                        statement.execute();
                        
                        
                        System.out.println("Table updated with increased salary");
                                                
                    }
	                break;
	                
	            
	            case "17": // Insert a new faculty member
	            	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        
                        //prepare query statement
                        final PreparedStatement statement = connection.prepareCall("{call query17}");
                        
                        //Execute query
                        statement.execute();
                        
                        
                        System.out.println("Table updated with proper deletions");
                                                
                    }
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
