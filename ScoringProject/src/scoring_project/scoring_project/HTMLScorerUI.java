/**
 * 
 */
package scoring_project.scoring_project;

import java.io.File;
import java.util.Scanner;

/**
 * User Interface to use the HTML Scorer and all its functionality (calculate
 * scores, retrieve all scores, retrieve all scores within a date range,
 * retrieve highest/lowest score, or retrieve average score for a specified
 * file). Keeps prompting the user for a command until the user wants to quit.
 * 
 * @author Courtney Ripoll
 *
 */
public class HTMLScorerUI {

	/** The HTML Scorer instance */
	private static HTMLScorer htmlScorer;

	/**
	 * Main method to run the User Interface
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.println("Please enter in your password for your root MySQL account."
				+ " (If there is no password set, type in N/A).");
		System.out.print("Password: ");
		String password = console.next().trim();
		boolean validPass = false;
		do {
			if (password.equals("N/A")) {
				password = "";
			}
			try {
				htmlScorer = new HTMLScorer(password);
				validPass = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid password. Try again.");
				System.out.print("Password: ");
				password = console.next().trim();
			}
		} while (!validPass);
		System.out.println("\nWelcome to the HTML File Scoring Program\n");
		System.out.println("This program reads in valid HTML files and calculates a score based on");
		System.out.println("a set of rules. A score is generated based on the following rules:");
		System.out.println(String.format("   %-17s", "•<div> = 3") + "•<p> = 1\n"
				+ String.format("   %-17s", "•<h1> = 3") + "•<h2> = 2\n" + String.format("   %-17s", "•<html> = 5")
				+ "•<body> = 5\n" + String.format("   %-17s", "•<header> = 10") + "•<footer> = 10\n"
				+ String.format("   %-17s", "•<font> = -1") + "•<center> = -2\n"
				+ String.format("   %-17s", "•<big> = -2") + "•<strike> = -1\n"
				+ String.format("   %-17s", "•<tt> = -2") + "•<frameset> = -5\n   •<frame> = -5");
		System.out.println("After scoring file, the score will be saved in a MySQL database with");
		System.out.println("its file name, score, and a date & time of generation. Under the assumption");
		System.out.println("that there is information in the database, you will be able to retrieve all");
		System.out.println("scores for a file, all scores for a file within a date range, the lowest and");
		System.out.println("highest score for a file, and the average score for a file.");
		System.out.println("\nEnter an option - Calculate a Score (C), Retrieve All Scores (RA),");
		System.out.println("                  Retrieve All Scores within a Date Range (RD),");
		System.out.println("                  Get Highest Score (H), Get Lowest Score (L),");
		System.out.println("                  Get Average Score (A), or Quit the Program (Q)");
		System.out.print("Option: ");
		String option = console.next();
		do {
			if (option.toUpperCase().equals("Q")) {
				System.exit(1);
			}
			if (!option.toUpperCase().equals("C")) {
				System.out.print("\nEnter a file name (only use the file name i.e. file.html): ");
				String file = console.next();
				if (option.toUpperCase().equals("RA")) {
					System.out.println(htmlScorer.retrieveAllScores(file));
				} else if (option.toUpperCase().equals("RD")) {
					System.out.println("Enter in desired date range.");
					System.out.print("Starting date [YYYY-MM-DD]: ");
					String date1 = console.next();
					while (date1.length() < 10 || date1.length() > 10 || date1.charAt(4) != '-'
							|| date1.charAt(7) != '-') {
						System.out.println("Please enter a date in the format YYYY-MM-DD.");
						System.out.print("Starting date [YYYY-MM-DD]: ");
						date1 = console.next();
					}
					System.out.print("Ending date [YYYY-MM-DD]: ");
					String date2 = console.next();
					while (date2.length() < 10 || date2.length() > 10 || date2.charAt(4) != '-'
							|| date2.charAt(7) != '-') {
						System.out.println("Please enter a date in the format YYYY-MM-DD.");
						System.out.print("Ending date [YYYY-MM-DD]: ");
						date2 = console.next();
					}
					System.out.println(htmlScorer.retrieveAllScoresDateRange(file, date1, date2));
				} else if (option.toUpperCase().equals("H")) {
					System.out.println("\n" + htmlScorer.retrieveHighestScore(file));
				} else if (option.toUpperCase().equals("L")) {
					System.out.println("\n" + htmlScorer.retrieveLowestScore(file));
				} else if (option.toUpperCase().equals("A")) {
					System.out.println("\n" + htmlScorer.retrieveAverageScore(file));
				} else {
					System.out.println("Invalid option. Try again.");
					System.out.print("Option: ");
					option = console.next();
					continue;
				}
			} else {
				System.out.print("How many files would you like to submit to the database?: ");
				while (!console.hasNextInt()) {
					System.out.println("Please enter a number.");
					System.out.print("How many files would you like to submit to the database?: ");
					console.next();
				}
				int numOfFiles = console.nextInt();
				System.out.println();
				for (int i = 0; i < numOfFiles; i++) {
					System.out.print("Enter a file name (use the entire file path): ");
					String file = console.next();
					File checker = new File(file);
					while (!checker.exists()) {
						System.out.println("The file you entered does not exist. Try again.");
						System.out.print("Enter a file name (use the entire file path): ");
						file = console.next();
						checker = new File(file);
					}
					htmlScorer.calculateScore(file);
				}
				System.out.println("Your file(s) have been added to the database.");
			}
			System.out.println("\nEnter an option - Calculate a Score (C), Retrieve All Scores (RA),");
			System.out.println("                  Retrieve All Scores within a Date Range (RD),");
			System.out.println("                  Get Highest Score (H), Get Lowest Score (L),");
			System.out.println("                  Get Average Score (A), or Quit the Program (Q)");
			System.out.print("Option: ");
			option = console.next();
		} while (!option.equals("Q"));
		htmlScorer.closeSQLConnection();
		console.close();
	}

}
