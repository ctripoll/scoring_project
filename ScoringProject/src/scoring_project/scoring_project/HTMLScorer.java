/**
 * 
 */
package scoring_project.scoring_project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import scoring_project.io.HTMLReader;
import scoring_project.io.SQLWriter;

/**
 * This class attains the functionality for the program. It calculates scores,
 * retrieves all scores, retrieves all scores within a date range, retrieves
 * highest/lowest score, or retrieves average score for a specified file. It can
 * also close the SQL connection.
 * 
 * @author Courtney Ripoll
 *
 */
public class HTMLScorer {
	/** Instance of an SQL Writer to write to MySQL */
	private SQLWriter sql;
	/** Flag to determine if data base is empty */
	private boolean flag = false;

	/**
	 * Constructs the HTML Scorer program
	 * 
	 * @param password
	 *            MySQL root password
	 */
	public HTMLScorer(String password) {
		sql = new SQLWriter(password);
	}

	/**
	 * Calculates the score for a given html file. After calculating the score,
	 * it gets added to the MySQL database.
	 * 
	 * @param fileName
	 *            file to calculate a score for
	 */
	public void calculateScore(String fileName) {
		sql.writeToDataBase(HTMLReader.readHTML(fileName));
	}

	/**
	 * Retrieves all scores in the database for the specified file.
	 * 
	 * @param fileName
	 *            file to find scores with
	 * @return a table listing all the scores for the file name
	 */
	public String retrieveAllScores(String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nAll Scores for " + fileName + "\n");
		sb.append("+---------------------------+--------+\n");
		sb.append(String.format("| %-25s", "Date & Time Generated") + " | Scores |\n");
		sb.append("+---------------------------+--------+\n");
		Connection con = sql.getConnection();
		Statement stmt;
		ResultSet rs;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM scoredata");
			boolean isFile = false;
			while (rs.next()) {
				flag = true;
				if (rs.getString("file").equals(fileName)) {
					isFile = true;
					sb.append(String.format("| %-25s", rs.getString("datetime")) + " | "
							+ String.format("%6d", rs.getInt("score")) + " |\n");
				}
			}
			if (!flag) {
				return "\nEmpty Set.";
			}
			if(!isFile) {
				return "\n" + fileName + " is not in the database.";
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
		sb.append("+---------------------------+--------+");
		return sb.toString();
	}

	/**
	 * Retrieves all scores in the database for the specified file within a date
	 * range.
	 * 
	 * @param fileName
	 *            file to find scores with
	 * @param date1
	 *            starting date
	 * @param date2
	 *            ending date
	 * @return a table listing all the scores for the file name between the
	 *         desired dates
	 */
	public String retrieveAllScoresDateRange(String fileName, String date1, String date2) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nAll Scores for " + fileName + "\n");
		sb.append("Date Range: " + date1 + " & " + date2 + "\n");
		sb.append("+---------------------------+--------+\n");
		sb.append(String.format("| %-25s", "Date & Time Generated") + " | Scores |\n");
		sb.append("+---------------------------+--------+\n");
		Connection con = sql.getConnection();
		Statement stmt;
		ResultSet rs;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM scoredata");
			boolean isFile = false;
			while (rs.next()) {
				flag = true;
				if (rs.getString("file").equals(fileName)
						&& rs.getTimestamp("datetime").toString().compareTo(date1) >= 0
						&& rs.getTimestamp("datetime").toString().substring(0, 10).compareTo(date2) <= 0) {
					isFile = true;
					sb.append(String.format("| %-25s", rs.getString("datetime")) + " | "
							+ String.format("%6d", rs.getInt("score")) + " |\n");
				}
			}
			if (!flag) {
				return "\nEmpty Set.";
			}
			if(!isFile) {
				return "\n" + fileName + " is not in the database.";
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
		sb.append("+---------------------------+--------+");
		return sb.toString();
	}

	/**
	 * Retrieves the highest score for a given file
	 * 
	 * @param fileName
	 *            file to find the score with
	 * @return highest score for the file name
	 */
	public String retrieveHighestScore(String fileName) {
		int max = Integer.MIN_VALUE;
		Connection con = sql.getConnection();
		Statement stmt;
		ResultSet rs;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM scoredata");
			while (rs.next()) {
				flag = true;
				if (rs.getString("file").equals(fileName) && rs.getInt("score") > max) {
					max = rs.getInt("score");
				}
			}
			if (!flag) {
				return "\nEmpty Set.";
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
		if (max == Integer.MIN_VALUE) {
			return fileName + " is not in the database.";
		}
		return "Highest Score for " + fileName + ": " + max;
	}

	/**
	 * Retrieves the lowest score for a given file
	 * 
	 * @param fileName
	 *            file to find the score with
	 * @return lowest score for the file name
	 */
	public String retrieveLowestScore(String fileName) {
		int min = Integer.MAX_VALUE;
		Connection con = sql.getConnection();
		Statement stmt;
		ResultSet rs;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM scoredata");
			while (rs.next()) {
				flag = true;
				if (rs.getString("file").equals(fileName) && rs.getInt("score") < min) {
					min = rs.getInt("score");
				}
			}
			if (!flag) {
				return "\nEmpty Set.";
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
		if (min == Integer.MAX_VALUE) {
			return fileName + " is not in the database.";
		}
		return "Lowest Score for " + fileName + ": " + min;
	}

	/**
	 * Retrieves the average score for a given file
	 * 
	 * @param fileName
	 *            file to find the scores and calculate the average score
	 * @return overall average score for the file name
	 */
	public String retrieveAverageScore(String fileName) {
		int numScores = 0;
		int runningTotal = 0;
		Connection con = sql.getConnection();
		Statement stmt;
		ResultSet rs;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM scoredata");
			while (rs.next()) {
				flag = true;
				if (rs.getString("file").equals(fileName)) {
					runningTotal += rs.getInt("score");
					numScores++;
				}
			}
			if (!flag) {
				return "\nEmpty Set.";
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException();
		}
		if (numScores == 0) {
			return fileName + " is not in the database.";
		}
		return "Average Score for " + fileName + ": " + (runningTotal / numScores);
	}

	/**
	 * Closes the connections to the MySQL database
	 */
	public void closeSQLConnection() {
		sql.closeConnection();
	}
}
