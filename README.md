#SIMPLE BANKING MANAGER
------------------
## FRONT GUI use the simple java GUI , and backend connect to mySQL by using the JDBC
####Pre-requirement for installation
- MyEclipse2014 or later
- MysqlV5.5 or later, you have to setup the all the proper table in order to practise the functionality
- win7-64 or later
------------
####FEATURES
> FUNCTION is listed as followed

###USER MENUS
- OPEN ACCOUNT
- DEPOSIT MONEY
- WITHDRAW MONEY
- TRANSFER MONEY
- INQUIRY ACCOUNT
- CHANGE PASSWORD
- CLOSE ACCOUNT


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
####Manuals for using the mysql
- open a command console, and invoke the MySQL CONSOLE by using :
  mysql -u root -p -h 127.0.0.1  (enter)
  password:  (enter)
- show databases;
- use myfirstsqldb;  (put the database myfirstsqldb in use)
- show tables;  (list all the existing tables)
- create table TABLE_NAME (id int(10) primary key not null,name varchar(20) not null);
- desc account;  (list the detail column information for table account)
- select * from account;(list all the detail row information for table account)
- insert into account (id, name, amount, ...) values (value1, value2, value3, ...);
- delete from Customers WHERE CustomerName='Alfreds Futterkiste';
