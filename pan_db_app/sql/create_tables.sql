DROP TABLE IF EXISTS Emergency_contact;
DROP TABLE IF EXISTS Work;
DROP TABLE IF EXISTS Serves;
DROP TABLE IF EXISTS Needs;
DROP TABLE IF EXISTS Insurance_providers;
DROP TABLE IF EXISTS Expenses;
DROP TABLE IF EXISTS Contribute;
DROP TABLE IF EXISTS Donate;
DROP TABLE IF EXISTS Donation;
DROP TABLE IF EXISTS Sponsors;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Volunteer;
DROP TABLE IF EXISTS Team;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Donor;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS External_organization;






CREATE TABLE External_organization (
  Org_name VARCHAR(50) PRIMARY KEY,
  Mailing_address VARCHAR(100) NOT NULL,
  Phone_number VARCHAR(20) NOT NULL,
  Contact_person VARCHAR(20) NOT NULL,
  Business_type VARCHAR(20) DEFAULT 'N/A',
  Business_size VARCHAR(20) DEFAULT 'N/A',
  Business_website VARCHAR(100) DEFAULT 'N/A',
  Church_religious_affiliation VARCHAR(50) DEFAULT 'N/A'
);

CREATE TABLE Person (
  SSN VARCHAR(20) PRIMARY KEY,
  person_name VARCHAR(50) NOT NULL,
  Date_of_birth DATETIME NOT NULL,
  Race VARCHAR(20) NOT NULL,
  Gender VARCHAR(20) NOT NULL,
  Profession VARCHAR(50) NOT NULL,
  Mailing_address VARCHAR(100) NOT NULL,
  Email_address VARCHAR(100) NOT NULL,
  Home_phone_number VARCHAR(20) NOT NULL,
  Work_phone_number VARCHAR(20) NOT NULL,
  Cell_phone_number VARCHAR(20) NOT NULL,
  Mailing_list BINARY(1) DEFAULT 0,
  Company_name VARCHAR(50) DEFAULT 'N/A',
  CONSTRAINT FK_org FOREIGN KEY (Company_name) REFERENCES External_organization (Org_name)
);

CREATE TABLE Emergency_contact (
  SSN VARCHAR(20),
  EC_name VARCHAR(50) NOT NULL,
  Relationship VARCHAR(50) NOT NULL,
  Mailing_address VARCHAR(100),
  Email_address VARCHAR(100) NOT NULL,
  Home_phone_number VARCHAR(20) NOT NULL,
  Work_phone_number VARCHAR(20),
  Cell_phone_number VARCHAR(50),
  CONSTRAINT PK_ec PRIMARY KEY (SSN, EC_name, Relationship),
  CONSTRAINT FK_ec_ssn FOREIGN KEY (SSN) REFERENCES Person (SSN)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE Client (
  SSN VARCHAR(20),
  Doctor_name VARCHAR(50) NOT NULL,
  Doctor_phone_number VARCHAR(20) NOT NULL,
  Attorney_name VARCHAR(50) NOT NULL,
  Attorney_phone_number VARCHAR(20) NOT NULL,
  Date_of_first_assignment DATETIME NOT NULL,
  CONSTRAINT PK_cli_ssn PRIMARY KEY (SSN),
  CONSTRAINT FK_cli_ssn FOREIGN KEY (SSN) REFERENCES Person (SSN)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE Volunteer (
  SSN VARCHAR(20),
  Joining_date DATETIME NOT NULL,
  Last_training_date DATETIME NOT NULL,
  Last_training_location VARCHAR(20) NOT NULL,
  CONSTRAINT PK_vol_ssn PRIMARY KEY (SSN),
  CONSTRAINT FK_vol_ssn FOREIGN KEY (SSN) REFERENCES Person (SSN)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE Employee (
  SSN VARCHAR(20),
  Salary REAL NOT NULL,
  Marital_status VARCHAR(20) NOT NULL,
  Hire_date DATETIME NOT NULL,
  CONSTRAINT PK_emp_ssn PRIMARY KEY (SSN),
  CONSTRAINT FK_emp_ssn FOREIGN KEY (SSN) REFERENCES Person (SSN)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE Donor (
  SSN VARCHAR(20),
  Donation_description VARCHAR(100) DEFAULT 'General donation',
  CONSTRAINT PK_don_ssn PRIMARY KEY (SSN),
  CONSTRAINT FK_don_ssn FOREIGN KEY (SSN) REFERENCES Person (SSN)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE Team (
  Team_name VARCHAR(20) PRIMARY KEY,
  Team_type VARCHAR(20) NOT NULL,
  Date_formed DATETIME NOT NULL,
  Employee_SSN VARCHAR(20),
  Report_date DATETIME,
  Report_description VARCHAR(100) DEFAULT 'Unavailable',
  CONSTRAINT FK_team FOREIGN KEY (Employee_SSN) REFERENCES Employee (SSN)
);

CREATE TABLE Work (
  Team_name VARCHAR(20) NOT NULL,
  Volunteer_SSN VARCHAR(20) NOT NULL,
  Active BINARY(1) NOT NULL,
  Monthly_hours REAL NOT NULL,
  Leader BINARY(1) NOT NULL,
  CONSTRAINT PK_work PRIMARY KEY (Team_name, Volunteer_SSN),
  CONSTRAINT FK_work_tn FOREIGN KEY (Team_name) REFERENCES Team (Team_name),
  CONSTRAINT FK_work_ssn FOREIGN KEY (Volunteer_SSN) REFERENCES Volunteer (SSN)
);

CREATE TABLE Serves (
  Team_name VARCHAR(20) NOT NULL,
  Client_SSN VARCHAR(20) NOT NULL,
  Active BINARY(1) NOT NULL,
  CONSTRAINT PK_serve PRIMARY KEY (Team_name, Client_SSN),
  CONSTRAINT FK_serve_tn FOREIGN KEY (Team_name) REFERENCES Team (Team_name),
  CONSTRAINT FK_serve_ssn FOREIGN KEY (Client_SSN) REFERENCES Client (SSN)
);

CREATE TABLE Expenses (
  Employee_SSN VARCHAR(20) NOT NULL,
  Date_charged DATETIME NOT NULL,
  Amount REAL NOT NULL,
  Expense_description VARCHAR(100) NOT NULL,
  CONSTRAINT PK_exp PRIMARY KEY (Employee_SSN, Date_charged, Amount, Expense_description),
  CONSTRAINT FK_exp_ssn FOREIGN KEY (Employee_SSN) REFERENCES Employee (SSN)
);

CREATE TABLE Insurance_providers (
  Client_SSN VARCHAR(20) NOT NULL,
  PolicyID VARCHAR(50) NOT NULL,
  ProviderID VARCHAR(50) NOT NULL,
  Provider_address VARCHAR(100) NOT NULL,
  Type_of_insurance VARCHAR(20) NOT NULL,
  CONSTRAINT PK_ip PRIMARY KEY (Client_SSN, PolicyID),
  CONSTRAINT FK_ip_ssn FOREIGN KEY (Client_SSN) REFERENCES Client (SSN)
);

CREATE TABLE Needs (
  Client_SSN VARCHAR(20) NOT NULL,
  Need_name VARCHAR(20) NOT NULL,
  Importance INT NOT NULL,
  CONSTRAINT PK_need PRIMARY KEY (Client_SSN, Need_name),
  CONSTRAINT FK_need_ssn FOREIGN KEY (Client_SSN) REFERENCES Client (SSN)
);

CREATE TABLE Donation (
  DonationID INT PRIMARY KEY,
  Donation_date DATETIME NOT NULL,
  Amount REAL NOT NULL,
  Donation_Type VARCHAR(20) NOT NULL,
  Fund_raising_campaign VARCHAR(50) DEFAULT 'N/A',
  Check_number VARCHAR(20) DEFAULT 'N/A',
  CC_Number VARCHAR(20) DEFAULT 'N/A',
  CC_type VARCHAR(20) DEFAULT 'N/A',
  CC_Exp_date VARCHAR(20) DEFAULT 'N/A',
  Anonymity BINARY(1) NOT NULL,
);

CREATE TABLE Donate (
  Donor_SSN VARCHAR(20) NOT NULL,
  DonationID INT NOT NULL,
  CONSTRAINT PK_donate PRIMARY KEY (Donor_SSN, DonationID),
  CONSTRAINT FK_donate_ssn FOREIGN KEY (Donor_SSN) REFERENCES Donor (SSN),
  CONSTRAINT FK_donate_id FOREIGN KEY (DonationID) REFERENCES Donation (DonationID)
);

CREATE TABLE Contribute (
  Org_name VARCHAR(50) NOT NULL,
  DonationID INT NOT NULL,
  CONSTRAINT PK_con PRIMARY KEY (Org_name, DonationID),
  CONSTRAINT FK_con_name FOREIGN KEY (Org_name) REFERENCES External_organization (Org_name),
  CONSTRAINT FK_con_id FOREIGN KEY (DonationID) REFERENCES Donation (DonationID)
);

CREATE TABLE Sponsors (
  Org_name VARCHAR(50) NOT NULL,
  Team_name VARCHAR(20) NOT NULL,
  CONSTRAINT PK_spn PRIMARY KEY (Org_name, Team_name),
  CONSTRAINT FK_spn_on FOREIGN KEY (Org_name) REFERENCES External_organization (Org_name),
  CONSTRAINT FK_spn_tn FOREIGN KEY (Team_name) REFERENCES Team (Team_name)
);

