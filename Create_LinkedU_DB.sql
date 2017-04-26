drop table LinkedU.LoginInfo;
drop table LinkedU.Users;
drop table LINKEDU.ApplyInfo;
drop table LinkedU.StudentProfile;
create table LinkedU.LoginInfo (
  UserID                    VARCHAR(25) NOT NULL,
  Password                  VARCHAR(50) NOT NULL
); 

--Student Accounts
insert into LinkedU.LoginInfo (UserID, Password) values
('pdkaufm', '40bf696d25dd56ed44c864e05f75d33a4cface91');
insert into LinkedU.LoginInfo (UserID, Password) values
('ksuth1', '40bf696d25dd56ed44c864e05f75d33a4cface91');
insert into LinkedU.LoginInfo (UserID, Password) values
('stiwar1', '40bf696d25dd56ed44c864e05f75d33a4cface91');
insert into LinkedU.LoginInfo (UserID, Password) values
('asriren', '40bf696d25dd56ed44c864e05f75d33a4cface91');

--University Accounts
--to be added

create table LinkedU.Users (
  UserID                    VARCHAR(25) NOT NULL,
  FirstName                 VARCHAR(25) NOT NULL,
  LastName                  VARCHAR(25) NOT NULL,
  Email                     VARCHAR(50) NOT NULL,
  AccountType               VARCHAR(13) NOT NULL,
  SecurityQuestion          VARCHAR(50) NOT NULL,
  SecurityAnswer            VARCHAR(50) NOT NULL
);

--Student Accounts
insert into LinkedU.Users(UserID, FirstName, LastName, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('pdkaufm','Perry','Kaufman','pdkaufm@ilstu.edu','Student','Q?','A');
insert into LinkedU.Users(UserID, FirstName, LastName, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('kssuth1','Keegan','Sutherland','kssuth1@ilstu.edu','Student','Q?','A');
insert into LinkedU.Users(UserID, FirstName, LastName, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('stiwar1','Shivangi','Tiwari','stiwar1@ilstu.edu','Student','Q?','A');
insert into LinkedU.Users(UserID, FirstName, LastName, Email, AccountType, SecurityQuestion, SecurityAnswer) values
('asriren','Anusha','Srirenganathan Malarvizhi','asriren@ilstu.edu','Student','Q?','A');

--University Accounts
--to be added

CREATE TABLE LINKEDU.ApplyInfo(
University varchar(50) NOT NULL,
Major varchar(25) NOT NULL,
FirstName varchar(25) NOT NULL, 
LastName varchar(25) NOT NULL, 
Age varchar(25) NOT NULL, 
Sex varchar(50) NOT NULL, 
Citizenship varchar(35) NOT NULL, 
Street varchar(100) NOT NULL, 
Postal_Code varchar(50) NOT NULL,
City varchar(50) NOT NULL, 
Email varchar(35) NOT NULL, 
Phone varchar(100) NOT NULL, 
Info varchar(50) NOT NULL,
Exam varchar(50) NOT NULL, 
Score varchar(35) NOT NULL, 
High_school varchar(100) NOT NULL, 
High_School_Address varchar(50) NOT NULL,
High_School_Country varchar(50) NOT NULL
);

create table LinkedU.StudentProfile(
UserID			varchar(25) not null,
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
insert into LinkedU.StudentProfile(UserID, ACT, SAT, PSAT_NMSQT, Universities, Majors, Image, Mixtape, Essay) values
('pdkaufm', 19, 999, 999, 'University of Illinois;', '', './Resources/default_student.png', 'none', 'none');
insert into LinkedU.StudentProfile(UserID, ACT, SAT, PSAT_NMSQT, Universities, Majors, Image, Mixtape, Essay) values
('kssuth1', 36, 1337, 1337, 'University of Illinois;', '', './Resources/default_student.png', 'none', 'none');
insert into LinkedU.StudentProfile(UserID, ACT, SAT, PSAT_NMSQT, Universities, Majors, Image, Mixtape, Essay) values
('stiwar1', 36, 1337, 1337, 'University of Illinois;', '', './Resources/default_student.png', 'none', 'none');
insert into LinkedU.StudentProfile(UserID, ACT, SAT, PSAT_NMSQT, Universities, Majors, Image, Mixtape, Essay) values
('asriren', 36, 1337, 1337, 'University of Illinois;', '', './Resources/default_student.png', 'none', 'none');