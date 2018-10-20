# Scoring Project
The Scoring Project is a console-based system that reads a given HTML file and calculates a score based on a predetermined scoring system, which is already imbedded into the system. The score is then store in the users local root database.

## Technology Used
* Java
* MySQL
* Eclipse IDE

## Scoring System
The scoring system used in this application was given in project requirements when assigned.

`<div>` = 3
`<p>` = 1
`<h1>` = 3
`<h2>` = 2
`<html>` = 5
`<body>` = 5
`<header>` = 10
`<footer>` = 10
`<font>` = -1
`<center>` = -2
`<big>` = -2
`<strike>` = -1
`<tt>` = -2
`<frameset>` = -5
`<frame>` = -5

## Purpose
The purpose of this system is to determine if an html page is using recommended and proper syntax. A higher score represents a well written html file.

## Running the Program
In any IDE, run the `HTMLScorerUI.java` file. Or if in the command line, navigate to the `src/scoring_project/scoring_project` folder and run the `HTMLScorerUI.java` file with the appropriate command. It is recommended to run via Eclipse. The program will then prompt login and upon successful login, it will present instructions on how to use the system.
