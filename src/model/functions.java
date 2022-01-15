package model;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class functions {
    private static final ObservableList<String> countryOptions = FXCollections.observableArrayList();
    private static final ObservableList<String> contactIdList = FXCollections.observableArrayList();
    private static final ObservableList<Integer> userList = FXCollections.observableArrayList();
    private static final ObservableList<Integer> customerList = FXCollections.observableArrayList();
    private static final DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId localZoneID = ZoneId.systemDefault();
    private static final ZoneId utcZoneID = ZoneId.of("UTC");


    /**utcToLocal.
     * @param utcTime
     * @return
     * This will convert UTC time to Local time*/
    public static String utcToLocal(LocalDateTime utcTime) {
        ZonedDateTime localTime = utcTime.atZone(utcZoneID).withZoneSameInstant(localZoneID);
        String localTimeString = localTime.format(datetimeDTF);
        return localTimeString;

    }

    /**utcToLocal.
     * @param localTime
     * @return
     * This will convert Local time to UTC time*/
    public static String localToUTC(LocalDateTime localTime) {
        ZonedDateTime utcTime = localTime.atZone(utcZoneID).withZoneSameInstant(localZoneID);
        String utcTimeString = utcTime.format(datetimeDTF);
        return utcTimeString;
    }

    /**fillContact.
     * @return
     * This will create a list of all Contact Names*/
    public static ObservableList<String> fillContact() throws SQLException {
        contactIdList.clear();
        String contactName = "";
        Statement statement = JDBC.connection.createStatement();
        String sqlStatement = "Select Contact_Name from client_schedule.contacts";
        ResultSet rs = statement.executeQuery(sqlStatement);
        while (rs.next()) {
            contactName = rs.getString("Contact_Name");
            contactIdList.add(contactName);
        }
        return contactIdList;
    }

    /**fillTypeList.
     * @return
     * This will create a list of all appointment types*/
    public static ObservableList<String> fillTypeList() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.clear();
        typeList.addAll("Planning Session", "De-Briefing", "Personal", "Close Account");
        return typeList;
    }

    /**fillUserId.
     * @throws SQLException
     * @return
     * This will create a list of all user ids. */
    public static SortedList<Integer> fillUserId() throws SQLException {
        userList.clear();
        int nextUser = 0;
        Statement statement = JDBC.connection.createStatement();
        String sqlStatement = "Select User_ID from client_schedule.users";

        ResultSet rs = statement.executeQuery(sqlStatement);
        while (rs.next()) {
            nextUser = rs.getInt("User_ID");
            userList.add(nextUser);
        }
        return userList.sorted();
    }

    /**fillCustomerId.
     * @throws SQLException
     * @return
     * This will create a list of all customer ids. */
    public static SortedList<Integer> fillCustomerId() throws SQLException {
        customerList.clear();
        int nextCustomer = 0;
        Statement statement = JDBC.connection.createStatement();
        String sqlStatement = "Select Customer_ID from client_schedule.customers";

        ResultSet rs = statement.executeQuery(sqlStatement);
        while (rs.next()) {
            nextCustomer = rs.getInt("Customer_ID");
            customerList.add(nextCustomer);
        }
        return customerList.sorted();
    }

    /**getContactID.
     * @param contactName
     * @throws SQLException
     * @return
     * This will get the contact ID from a contact Name. */
    public static int getContactID(String contactName) throws SQLException {
        int contact = 0;
        Statement statement = JDBC.connection.createStatement();
        String sqlStatement = "Select Contact_ID from client_schedule.contacts WHERE Contact_Name= '"
                +contactName +"'";
        ResultSet rs = statement.executeQuery(sqlStatement);
        while (rs.next()) {
            contact = rs.getInt("Contact_ID");
        }
        return contact;
    }

    /**getContact.
     * @param contactID
     * @throws SQLException
     * @return
     * This will get the contact Name from a contact ID. */
    public static String getContact(int contactID) throws SQLException {
        String contactName="";
        Statement statement = JDBC.connection.createStatement();
        String sqlStatement = "Select Contact_Name FROM client_schedule.contacts WHERE Contact_ID =" + contactID;
        ResultSet result = statement.executeQuery(sqlStatement);
        while (result.next()){
            contactName = result.getString("Contact_Name");
        }

        return contactName;
    }

    /**FillCountryCombo.
     * @throws SQLException
     * @return
     * This will create a list of all customer ids. */
    public static ObservableList<String> FillCountryCombo() throws SQLException {
        Statement statement = JDBC.connection.createStatement();

        String sqlStatement = "Select Country from client_schedule.countries";

        ResultSet rs = statement.executeQuery(sqlStatement);
        while (rs.next()) {
            customer cus = new customer();
            cus.setCountry(rs.getString("Country"));
            countryOptions.add(cus.getCountry());
        }
        return countryOptions;
    }
}