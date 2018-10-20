/**
 *
 */
package scoring_project.scoring_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private final SQLWriter  sql;
    /** Connection to the SQLWriter to write to the database */
    private final Connection con;

    /**
     * Constructs the HTML Scorer program
     *
     * @param password
     *            MySQL root password
     */
    public HTMLScorer ( String password ) {
        sql = new SQLWriter( password );
        con = sql.getConnection();
    }

    /**
     * Calculates the score for a given html file. After calculating the score,
     * it gets added to the MySQL database.
     *
     * @param fileName
     *            file to calculate a score for
     */
    public void calculateScore ( String fileName ) {
        sql.writeToDataBase( HTMLReader.readHTML( fileName ) );
    }

    /**
     * Retrieves all scores in the database for the specified file.
     *
     * @param fileName
     *            file to find scores with
     * @return a table listing all the scores for the file name
     */
    public String retrieveAllScores ( String fileName ) {
        final StringBuilder sb = new StringBuilder();
        sb.append( "\nAll Scores for " + fileName + "\n" );
        constructOutputTableHeader( sb );
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = con.prepareStatement( "SELECT * FROM scoredata WHERE file = ?" );
            // prevents SQL injection vulnerability
            // b/c it correctly uses parameterized queries
            stmt.setString( 1, fileName );
            rs = stmt.executeQuery();
            boolean isFile = false;
            while ( rs.next() ) {
                isFile = true;
                sb.append( String.format( "| %-25s", rs.getString( "datetime" ) ) + " | "
                        + String.format( "%6d", rs.getInt( "score" ) ) + " |\n" );
            }

            if ( !isFile ) {
                return "\n" + fileName + " is not in the database.";
            }
        }
        catch ( final SQLException e ) {
            throw new IllegalArgumentException();
        }
        sb.append( "+---------------------------+--------+" );
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
    public String retrieveAllScoresDateRange ( String fileName, String date1, String date2 ) {
        final StringBuilder sb = new StringBuilder();
        sb.append( "\nAll Scores for " + fileName + "\n" );
        sb.append( "Date Range: " + date1.substring( 0, date1.indexOf( " " ) ) + " & "
                + date2.substring( 0, date2.indexOf( " " ) ) + "\n" );
        constructOutputTableHeader( sb );
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = con.prepareStatement( "SELECT * FROM scoredata WHERE (file = ?) AND (datetime BETWEEN ? AND ?)" );
            // prevents SQL injection vulnerability
            // b/c it correctly uses parameterized queries
            stmt.setString( 1, fileName );
            stmt.setString( 2, date1 );
            stmt.setString( 3, date2 );
            rs = stmt.executeQuery();
            boolean isDaterange = false;
            while ( rs.next() ) {
                isDaterange = true;
                sb.append( String.format( "| %-25s", rs.getString( "datetime" ) ) + " | "
                        + String.format( "%6d", rs.getInt( "score" ) ) + " |\n" );
            }

            if ( !isDaterange ) {
                return "\nNo data to provide for " + fileName + " within this date range.";
            }
        }
        catch ( final SQLException e ) {
            e.printStackTrace();
        }
        sb.append( "+---------------------------+--------+" );
        return sb.toString();
    }

    /**
     * Retrieves the highest score for a given file
     *
     * @param fileName
     *            file to find the score with
     * @return highest score for the file name
     */
    public String retrieveHighestScore ( String fileName ) {
        int max = Integer.MIN_VALUE;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = con.prepareStatement( "SELECT MAX(score) AS maxScore FROM scoredata WHERE file = ?" );
            // prevents SQL injection vulnerability
            // b/c it correctly uses parameterized queries
            stmt.setString( 1, fileName );
            rs = stmt.executeQuery();
            if ( rs.next() ) {
                max = rs.getInt( "maxScore" );
            }
        }
        catch ( final SQLException e ) {
            e.printStackTrace();
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
    public String retrieveLowestScore ( String fileName ) {
        int min = Integer.MAX_VALUE;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = con.prepareStatement( "SELECT MIN(score) AS minScore FROM scoredata WHERE file = ?" );
            // prevents SQL injection vulnerability
            // b/c it correctly uses parameterized queries
            stmt.setString( 1, fileName );
            rs = stmt.executeQuery();
            if ( rs.next() ) {
                min = rs.getInt( "minScore" );
            }
        }
        catch ( final SQLException e ) {
            e.printStackTrace();
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
    public String retrieveAverageScore ( String fileName ) {
        double avg = Double.MIN_NORMAL;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = con.prepareStatement( "SELECT AVG(score) AS avgScore FROM scoredata WHERE file = ?" );
            // prevents SQL injection vulnerability
            // b/c it correctly uses parameterized queries
            stmt.setString( 1, fileName );
            rs = stmt.executeQuery();
            if ( rs.next() ) {
                avg = rs.getInt( "avgScore" );
            }
        }
        catch ( final SQLException e ) {
            e.printStackTrace();
        }
        return "Average Score for " + fileName + ": " + avg;
    }

    /**
     * Closes the connections to the MySQL database
     */
    public void closeSQLConnection () {
        sql.closeConnection();
    }

    /**
     * Constructs the table header for outputting all scores for a given file
     *
     * @param sb
     *            string builder to append to
     * @return built table header string
     */
    private static String constructOutputTableHeader ( StringBuilder sb ) {
        sb.append( "+---------------------------+--------+\n" );
        sb.append( String.format( "| %-25s", "Date & Time Generated" ) + " | Scores |\n" );
        sb.append( "+---------------------------+--------+\n" );
        return sb.toString();
    }
}
