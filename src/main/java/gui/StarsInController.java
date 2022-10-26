package gui;

import db.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.Optional;

class StarsInController {

    TableColumn<StarsIn, String> starsInTitle;
    TableColumn<StarsIn, String> actorName;

    StarsInDAO dao = new StarsInDAO();

    public void initTable(TableView<StarsIn> table) {
        starsInTitle = new TableColumn<>("Film");
        actorName = new TableColumn<>("Színész");

        starsInTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovie().toString()));
        actorName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getActor().toString()));

        addToTable(table);

        table.getColumns().addAll(starsInTitle, actorName);
    }

    private void addToTable(TableView<StarsIn> table) {
        ObservableList<StarsIn> list = dao.listStarsIn();

        table.setItems(list);
    }

    public void handleStarsInSubmit(TableView<StarsIn> table, ListView<Actor> selected, Movie movie) {
        ObservableList<Actor> actors = selected.getItems();
        for (Actor actor : actors) {
            StarsIn starsIn = new StarsIn(actor, movie);
            dao.insertStarsIn(starsIn);
            addToTable(table);
        }
    }

    public void handleDeleteStarsInBtn(TableView<StarsIn> table) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        StarsIn selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Biztos törlöd a kiválasztott rekordot?");
            confirm.setContentText("Film: " + selectedItem.getMovie().toString() + "\nSzínész: " + selectedItem.getActor().toString());
            ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Igen");
            confirm.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == yesButton) {
                dao.deleteStarsIn(selectedItem);
                table.getItems().remove(selectedItem);
            }
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }
}
