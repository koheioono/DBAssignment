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
    private TableView<ObservableList> result_table;

    private List<String> columnNames = new ArrayList<>();


    public void DBTest() {
        String conn_url = "jdbc:mysql://localhost:3306/employees?user=root&password=&serverTimezone=UTC";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(conn_url);
            String cmd = sqlCode.getText();

            PreparedStatement stmt = conn.prepareStatement(cmd);

            ResultSet rs = stmt.executeQuery();

            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            result_table.getColumns().clear();

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
                result_table.getColumns().addAll(col);
                columnNames.add(col.getText());
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
            result_table.getItems().clear();
            //FINALLY ADDED TO TableView
            result_table.setItems(data);

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
    }
}
