/**
 *
 */
package scoring_project.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import scoring_project.score.Score;
import scoring_project.util.ScoreReturn;

/**
 * Reads in an HTML file and parses each opening tag to get the contents of the
 * tag. Reader ignores closing tags and style elements within an opening tag.
 * After grabbing a tag, the program calculates the score per tag and adds it to
 * an overall score for the file.
 *
 * @author Courtney Ripoll
 *
 */
public class HTMLReader {

    static ScoreReturn sr = new ScoreReturn();

    /**
     * Reads in an HTML file and parses it. Calculates a score for the read-in
     * file.
     *
     * @param fileName
     *            file to parse
     * @return score for the file
     */
    public static Score readHTML ( String fileName ) {
        int score = 0;
        try ( Scanner read = new Scanner( new FileInputStream( fileName ), "UTF8" ) ) {
            while ( read.hasNextLine() ) {
                final Scanner line = new Scanner( read.nextLine() );
                line.useDelimiter( ">" );
                while ( line.hasNext() ) {
                    // accounts for capitalized tags
                    String tag = line.next().toLowerCase().trim();

                    if ( !tag.equals( null ) && !tag.equals( "" ) ) {
                        // skips closing tags not self-closing tags
                        if ( tag.contains( "/" ) && !tag.contains( "/>" ) ) {
                            continue;
                        }
                        // ignores extra attributes in tags such as href
                        else if ( tag.contains( " " ) ) {
                            tag = tag.substring( 1, tag.indexOf( " " ) );
                        }
                        else {
                            tag = tag.substring( 1 );
                        }
                        score += sr.scorer( tag );
                    }
                }
                line.close();
            }
            read.close();
        }
        catch ( final FileNotFoundException e ) {
            e.printStackTrace();
        }
        catch ( final NoSuchElementException e ) {
            e.printStackTrace();
        }
        return new Score( fileName.substring( fileName.lastIndexOf( "/" ) + 1 ), score );
    }
}
