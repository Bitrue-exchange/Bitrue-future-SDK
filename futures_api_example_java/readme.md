## Getting started

#### How to run this project

* To create a new self executable jar 
  * run the command on the terminal > mvn clean install
  * run self executable jar > java -jar <jarname> e.g. bitrue-future-client-0.9.0-SNAPSHOT-jar-with-dependencies.jar
  * run unit test > mvn test
  * skip unit test > mvn clean install -DskipTests
  

#### API key and signature
* See the class PrivateConfig.java, add your Bittrue APIKey and Signature


