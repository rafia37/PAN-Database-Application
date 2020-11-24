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
    final static String PASSWORD = "gf37C5I5zvLi";

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
	            	
	            	String ssnP4; //Volunteer ssn
	            	
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
	            		System.out.println("Please enter volunteer ssn:");
		                ssnP4 = sc.nextLine();
	            	}
	                
	                
	                
	                //////////////////////Creating Employee//////////////////////////
	            	
	            	// Collect values for every record from user inputs
	            	//--------------------------------------------------
	                
	                System.out.println("Please enter Employee specific information:");
	                System.out.println("Please enter salary:");
	                float salary = sc.nextFloat();
	                
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
	                	stmt_q5a.setString(4, mStatus); 
	                	stmt_q5a.setTimestamp(3, hd);  
	                	
	                	
	                	
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
