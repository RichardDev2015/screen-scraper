# screen-scraper
This application has been written to scrape product data from sainsbury's product web page.
The application has been written in Java and incorporates a maven pom.xml that contains 
all the build dependencies.

How to build the project in eclipse
===================================
Right click pom.xml 
Select on Run As
Select Maven clean

Right click pom.xml 
Select on Run As
Select Maven install


How run the project against JUnit tests in eclipse
==================================================
Right click on ScraperDemoTest.java
Select on Run As
Select JUnit test
Result: you should display that all the tests are successful in the JUnit tab

How to Run ScrapeDemo implementation in eclipse
===============================================
Right click on ScraperDemoImpl.java
Click on Run As
Select Java Application
Result: you should display the Json representation of each scraped product information

Project Content
===============
The project comprises of 5 java files, that incorporate JUnit tests and the application page scrape
implementation class.

Interface:ScraperDemo.java 
--------------------------
Defines the interface method processScreen.
This method scrapes product data from sainsbury's product web page and returns the 
result in a Json object.

Class:ScraperDemoImpl.java 
--------------------------
This class implements the business functionality within method processScreen.

Class:TestScraperDemoImpl.java
------------------------------
This class acts as a mock class of ScraperDemoImpl.java. It simulates the responses
that the actual implementation class would return.

Class:ScraperDemoTest.java
--------------------------
This class contains all the test data used within the JUnit tests


Location of Project
===================
The project can be down loaded from github

https://github.com/RichardDev2015/screen-scraper

