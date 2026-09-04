[![it](https://img.shields.io/badge/lang-it-green.svg)](README.it.md)   [![en](https://img.shields.io/badge/lang-en-orange.svg)](README.md)

# Tic-Tac-Toe

*[Leggi in italiano](README.it.md)*

Distributed system for playing Tic-Tac-Toe, developed for the PCD course.

## First time setup

You need to have Maven installed!
https://maven.apache.org/download.cgi

Example of installation:

![Link Installed](images/maven-installation.png)

After installing, you need to set up two environment variables (search for "Environment Variables" in Windows settings, then edit the System variables):

*MAVEN_HOME*: create a new variable with this value:

  ```
  C:\Program Files\apache-maven-3.9.16-bin\apache-maven-3.9.16
  ```

*Path*: edit the existing variable and add a new line at the end with:

```
C:\Program Files\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin
```

## How to run the project

In the first terminal:
```
mvn clean compile
cd .\target\classes\
rmiregistry
```
 
Open a second terminal to run the server:
```
java -cp target/classes main.java.server.RunServerTTT
```
 
Open a third terminal to run a client:
```
java -cp target/classes main.java.client.RunClientTTT
```
 
Open a fourth terminal to run another client (same command as before):
```
java -cp target/classes main.java.client.RunClientTTT
```
 
