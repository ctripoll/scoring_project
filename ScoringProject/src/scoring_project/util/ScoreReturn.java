/**
 * 
 */
package scoring_project.util;

/**
 * Returns a score for a given tag and returns it. Score is based on a preset
 * set of rules.
 * 
 * @author Courtney Ripoll
 *
 */
public class ScoreReturn {
	/** Array of scores lined up with each tag */
	private static int[] scores = { 3, 1, 3, 2, 5, 5, 10, 10, -1, -2, -2, -1, -2, -5, -5 };
	/** Array of possible tags to score */
	private static String[] tags = { "div", "p", "h1", "h2", "html", "body", "header", "footer", "font", "center",
			"big", "strike", "tt", "frameset", "frame" };

	/**
	 * Takes in a tag as a String and returns a score for that tag
	 * 
	 * @param tag
	 *            tag to give a score
	 * @return score for the given tag
	 */
	public static int scorer(String tag) {
		int idx = contains(tag);
		if (idx != -1) {
			return scores[idx];
		} else {
			return 0;
		}
	}

	/**
	 * Checks if a tag is a scorable tag. If is it, it returns the index of the
	 * tag in reference to the tags array
	 * 
	 * @param tag
	 *            tag to check
	 * @return index of the tag in the array, -1 if it is not in the array
	 */
	private static int contains(String tag) {
		for (int i = 0; i < tags.length; i++) {
			if (tags[i].equals(tag)) {
				return i;
			}
		}
		return -1;
	}
}
