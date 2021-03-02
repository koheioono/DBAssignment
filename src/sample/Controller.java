package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML //fx:id="sqlCode"
    private TextArea sqlCode;

    @FXML //fx:id="goBtn"
    private Button goBtn;

    @FXML //fx:id="result_table"
    private TableView result_table;

    private List<String> columnNames = new ArrayList<>();

//    @FXML //fx:id="empNoColumn"
//    private TableColumn<Employee, Integer> empNoColumn;
//
//    @FXML //fx:id="birthDateColumn"
//    private TableColumn<Employee, String> birthDateColumn;
//
//    @FXML //fx:id="firstNameColumn"
//    private TableColumn<Employee, String> firstNameColumn;
//
//    @FXML //fx:id="lastNameColumn"
//    private TableColumn<Employee, String> lastNameColumn;
//
//    @FXML //fx:id="genderColumn"
//    private TableColumn<Employee, String> genderColumn;
//
//    @FXML //fx:id="hireDateColumn"
//    private TableColumn<Employee, String> hireDateColumn;

    public void DBTest() {
        String conn_url = "jdbc:mysql://localhost:3306/employees?user=root&password=&serverTimezone=UTC";
        Connection conn = null;

//        ObservableList<Employee> data = FXCollections.observableArrayList();
        try {
            conn = DriverManager.getConnection(conn_url);
            String cmd = this.sqlCode.getText();

            PreparedStatement stmt = conn.prepareStatement(cmd);

            ResultSet rs = stmt.executeQuery();
            result_table = new TableView<>();

//            while (rs != null && rs.next()) {
//                int empNo = Integer.parseInt(rs.getString(1));
//                String birthDate = rs.getString(2);
//                String firstName = rs.getString(3);
//                String lastName = rs.getString(4);
//                String gender = rs.getString(5);
//                String hireDate = rs.getString(6);
//                System.out.println(empNo + " " + birthDate + " " + firstName + " " + lastName + " " + gender + " " + hireDate);
//
//                Employee empRow = new Employee(empNo, birthDate, firstName, lastName, gender, hireDate);
//                data.add(empRow);
//            }
//            result_table.setItems(data);


            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                    if (param.getValue().get(j) != null) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    } else {
                        return null;
                    }
                });

                this.result_table.getColumns().addAll(col);
                this.columnNames.add(col.getText());
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            this.result_table.setItems(data);

            System.out.println("result");
            System.out.println(result_table);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void buttonPressed(Event evt) throws IOException {
        DBTest();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        empNoColumn = new TableColumn<>();
//        birthDateColumn = new TableColumn<>();
//        firstNameColumn = new TableColumn<>();
//        lastNameColumn = new TableColumn<>();
//        genderColumn = new TableColumn<>();
//        hireDateColumn = new TableColumn<>();
//
//        empNoColumn.setCellValueFactory(new PropertyValueFactory<>("empNoColumn"));
//        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDateColumn"));
//        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstNameColumn"));
//        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastNameColumn"));
//        genderColumn.setCellValueFactory(new PropertyValueFactory<>("genderColumn"));
//        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDateColumn"));

//        String conn_url = "jdbc:mysql://localhost:3306/employees?user=root&password=&serverTimezone=UTC";
//        Connection conn = null;
//        ObservableList<Employee> data = FXCollections.observableArrayList();
//        try {
//            conn = DriverManager.getConnection(conn_url);
//            String cmd = "select * from employees limit 10;";
//
//            PreparedStatement stmt = conn.prepareStatement(cmd);
//            ResultSet rs = stmt.executeQuery();
////            ResultSet rs = conn.createStatement().executeQuery(cmd);
//            result_table = new TableView<>();
//
//            while (rs != null && rs.next()) {
//                int empNo = Integer.parseInt(rs.getString("emp_no"));
//                String birthDate = rs.getString("birth_date");
//                String firstName = rs.getString("first_name");
//                String lastName = rs.getString("last_name");
//                String gender = rs.getString("gender");
//                String hireDate = rs.getString("hire_date");
//                System.out.println(empNo + " " + birthDate + " " + firstName + " " + lastName + " " + gender + " " + hireDate);
//
//                Employee empRow = new Employee(empNo, birthDate, firstName, lastName, gender, hireDate);
//                data.add(empRow);
//            }
//
//            result_table.setItems(data);
//
//            System.out.println("result");
//            System.out.println(result_table);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
