package it.ninjatech.kvo.db;

import it.ninjatech.kvo.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

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
//				self.dataSource.close();
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
			
			File directory = new File(ConnectionHandler.class.getResource("/db_script").toURI());
			for (File file : directory.listFiles()) {
				String tableName = file.getName().substring(file.getName().indexOf('.') + 1, file.getName().indexOf(".sql"));

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
					try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
						StringBuilder script = new StringBuilder();
						String line;
						while ((line = reader.readLine()) != null) {
							script.append(line);
						}
						connection.createStatement().execute(script.toString());
					}
				}
			}
		}
		finally {
			if (connection != null) {
				try {
					connection.close();
				}
				catch (SQLException e) {}
			}
		}
	}

}
