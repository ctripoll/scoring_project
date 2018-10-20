package scoring_project.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for the ScoreReturn class
 *
 * @author Courtney Ripoll
 *
 */
public class ScoreReturnTest {

    ScoreReturn sr = new ScoreReturn();

    /**
     * Tests the ScoreReturn scorer method to see if it returns the correct
     * score for each possible html opening tag
     */
    @Test
    public void test () {
        assertEquals( sr.scorer( "div" ), 3 );
        assertEquals( sr.scorer( "p" ), 1 );
        assertEquals( sr.scorer( "h1" ), 3 );
        assertEquals( sr.scorer( "h2" ), 2 );
        assertEquals( sr.scorer( "html" ), 5 );
        assertEquals( sr.scorer( "body" ), 5 );
        assertEquals( sr.scorer( "header" ), 10 );
        assertEquals( sr.scorer( "footer" ), 10 );
        assertEquals( sr.scorer( "font" ), -1 );
        assertEquals( sr.scorer( "center" ), -2 );
        assertEquals( sr.scorer( "big" ), -2 );
        assertEquals( sr.scorer( "strike" ), -1 );
        assertEquals( sr.scorer( "tt" ), -2 );
        assertEquals( sr.scorer( "frameset" ), -5 );
        assertEquals( sr.scorer( "frame" ), -5 );
    }

}
