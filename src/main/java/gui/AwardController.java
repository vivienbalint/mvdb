package gui;

import db.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class AwardController {

    @FXML
    public TextField awardName;
    public TextField awardYear;
    public TextField awardCategory;
    public ComboBox<Movie> awardedMovie;
    public TableView<Award> awardTable;

    TableColumn<Award, String> nameCol;
    TableColumn<Award, Integer> yearCol;
    TableColumn<Award, String> catCol;
    TableColumn<Award, String> movieCol;

    AwardDAO awardDAO = new AwardDAO();
    MovieDAO movieDAO = new MovieDAO();

    Award selectedAward;
    boolean isEdit;

    public void initialize() {
        nameCol = new TableColumn<>("Díjátadó neve");
        yearCol = new TableColumn<>("Átadás éve");
        catCol = new TableColumn<>("Kategória");
        movieCol = new TableColumn<>("Elnyerte");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("awardName"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("awardYear"));
        catCol.setCellValueFactory(new PropertyValueFactory<>("awardCategory"));
        movieCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAwardedMovie().toString()));

        addToTable();

        awardTable.getColumns().addAll(nameCol, yearCol, catCol, movieCol);

        awardedMovie.setItems(movieDAO.listMovies());
    }

    public void addToTable() {
        ObservableList<Award> list = awardDAO.listAwards();

        awardTable.setItems(list);
    }

    public void handleSubmitBtn() {
            if (isValid()) {
               Award award = new Award(awardName.getText(), Integer.parseInt(awardYear.getText()), awardCategory.getText(), awardedMovie.getSelectionModel().getSelectedItem());
                if (!isEdit) {
                    awardDAO.insertAward(award);
                } else {
                    selectedAward.setAwardedMovie(awardedMovie.getSelectionModel().getSelectedItem());
                    isEdit = false;
                    updateAward(selectedAward);
                    awardName.setDisable(false);
                    awardYear.setDisable(false);
                    awardCategory.setDisable(false);
                }
                awardName.setText("");
                awardYear.setText("");
                awardCategory.setText("");
                addToTable();
            }
        }

    public void handleDeleteBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Award selectedItem = awardTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Biztos törlöd a kiválasztott rekordot?");
            confirm.setContentText(selectedItem.toString());
            ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Igen");
            confirm.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == yesButton) {
                awardDAO.deleteAward(selectedItem);
                awardTable.getItems().remove(selectedItem);
            }
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }

    public void handleEditBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        selectedAward = awardTable.getSelectionModel().getSelectedItem();

        if (selectedAward != null) {
            isEdit = true;
            setEditAward(selectedAward);
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }

    private void setEditAward(Award award) {
        awardName.setText(award.getAwardName());
        awardName.setDisable(true);
        awardYear.setText(String.valueOf(award.getAwardYear()));
        awardYear.setDisable(true);
        awardCategory.setText(award.getAwardCategory());
        awardCategory.setDisable(true);
        awardedMovie.getSelectionModel().select(award.getAwardedMovie());
    }

    private void updateAward(Award award) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Biztos frissíted a rekordot?");
        ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType yesButton = new ButtonType("Igen");
        confirm.getButtonTypes().setAll(noButton, yesButton);

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == yesButton) {
            awardDAO.updateAward(award);
        }
    }

    private boolean isValid() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (!awardName.getText().isEmpty() && !awardYear.getText().isEmpty() && !awardCategory.getText().isEmpty() && awardedMovie.getSelectionModel().getSelectedItem() != null) {
            if (awardName.getText().length() <= 45 && awardCategory.getText().length() <= 45) {
                if (awardYear.getText().matches("^\\d{4}$")) {
                    if (Integer.parseInt(awardYear.getText()) >= 1901 && Integer.parseInt(awardYear.getText()) <= 2022) {
                        return true;
                    } else {
                        error.setContentText("A díj átadási éve csak 1901 és 2022 közötti érték lehet!");
                        error.show();
                    }
                }
            } else {
                error.setContentText("A díjátadó neve és a kategória maximum 45 karakteres lehet!");
                error.show();
            }
        } else {
            error.setContentText("Nem lehet üres mező!");
            error.show();
        }
        return false;
    }
}
