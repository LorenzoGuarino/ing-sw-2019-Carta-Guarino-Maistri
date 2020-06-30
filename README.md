


# Software Engineering Project 2020 - Carta, Guarino, Maistri
## Group: PSP027

### Group Members:
* ### 10573704 Carta Daniele ([@icsdaniel](https://github.com/icsdaniel)) - danieleantonio.carta@mail.polimi.it
* ### 10568234 Guarino Lorenzo ([@LorenzoGuarino](https://github.com/LorenzoGuarino)) - lorenzo1.guarino@mail.polimi.it
* ### 10531069 Maistri Elisa ([@elisamaistri](https://github.com/elisamaistri)) - elisa.maistri@mail.polimi.it

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Simplified rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket |[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)|
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Advanced Gods | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Persistence | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Undo | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |


<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

### Multiple Games:
We implemented a server that supports multiple matches happening at the same time.
When a player registers himself on the server, he's asked whether he wants to play a match for 2 or 3 people.<br>
Depending on the player's choice the server will search for an available match of that type that is waiting for other players to join before starting the actual match.<br>
If there are no waiting matches of the wanted type or each match has already started the server will then create another one of the type and the player will then be waiting for 1 or 2 other players to join depending on his choice.


### Advanced Gods:
We added 5 additional gods within the game:<br>
- Ares<br>
- Hestia<br>
- Medusa<br>
- Poseidon<br>
- Zeus<br>


### How to setup/run the Server
From the directory [deliverables/JARS](./deliverables/JARS), you can run the application server with the command-line command

    java -jar santorini-server.jar [socket-port-number]

If port number is not specified a default socket port number will be used (2705)

### How to run CLI:
From the directory [deliverables/JARS](./deliverables/JARS), you can run the application CLI with the command-line command

    java -jar santorini-cli.jar

Note: CLI can be viewed correctly only on consoles that support ANSI escape codes.

### How to run GUI:
From the directory [deliverables/JARS](./deliverables/JARS), you can run the application GUI with the command-line command

    java -jar santorini-gui.jar

