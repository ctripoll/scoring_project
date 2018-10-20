/**
 *
 */
package scoring_project.score;

import java.util.Calendar;

/**
 * Object class that creates a score object. Stores a file name, a score, and a
 * create date/time to associate a score with a specified file name.
 *
 * @author Courtney Ripoll
 *
 */
public class Score {
    /** File name associated with each generated score */
    private String fileName;
    /** Generated score */
    private int    score;
    /** Time generated */
    private String date;

    /**
     * Constructs a score object with a file name and a score
     * 
     * @param fileName
     *            specified file name
     * @param score
     *            generated score for a file
     */
    public Score ( String fileName, int score ) {
        setFileName( fileName );
        setScore( score );
        setDate();
    }

    /**
     * Returns the file name
     * 
     * @return file name
     */
    public String getFileName () {
        return fileName;
    }

    /**
     * Sets the file name
     * 
     * @param fileName
     *            specified file name
     */
    private void setFileName ( String fileName ) {
        this.fileName = fileName;
    }

    /**
     * Returns the score
     * 
     * @return score
     */
    public int getScore () {
        return score;
    }

    /**
     * Sets the score
     * 
     * @param score
     *            generated score
     */
    private void setScore ( int score ) {
        this.score = score;
    }

    /**
     * Returns the date the score was generated
     * 
     * @return current date
     */
    public String getDate () {
        return date;
    }

    /**
     * Sets the date to current date and time
     * 
     * @param date
     */
    private void setDate () {
        final Calendar c = Calendar.getInstance();
        final String d = c.get( Calendar.YEAR ) + "-" + ( c.get( Calendar.MONTH ) + 1 ) + "-"
                + c.get( Calendar.DAY_OF_MONTH ) + " " + c.get( Calendar.HOUR_OF_DAY ) + ":" + c.get( Calendar.MINUTE )
                + ":" + c.get( Calendar.SECOND ) + "." + c.get( Calendar.MILLISECOND );
        this.date = d;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( fileName == null ) ? 0 : fileName.hashCode() );
        result = prime * result + score;
        return result;
    }

    @Override
    public boolean equals ( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Score other = (Score) obj;
        if ( fileName == null ) {
            if ( other.fileName != null ) {
                return false;
            }
        }
        else if ( !fileName.equals( other.fileName ) ) {
            return false;
        }
        if ( score != other.score ) {
            return false;
        }
        return true;
    }
}
