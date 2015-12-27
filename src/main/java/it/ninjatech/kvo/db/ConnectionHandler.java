package it.ninjatech.kvo.db;

import it.ninjatech.kvo.util.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.h2.jdbcx.JdbcDataSource;

public class ConnectionHandler {

    private static final Map<String, String> TABLES = new LinkedHashMap<>();

    static {
        StringBuilder tableBuilder;

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_series (")
                .append("    id              UUID        NOT NULL,")
                .append("    path            VARCHAR     NOT NULL,")
                .append("    label           VARCHAR     NOT NULL,")
                .append("    PRIMARY KEY (id)")
                .append(");");
        TABLES.put("tv_series", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie (")
                .append("    id                  UUID            NOT NULL,")
                .append("    tv_series_id        UUID            NOT NULL,")
                .append("    path                VARCHAR         NOT NULL,")
                .append("    provider_id         VARCHAR         NOT NULL,")
                .append("    name                VARCHAR                 ,")
                .append("    language            VARCHAR(2)      NOT NULL,")
                .append("    first_aired         DATE                    ,")
                .append("    content_rating      VARCHAR(5)              ,")
                .append("    network             VARCHAR                 ,")
                .append("    overview            CLOB                    ,")
                .append("    rating              VARCHAR(4)              ,")
                .append("    rating_count        VARCHAR                 ,")
                .append("    status              VARCHAR(10)             ,")
                .append("    banner              VARCHAR                 ,")
                .append("    fanart              VARCHAR                 ,")
                .append("    poster              VARCHAR                 ,")
                .append("    imdb_id             VARCHAR(10)             ,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_fk FOREIGN KEY (tv_series_id) REFERENCES tv_series(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_genre (")
                .append("    id                  IDENTITY        NOT NULL,")
                .append("    tv_serie_id         UUID            NOT NULL,")
                .append("    genre               VARCHAR         NOT NULL,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_genre_fk FOREIGN KEY (tv_serie_id) REFERENCES tv_serie(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_genre", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_actor (")
                .append("    id                  UUID            NOT NULL,")
                .append("    tv_serie_id         UUID            NOT NULL,")
                .append("    name                VARCHAR         NOT NULL,")
                .append("    role                VARCHAR         NOT NULL,")
                .append("    image_path          VARCHAR         NOT NULL,")
                .append("    sort_order          INT(1)          NOT NULL,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_actor_fk FOREIGN KEY (tv_serie_id) REFERENCES tv_serie(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_actor", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_image (")
                .append("id                  UUID            NOT NULL,")
                .append("tv_serie_id         UUID            NOT NULL,")
                .append("provider            VARCHAR(10)     NOT NULL,")
                .append("fanart              VARCHAR(10)     NOT NULL,")
                .append("path                VARCHAR         NOT NULL,")
                .append("rating              VARCHAR(4)              ,")
                .append("rating_count        VARCHAR                 ,")
                .append("language            VARCHAR(2)      NOT NULL,")
                .append("PRIMARY KEY (id),")
                .append("CONSTRAINT tv_serie_image_fk FOREIGN KEY (tv_serie_id) REFERENCES tv_serie(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_image", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_season (")
                .append("    id                  UUID            NOT NULL,")
                .append("    tv_serie_id         UUID            NOT NULL,")
                .append("    number              INT(2)          NOT NULL,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_season FOREIGN KEY (tv_serie_id) REFERENCES tv_serie(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_season", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_season_image (")
                .append("    id                  UUID            NOT NULL,")
                .append("    tv_serie_season_id  UUID            NOT NULL,")
                .append("    provider            VARCHAR(10)     NOT NULL,")
                .append("    path                VARCHAR         NOT NULL,")
                .append("    rating              VARCHAR(4)              ,")
                .append("    rating_count        VARCHAR                 ,")
                .append("    language            VARCHAR(2)      NOT NULL,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_season_image_fk FOREIGN KEY (tv_serie_season_id) REFERENCES tv_serie_season(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_season_image", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_episode (")
                .append("    id                  UUID            NOT NULL,")
                .append("    tv_serie_season_id  UUID            NOT NULL,")
                .append("    provider_id         VARCHAR(10)     NOT NULL,")
                .append("    number              INT(4)          NOT NULL,")
                .append("    language            VARCHAR(2)      NOT NULL,")
                .append("    filename            VARCHAR                 ,")
                .append("    dvd_number          DECIMAL(5,2)            ,")
                .append("    name                VARCHAR                 ,")
                .append("    first_aired         DATE                    ,")
                .append("    imdb_id             VARCHAR                 ,")
                .append("    overview            CLOB                    ,")
                .append("    rating              DECIMAL(3,1)            ,")
                .append("    rating_count        INT                     ,")
                .append("    artwork             VARCHAR                 ,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_episode_fk FOREIGN KEY (tv_serie_season_id) REFERENCES tv_serie_season(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_episode", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_episode_subtitle (")
                .append("    id                  IDENTITY    NOT NULL,")
                .append("    tv_serie_episode_id UUID        NOT NULL,")
                .append("    filename            VARCHAR     NOT NULL,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_episode_subtitle FOREIGN KEY (tv_serie_episode_id) REFERENCES tv_serie_episode(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_episode_subtitle", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_episode_director (")
                .append("    id                  IDENTITY    NOT NULL,")
                .append("    tv_serie_episode_id UUID        NOT NULL,")
                .append("    director            VARCHAR     NOT NULL,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_episode_director FOREIGN KEY (tv_serie_episode_id) REFERENCES tv_serie_episode(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_episode_director", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_episode_guest_star (")
                .append("    id                  IDENTITY    NOT NULL,")
                .append("    tv_serie_episode_id UUID        NOT NULL,")
                .append("    guest_star          VARCHAR     NOT NULL,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_episode_guest_star FOREIGN KEY (tv_serie_episode_id) REFERENCES tv_serie_episode(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_episode_guest_star", tableBuilder.toString());

        tableBuilder = new StringBuilder();
        tableBuilder
                .append("CREATE TABLE tv_serie_episode_writer (")
                .append("    id                  IDENTITY    NOT NULL,")
                .append("    tv_serie_episode_id UUID        NOT NULL,")
                .append("    writer              VARCHAR     NOT NULL,")
                .append("    PRIMARY KEY (id),")
                .append("    CONSTRAINT tv_serie_episode_writer FOREIGN KEY (tv_serie_episode_id) REFERENCES tv_serie_episode(id) ON DELETE CASCADE")
                .append(");");
        TABLES.put("tv_serie_episode_writer", tableBuilder.toString());
    }

    private static ConnectionHandler self;

    public static void init() throws Exception {
        if (self == null) {
            self = new ConnectionHandler();
            self.checkAndCreateTables();
        }
    }

    public static void shutdown() {
        if (self != null) {
            try {
// self.dataSource.close();
            }
            catch (Exception e) {
            }
            self = null;
        }
    }

    public static ConnectionHandler getInstance() {
        return self;
    }

    private final JdbcDataSource dataSource;

    private ConnectionHandler() throws SQLException {
        this.dataSource = new JdbcDataSource();

        File db = new File(Utils.getWorkingDirectory(), "data");

        String url = String.format("jdbc:h2:file:%s;AUTO_SERVER=TRUE", db.getAbsolutePath());

        this.dataSource.setUrl(url);
        this.dataSource.setUser("SA");
    }

    public Connection getConnection() throws SQLException {
        Connection result = this.dataSource.getConnection();

        result.setAutoCommit(false);

        return result;
    }

    private boolean existsTable(Connection connection, String tableName) throws Exception {
        boolean result = true;

        try {
            connection.createStatement().execute(String.format("SELECT COUNT(*) FROM %s", tableName));
        }
        catch (SQLException e) {
            if (e.getErrorCode() == 42102) {
                result = false;
            }
            else {
                throw e;
            }
        }

        return result;
    }

    private void checkAndCreateTables() throws Exception {
        Connection connection = null;

        try {
            connection = getConnection();

            for (String table : TABLES.keySet()) {
                if (!existsTable(connection, table)) {
                    connection.createStatement().execute(TABLES.get(table));
                }
            }
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                }
            }
        }
    }

}
