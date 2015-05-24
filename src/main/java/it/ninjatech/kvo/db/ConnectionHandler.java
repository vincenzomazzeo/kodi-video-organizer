package it.ninjatech.kvo.db;

import it.ninjatech.kvo.utils.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

public class ConnectionHandler {

	private static ConnectionHandler self;
	
	public static void init() throws SQLException {
		if (self == null) {
			self = new ConnectionHandler();
		}
	}
	
	public static void shutdown() {
		if (self != null) {
			try {
				self.connection.close();
			}
			catch (Exception e) {}
			self = null;
		}
	}
	
	public static ConnectionHandler getInstance() {
		return self;
	}
	
	private final JdbcDataSource dataSource;
	private final Connection connection;
	
	private ConnectionHandler() throws SQLException {
		this.dataSource = new JdbcDataSource();
		
		File db = new File(Utils.getWorkingDirectory(), "data");
		
		String url = String.format("jdbc:h2:file:%s;AUTO_SERVER=TRUE", db.getAbsolutePath());
		
		this.dataSource.setUrl(url);
		
		this.connection = this.dataSource.getConnection();
	}

	public Connection getConnection() {
		return this.connection;
	}
	
}
