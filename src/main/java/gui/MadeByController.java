package gui;

import db.MadeBy;
import db.MadeByDAO;
import db.Movie;
import db.Studio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.Optional;

class MadeByController {

    TableColumn<MadeBy, String> madeByTitle;
    TableColumn<MadeBy, String> madeByStudioName;

    MadeByDAO dao = new MadeByDAO();

    public void initTable(TableView<MadeBy> table) {
        madeByTitle = new TableColumn<>("Film");
        madeByStudioName = new TableColumn<>("Készítette");

        madeByTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovie().toString()));
        madeByStudioName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudio().getStudio_name()));

        addToTable(table);

        table.getColumns().addAll(madeByTitle, madeByStudioName);
    }

    private void addToTable(TableView<MadeBy> table) {
        ObservableList<MadeBy> list = dao.listMadeBy();
        table.setItems(list);
    }

    public void handleMadeBySubmit(TableView<MadeBy> table, ListView<Studio> selected, Movie movie) {
        ObservableList<Studio> studios = selected.getItems();

        for (Studio studio : studios) {
            MadeBy madeBy = new MadeBy(studio, movie);
            dao.insertMadeBy(madeBy);
            addToTable(table);
        }
    }

    public void handleDeleteStudioBtn(TableView<MadeBy> table) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        MadeBy selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Biztos törlöd a kiválasztott rekordot?");
            confirm.setContentText("Film: " + selectedItem.getMovie().toString() + "\nStudió: " + selectedItem.getStudio().getStudio_name());
            ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Igen");
            confirm.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == yesButton) {
                dao.deleteMadeBy(selectedItem);
                table.getItems().remove(selectedItem);
            }
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }
}
