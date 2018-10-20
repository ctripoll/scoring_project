/**
 *
 */
package scoring_project.util;

import java.util.Hashtable;

/**
 * Returns a score for a given tag and returns it. Score is based on a preset
 * set of rules.
 *
 * @author Courtney Ripoll
 *
 */
public class ScoreReturn {
    /** Hashtable of tags and their associated scores */
    Hashtable<String, Integer> scores = new Hashtable<String, Integer>();

    /**
     * Constructs and initializes a hashtable that will be used for retrieving
     * the scores on each individual html tag. The hashtable uses the tags as
     * keys and the scores as values
     */
    public ScoreReturn () {
        scores.put( "div", 3 );
        scores.put( "p", 1 );
        scores.put( "h1", 3 );
        scores.put( "h2", 2 );
        scores.put( "html", 5 );
        scores.put( "body", 5 );
        scores.put( "header", 10 );
        scores.put( "footer", 10 );
        scores.put( "font", -1 );
        scores.put( "center", -2 );
        scores.put( "big", -2 );
        scores.put( "strike", -1 );
        scores.put( "tt", -2 );
        scores.put( "frameset", -5 );
        scores.put( "frame", -5 );
    }

    /**
     * Takes in a tag as a String and returns a score for that tag
     *
     * @param tag
     *            tag to give a score
     * @return score for the given tag
     */
    public int scorer ( String tag ) {
        try {
            return scores.get( tag );
        }
        catch ( final NullPointerException e ) {
            return 0;
        }
    }

}
