[![it](https://img.shields.io/badge/lang-it-green.svg)](README.it.md)   [![en](https://img.shields.io/badge/lang-en-orange.svg)](README.md)
# Tic-Tac-Toe
*[Read in English](README.md)*


Sistema distribuito per giocare a Tic-Tac-Toe, sviluppato per il corso di PCD.
## Da fare solo la prima volta
Devi aver installato Maven!
https://maven.apache.org/download.cgi

Esempio di installazione:

![Link Installed](images/maven-installation.png)

Dopo l'installazione, devi impostare due variabili d'ambiente (cerca "Variabili d'ambiente" nelle impostazioni di Windows, poi modifica le Variabili di Sistema):

*MAVEN_HOME*: crea una nuova variabile con questo valore:
```
C:\Program Files\apache-maven-3.9.16-bin\apache-maven-3.9.16
```

*Path*: modifica la variabile esistente e aggiungi una nuova riga in fondo con:
```
C:\Program Files\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin
```


## Come avviare il progetto
Nel primo terminale:
```
mvn clean compile
cd .\target\classes\
rmiregistry
```
 
Apri un secondo terminale per avviare il server:
```
java -cp target/classes main.java.server.RunServerTTT
```
 
Apri un terzo terminale per avviare un client:
```
java -cp target/classes main.java.client.RunClientTTT
```
 
Apri un quarto terminale per avviare un altro client (stesso comando di prima):
```
java -cp target/classes main.java.client.RunClientTTT
```
