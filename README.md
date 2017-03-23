
Contracts visualization web service
===================================

Feature:
--------

This is a first tutorial project to realize a web application for a more clear visualization of data stored in a DataBase, organizing them in a table or using graphs, with the purpose to improve their interpretation and make data analysis.

This is an excellent exercise to learn all the necessary tools and every operation to develop a web application. In fact, it is composed from a java servlet based backend that will send to frontend the requested data through API REST using JSON for a easier organization of them. 
The frontend will be structured in dynamic web pages collecting data and visualizing them in interactive tables and graphs.

In this project will be considered data representing job contracts activation from Regione Lazio.
The original dataset can be found here: http://dati.lazio.it/catalog/en/dataset/comunicazioni-obbligatorie-attivazioni .
This app is designed to expose a large amount of data, so will be used for example the whole dataset referring to 2014 contracts activation (in total 4 quarters, corresponding to about 1.6M entries).

It will be most important the storage structure of data. For this example it may be convenient to use a NoSql DB, for example MongoDB or anything else is a good choice.

Two really graceful jquery frameworks for the client visualization are DataTables (see https://datatables.net/manual/index ) and D3.js (see https://d3js.org/ ).
Pivot/OLAP based tables are also good alternatives for the exposition.

The last but not least is the implementation of the automation build like Maven o Gradle. In this example will be used Maven for this purpose.


Tools:
------

+ Java Developers Kit 8 - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html ;
+ Multi-language IDE (e.g. Eclipse - https://www.eclipse.org/downloads/? );
+ M2Eclipse - http://www.eclipse.org/m2e/ ;
+ MongoDB - https://www.mongodb.com/ . A MongoDB Windows service could be configured by following this guide: https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/#configure-a-windows-service-for-mongodb-community-edition ;
+ Apache Tomcat 9 - http://tomcat.apache.org/download-90.cgi . Follow this guide to configure it in Eclipse: https://www.eclipse.org/webtools/jst/components/ws/1.0/tutorials/InstallTomcat/InstallTomcat.html ;
+ Jquery, already implemented;
+ DataTables - https://datatables.net/manual/index , already implemented;
+ D3 - https://d3js.org/ , will be included in next implementations;



Configuration:
--------------

+ set up MongoDB environment, importing the dataset into mongo using mongoimport or other tools (e.g. Studio 3T or Mongoose);
+ import the project in Eclipse workspace;
+ update the project using Maven;
+ configure on Tomcat "my_app" resource;
+ start Tomcat;
+ go to https://localhost:8080/my_app/ and enjoy!
