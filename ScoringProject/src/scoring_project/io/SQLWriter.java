/**
 * 
 */
package scoring_project.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import scoring_project.score.Score;

/**
 * Writes given information to a specific MySQL database for storing the HTML
 * file information. This information includes the file name, the score the file
 * received, and the date the score was generated.
 * 
 * @author Courtney Ripoll
 *
 */
public class SQLWriter {

	/** Connection variable that connects to the database */
	private Connection con;

	/**
	 * Constructs a connection to a MySQL database
	 * 
	 * @param password
	 *            MySQL root password
	 */
	public SQLWriter(String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=" + password);
			if (!databaseExists()) {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("CREATE DATABASE scores;");
				this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/scores", "root", password);
				stmt = con.createStatement();
				stmt.executeUpdate("CREATE TABLE scoredata (file VARCHAR(20), score INT(5), datetime TIMESTAMP);");
			} else {
				this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/scores", "root", password);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Determines if the database scores already exists in the system
	 * 
	 * @return true if the database exists, false otherwise
	 */
	private boolean databaseExists() {
		try {
			ResultSet rs = con.getMetaData().getCatalogs();
			while (rs.next()) {
				if (rs.getString(1).equals("scores")) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
		return false;
	}

	/**
	 * Writes the appropriate information to the MySQL database (writes
	 * filename, score, and creation date)
	 * 
	 * @param score
	 *            the information to add to the database
	 * @return true if the addition was successful, false if it was unsuccessful
	 */
	public boolean writeToDataBase(Score score) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO scoredata VALUES ('" + score.getFileName() + "', " + score.getScore()
					+ ", '" + score.getDate() + "');");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Returns the connection to the MySQL database
	 * 
	 * @return connection to the database
	 */
	public Connection getConnection() {
		return con;
	}

	/**
	 * Closes the connection to the database
	 */
	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
	}
}
