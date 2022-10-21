package gui;

import db.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.Optional;

public class GenreController {
    TableColumn<Genre, String> movieTitle;
    TableColumn<Genre, String> genreName;

    GenreDAO dao = new GenreDAO();

    public void initTable(TableView<Genre> table) {
        movieTitle = new TableColumn<>("Film");
        genreName = new TableColumn<>("Műfaj");

        movieTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovie().toString()));
        genreName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenreName()));

        addToTable(table);

        table.getColumns().addAll(movieTitle, genreName);
    }

    private void addToTable(TableView<Genre> table) {
        ObservableList<Genre> list = dao.listGenre();

        table.setItems(list);
    }

    public void handleGenreSubmit(TableView<Genre> table, String genreName, Movie movie) {
            Genre genre = new Genre(genreName, movie);
            dao.insertGenre(genre);
            addToTable(table);
    }

    public void handleDeleteGenreBtn(TableView<Genre> table) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Genre selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Biztos törlöd a kiválasztott rekordot?");
            confirm.setContentText("Film: " + selectedItem.getMovie().toString() + "\nMűfaj " + selectedItem.getGenreName());
            ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Igen");
            confirm.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == yesButton) {
                dao.deleteGenre(selectedItem);
                table.getItems().remove(selectedItem);
            }
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }
}
