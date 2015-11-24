package it.ninjatech.kvo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public abstract class AbstractDbMapper<T> {

	protected abstract T map(ResultSet resultSet) throws Exception;

	public void save(T entity) throws Exception {
	}
	
	public void delete(T entity) throws Exception {
	}
	
	public void update(T entity) throws Exception {
	}

	public T findOne() throws Exception {
		return null;
	}

	public List<T> find() throws Exception {
		return new ArrayList<>();
	}

	@SafeVarargs
	protected final void write(String statement, Entry<Object, Integer>... parameters) throws Exception {
		Connection connection = null;

		try {
			connection = ConnectionHandler.getInstance().getConnection();

			write(connection, statement, parameters);

			connection.commit();
		}
		catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				}
				catch (Exception e2) {
				}
			}

			throw e;
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

	@SafeVarargs
	protected final void write(Connection connection, String statement, Entry<Object, Integer>... parameters) throws Exception {
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(statement);
			int index = 0;
			for (Entry<Object, Integer> parameter : parameters) {
				Object value = parameter.getKey();
				if (value == null) {
					preparedStatement.setNull(++index, parameter.getValue());
				}
				else {
					preparedStatement.setObject(++index, parameter.getKey(), parameter.getValue());
				}
			}

			preparedStatement.execute();
		}
		finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				}
				catch (SQLException e) {
				}
			}
		}
	}

	@SafeVarargs
	protected final T findOne(String statement, Entry<Object, Integer>... parameters) throws Exception {
		T result = null;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Connection connection = ConnectionHandler.getInstance().getConnection();
		try {
			preparedStatement = connection.prepareStatement(statement);
			int index = 0;
			for (Entry<Object, Integer> parameter : parameters) {
				preparedStatement.setObject(++index, parameter.getKey(), parameter.getValue());
			}

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				result = map(resultSet);
			}
		}
		finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				}
				catch (SQLException e) {
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				}
				catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				}
				catch (SQLException e) {
				}
			}
		}

		return result;
	}

	@SafeVarargs
	protected final List<T> find(String statement, Entry<Object, Integer>... parameters) throws Exception {
		List<T> result = new ArrayList<>();

		Connection connection = ConnectionHandler.getInstance().getConnection();
		try {
		    result = find(connection, statement, parameters);
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

		return result;
	}
	
	@SafeVarargs
    protected final List<T> find(Connection connection, String statement, Entry<Object, Integer>... parameters) throws Exception {
	    List<T> result = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(statement);
            int index = 0;
            for (Entry<Object, Integer> parameter : parameters) {
                preparedStatement.setObject(++index, parameter.getKey(), parameter.getValue());
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(map(resultSet));
            }
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }
                catch (SQLException e) {
                }
            }
        }

        return result;
	}

}
