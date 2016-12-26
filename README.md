Lo scopo è visualizzare dei dati sull’attivazione di contratti di lavoro messi a disposizione della regione lazio, in modo che siano facilmente fruibili e analizzabili. Puoi scaricare i dataset qui: http://dati.lazio.it/catalog/en/dataset/comunicazioni-obbligatorie-attivazioni

SI potrebbero prendere in considerazione tutte le attivazioni del 2014 (dal primo all’ultimo quarto). Se vuoi puoi anche dare uno sguardo ad altri dataset e sceglierne altri che ti interessano di più.

Ci sarà un backend (possibilmente Java su servlet container) che esporrà questi dati tramite API REST usando JSON come formato di scambio; il frontend sarà una sorta di dashboard con grafici interattivi che espongono i dati in modo significativo. Per i grafici puoi usare D3 (https://d3js.org/). È importante che la build sia automatizzata (tramite tool come Maven o Gradle)

Sarà determinante il formato di storage dei dati e scelta del database (visto che si parla di una semplice lista di record opterei per un DB NoSql). Il design delle API esposte all’interfaccia poi determinerà la facilità con cui si possono implementare grafici interattivi. Si può anche pensare all’uso di tabelle pivot/OLAP.

Naturalmente conviene partire con qualcosa di molto semplice, in modo che riesci a dare uno sguardo a tutto lo stack e al tooling necessario, poi, se c'è il tempo e l'interesse, si può pensare di creare qualcosa di più strutturato e magari anche pubblicarlo.
