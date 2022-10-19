package gui;

import db.Director;
import db.DirectorDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.util.Optional;

public class DirectorController {

    @FXML
    public TableView<Director> directorTable;
    public TextField directorLastName;
    public TextField directorFirstName;
    public ComboBox<String> directorSex;

    TableColumn<Director, String> lastNameCol;
    TableColumn<Director, String> firstNameCol;
    TableColumn<Director, Integer> sexCol;

    DirectorDAO dao = new DirectorDAO();

    public void initialize() {

        lastNameCol = new TableColumn<>("Vezetéknév");
        firstNameCol = new TableColumn<>("Keresztnév");
        sexCol = new TableColumn<>("Nem");

        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));

        addToTable();

        directorTable.getColumns().addAll(lastNameCol, firstNameCol, sexCol);
        updateRecord();
    }

    public void addToTable() {
        ObservableList<Director> list = dao.listDirectors();

        directorTable.setItems(list);
    }

    public void handleSubmitBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (directorLastName.getText() != null && !directorLastName.getText().isEmpty() && directorFirstName.getText() != null && !directorFirstName.getText().isEmpty() && !directorSex.getSelectionModel().isEmpty()) {
            if (directorLastName.getText().length() <= 45 && directorFirstName.getText().length() <= 45) {
                int sex;
                if (directorSex.getValue().equals("férfi")) {
                    sex = 1;
                } else sex = 0;
                Director director = new Director(directorLastName.getText(), directorFirstName.getText(), sex);
                dao.insertDirector(director);
                directorLastName.setText("");
                directorFirstName.setText("");
                directorSex.valueProperty().set(null);
                addToTable();

            } else {
                error.setContentText("A vezetéknév és a keresztnév maximum 45 karakteres lehet!");
                error.show();
            }

        } else {
            error.setContentText("Nem lehet üres mező!");
            error.show();
        }
    }

    public void handleDeleteBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Director selectedItem = directorTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Biztos törlöd a kiválasztott rekordot?");
            confirm.setContentText("Név: " + selectedItem.getFirstName() + " " + selectedItem.getLastName() + "\nNem: " + selectedItem.getSex());
            ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Igen");
            confirm.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == yesButton) {
                dao.deleteDirector(selectedItem);
                directorTable.getItems().remove(selectedItem);
            }
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }

    public void updateRecord() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
                e -> {
                    Director currentDirector = e.getTableView().getItems().get(e.getTablePosition().getRow());
                    currentDirector.setLastName(e.getNewValue());
                    dao.updateDirector(currentDirector);
                }
        );

        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
                e -> {
                    Director currentDirector = e.getTableView().getItems().get(e.getTablePosition().getRow());
                    currentDirector.setFirstName(e.getNewValue());
                    dao.updateDirector(currentDirector);
                }
        );

        sexCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                } catch (IllegalArgumentException e) {
                    error.setHeaderText("A beírt érték nem megfelelő!");
                    error.setContentText("Az elfogadott értékek 0 (ha a nem nő), 1 (ha a nem férfi)");
                    error.show();
                    return null;
                }
            }
        }));
        sexCol.setOnEditCommit(
                e -> {
                    Director currentDirector = e.getTableView().getItems().get(e.getTablePosition().getRow());
                    try {
                        currentDirector.setSex(e.getNewValue());
                        dao.updateDirector(currentDirector);

                    } catch (IllegalArgumentException er) {
                        error.setHeaderText("A beírt érték nem megfelelő!");
                        error.setContentText("Az elfogadott értékek 0 (ha a nem nő), 1 (ha a nem férfi)");
                        error.show();
                    }
                }
        );
    }
}
