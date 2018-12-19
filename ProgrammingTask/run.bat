@echo off
start "Sender" java -jar FileSender.jar
java -jar FileReceiver.jar
pause