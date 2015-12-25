package it.ninjatech.kvo.db;

import it.ninjatech.kvo.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.h2.jdbcx.JdbcDataSource;

public class ConnectionHandler {

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

    private void checkAndCreateTables() throws Exception {
        Connection connection = null;

        try {
            connection = getConnection();

            List<String> scripts = new ArrayList<>();
            
            File source = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            if (source.isFile()) {
                JarFile jar = new JarFile(source);
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (name.startsWith("db_script/") && name.endsWith(".sql")) {
                        scripts.add(name);
                    }
                }
                jar.close();
            }
            else {
                URL url = ConnectionHandler.class.getResource("/db_script");
                if (url != null) {
                    try {
                        File directory = new File(url.toURI());
                        for (File file : directory.listFiles()) {
                            scripts.add(file.getAbsolutePath());
                        }
                    }
                    catch (URISyntaxException ex) {
                        // never happens
                    }
                }
            }

            for (String script : scripts) {
                String tableName = script.substring(script.indexOf('.') + 1, script.indexOf(".sql"));
                
                boolean exists = true;
                try {
                    connection.createStatement().execute(String.format("SELECT COUNT(*) FROM %s", tableName));
                }
                catch (SQLException e) {
                    if (e.getErrorCode() == 42102) {
                        exists = false;
                    }
                    else {
                        throw e;
                    }
                }

                if (!exists) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(ConnectionHandler.class.getResourceAsStream(script)))) {
                        StringBuilder scriptContent = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            scriptContent.append(line);
                        }
                        connection.createStatement().execute(scriptContent.toString());
                    }
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
