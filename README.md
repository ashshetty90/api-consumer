# api-consumer

# Overview
Api Consumer Application makes a web application call to fetch data from a remotely running web server. Based on the response from the 
server certain computations/aggregations are performed. The result of these aggregations is stored in a file in json format.

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

# Prerequisites
Java 8
Maven 3 
Junit 4

# Design and Assumption
App class holds the entry point of application which takes input from the command line.
UserInfo.java is a POJO class used to wrap the user data with the help of GSON parsers.
GenerateUserDataImpl is the class containing the core business logic of fetching data from the Web Api and performing computations based
on the response.


# Installing
Unzip the file to any directory. And open the project in your favourite IDE. Do the following

mvn clean package
$ java -jar api-consumer-1.0-SNAPSHOT.jar /path/to/transaction/file/transactions.json /path/to/enriched/transaction/file/transactions-enriched.json http://127.0.0.1:5000/user_status/%s?date=%s http://127.0.0.1:5000/ip_city/%s /path/to/aggregate/user/data/file/aggregate-user-data.json

Run the App.java 5 program arguments

* /path/to/transaction/file/transactions.json // should be a json file 
* /path/to/enriched/transaction/file/transactions-enriched.json // path to the enriched json data file
* http://127.0.0.1:5000/user_status/%s?date=%s // Web App to get user status
* http://127.0.0.1:5000/ip_city/%s // Web App to get Ip to city mapping
* /path/to/aggregate/user/data/file/aggregate-user-data.json // path to aggregate user output data file


# Running the tests
Tests are included to check the HttpUtilsTest.java  for validating the response data and JsonUtilsTest.java for valdating json input file.

mvn clean test
Built With
Maven - Dependency Management
