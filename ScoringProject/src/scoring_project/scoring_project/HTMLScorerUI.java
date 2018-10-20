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
    public static void main ( String[] args ) {
        final Scanner console = new Scanner( System.in );

        // Log into the database
        System.out.println( "Please enter in your password for your root MySQL account."
                + " (If there is no password set, type N/A)." );
        while ( !loginDatabase( console ) ) {
            System.out.println( "\nInvalid password. Try again." );
        }

        printIntroduction();
        printOptions();
        String option = console.next();
        do {
            if ( option.toUpperCase().equals( "Q" ) ) {
                System.exit( 1 );
            }
            if ( !option.toUpperCase().equals( "C" ) ) {
                String file = null;
                if ( option.toUpperCase().equals( "RA" ) ) {
                    System.out.print( "\nEnter a file name: " );
                    file = console.next();
                    System.out.println( htmlScorer.retrieveAllScores( file ) );
                }
                else if ( option.toUpperCase().equals( "RD" ) ) {
                    System.out.print( "\nEnter a file name: " );
                    file = console.next();
                    System.out.println( "\nEnter in desired date range." );
                    String date1 = getDate( console, "Start" );
                    String date2 = getDate( console, "End" );
                    while ( date1.compareTo( date2 ) > 0 ) {
                        System.out.println(
                                "\nThe start date you entered occurs after the end date. Please enter a valid range." );
                        System.out.println( "\nEnter in desired date range." );
                        date1 = getDate( console, "Start" );
                        date2 = getDate( console, "End" );
                    }
                    System.out.println( htmlScorer.retrieveAllScoresDateRange( file, date1, date2 ) );
                }
                else if ( option.toUpperCase().equals( "H" ) ) {
                    System.out.print( "\nEnter a file name: " );
                    file = console.next();
                    System.out.println( "\n" + htmlScorer.retrieveHighestScore( file ) );
                }
                else if ( option.toUpperCase().equals( "L" ) ) {
                    System.out.print( "\nEnter a file name: " );
                    file = console.next();
                    System.out.println( "\n" + htmlScorer.retrieveLowestScore( file ) );
                }
                else if ( option.toUpperCase().equals( "A" ) ) {
                    System.out.print( "\nEnter a file name: " );
                    file = console.next();
                    System.out.println( "\n" + htmlScorer.retrieveAverageScore( file ) );
                }
                else {
                    System.out.println( "\nInvalid option. Try again." );
                    System.out.print( "Option: " );
                    option = console.next();
                    continue;
                }
            }
            else {
                System.out.print( "How many files would you like to submit to the database?: " );
                while ( !console.hasNextInt() ) {
                    System.out.println( "Please enter a number." );
                    System.out.print( "How many files would you like to submit to the database?: " );
                    console.next();
                }
                final int numOfFiles = console.nextInt();
                System.out.println();
                for ( int i = 0; i < numOfFiles; i++ ) {
                    System.out.print( "Enter a file name: " );
                    String file = console.next();
                    File checker = new File( "data/" + file );
                    while ( !checker.exists() ) {
                        System.out.println( "The file you entered does not exist. Try again." );
                        System.out.print( "Enter a file name: " );
                        file = console.next();
                        checker = new File( "data/" + file );
                    }
                    htmlScorer.calculateScore( "data/" + file );
                }
                System.out.println( "Your file(s) have been added to the database." );
            }
            printOptions();
            option = console.next();
        }
        while ( !option.equals( "Q" ) );
        htmlScorer.closeSQLConnection();
        console.close();
    }

    /**
     * Logs into the root MySQL database with a given password
     *
     * @param console
     *            scanner to read input from the user
     * @return true if the application successfully logs into the root MySQL
     *         database
     */
    private static boolean loginDatabase ( Scanner console ) {
        System.out.print( "Password: " );
        String password = console.next().trim();
        if ( password.equals( "N/A" ) ) {
            password = "";
        }
        try {
            htmlScorer = new HTMLScorer( password );
        }
        catch ( final IllegalArgumentException e ) {
            return false;
        }
        return true;
    }

    /**
     * Prints the introduction and program information upon entering the console
     * application.
     */
    public static void printIntroduction () {
        System.out.println( "\nWelcome to the HTML File Scoring Program\n" );
        System.out.println( "This program reads in valid HTML files and calculates a score based on" );
        System.out.println( "a set of rules. A score is generated based on the following rules:" );
        System.out.println( String.format( "   %-17s", "•<div> = 3" ) + "•<p> = 1\n"
                + String.format( "   %-17s", "•<h1> = 3" ) + "•<h2> = 2\n" + String.format( "   %-17s", "•<html> = 5" )
                + "•<body> = 5\n" + String.format( "   %-17s", "•<header> = 10" ) + "•<footer> = 10\n"
                + String.format( "   %-17s", "•<font> = -1" ) + "•<center> = -2\n"
                + String.format( "   %-17s", "•<big> = -2" ) + "•<strike> = -1\n"
                + String.format( "   %-17s", "•<tt> = -2" ) + "•<frameset> = -5\n   •<frame> = -5" );
        System.out.println( "After scoring file, the score will be saved in a MySQL database with" );
        System.out.println( "its file name, score, and a date & time of generation. Under the assumption" );
        System.out.println( "that there is information in the database, you will be able to retrieve all" );
        System.out.println( "scores for a file, all scores for a file within a date range, the lowest and" );
        System.out.println( "highest score for a file, and the average score for a file." );
    }

    /**
     * Prints user options when interacting with the console application.
     */
    public static void printOptions () {
        System.out.println( "\nEnter an option - Calculate a Score (C), Retrieve All Scores (RA)," );
        System.out.println( "                  Retrieve All Scores within a Date Range (RD)," );
        System.out.println( "                  Get Highest Score (H), Get Lowest Score (L)," );
        System.out.println( "                  Get Average Score (A), or Quit the Program (Q)" );
        System.out.print( "Option: " );
    }

    /**
     * Gets a valid (start or end) date from the user for retrieving html scores
     * between a date range. checks if the user typed in the date according to a
     * specific format
     *
     * @param console
     *            scanner to read input from the user
     * @param type
     *            differentiates between getting a start date and an end date
     *            (for prompting the user)
     * @return date provided by the user (start or end)
     */
    private static String getDate ( Scanner console, String type ) {
        System.out.print( type + " date [YYYY-MM-DD]: " );
        String date = console.next();
        while ( date.length() < 10 || date.length() > 10 || date.charAt( 4 ) != '-' || date.charAt( 7 ) != '-'
                || date.substring( 5, 7 ).compareTo( "01" ) < 0 || date.substring( 5, 7 ).compareTo( "12" ) > 0
                || date.substring( 8 ).compareTo( "01" ) < 0 || date.substring( 8 ).compareTo( "31" ) > 0 ) {
            System.out.println( "\nPlease enter a date in the format YYYY-MM-DD." );
            System.out.print( type + " date [YYYY-MM-DD]: " );
            date = console.next();
        }
        if ( type.equals( "Start" ) ) {
            return date + " 00:00:00.0";
        }
        else {
            return date + " 23:59:59.9";
        }
    }

}
