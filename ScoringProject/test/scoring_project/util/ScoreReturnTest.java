package scoring_project.util;

import static org.junit.Assert.*;

import org.junit.Test;

import scoring_project.util.ScoreReturn;

/**
 * Test class for the ScoreReturn class
 * 
 * @author Courtney Ripoll
 *
 */
public class ScoreReturnTest {

	/**
	 * Tests the ScoreReturn scorer method to see if it returns the correct
	 * score for each possible html opening tag
	 */
	@Test
	public void test() {
		assertEquals(ScoreReturn.scorer("div"), 3);
		assertEquals(ScoreReturn.scorer("p"), 1);
		assertEquals(ScoreReturn.scorer("h1"), 3);
		assertEquals(ScoreReturn.scorer("h2"), 2);
		assertEquals(ScoreReturn.scorer("html"), 5);
		assertEquals(ScoreReturn.scorer("body"), 5);
		assertEquals(ScoreReturn.scorer("header"), 10);
		assertEquals(ScoreReturn.scorer("footer"), 10);
		assertEquals(ScoreReturn.scorer("font"), -1);
		assertEquals(ScoreReturn.scorer("center"), -2);
		assertEquals(ScoreReturn.scorer("big"), -2);
		assertEquals(ScoreReturn.scorer("strike"), -1);
		assertEquals(ScoreReturn.scorer("tt"), -2);
		assertEquals(ScoreReturn.scorer("frameset"), -5);
		assertEquals(ScoreReturn.scorer("frame"), -5);
	}

}
