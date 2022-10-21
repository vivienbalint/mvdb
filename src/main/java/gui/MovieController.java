package gui;

import db.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class MovieController {

    @FXML
    public TableView<Movie> movieTable;
    public TableView<MadeBy> madeByTable;
    public TableView<StarsIn> starsInTable;
    public TableView<Genre> genreTable;
    public TextField title;
    public TextField year;
    public TextField length;
    public TextField genreName;
    public ComboBox<Director> movieDirector;
    public ComboBox<Movie> madeByMovies;
    public ComboBox<Movie> starsInMovies;
    public ComboBox<Movie> genreMovies;
    public ListView<Studio> studioListView;
    public ListView<Actor> actorListView;
    public Button madeBySubmitBtn;

    MadeByController madeByController = new MadeByController();
    StarsInController starsInController = new StarsInController();
    GenreController genreController = new GenreController();
    EditController editController = new EditController();
    MovieDAO movieDAO = new MovieDAO();
    DirectorDAO directorDAO = new DirectorDAO();
    StudioDAO studioDAO = new StudioDAO();
    ActorDAO actorDAO = new ActorDAO();

    ListView<Studio> selectedStudios = new ListView<>();
    ListView<Actor> selectedActors = new ListView<>();

    TableColumn<Movie, String> movieTitle;
    TableColumn<Movie, Integer> movieYear;
    TableColumn<Movie, Integer> movieLength;
    TableColumn<Movie, String> directorName;

    boolean isEdit = false;
    Movie selectedMovie;

    public void initialize() {

        movieTitle = new TableColumn<>("Cím");
        movieYear = new TableColumn<>("Premier éve");
        movieLength = new TableColumn<>("Játékidő");
        directorName = new TableColumn<>("Rendezte");

        movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        movieYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        movieLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        directorName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDirector().toString()));

        addToTable();

        movieTable.getColumns().addAll(movieTitle, movieYear, movieLength, directorName);

        movieDirector.setItems(directorDAO.listDirectors());

        madeByMovies.setItems(movieDAO.listMovies());
        madeByController.initTable(madeByTable);

        starsInMovies.setItems(movieDAO.listMovies());
        starsInController.initTable(starsInTable);

        genreMovies.setItems(movieDAO.listMovies());
        genreController.initTable(genreTable);

        handleStudioListView();
        handleActorListView();
    }

    public void addToTable() {
        ObservableList<Movie> list = movieDAO.listMovies();

        movieTable.setItems(list);
    }

    private void handleStudioListView() {
        studioListView.setItems(studioDAO.listStudios());

        studioListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Studio item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? null : item.getStudio_name());
            }
        });
        studioListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        studioListView.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> selectedStudios.setItems(studioListView.getSelectionModel().getSelectedItems()));
    }

    private void handleActorListView() {
        actorListView.setItems(actorDAO.listActors());

        actorListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Actor item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? null : item.toString());
            }
        });
        actorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        actorListView.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> selectedActors.setItems(actorListView.getSelectionModel().getSelectedItems()));

    }

    public void handleMovieSubmitBtn() {
        if (isValid()) {
            Movie movie = new Movie(title.getText(), Integer.parseInt(year.getText()), Integer.parseInt(length.getText()), movieDirector.getValue());
            if (!isEdit) {
                movieDAO.insertMovie(movie);
            } else {
                selectedMovie.setTitle(title.getText());
                selectedMovie.setYear(Integer.parseInt(year.getText()));
                selectedMovie.setLength(Integer.parseInt(length.getText()));
                selectedMovie.setDirector(movieDirector.getValue());
                isEdit = false;
                editController.updateMovie(movieDAO, selectedMovie);
            }
            title.setText("");
            year.setText("");
            length.setText("");
            addToTable();
            madeByMovies.setItems(movieDAO.listMovies());
            starsInMovies.setItems(movieDAO.listMovies());
            genreMovies.setItems(movieDAO.listMovies());
        }
    }

    public void handleMadeBySubmitBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (madeByMovies.getSelectionModel().getSelectedItem() != null) {
            madeByController.handleMadeBySubmit(madeByTable, selectedStudios, madeByMovies.getValue());
            madeByMovies.valueProperty().set(null);
        } else {
            error.setContentText("Nincs film kiválasztva!");
            error.show();
        }
    }

    public void handleStarsInSubmitBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (!starsInMovies.getSelectionModel().isEmpty()) {
            starsInController.handleStarsInSubmit(starsInTable, selectedActors, starsInMovies.getValue());
            starsInMovies.valueProperty().set(null);
        } else {
            error.setContentText("Nincs film kiválasztva");
            error.show();
        }
    }

    public void handleGenreSubmitBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (!genreMovies.getSelectionModel().isEmpty() && genreName.getText() != null && !genreName.getText().isEmpty()) {
            if (genreName.getText().length() <= 25) {
                genreController.handleGenreSubmit(genreTable, genreName.getText(), genreMovies.getValue());
                genreMovies.valueProperty().set(null);
                genreName.clear();
            } else {
                error.setContentText("A műfaj maximum 25 karakter lehet!");
            }
        } else {
            error.setContentText("Nincs film kiválasztva, vagy nincs műfaj megadva!");
            error.show();
        }
    }

    public void handleDeleteMovieBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Movie selectedItem = movieTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Biztos törlöd a kiválasztott rekordot?");
            confirm.setContentText("Film: " + selectedItem.toString());
            ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Igen");
            confirm.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == yesButton) {
                movieDAO.deleteMovie(selectedItem);
                movieTable.getItems().remove(selectedItem);
            }
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }

    public void handleDeleteStudioBtn() {
        madeByController.handleDeleteStudioBtn(madeByTable);
    }

    public void handleDeleteActorBtn() {
        starsInController.handleDeleteStarsInBtn(starsInTable);
    }

    public void handleDeleteGenreBtn() {
        genreController.handleDeleteGenreBtn(genreTable);
    }

    public void handleEditMovieBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        selectedMovie = movieTable.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            isEdit = true;
            editController.setEditMovie(selectedMovie, title, year, length, movieDirector);
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }

    private boolean isValid() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (!title.getText().isEmpty() && !year.getText().isEmpty() && !length.getText().isEmpty() && movieDirector.getSelectionModel().getSelectedItem() != null) {
            if (title.getText().length() <= 45) {
                if (year.getText().matches("^\\d{4}$") && length.getText().matches("^\\d+$")) {
                    int movieYear = Integer.parseInt(year.getText());
                    int movieLength = Integer.parseInt(length.getText());
                    if (movieYear >= 1901 && movieYear <= 2025 && movieLength >= 5 && movieLength <= 600) {
                        return true;
                    } else {
                        error.setContentText("A premier éve csak 1901 és 2025 közötti érték lehet, a hossz csak 5 és 600 közötti érték lehet!");
                        error.show();
                    }
                }
            } else {
                error.setContentText("A cím maximum 45 karakteres lehet!");
                error.show();
            }
        } else {
            error.setContentText("Nem lehet üres mező!");
            error.show();
        }
        return false;
    }
}
