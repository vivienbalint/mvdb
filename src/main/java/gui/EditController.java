package gui;

import db.*;
import javafx.scene.control.*;

import java.util.Optional;

class EditController {

    public void setEditMovie(Movie movie, TextField title, TextField year, TextField length, ComboBox<Director> director) {
        title.setText(movie.getTitle());
        year.setText(String.valueOf(movie.getYear()));
        length.setText(String.valueOf(movie.getLength()));
        director.getSelectionModel().select(movie.getDirector());
    }

    public void updateMovie(MovieDAO dao, Movie newMovie) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Biztos frissíted a rekordot?");
        ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType yesButton = new ButtonType("Igen");
        confirm.getButtonTypes().setAll(noButton, yesButton);

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == yesButton) {
            dao.updateMovie(newMovie);
        }
    }
}
