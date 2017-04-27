--Current Tables
drop table LinkedU.LoginInfo;
drop table LinkedU.Accounts;
drop table LINKEDU.ApplyInfo;
drop table LinkedU.Students;
drop table LinkedU.Universities;

-- Old Tables (no longer in use)
drop table LinkedU.Users;
drop table LinkedU.StudentProfile;

create table LinkedU.LoginInfo (
  UserID                    VARCHAR(25) NOT NULL,
  Password                  VARCHAR(50) NOT NULL
); 

--Student Accounts
insert into LinkedU.LoginInfo (UserID, Password) values
('pdkaufm', '40bf696d25dd56ed44c864e05f75d33a4cface91');
insert into LinkedU.LoginInfo (UserID, Password) values
('kssuth1', '40bf696d25dd56ed44c864e05f75d33a4cface91');
insert into LinkedU.LoginInfo (UserID, Password) values
('stiwar1', '40bf696d25dd56ed44c864e05f75d33a4cface91');
insert into LinkedU.LoginInfo (UserID, Password) values
('asriren', '40bf696d25dd56ed44c864e05f75d33a4cface91');

--University Accounts
insert into LinkedU.LoginInfo (UserID, Password) values
('blim', '40bf696d25dd56ed44c864e05f75d33a4cface91');
insert into LinkedU.LoginInfo (UserID, Password) values
('test', '40bf696d25dd56ed44c864e05f75d33a4cface91');

create table LinkedU.Accounts (
  UserID                    VARCHAR(25) NOT NULL,
  Email                     VARCHAR(50) NOT NULL,
  AccountType               VARCHAR(13) NOT NULL,
  SecurityQuestion          VARCHAR(50) NOT NULL,
  SecurityAnswer            VARCHAR(50) NOT NULL
);

--Student Accounts
insert into LinkedU.Accounts(UserID, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('pdkaufm','pdkaufm@ilstu.edu','Student','Q?','A');
insert into LinkedU.Accounts(UserID, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('kssuth1','kssuth1@ilstu.edu','Student','Q?','A');
insert into LinkedU.Accounts(UserID, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('stiwar1','stiwar1@ilstu.edu','Student','Q?','A');
insert into LinkedU.Accounts(UserID, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('asriren','asriren@ilstu.edu','Student','Q?','A');

--University Accounts
insert into LinkedU.Accounts(UserID, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('blim','pdkaufm@ilstu.edu','University','Q?','A');
insert into LinkedU.Accounts(UserID, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('test','pdkaufm@ilstu.edu','University','Q?','A');

CREATE TABLE LINKEDU.ApplyInfo(
University				varchar(50) NOT NULL,
Major					varchar(25) NOT NULL,
FirstName				varchar(25) NOT NULL, 
LastName				varchar(25) NOT NULL, 
Age						varchar(25) NOT NULL, 
Sex						varchar(50) NOT NULL, 
Citizenship				varchar(35) NOT NULL, 
Street					varchar(100) NOT NULL, 
Postal_Code				varchar(50) NOT NULL,
City					varchar(50) NOT NULL, 
Email					varchar(35) NOT NULL, 
Phone					varchar(100) NOT NULL, 
Info					varchar(50) NOT NULL,
Exam					varchar(50) NOT NULL, 
Score					varchar(35) NOT NULL, 
High_school				varchar(100) NOT NULL, 
High_School_Address		varchar(50) NOT NULL,
High_School_Country		varchar(50) NOT NULL
);

create table LinkedU.Students(
UserID			varchar(25) not null,
FirstName       varchar(25) not null,
LastName        varchar(25) not null,
ACT				integer not null,
SAT				integer not null,
PSAT_NMSQT		integer not null,
Universities	varchar(200) not null,
Majors			varchar(200) not null,
Image			varchar(50) not null,
Mixtape			varchar(50) not null,
Essay			varchar(50) not null
);

--Student Accounts
insert into LinkedU.Students(UserID, FirstName, LastName, ACT, SAT, PSAT_NMSQT, Universities, Majors, Image, Mixtape, Essay) values
('pdkaufm', 'Perry','Kaufman', 19, 999, 999, 'University of Illinois;', '', './Resources/default_student.png', 'none', 'none');
insert into LinkedU.Students(UserID, FirstName, LastName,  ACT, SAT, PSAT_NMSQT, Universities, Majors, Image, Mixtape, Essay) values
('kssuth1','Keegan','Sutherland', 36, 1337, 1337, 'University of Illinois;', '', './Resources/default_student.png', 'none', 'none');
insert into LinkedU.Students(UserID, FirstName, LastName, ACT, SAT, PSAT_NMSQT, Universities, Majors, Image, Mixtape, Essay) values
('stiwar1','Shivangi','Tiwari', 36, 1337, 1337, 'University of Illinois;', '', './Resources/default_student.png', 'none', 'none');
insert into LinkedU.Students(UserID, FirstName, LastName, ACT, SAT, PSAT_NMSQT, Universities, Majors, Image, Mixtape, Essay) values
('asriren','Anusha','Srirenganathan Malarvizhi', 36, 1337, 1337, 'University of Illinois;', '', './Resources/default_student.png', 'none', 'none');

create table LinkedU.Universities(
UserID				varchar(25) not null,
Premium				boolean not null,
Name				varchar(50) not null,
Majors				varchar(500) not null,
Street				varchar(100) not null,
City				varchar(30) not null,
State				varchar(20) not null,
Zip					varchar(15) not null,
Image           	varchar(50) not null
);

insert into LinkedU.Universities(UserID, Premium, Name, Majors, Street, City, State, Zip, Image) values
('blim', TRUE, 'Illinois State University', 'Computer Science;English;Biology;Chemistry;Music;Physics;Psychology;Mathematics', '100 N University St', 'Normal', 'IL', '61761', './Resources/default_university.png');
insert into LinkedU.Universities(UserID, Premium, Name, Majors, Street, City, State, Zip, Image) values
('test', FALSE, 'University of Illinois', 'Dance;Computer Science;English;Biology;Chemistry;Music;Astronomy;Physics;Psychology;Art;Mathematics', '1337 Example St', 'Champaign', 'IL', '61820', './Resources/default_university.png');