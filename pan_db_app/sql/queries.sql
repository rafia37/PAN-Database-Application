--General Insertion Queries
----------------------------
----------------------------
--Insert a Person
DROP PROCEDURE IF EXISTS insert_person;

GO
CREATE PROCEDURE insert_person
(
    @ssn VARCHAR(20),
    @name VARCHAR(50),
    @dob DATETIME,
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
        (SSN, person_name, Date_of_birth, Race, Gender, Profession, Mailing_address, Email_address, Home_phone_number, Work_phone_number, Cell_phone_number, Mailing_list, Company_name)
    VALUES
        (@ssn, @name, @dob, @race, @gender, @profession, @m_address, @e_address, @home_pn, @work_pn, @cell_pn, @m_list, @company)
END
GO


--Insert Emergency Contact
DROP PROCEDURE IF EXISTS insert_EC;

GO
CREATE PROCEDURE insert_EC
(
    @ssn VARCHAR(20),
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
    @ssn VARCHAR(20),
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
    @ssn VARCHAR(20),
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
    @date DATETIME,
    @ssn VARCHAR(20),
    @report_date DATETIME,
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
    @ssn VARCHAR(20),
    @doc_name VARCHAR(50),
    @doc_pn VARCHAR(20),
    @att_name VARCHAR(50),
    @att_pn VARCHAR(20),
    @date DATETIME
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
    @cSSN VARCHAR(20),
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
    @ssn VARCHAR(20),
    @j_date DATETIME,
    @t_date DATETIME,
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
    @ssn VARCHAR(20),
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
    @ssn VARCHAR(20),
    @salary REAL,
    @marital VARCHAR(20),
    @date DATETIME
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
    @Ndate DATETIME,
    @Nssn VARCHAR(20),
    @Nreport_date DATETIME,
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
    @ssn VARCHAR(20),
    @date DATETIME,
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
    @ssn VARCHAR(20),
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
    @date DATETIME,
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
    @ssn VARCHAR(20),
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




----QUERY 10----
DROP PROCEDURE IF EXISTS query10;

GO
CREATE PROCEDURE query10 
(
    @ssn VARCHAR(20)    
)
AS
BEGIN
    SELECT Doctor_name, Doctor_phone_number FROM dbo.Client
    WHERE SSN = @ssn;
END
GO




----QUERY 11----
DROP PROCEDURE IF EXISTS query11;

GO
CREATE PROCEDURE query11 
(
    @ssn VARCHAR(20),
    @start DATETIME,
    @stop DATETIME    
)
AS
BEGIN
    SELECT SUM(Amount) FROM dbo.Expenses
    WHERE Employee_SSN = @ssn
        AND Date_charged BETWEEN @start and @stop;
END
GO



----QUERY 12----
DROP PROCEDURE IF EXISTS query12;

GO
CREATE PROCEDURE query12 
(
    @cli_ssn VARCHAR(20)    
)
AS
BEGIN
    SELECT * FROM dbo.Person
    WHERE SSN = 
        (SELECT w.Volunteer_SSN AS SSN 
        FROM dbo.Work AS w
        JOIN dbo.Serves AS s
            ON w.Team_name = s.Team_name 
        WHERE s.Client_SSN = @cli_ssn);
END
GO



----QUERY 13----
DROP PROCEDURE IF EXISTS query13;

GO
CREATE PROCEDURE query13 
AS
BEGIN
    SELECT person_name AS Client_Name, Mailing_address, Email_address, Home_phone_number, Work_phone_number, Cell_phone_number 
    FROM dbo.Person
    WHERE SSN = 
        (SELECT sr.Client_SSN AS SSN 
        FROM dbo.Serves AS sr
        JOIN dbo.Sponsors AS sp
            ON sr.Team_name = sp.Team_name 
        WHERE SUBSTRING(sp.Org_name, 1, 1) BETWEEN 'B' AND 'K')
    ORDER BY Client_Name;
END
GO



----QUERY 14----
DROP PROCEDURE IF EXISTS query14;

GO
CREATE PROCEDURE query14 
AS
BEGIN
    SELECT person_name AS Employee_Name, SUM(Amount) AS Total_Donation, MAX(Anonymity)
    FROM dbo.Donate AS d1
    JOIN dbo.Donation AS d2 ON d1.DonationID = d2.DonationID
    JOIN dbo.Person AS p ON p.SSN = d1.Donor_SSN
    WHERE Donor_SSN = 
        (SELECT d.SSN AS Donor_SSN
        FROM dbo.Donor AS d, dbo.Employee AS e 
        WHERE d.SSN = e.SSN)
    GROUP BY person_name
    ORDER BY Total_Donation;
END
GO




----QUERY 15----
DROP PROCEDURE IF EXISTS query15;

GO
CREATE PROCEDURE query15 
(
    @date DATETIME    
)
AS
BEGIN
    SELECT Team_name FROM dbo.Team
    WHERE Date_formed BETWEEN @date AND GETDATE();
END
GO



----QUERY 16----
DROP PROCEDURE IF EXISTS query16;

GO
CREATE PROCEDURE query16 
AS
BEGIN
    UPDATE dbo.Employee
    SET Salary = Salary*1.1
    WHERE SSN =  
        (SELECT temp.SSN 
        FROM 
            (SELECT Employee_SSN AS SSN, COUNT(*) AS nDon 
            FROM dbo.Team
            GROUP BY Employee_SSN) AS temp 
        WHERE temp.nDon > 1 
        );
END
GO





----QUERY 17----
DROP PROCEDURE IF EXISTS query17;

GO
CREATE PROCEDURE query17 
AS
BEGIN
    DELETE FROM dbo.Person 
    WHERE SSN IN
        (SELECT Client_SSN AS SSN 
        FROM dbo.Insurance_providers AS ip,
            (SELECT Client_SSN FROM dbo.Needs
            WHERE Need_name = 'Transportation'
                AND Importance < 5) AS temp 
        WHERE ip.Client_SSN = temp.Client_SSN
            AND 'Health' NOT IN
                (SELECT Type_of_insurance FROM ip WHERE ip.Client_SSN = temp.Client_SSN) 
        );
END
GO

