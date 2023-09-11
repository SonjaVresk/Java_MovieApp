/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Movie;
import hr.algebra.model.Genre;
import hr.algebra.model.Person;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class SqlRepository<T> implements Repository<T> {

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String LINK = "Link";
    private static final String DESCRIPTION = "Description";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String PUBLISHED_DATE = "PublishedDate";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

    private static final String ID_GENRE = "IDGenre";
    private static final String GENRE_NAME = "Name";

    private static final String CREATE_GENRE = "{ CALL createGenre (?,?) }";
    private static final String UPDATE_GENRE = "{ CALL updateGenre (?,?) }";
    private static final String DELETE_GENRE = "{ CALL deleteGenre (?) }";
    private static final String SELECT_GENRE = "{ CALL selectGenre (?) }";
    private static final String SELECT_GENRES = "{ CALL selectGenres }";

    private static final String CREATE_MOVIE_GENRE = "{ CALL createMovieGenre (?,?,?) }";
    private static final String GET_GENRE_NAME = "{ CALL getGenreName(?, ?) }";

    private static final String ID_PERSON = "IDPerson";
    private static final String PERSON_NAME = "Name";
    private static final int ACTOR_ID = 1;
    private static final int DIRECTOR_ID = 2;

    private static final String CREATE_MOVIE_PERSON = "{ CALL createMoviePerson (?,?,?) }";
    private static final String GET_PERSON_NAME = "{ CALL getPersonName(?, ?) }";
    private static final String CREATE_PERSON_JOB = "{ CALL createPersonJob(?,?,?) }";

    private static final String CREATE_ACTOR = "{ CALL createActor (?,?) }";
    private static final String UPDATE_ACTOR = "{ CALL updateActor (?,?) }";
    private static final String DELETE_ACTOR = "{ CALL deleteActor (?) }";
    private static final String SELECT_ACTOR = "{ CALL selectActor (?) }";
    private static final String SELECT_ACTORS = "{ CALL selectActors }";

    private static final String CREATE_DIRECTOR = "{ CALL createDirector (?,?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector  (?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?) }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector  (?) }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors  }";

    private static final String ID_USER = "IDUser";
    private static final String USER_NAME = "Name";
    private static final String PASSWORD = "Password";

    private static final String CREATE_USER = "{ CALL createUser (?,?,?) }";
    private static final String CHECK_USER = "{ CALL checkUser (?,?,?) }";
    private static final String GET_USER_NAME = "{ CALL getUserName (?,?) }";

    private static final String DELETE_ALL = "{ CALL deleteAll }";

    @Override
    public int create(T object) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        int result = -1;

        if (object instanceof Movie) {
            Movie movie = (Movie) object;
            result = createMovie(movie, dataSource);

        } else if (object instanceof Genre) {
            Genre genre = (Genre) object;
            result = createGenre(genre, dataSource);

        } else if (object instanceof Person) {
            Person person = (Person) object;
            result = createPerson(person, dataSource);

        } else if (object instanceof User) {
            User user = (User) object;
            result = createUser(user, dataSource);
        }
        return result;
    }

    @Override
    public void update(int id, T object) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        if (object instanceof Movie) {
            Movie movie = (Movie) object;
            updateMovie(id, movie, dataSource);

        } else if (object instanceof Genre) {
            Genre genre = (Genre) object;
            updateGenre(id, genre, dataSource);

        } else if (object instanceof Person) {
            Person person = (Person) object;
            updatePerson(id, person, dataSource);
        }
    }

    @Override
    public void delete(int id, T object) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        if (object instanceof Movie) {
            deleteMovie(id, dataSource);

        } else if (object instanceof Genre) {
            deleteGenre(id, dataSource);

        } else if (object instanceof Person) {
            Person person = (Person) object;
            deletePerson(id, person, dataSource);
        }
    }

    @Override
    public Optional<T> select(int id, T object) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        Optional<T> result = Optional.empty();

        if (object instanceof Movie) {
            result = selectMovie(id, dataSource);

        } else if (object instanceof Genre) {
            result = selectGenre(id, dataSource);

        } else if (object instanceof Person) {
            Person person = (Person) object;
            result = selectPerson(id, person, dataSource);

        }
        return result;
    }

    @Override
    public List<T> selectAll(T object) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        List<T> result = new ArrayList<>();

        if (object instanceof Movie) {
            result = selectAllMovies(dataSource);

        } else if (object instanceof Genre) {
            result = selectAllGenres(dataSource);

        } else if (object instanceof Person) {
            Person person = (Person) object;
            result = selectAllPersons(person, dataSource);
        } else {
            throw new IllegalArgumentException("Object error");
        }
        return result;
    }

    @Override
    public void deleteAll() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ALL)) {
            stmt.execute();
        }
    }

    @Override
    public void createAll(List<T> objects) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        if (!objects.isEmpty()) {
            T firstObject = objects.get(0); // Provjera koji je tip objekta u listi

            if (firstObject instanceof Movie) {
                try (Connection con = dataSource.getConnection(); 
                        CallableStatement stmt = con.prepareCall(CREATE_MOVIE); 
                        CallableStatement stmtGenre = con.prepareCall(CREATE_GENRE); 
                        CallableStatement stmtGetGenreName = con.prepareCall(GET_GENRE_NAME); 
                        CallableStatement stmtMovieGenre = con.prepareCall(CREATE_MOVIE_GENRE); 
                        CallableStatement stmtPersonJob = con.prepareCall(CREATE_PERSON_JOB); 
                        CallableStatement stmtActor = con.prepareCall(CREATE_ACTOR); 
                        CallableStatement stmtDirector = con.prepareCall(CREATE_DIRECTOR); 
                        CallableStatement stmtGetPersonName = con.prepareCall(GET_PERSON_NAME); 
                        CallableStatement stmtMoviePerson = con.prepareCall(CREATE_MOVIE_PERSON);) {

                    for (T object : objects) {
                        Movie movie = (Movie) object;
                        stmt.setString(TITLE, movie.getTitle());
                        stmt.setString(LINK, movie.getLink());
                        stmt.setString(DESCRIPTION, movie.getDescription());
                        stmt.setString(PICTURE_PATH, movie.getPicturePath());
                        stmt.setString(PUBLISHED_DATE, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
                        stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);
                        stmt.executeUpdate();

                        int movieId = stmt.getInt(ID_MOVIE);

                        // Insert Genre:
                        for (Genre genre : movie.getGenres()) {
                            insertGenre(genre, movieId, stmtGetGenreName, stmtGenre, stmtMovieGenre);
                        }
                        // Insert Actor                        
                        for (Person actor : movie.getActors()) {                            
                            insertActor(actor, movieId, stmtGetPersonName, stmtActor, stmtPersonJob, stmtMoviePerson);
                        }
                        // Insert Director:
                        for (Person director : movie.getDirectors()) {
                            insertDirector(director, movieId, stmtGetPersonName, stmtDirector, stmtPersonJob, stmtMoviePerson);
                        }

                    }
                }
            }
        }
    }

    @Override
    public int checkUser(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        int roleId;

        try (Connection con = dataSource.getConnection(); CallableStatement stmtUser = con.prepareCall(CHECK_USER)) {
            stmtUser.setString(1, username);
            stmtUser.setString(2, password);
            stmtUser.registerOutParameter(3, Types.INTEGER);
            stmtUser.execute();

            roleId = stmtUser.getInt(3);
        }

        return roleId;
    }

    private int createMovie(Movie movie, DataSource dataSource) throws SQLException {

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(TITLE, movie.getTitle());
            stmt.setString(LINK, movie.getLink());
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(PICTURE_PATH, movie.getPicturePath());
            stmt.setString(PUBLISHED_DATE, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_MOVIE);
        }
    }

    private int createGenre(Genre genre, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_GENRE)) {
            stmt.setString(GENRE_NAME, genre.getName());
            stmt.registerOutParameter(ID_GENRE, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_GENRE);
        }
    }

    private int createPerson(Person person, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = createPersonCall(person.getJobID(), con); CallableStatement stmtPersonJob = con.prepareCall(CREATE_PERSON_JOB);) {
            stmt.setString(PERSON_NAME, person.getName());
            stmt.registerOutParameter(ID_PERSON, Types.INTEGER);

            stmt.executeUpdate();
            int personId = stmt.getInt(ID_PERSON);

            stmtPersonJob.setInt(1, personId);
            stmtPersonJob.setInt(2, person.getJobID());
            stmtPersonJob.registerOutParameter(3, Types.INTEGER);
            stmtPersonJob.executeUpdate();

            return personId;
        }
    }

    private CallableStatement createPersonCall(int jobId, Connection con) throws SQLException {
        switch (jobId) {
            case ACTOR_ID:
                return con.prepareCall(CREATE_ACTOR);
            case DIRECTOR_ID:
                return con.prepareCall(CREATE_DIRECTOR);
            default:
                throw new IllegalArgumentException("Invalid jobId: " + jobId);
        }
    }

    private int createUser(User user, DataSource dataSource) throws SQLException {

        int userId = -1;
        try (Connection con = dataSource.getConnection(); CallableStatement stmtUserCheck = con.prepareCall(GET_USER_NAME)) {
            stmtUserCheck.setString(USER_NAME, user.getName());
            stmtUserCheck.registerOutParameter(ID_USER, Types.INTEGER);
            stmtUserCheck.execute();
            if (stmtUserCheck.getInt(2) == 0) {
                try (CallableStatement stmtCreateUser = con.prepareCall(CREATE_USER)) {
                    stmtCreateUser.setString(USER_NAME, user.getName());
                    stmtCreateUser.setString(PASSWORD, user.getPassword());
                    stmtCreateUser.registerOutParameter(ID_USER, Types.INTEGER);
                    stmtCreateUser.executeUpdate();
                    userId = stmtCreateUser.getInt(ID_USER);
                }
            }
        }
        return userId;
    }

    private void updateMovie(int id, Movie movie, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            stmt.setString(TITLE, movie.getTitle());
            stmt.setString(LINK, movie.getLink());
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(PICTURE_PATH, movie.getPicturePath());
            stmt.setString(PUBLISHED_DATE, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setInt(ID_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    private void updateGenre(int id, Genre genre, DataSource dataSource) throws SQLException {

        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_GENRE)) {
            stmt.setString(GENRE_NAME, genre.getName());
            stmt.setInt(ID_GENRE, id);

            stmt.executeUpdate();
        }
    }

    private void updatePerson(int id, Person person, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = updatePersonCall(person.getJobID(), con)) {
            stmt.setString(PERSON_NAME, person.getName());
            stmt.setInt(ID_PERSON, id);

            stmt.executeUpdate();
        }
    }

    private CallableStatement updatePersonCall(int jobId, Connection con) throws SQLException {
        switch (jobId) {
            case ACTOR_ID:
                return con.prepareCall(UPDATE_ACTOR);
            case DIRECTOR_ID:
                return con.prepareCall(UPDATE_DIRECTOR);
            default:
                throw new IllegalArgumentException("Invalid jobId: " + jobId);
        }
    }

    private void deleteMovie(int id, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            stmt.setInt(ID_MOVIE, id);
            stmt.executeUpdate();
        }
    }

    private void deleteGenre(int id, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_GENRE)) {
            stmt.setInt(ID_GENRE, id);
            stmt.executeUpdate();
        }
    }

    private void deletePerson(int id, Person person, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = deletePersonCall(person.getJobID(), con)) {
            stmt.setInt(ID_PERSON, id);
            stmt.executeUpdate();
        }
    }

    private CallableStatement deletePersonCall(int jobId, Connection con) throws SQLException {
        switch (jobId) {
            case ACTOR_ID:
                return con.prepareCall(DELETE_ACTOR);
            case DIRECTOR_ID:
                return con.prepareCall(DELETE_DIRECTOR);
            default:
                throw new IllegalArgumentException("Invalid jobId: " + jobId);
        }
    }

    private Optional<T> selectMovie(int id, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {
            stmt.setInt(ID_MOVIE, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Movie selectedMovie = new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            rs.getString(LINK),
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER));

                    return Optional.of((T) selectedMovie);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<T> selectGenre(int id, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_GENRE)) {
            stmt.setInt(ID_GENRE, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Genre selectedGenre = new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(GENRE_NAME));

                    return Optional.of((T) selectedGenre);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<T> selectPerson(int id, Person person, DataSource dataSource) throws SQLException {
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = selectPersonCall(person.getJobID(), con)) {
            stmt.setInt(ID_PERSON, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Person selectedPerson = new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(PERSON_NAME));

                    return Optional.of((T) selectedPerson);
                }
            }
        }
        return Optional.empty();
    }

    private CallableStatement selectPersonCall(int jobId, Connection con) throws SQLException {

        switch (jobId) {
            case ACTOR_ID:
                return con.prepareCall(SELECT_ACTOR);
            case DIRECTOR_ID:
                return con.prepareCall(SELECT_DIRECTOR);
            default:
                throw new IllegalArgumentException("Invalid jobId: " + jobId);
        }
    }

    private List<T> selectAllMovies(DataSource dataSource) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            rs.getString(LINK),
                            rs.getString(DESCRIPTION),
                            rs.getString(PICTURE_PATH),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER)
                    ));
                }

            }
        }
        return (List<T>) movies;
    }

    private List<T> selectAllGenres(DataSource dataSource) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_GENRES)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genres.add(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(GENRE_NAME)));
                }
            }
        }
        return (List<T>) genres;
    }

    private List<T> selectAllPersons(Person person, DataSource dataSource) throws SQLException {
        List<Person> persons = new ArrayList<>();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = selectAllCall(person.getJobID(), con)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    persons.add(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(PERSON_NAME)));
                }
            }
        }
        return (List<T>) persons;
    }

    private CallableStatement selectAllCall(int jobId, Connection con) throws SQLException {
        switch (jobId) {
            case ACTOR_ID:
                return con.prepareCall(SELECT_ACTORS);
            case DIRECTOR_ID:
                return con.prepareCall(SELECT_DIRECTORS);
            default:
                throw new IllegalArgumentException("Invalid jobId: " + jobId);
        }
    }

    private void insertDirector(Person director, int movieId, CallableStatement stmtGetPersonName, CallableStatement stmtDirector, CallableStatement stmtPersonJob, CallableStatement stmtMoviePerson) throws SQLException {
        int personDirId = 0;
        int jobId = DIRECTOR_ID;

        // Provjera ima li već isti Person:
        stmtGetPersonName.setString(PERSON_NAME, director.getName());
        stmtGetPersonName.registerOutParameter(ID_PERSON, Types.INTEGER);
        stmtGetPersonName.execute();
        personDirId = stmtGetPersonName.getInt(ID_PERSON);

        if (personDirId == 0) {
            stmtDirector.setString(PERSON_NAME, director.getName());
            stmtDirector.registerOutParameter(ID_PERSON, Types.INTEGER);
            stmtDirector.executeUpdate();
            personDirId = stmtDirector.getInt(ID_PERSON);
        }

        stmtPersonJob.setInt(1, personDirId);
        stmtPersonJob.setInt(2, jobId);
        stmtPersonJob.registerOutParameter(3, Types.INTEGER);
        stmtPersonJob.executeUpdate();

        stmtMoviePerson.setInt(1, movieId);
        stmtMoviePerson.setInt(2, personDirId);
        stmtMoviePerson.registerOutParameter(3, Types.INTEGER);
        stmtMoviePerson.executeUpdate();
    }

    private void insertActor(Person actor, int movieId, CallableStatement stmtGetPersonName, CallableStatement stmtActor, CallableStatement stmtPersonJob, CallableStatement stmtMoviePerson) throws SQLException {
        int personId = 0;
        int jobId = ACTOR_ID;

        // Provjera ima li već isti Person:
        stmtGetPersonName.setString(PERSON_NAME, actor.getName());
        stmtGetPersonName.registerOutParameter(ID_PERSON, Types.INTEGER);
        stmtGetPersonName.execute();
        personId = stmtGetPersonName.getInt(ID_PERSON);

        if (personId == 0) {
            stmtActor.setString(PERSON_NAME, actor.getName());
            stmtActor.registerOutParameter(ID_PERSON, Types.INTEGER);
            stmtActor.executeUpdate();
            personId = stmtActor.getInt(ID_PERSON);
        }

        stmtPersonJob.setInt(1, personId);
        stmtPersonJob.setInt(2, jobId);
        stmtPersonJob.registerOutParameter(3, Types.INTEGER);
        stmtPersonJob.executeUpdate();

        stmtMoviePerson.setInt(1, movieId);
        stmtMoviePerson.setInt(2, personId);
        stmtMoviePerson.registerOutParameter(3, Types.INTEGER);
        stmtMoviePerson.executeUpdate();

    }

    private void insertGenre(Genre genre, int movieId, CallableStatement stmtGetGenreName, CallableStatement stmtGenre, CallableStatement stmtMovieGenre) throws SQLException {
        int genreId = 0;

        // Provjera ima li već isti Genre:
        stmtGetGenreName.setString(GENRE_NAME, genre.getName());
        stmtGetGenreName.registerOutParameter(ID_GENRE, Types.INTEGER);
        stmtGetGenreName.execute();
        genreId = stmtGetGenreName.getInt(ID_GENRE);

        // Ako Genre ne postoji stvori novi:
        if (genreId == 0) {
            stmtGenre.setString(GENRE_NAME, genre.getName());
            stmtGenre.registerOutParameter(ID_GENRE, Types.INTEGER);
            stmtGenre.executeUpdate();
            genreId = stmtGenre.getInt(ID_GENRE);
        }

        // Insert u MovieGenre tablicu:
        stmtMovieGenre.setInt(1, movieId);
        stmtMovieGenre.setInt(2, genreId);
        stmtMovieGenre.registerOutParameter(3, Types.INTEGER);
        stmtMovieGenre.executeUpdate();
    }
}
