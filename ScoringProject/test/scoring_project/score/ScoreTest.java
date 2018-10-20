/**
 *
 */
package scoring_project.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

/**
 * Test class for the Score object class
 *
 * @author Courtney Ripoll
 *
 */
public class ScoreTest {

    /**
     * Tests the getters in the Score class to see if they return the correct
     * information
     */
    @Test
    public void testGetters () {
        final Score s1 = new Score( "test.html", 10 );
        final Calendar c = Calendar.getInstance();
        final String d1 = c.get( Calendar.YEAR ) + "-" + ( c.get( Calendar.MONTH ) + 1 ) + "-"
                + c.get( Calendar.DAY_OF_MONTH ) + " " + c.get( Calendar.HOUR_OF_DAY ) + ":" + c.get( Calendar.MINUTE )
                + ":" + c.get( Calendar.SECOND ) + "." + c.get( Calendar.MILLISECOND );
        assertEquals( s1.getFileName(), "test.html" );
        assertEquals( s1.getScore(), 10 );
        assertEquals( s1.getDate(), d1 );
    }

    /**
     * Tests the hashcode and equals methods to see if they compare correctly
     */
    @SuppressWarnings ( "unlikely-arg-type" )
    @Test
    public void testEqualsHashcode () {
        final Score s1 = new Score( "test.html", 10 );
        final Score s2 = new Score( "test1.html", 5 );
        final Score s3 = new Score( "test.html", 10 );
        assertTrue( s1.equals( s3 ) );
        assertTrue( s3.equals( s1 ) );
        assertFalse( s1.equals( s2 ) );
        assertFalse( s2.equals( s3 ) );
        assertFalse( s1.equals( 10 ) );

        assertEquals( s1.hashCode(), s3.hashCode() );
        assertNotEquals( s2.hashCode(), s3.hashCode() );
        assertNotEquals( s2.hashCode(), s1.hashCode() );
    }

}
