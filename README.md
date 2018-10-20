# Scoring Project
The Scoring Project is a console-based system that reads a given HTML file and calculate the score for a given HTML file and save the score, as well as the date and time it was calculated. The score is then stored in the users local root MySQL database.

## Technology Used
- Java v1.8.0
- MySQL 5.7.17
- Eclipse Oxygen as my IDE
- Windows 10

## Scoring System
Each starting tag in the table below has been assigned a score. Any tags not listed in this table will not factor into scoring. Each tag in the content should be added to or subtracted from the total score based on this criteria.
 
 | TagName | Score Modifier | TagName | Score Modifier |
 | ------- | :------------: | ------- | -------------- |
 | div     | 3              | font    | -1             |
 | p       | 1              | center  | -2             |
 | h1      | 3              | big     | -2             |
 | h2      | 2              | strike  | -1             |
 | html    | 5              | tt      | -2             |
| body    | 5              | frameset| -5             |
| header  | 10             | frame   | -5             |
| footer  | 10             |

## Purpose
The purpose of this system is to determine if an html page is using recommended and proper syntax. A higher score represents a well written html file.
- Upon starting the console application, it will prompt you to log in to your MySQL account using your root password. The application will then create the database table for you, if it does not already exist. If you have no root MySQL password, type in "N/A". 

## Running the Program
1. Start up Eclipse Oxygen or Eclispe Neon.
2. Import the project into the workspace.
3. Navigate to the src/scoring_project/scoring_project directory with the ScoringProject folder.
4. Run HTMLScorerUI.java as a Java Application to start the console program.
