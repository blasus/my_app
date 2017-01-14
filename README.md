Lo scopo è visualizzare dei dati sull’attivazione di contratti di lavoro messi a disposizione della regione lazio, in modo che siano facilmente fruibili e analizzabili. Puoi scaricare i dataset qui: http://dati.lazio.it/catalog/en/dataset/comunicazioni-obbligatorie-attivazioni

SI potrebbero prendere in considerazione tutte le attivazioni del 2014 (dal primo all’ultimo quarto). Se vuoi puoi anche dare uno sguardo ad altri dataset e sceglierne altri che ti interessano di più.

Ci sarà un backend (possibilmente Java su servlet container) che esporrà questi dati tramite API REST usando JSON come formato di scambio; il frontend sarà una sorta di dashboard con grafici interattivi che espongono i dati in modo significativo. Per i grafici puoi usare D3 (https://d3js.org/). È importante che la build sia automatizzata (tramite tool come Maven o Gradle)

Sarà determinante il formato di storage dei dati e scelta del database (visto che si parla di una semplice lista di record opterei per un DB NoSql). Il design delle API esposte all’interfaccia poi determinerà la facilità con cui si possono implementare grafici interattivi. Si può anche pensare all’uso di tabelle pivot/OLAP.

Naturalmente conviene partire con qualcosa di molto semplice, in modo che riesci a dare uno sguardo a tutto lo stack e al tooling necessario, poi, se c'è il tempo e l'interesse, si può pensare di creare qualcosa di più strutturato e magari anche pubblicarlo.


Software richiesto:<br>
1. Eclipse;<br>
2. MongoDb installato e configurato;<br>
3. Plugin M2Eclipse in Eclipse per installare Maven;<br>
4. Apache Tomcat 9.0 (ultima versione) installato e configurato;<br>

Avvio:
1. Aprire da command prompt in amministratore l'eseguibile "mongod" specificando la directory di storing del database con il comando "--dbpath" (senza usare "");
2. Aprire Eclipse assicurandosi di avere M2Eclipse installato (Help --> Install new sofware... e seguire comunque la procedura indicata sul sito di M2Eclipse;
3. Importare il progetto Maven nel proprio workspace di Eclipse;
4. Attendere la sincronizzazione di tutte le dipendenze;
5. Configurare su server Tomcat la risorsa "my_app";
6. Avviare il web server;
7. Provare su browser o usando il plugin "HTTP4E" per Eclipse a inviare richieste http sul canale "localhost" con porta 8080.
=======
Software richiesto:<br>
1. Eclipse;<br>
2. MongoDb installato e configurato;<br>
3. Plugin M2Eclipse in Eclipse per installare Maven;<br>
4. Apache Tomcat 9.0 (ultima versione) installato e configurato in Eclipse.<br>

Avvio:<br>
1. Aprire da command prompt in amministratore l'eseguibile "mongod" specificando la directory di storing del database con il comando "--dbpath" (senza usare "");<br>
2. Aprire Eclipse assicurandosi di avere M2Eclipse installato (Help --> Install new sofware... e seguire comunque la procedura indicata sul sito di M2Eclipse;<br>
3. Importare il progetto Maven nel proprio workspace di Eclipse;<br>
4. Attendere la sincronizzazione di tutte le dipendenze;<br>
5. Configurare su server Tomcat la risorsa "my_app";<br>
6. Avviare il web server;<br>
7. Provare su browser o usando il plugin "HTTP4E" per Eclipse a inviare richieste http sul canale "localhost" con porta 8080.

URL di prova: https://localhost:8080/my_app/contracts o https://localhost:8080/my_app/contracts/PF2398
>>>>>>> origin/master
