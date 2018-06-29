/**
 * 
 */
package scoring_project.io;

import static org.junit.Assert.*;

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
	private final String validTestFile1 = "test-files/test.html";
	private final String validTestFile2 = "test-files/poorformat_test.html";
	private final String validTestFile3 = "test-files/uppercase-test.html";
	
	/**
	 * Tests to see if the HTML reader reads in files accurately
	 */
	@Test
	public void test() {
		Score test1 = HTMLReader.readHTML(validTestFile1);
		Score test2 = HTMLReader.readHTML(validTestFile2);
		Score test3 = HTMLReader.readHTML(validTestFile3);
		assertEquals(test1.getScore(), 13);
		assertEquals(test1.getFileName(), "test.html");
		assertEquals(test2.getScore(), 13);
		assertEquals(test2.getFileName(), "poorformat_test.html");
		assertEquals(test3.getScore(), 13);
		assertEquals(test3.getFileName(), "uppercase-test.html");
	}

}
