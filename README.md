Worms
=====

This is a simple 2d game for 2-5 players where you play as worms against each other and the last man standing wins a round.

This branch is changed to accommodate genetic evolution for player controllers which use recurrent neural network.

Build from sources
------------------
To build this project you need to have git and maven2 installed.
If you have them installed then the series of commands to get final product is the following:

* git clone git@github.com:vaclavblazej/worms.git
* cd worms/
* git checkout artificial_intelligence
* mvn package
* cd target/
* chmod u+x worms-jar-with-dependencies.jar 
* java -jar worms-jar-with-dependencies.jar 
