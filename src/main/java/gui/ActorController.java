package gui;

import db.Actor;
import db.ActorDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import java.sql.Date;
import java.util.Optional;

public class ActorController {

    @FXML
    public TableView<Actor> actorTable;
    public TextField actorLastName;
    public TextField actorFirstName;
    public DatePicker actorDateOfBirth;
    public ComboBox<String> actorSex;

    TableColumn<Actor, String> lastNameCol;
    TableColumn<Actor, String> firstNameCol;
    TableColumn<Actor, String> dateCol;
    TableColumn<Actor, Integer> sexCol;

    ActorDAO dao = new ActorDAO();

    public void initialize() {

        lastNameCol = new TableColumn<>("Vezetéknév");
        firstNameCol = new TableColumn<>("Keresztnév");
        dateCol = new TableColumn<>("Születési idő");
        sexCol = new TableColumn<>("Nem");

        lastNameCol.setCellValueFactory(new PropertyValueFactory<Actor, String>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Actor, String>("firstName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Actor, String>("dateOfBirth"));
        sexCol.setCellValueFactory(new PropertyValueFactory<Actor, Integer>("sex"));

        addToTable();

        actorTable.getColumns().addAll(lastNameCol, firstNameCol, dateCol, sexCol);
//        updateRecord();

    }

    public void addToTable() {
        ObservableList<Actor> list = dao.listActors();

        actorTable.setItems(list);
    }

    public void handleSubmitBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (actorLastName.getText() != null && !actorLastName.getText().isEmpty() && actorFirstName.getText() != null && !actorFirstName.getText().isEmpty() && actorDateOfBirth.getValue() != null && !actorSex.getSelectionModel().isEmpty()) {
            if (actorLastName.getText().length() <= 45 && actorFirstName.getText().length() <= 45) {
                        int sex;
                        if(actorSex.getValue().equals("férfi")) {
                            sex = 1;
                        } else sex = 0;
                        Actor actor = new Actor(actorFirstName.getText(), actorLastName.getText(), Date.valueOf(actorDateOfBirth.getValue()), sex);
                        dao.insertActor(actor);
                        actorLastName.setText("");
                        actorFirstName.setText("");
                        actorDateOfBirth.getEditor().clear();
                        actorSex.valueProperty().set(null);
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
        Actor selectedItem = actorTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Biztos törlöd a kiválasztott rekordot?");
            confirm.setContentText("Név: " + selectedItem.getFirstName() + " " + selectedItem.getLastName() + "\nSzületési idő: " + selectedItem.getDateOfBirth() + "\nNem: " + selectedItem.getSex());
            ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Igen");
            confirm.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == yesButton) {
                dao.deleteActor(selectedItem);
                actorTable.getItems().remove(selectedItem);
            }
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }

    public void updateRecord() {
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
                e -> {
                    Actor currentActor = e.getTableView().getItems().get(e.getTablePosition().getRow());
                    currentActor.setLastName(e.getNewValue());
                    dao.updateActor(currentActor);
                }
        );

        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
                e -> {
                    Actor currentActor = e.getTableView().getItems().get(e.getTablePosition().getRow());
                    currentActor.setFirstName(e.getNewValue());
                    dao.updateActor(currentActor);
                }
        );

        //TODO: format String and convert to Date

        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setOnEditCommit(
                e -> {
                    Actor currentActor = e.getTableView().getItems().get(e.getTablePosition().getRow());
                }
        );
    }
}
