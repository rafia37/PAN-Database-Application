--General Insertion Queries
----------------------------
----------------------------
--Insert a Person
DROP PROCEDURE IF EXISTS insert_person;

GO
CREATE PROCEDURE insert_person
(
    @ssn INT,
    @name VARCHAR(50),
    @dob VARCHAR(20),
    @race VARCHAR(20),
    @gender VARCHAR(20),
    @profession VARCHAR(50),
    @m_address VARCHAR(100),
    @e_address VARCHAR(100),
    @home_pn VARCHAR(20),
    @work_pn VARCHAR(20),
    @cell_pn VARCHAR(20),
    @m_list BINARY(1),
    @company VARCHAR(50)
)
AS
BEGIN
    INSERT INTO dbo.Person
        (SSN, person_name, Date_of_birth, Race, Gender, Profession, Mailing_address, Email_address, Home_phone_number, Work_phone_umber, Cell_phone_number, Mailing_list, Company_name)
    VALUES
        (@ssn, @name, @dob, @race, @gender, @profession, @m_address, @e_address, @home_pn, @work_pn, @cell_pn, @m_list, @company)
END
GO


--Insert Emergency Contact
DROP PROCEDURE IF EXISTS insert_EC;

GO
CREATE PROCEDURE insert_EC
(
    @ssn INT,
    @ec_name VARCHAR(50),
    @relation VARCHAR(50),
    @ec_Maddress VARCHAR(100),
    @ec_Eaddress VARCHAR(100),
    @ec_Hpn VARCHAR(20),
    @ec_Wpn VARCHAR(20),
    @ec_Cpn VARCHAR(50)
)
AS
BEGIN
    INSERT INTO dbo.Emergency_contact
        (SSN, EC_name, Relationship, Mailing_address, Email_address, Home_phone_number, Work_phone_number, Cell_phone_number)
    VALUES
        (@ssn, @ec_name, @relation, @ec_Maddress, @ec_Eaddress, @ec_Hpn, @ec_Wpn, @ec_Cpn)
END
GO



--Insert Client Needs
DROP PROCEDURE IF EXISTS insert_needs;

GO
CREATE PROCEDURE insert_needs 
(
    @ssn INT,
    @need VARCHAR(20),
    @imp INT
)
AS
BEGIN
    INSERT INTO dbo.Needs
        (Client_SSN, Need_name, Importance)
    VALUES
        (@ssn, @need, @imp)
END
GO



--Insert Client insurance providers
DROP PROCEDURE IF EXISTS insert_ip;

GO
CREATE PROCEDURE insert_ip 
(
    @ssn INT,
    @policyID VARCHAR(50),
    @providerID VARCHAR(50),
    @p_address VARCHAR(100),
    @ins_type VARCHAR(20)
)
AS
BEGIN
    INSERT INTO dbo.Insurance_providers
        (Client_SSN, PolicyID, ProviderID, Provider_address, Type_of_insurance)
    VALUES
        (@ssn, @policyID, @providerID, @p_address, @ins_type)
END
GO





--Numbered Application Queries
-------------------------------
-------------------------------

----QUERY 1----
--Insert a team
DROP PROCEDURE IF EXISTS query1;

GO
CREATE PROCEDURE query1 
(
    @name VARCHAR(20),
    @type VARCHAR(20),
    @date VARCHAR(20),
    @ssn INT,
    @report_date VARCHAR(20),
    @report_des VARCHAR(100)
)
AS
BEGIN
    INSERT INTO dbo.Team
        (Team_name, Team_type, Date_formed, Employee_SSN, Report_date, Report_description)
    VALUES
        (@name, @type, @date, @ssn, @report_date, @report_des)
END
GO





----QUERY 2----
----Run insert_person
----Run insert_EC (loop)
----Run query_2a
----Run insert_needs (loop)
----Run insert_ip (loop)
----Run query_2b
---- 
--Insert a client
DROP PROCEDURE IF EXISTS query2a;

GO
CREATE PROCEDURE query2a 
(
    @ssn INT,
    @doc_name VARCHAR(50),
    @doc_pn VARCHAR(20),
    @att_name VARCHAR(50),
    @att_pn VARCHAR(20),
    @date VARCHAR(20)
)
AS
BEGIN
    INSERT INTO dbo.Client
        (SSN, Doctor_name, Doctor_phone_number, Attorney_name, Attorney_phone_number, Date_of_first_assignment)
    VALUES
        (@ssn, @doc_name, @doc_pn, @att_name, @att_pn, @date)
END
GO



--Associate client to a team
DROP PROCEDURE IF EXISTS query2b;

GO
CREATE PROCEDURE query2b 
(
    @team VARCHAR(20),
    @cSSN INT,
    @active BINARY(1)
)
AS
BEGIN
    INSERT INTO dbo.Serves
        (Team_name, Client_SSN, Active)
    VALUES
        (@team, @cSSN, @active)
END
GO



----QUERY 3----
--Insert a volunteer
DROP PROCEDURE IF EXISTS query3a;

GO
CREATE PROCEDURE query3a 
(
    @ssn INT,
    @j_date VARCHAR(20),
    @t_date VARCHAR(20),
    @t_loc VARCHAR(20)
)
AS
BEGIN
    INSERT INTO dbo.Volunteer
        (SSN, Joining_date, Last_training_date, Last_training_location)
    VALUES
        (@ssn, @j_date, @t_date, @t_loc)
END
GO



--Associate volunteer to a team
DROP PROCEDURE IF EXISTS query3b;

GO
CREATE PROCEDURE query3b 
(
    @team VARCHAR(20),
    @ssn INT,
    @active BINARY(1),
    @hours REAL,
    @leader BINARY(1)
)
AS
BEGIN
    INSERT INTO dbo.Work
        (Team_name, Volunteer_SSN, Active, Monthly_hours, Leader)
    VALUES
        (@team, @ssn, @active, @hours, @leader)
END
GO



---- QUERY 4----
----Run query_3b



----QUERY 5----
----Run insert_person
----Run insert_EC (loop)
----Run query_5a
----Run query_5b
---- 

--Insert an employee
DROP PROCEDURE IF EXISTS query5a;

GO
CREATE PROCEDURE query5a 
(
    @ssn INT,
    @salary REAL,
    @marital VARCHAR(20),
    @date VARCHAR(20)
)
AS
BEGIN
    INSERT INTO dbo.Employee
        (SSN, Salary, Marital_status, Hire_date)
    VALUES
        (@ssn, @salary, @marital, @date)
END
GO



--Associate employee to a team
DROP PROCEDURE IF EXISTS query5b;

GO
CREATE PROCEDURE query5b 
(
    @Nname VARCHAR(20),
    @Ntype VARCHAR(20),
    @Ndate VARCHAR(20),
    @Nssn INT,
    @Nreport_date VARCHAR(20),
    @Nreport_des VARCHAR(100)
)
AS
BEGIN
    IF EXISTS(SELECT 1 FROM dbo.Team WITH(NOLOCK)
            WHERE Team_name = @Nname)
        BEGIN
            PRINT 'Associating with an existing team'
            UPDATE dbo.Team
            SET Employee_SSN = @Nssn, Report_date = @Nreport_date, Report_description = @Nreport_des 
            WHERE Team_name = @Nname;
        END
    ELSE
        BEGIN
            PRINT 'Associating with a new team'
            EXEC query1 @name=@Nname, @type=@Ntype, @date=@Ndate, @ssn=@Nssn, @report_date=@Nreport_date, @report_des=@Nreport_des
        END
END
GO




----QUERY 6----
--insert an employee expense
DROP PROCEDURE IF EXISTS query6;

GO
CREATE PROCEDURE query6 
(
    @ssn INT,
    @date VARCHAR(20),
    @amount REAL,
    @descrip VARCHAR(100)
)
AS
BEGIN
    INSERT INTO dbo.Expenses
        (Employee_SSN, Date_charged, Amount, Expense_description)
    VALUES
        (@ssn, @date, @amount, @descrip)
END
GO



----QUERY7----
--Insert a external organization
DROP PROCEDURE IF EXISTS query7a;

GO
CREATE PROCEDURE query7a 
(
    @name VARCHAR(50),
    @address VARCHAR(100),
    @pnum VARCHAR(20),
    @contact VARCHAR(20),
    @btype VARCHAR(20),
    @bsize VARCHAR(20),
    @bwebsite VARCHAR(100),
    @church VARCHAR(50)
)
AS
BEGIN
    INSERT INTO dbo.External_organization
        (Org_name, Mailing_address, Phone_number, Contact_person, Business_type, Business_size, Business_website, Church_religious_affiliation)
    VALUES
        (@name, @address, @pnum, @contact, @btype, @bsize, @bwebsite, @church)
END
GO



--Associate organization to a team
DROP PROCEDURE IF EXISTS query7b;

GO
CREATE PROCEDURE query7b 
(
    @oname VARCHAR(50),
    @tname VARCHAR(20)    
)
AS
BEGIN
    INSERT INTO dbo.Sponsors
        (Org_name, Team_name)
    VALUES
        (@oname, @tname)
END
GO




----QUERY 8----
----Run insert_person
----Run insert_EC (loop)
----Run query_8a
----Run query_8b
----Run query_8c
---- 
--Insert a donor
DROP PROCEDURE IF EXISTS query8a;

GO
CREATE PROCEDURE query8a 
(
    @ssn INT,
    @descrip VARCHAR(100)
)
AS
BEGIN
    INSERT INTO dbo.Donor
        (SSN, Donation_description)
    VALUES
        (@ssn, @descrip)
END
GO



--Insert a donation
DROP PROCEDURE IF EXISTS query8b;

GO
CREATE PROCEDURE query8b 
(
    @did INT,
    @date VARCHAR(20),
    @amount REAL,
    @type VARCHAR(20),
    @camp VARCHAR(50),
    @check VARCHAR(20),
    @cc_num VARCHAR(20),
    @cc_type VARCHAR(20),
    @cc_date VARCHAR(20),
    @anon BINARY(1)
)
AS
BEGIN
    INSERT INTO dbo.Donation
        (DonationID, Donation_date, Amount, Donation_Type, Fund_raising_campaign, Check_number, CC_Number, CC_type, CC_Exp_Date, Anonymity)
    VALUES
        (@did, @date, @amount, @type, @camp, @check, @cc_num, @cc_type, @cc_date, @anon)
END
GO


--Associate donor to a donation
DROP PROCEDURE IF EXISTS query8c;

GO
CREATE PROCEDURE query8c 
(
    @ssn INT,
    @id INT    
)
AS
BEGIN
    INSERT INTO dbo.Donate
        (Donor_SSN, DonationID)
    VALUES
        (@ssn, @id)
END
GO




----QUERY 9----
----Run query_7a
----Run query_8b
----Run query_9
----
--Associate organization to a donation
DROP PROCEDURE IF EXISTS query9;

GO
CREATE PROCEDURE query9
(
    @name VARCHAR(50),
    @id INT    
)
AS
BEGIN
    INSERT INTO dbo.Contribute
        (Org_name, DonationID)
    VALUES
        (@name, @id)
END
GO








