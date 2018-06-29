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

	/**
	 * Reads in an HTML file and parses it. Calculates a score for the read-in
	 * file.
	 * 
	 * @param fileName
	 *            file to parse
	 * @return score for the file
	 */
	public static Score readHTML(String fileName) {
		int score = 0;
		try (Scanner read = new Scanner(new FileInputStream(fileName), "UTF8")) {
			while (read.hasNextLine()) {
				Scanner line = new Scanner(read.nextLine());
				line.useDelimiter(">");
				while (line.hasNext()) {
					String tag = line.next().trim();
					if (tag.contains("/")) {
						continue;
					} else if (tag.contains(" ")) {
						tag = tag.substring(1, tag.indexOf(" "));
					} else {
						tag = tag.substring(1, tag.length());
					}
					score += ScoreReturn.scorer(tag.toLowerCase());
				}
				line.close();
			}
			read.close();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
		return new Score(fileName.substring(fileName.lastIndexOf("/") + 1), score);
	}
}
