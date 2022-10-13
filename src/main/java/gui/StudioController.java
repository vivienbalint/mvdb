package gui;

import db.Studio;
import db.StudioDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.util.Optional;

public class StudioController {

    @FXML
    public TableView<Studio> studioTable;
    public TextField studioName;
    public TextField studioHq;
    public TextField studioYear;

    TableColumn<Studio, String> nameCol;
    TableColumn<Studio, String> headquarterCol;
    TableColumn<Studio, Integer> yearCol;

    StudioDAO dao = new StudioDAO();

    public void initialize() {

        nameCol = new TableColumn<>("Studió név");
        headquarterCol = new TableColumn<>("Székhely");
        yearCol = new TableColumn<>("Alapítási év");

        nameCol.setCellValueFactory(new PropertyValueFactory<Studio, String>("studio_name"));
        headquarterCol.setCellValueFactory(new PropertyValueFactory<Studio, String>("headquarter"));
        yearCol.setCellValueFactory(new PropertyValueFactory<Studio, Integer>("year"));

        addToTable();

        studioTable.getColumns().addAll(nameCol, headquarterCol, yearCol);
        updateRecord();

    }

    public void addToTable() {
        ObservableList<Studio> list = dao.listStudios();

        studioTable.setItems(list);
    }

    public void handleSubmitBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (studioName.getText() != null && !studioName.getText().isEmpty() && studioHq.getText() != null && !studioHq.getText().isEmpty() && studioYear != null && !studioYear.getText().isEmpty()) {
            if (studioName.getText().length() <= 100 && studioHq.getText().length() <= 45) {
                if (studioYear.getText().matches("^\\d{4}$")) {
                    int year = Integer.parseInt(studioYear.getText());
                    if (year > 1900 && year <= 2022) {
                        String name = studioName.getText();
                        String hq = studioHq.getText();
                        Studio studio = new Studio(name, hq, year);
                        dao.insertStudio(studio);
                        studioName.setText("");
                        studioHq.setText("");
                        studioYear.setText("");
                        addToTable();

                    } else {
                        error.setContentText("Az alapítási év csak 1901 és 2022 közötti érték lehet!");
                        error.show();
                    }
                } else {
                    error.setContentText("Az alapítási év csak 1901 és 2022 közötti érték lehet!");
                    error.show();
                }
            } else {
                error.setContentText("A studió név maximum 100 karakteres, a székhely maximum 45 karakteres lehet!");
                error.show();
            }

        } else {
            error.setContentText("Nem lehet üres mező!");
            error.show();
        }
    }

    public void handleDeleteBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Studio selectedItem = studioTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Biztos törlöd a kiválasztott rekordot?");
            confirm.setContentText("Studió név: " + selectedItem.getStudio_name() + "\nSzékhely: " + selectedItem.getHeadquarter() + "\nAlapítási év: " + selectedItem.getYear());
            ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Igen");
            confirm.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == yesButton) {
                dao.deleteStudio(selectedItem);
                studioTable.getItems().remove(selectedItem);
            }
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }

    public void updateRecord() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                e -> {
                    Studio currentStudio = e.getTableView().getItems().get(e.getTablePosition().getRow());
                    currentStudio.setStudio_name(e.getNewValue());
                    dao.updateStudio(currentStudio);
                }
        );

        headquarterCol.setCellFactory(TextFieldTableCell.forTableColumn());
        headquarterCol.setOnEditCommit(
                e -> {
                    Studio currentStudio = e.getTableView().getItems().get(e.getTablePosition().getRow());
                    currentStudio.setHeadquarter(e.getNewValue());
                    dao.updateStudio(currentStudio);
                }
        );

        yearCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter(){
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                } catch (IllegalArgumentException er) {
                    error.setHeaderText("A beírt érték nem megfelelő!");
                    error.setContentText("Az elfogadott érték csak egy 1901 és 2022 közötti szám lehet.");
                    error.show();
                    return null;
                }
            }
        }));
        yearCol.setOnEditCommit(
                e -> {
                    Studio currentStudio = e.getTableView().getItems().get(e.getTablePosition().getRow());
                    try {
                        currentStudio.setYear(e.getNewValue());
                        dao.updateStudio(currentStudio);
                    } catch (IllegalArgumentException er) {
                        error.setHeaderText("A beírt érték nem megfelelő!");
                        error.setContentText("Az elfogadott érték csak egy 1901 és 2022 közötti szám lehet.");
                        error.show();
                    }

                }
        );
    }
}
