Title: Scheduling Application

Purpose: This application connects with a mySQL database to pull in Scheduling, Contact Management, Customer Management,
and Login information. This application can add and update Customers, and Appointments. This will be able to determine a
user's computer location and be able to translate the login page but will update all time zones for the users and then convert
them back for storage in the database.

Author: Justin Young, jyou111@wgu.edu, Version 1.0, Date: 1/11/2022

IDE: IntelliJ IDEA 2021.3.1, JDK version 11.0.13, JavaFX-SDK-17.0.0.1,

Directions to run program:
1. Unzip package to your downloads folder.
2. After you unzip go in IntelliJ -> Open -> find your Download folder and open the entire folder.
3. After the file is open, Click File -> Project Structure-> Click the + button -> Add the Javafx-SDK lib, then add the mysql connector by repeating the same steps just selecting the mysql folder instead of the Javafx folder.
4. Click on "Edit Configuration" in the top right
6. Select the JDK Version 11 and select the main method in the list it provides.
7. Click the Green Play button, and they program should then open.

I build a report to easily determine the latest appointments that have been added and who added those appointments.
This will allow users to determine who is creating appointments and what the most recent changes are coming from.

Mysql Connector Driver Version: 8.0.27