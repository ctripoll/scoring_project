/**
 *
 */
package scoring_project.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import scoring_project.score.Score;

/**
 * Test class for the HTML Reader
 *
 * @author Courtney Ripoll
 *
 */
public class HTMLReaderTest {

    /** Test files */
    private final String validTestFile1 = "data/test.html";
    private final String validTestFile2 = "data/poorformat_test.html";
    private final String validTestFile3 = "data/uppercase-test.html";

    /**
     * Tests to see if the HTML reader reads in files accurately
     */
    @Test
    public void test () {
        final Score test1 = HTMLReader.readHTML( validTestFile1 );
        final Score test2 = HTMLReader.readHTML( validTestFile2 );
        final Score test3 = HTMLReader.readHTML( validTestFile3 );
        assertEquals( test1.getScore(), 13 );
        assertEquals( test1.getFileName(), "test.html" );
        assertEquals( test2.getScore(), 13 );
        assertEquals( test2.getFileName(), "poorformat_test.html" );
        assertEquals( test3.getScore(), 13 );
        assertEquals( test3.getFileName(), "uppercase-test.html" );
    }

}
