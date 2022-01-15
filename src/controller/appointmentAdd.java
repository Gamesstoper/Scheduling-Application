package controller;
import helper.JDBC;
import helper.dateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import model.*;

public class appointmentAdd implements Initializable {
    public TextArea appointmentDescription;
    public TextField appointmentID;
    public TextField appointmentTitle;
    public TextField appointmentLocation;
    public ComboBox appointmentContact;
    public DatePicker appointmentDate;
    public ComboBox customerID;
    public ComboBox userID;
    public ComboBox startTime;
    public ComboBox endTime;
    public Button save;
    public Button cancel;
    public ComboBox typeBox;
    Parent root;
    Stage stage;
    private final ZoneId localZoneID = ZoneId.systemDefault();
    private final ObservableList<String> startTimes = FXCollections.observableArrayList();
    private final ObservableList<String> endTimes = FXCollections.observableArrayList();
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final ZoneId utcZoneID = ZoneId.of("UTC");
    private final ZoneId estZoneID = ZoneId.of("US/Eastern");


    @Override
    /**initialize.
     * This sets all our combo boxes to start the page. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentContact.setItems(functions.fillContact());
            typeBox.setItems(functions.fillTypeList());
            userID.setItems(functions.fillUserId());
            customerID.setItems(functions.fillCustomerId());
            fillStartTimesList();
            appointmentID.setText("Auto Created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**fillStartTimesList.
     * This will fill our Start time and End time combo box. This will also convert the time zone for those to the local time.
     * This also will only allow users to set an appointment during the business hours stated in requirements. */
    private void fillStartTimesList() {

        LocalDate estdate = LocalDate.now();
        LocalTime esttime = LocalTime.of(8, 0, 0);

        do {
            ZonedDateTime est = ZonedDateTime.of(estdate, esttime, estZoneID);
            ZonedDateTime localStart = est.withZoneSameInstant(localZoneID);
            ZonedDateTime localEnd = est.withZoneSameInstant(localZoneID);

            String locStart = localStart.format(timeDTF);
            String locEnd = localEnd.format(timeDTF);

            startTimes.add(locStart);
            endTimes.add(locEnd);
            esttime = esttime.plusMinutes(15);
        } while (!esttime.equals(LocalTime.of(22, 15, 0)));
        startTimes.remove(startTimes.size() - 1);
        endTimes.remove(0);
        startTime.setItems(startTimes);
        endTime.setItems(endTimes);
    }
    /**save.
     * @param event
     * @throws SQLException
     * @throws IOException
     * This will upload all data that was added from the fields and import them into the database. This will convert the time zones
     * to UTC as stated in the requirements. */
    public void save(ActionEvent event) throws SQLException, IOException {
        String description = appointmentDescription.getText();
        String title = appointmentTitle.getText();
        String location = appointmentLocation.getText();
        String contactName = String.valueOf(appointmentContact.getSelectionModel().getSelectedItem());
        int contactID = functions.getContactID(contactName);
        String customer = String.valueOf(customerID.getSelectionModel().getSelectedItem());
        String user = String.valueOf(userID.getSelectionModel().getSelectedItem());
        String type = String.valueOf(typeBox.getSelectionModel().getSelectedItem());
        String startCheck = (String) startTime.getValue();
        String endCheck = (String) endTime.getValue();

        String errorMessage = "";
        if (startCheck == null) {
            errorMessage += "You must select a Start time";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        if (endCheck == null) {
            errorMessage += "You must select an End time.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;

        }
        if (appointmentDate.getValue() == null) {
            errorMessage += "Please Select a Date.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }

        Timestamp nowTS = dateTime.getTimeStamp();

        LocalTime localStartTime = LocalTime.parse((CharSequence) startTime.getSelectionModel().getSelectedItem(), timeDTF);
        LocalTime localEndTime = LocalTime.parse((CharSequence) endTime.getSelectionModel().getSelectedItem(), timeDTF);
        LocalDate localDate = appointmentDate.getValue();

        LocalDateTime startTime = LocalDateTime.of(localDate, localStartTime);
        LocalDateTime endTime = LocalDateTime.of(localDate, localEndTime);

        ZonedDateTime firstStartUTC = startTime.atZone(localZoneID).withZoneSameInstant(utcZoneID);
        ZonedDateTime firstEndUTC = endTime.atZone(localZoneID).withZoneSameInstant(utcZoneID);

        Timestamp sqlStart = Timestamp.valueOf(firstStartUTC.toLocalDateTime());
        Timestamp sqlEnd = Timestamp.valueOf(firstEndUTC.toLocalDateTime());

        if (customer == "null") {
            errorMessage += "Please Select a Customer.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        if (user == "null") {
            errorMessage += "Please Select a User ID.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }

        if (title == null || title.length() == 0) {
            errorMessage += "You must enter an Appointment title.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        if (description == null || description.length() == 0) {
            errorMessage += "You must enter an appointment description.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        if (type == null || type.length() == 0) {
            errorMessage += "You must select an Appointment type.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        if (location == null || location.length() == 0) {
            errorMessage += "You must select an Appointment location.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        if (contactName == "null"){
            errorMessage += "You must select an Appointment contact.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;

        }else if (firstEndUTC.equals(firstStartUTC) || firstEndUTC.isBefore(firstStartUTC)) {
            errorMessage += "End time must be after Start time.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        if (hasConflict(sqlStart, sqlEnd) == true){
            errorMessage += "The Customer Has a conflict with another Appointment. Please reschedule appointment.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(errorMessage);
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }

        else {
                PreparedStatement ps = JDBC.connection.prepareStatement("INSERT INTO client_schedule.appointments " +
                        "(Title,Description,Location,Type,Start,End,Create_Date,Created_By,Last_Update,Last_Updated_By,Customer_ID,User_ID,Contact_ID)" +
                        "VALUES('" + title + "' , '" + description + "' , '" + location + "' , '" + type + "' , '" + sqlStart + "' , '" + sqlEnd +
                        "' , '"+ nowTS + "' , '" + model.user.getUsername() + "' , '"+ nowTS +"' ,'" + model.user.getUsername() + "' , " + customer + " , " + user +
                        " , " + contactID + ");");
                ps.executeUpdate();
                root = FXMLLoader.load(getClass().getResource("/view/appointmentMain.fxml"));
                stage = (Stage) cancel.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Add Complete");
                alert1.setContentText("Appointment: " + title + " has been added.");
                alert1.showAndWait();
        }
    }

     /**hasConflict.
      * @param start
      * @param end
      * @throws SQLException
      * @return
     * This will determine if the appointment has any conflicts with other appointments our customer may have. */
    public boolean hasConflict(Timestamp start, Timestamp end)throws SQLException {
        int appID = -1;
        int customer = 0;

        customer = (int) customerID.getValue();
        PreparedStatement ps = JDBC.connection.prepareStatement(
                "SELECT * FROM client_schedule.appointments\n" +
                        "WHERE ('" + start + "'BETWEEN Start AND End OR '"+end+ "' BETWEEN Start AND End OR '"
                + start + "' < Start AND '"+ end +"'> End)\n" + "AND (Customer_ID = " + customer + " AND Appointment_ID !=" + appID+");");
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            return true;
        }

        return false;
    }

    /**cancel.
     * @param event
     * @throws IOException
     * This will cancel all changes and takes you back to the main appointment page. */
    public void cancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Are you sure you want to cancel this update? Nothing will be saved and you will return to the Customers screen");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            root = FXMLLoader.load(getClass().getResource("/view/appointmentMain.fxml"));
            stage = (Stage) cancel.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
    }

    /**typeBoxHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void typeBoxHandler(ActionEvent event) {
    }

    /**appointmentIDHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void appointmentIDHandler(ActionEvent event) {
    }

    /**appointmentTitleHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void appointmentTitleHandler(ActionEvent event) {
    }

    /**appointmentLocationHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void appointmentLocationHandler(ActionEvent event) {
    }

    /**appointmentContactComboHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void appointmentContactComboHandler(ActionEvent event) {
    }

    /**appointmentDateHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void appointmentDateHandler(ActionEvent event) {
    }

    /**customerIDHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void customerIDHandler(ActionEvent event) {
    }

    /**userIDHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void userIDHandler(ActionEvent event) {
    }

    /**startTimeHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void startTimeHandler(ActionEvent event) {
    }

    /**endTimeHandler.
     * @param event
     * This is here for any future development or improvements but currently not being used */
    public void endTimeHandler(ActionEvent event) {
    }
}


