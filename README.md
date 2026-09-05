[![it](https://img.shields.io/badge/lang-it-green.svg)](README.it.md)   [![en](https://img.shields.io/badge/lang-en-orange.svg)](README.md)

# Tic-Tac-Toe

*[Leggi in italiano](README.it.md)*

Distributed system for playing Tic-Tac-Toe, developed for the PCD course.

## First time setup

You need to have Maven installed!
https://maven.apache.org/download.cgi

For example, you can install (updated September 2026):

![Link Installed](images/maven-installation.png)

After installing, extract the zip file. Move the folder to the
`C:\Program Files` path (the "Program Files" directory found under
"This PC"). Then, navigate through the directories until you are
inside the `bin` folder. Copy the current path.

Now, you need to set up two environment variables (search for
"Environment Variables" in Windows settings, then edit the System variables):

`MAVEN_HOME`: create a new variable with the previously copied path as its value, but without `bin`:

  ```
  C:\Program Files\apache-maven-3.9.16-bin\apache-maven-3.9.16
  ```

`Path`: edit the existing variable and add a new line at the end with the previously copied path as its value:

```
C:\Program Files\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin
```

(Warning: The examples above can only be copied by those who have installed apache-maven-3.9.16
and correctly moved the folder to `C:\Program Files`.)

## How to run the project

In the first terminal:
```
mvn clean
mvn compile
cd .\target\classes\
rmiregistry
```
 
Open a second terminal to run the server:
```
java -cp target/classes main.java.server.RunServerTTT
```
 
Open a third terminal to run a client (i.e., you can use it to create a new game):
```
java -cp target/classes main.java.client.RunClientTTT
```
 
Open a fourth terminal to run another client (same command as before. I.e., you can use it to join to the
game created by the previous client):
```
java -cp target/classes main.java.client.RunClientTTT
```

## If it still doesn't work...

- Check that the source root is just `src`
- Check that the `pom.xml` file is being interpreted correctly