Programing Task

Author: Gilad Sagi
Date: 12/19/2018

Description:
The project contains two small program, one for sending a file and another for receiving it.
The file is transfered over the network using Java NIO channels and buffers.
The file can be in different formats and differnt sizes (big files are supported >3gb)

Installation:
unzip the file,
you can import the project into your eclipse (or other IDE)

file content:
1. FileReceiver.java - in the src directory - the main program for receiving the file (and create the channel).
2. FileSender.java - in the src directory - the main program for sending the file.
3. Configurations.java - in the src directory - a class for implementing the configuraion file
4. Configurations.con - the configuration file with the properties - IMPORTANT!! - this file need to be modify with relevant file to send and path to receiving
5. FileReceiver.jar - the artifact from the FileReceiver program
6. FileSender.jar - the artifact from the FileSender program
7. run.bat - running batch script to run test the program

testing the program:
1. edit the Configurations.con file:
		edit FILE_TO_SEND property
		edit RETRIVED_PATH property - place it in a different path than where the file exist to see acutal results.
		please note the locations of both properties must have read and write access (you can check the permissions by right click on the directory path -> properties -> security)
		by default the directory under c:\user\YOUR-USER have read and write access, so you can test the program by choosing a file and a retrived path in this directory and sub directories
		without changing permissions
		please write the paths with double slashes "\\" between directories as the example in the Configurations.con file.
2. execute run.bat file - the file should be send over the networkde and recived in the desired path

* you can also test it directly from eclipse or other IDE, just run FileReceiver.java first then run FileSender.java


for any questions please let me know.