function load(){
	
	var length = $('#pages option:selected').text();
	var page = $('#entries').val()-1;
	
	console.log(page + " "+ length);
	
	//chiamata al server tramite ajax
	$.ajax({
		url: 'rest/contracts/page?start='+page+'&length='+length,
		dataType: 'json',
		success: function(json){
			fillTable(json); //chiama la funzione per il riempimento della tabella
		},
		error: function(jqXHR, exception){//gestione del messaggio di errore
			var msg = '';
	        if (jqXHR.status === 0) {
	            msg = 'Not connect.\n Verify Network.';
	        } else if (jqXHR.status == 404) {
	            msg = 'Requested page not found. [404]';
	        } else if (jqXHR.status == 500) {
	            msg = 'Internal Server Error [500].';
	        } else if (exception === 'parsererror') {
	            msg = 'Requested JSON parse failed.';
	        } else if (exception === 'timeout') {
	            msg = 'Time out error.';
	        } else if (exception === 'abort') {
	            msg = 'Ajax request aborted.';
	        } else {
	            msg = 'Uncaught Error.\n' + jqXHR.responseText;
	        }
	        $('#post').html(msg);
		}
	});
}

/*
 * la funzione considera l'array dei dati richiesti in formato json, l'array
 * relativo all'header della tabella (proprietà dei dati in colonne) e un array
 * della stessa lunghezza relativo all'indice delle proprietà contenute da un oggetto
 * json. Queste informazioni sono necessarie per un riempimento dinamico della tabella
 * in caso di oggetti con proprietà non ordinate necessariamente secondo il pattern
 * della tabella stessa. Ciò consente di separare l'implementazione dalla struttura
 * rigida degli oggetti inviati dal server.
 */
function fillTable(json){
	
	//pulisce la tabella in caso di chiamate successive
	$("#contract").empty();
	
	var table = document.getElementById("contract");
	var _th_ = document.createElement("th");
	
	//array delle proprietà identificate nell'header
	var thArray = [
			"Identificativo lavoratore",
			"Genere",
			"Anno di nascita",
			"Provincia di domicilio",
			"Codice titolo di studio",
			"Cittadinanza",
			"Identificativo azienda",
			"Settore ateco",
			"Provincia sede operativa",
			"Tipologia contrattuale",
			"Qualifica",
			"Tipo orario",
			"Data inizio",
			"Data fine"
	];
	
	//costruzione dell'header
	var thLength = thArray.length;
	var thead = table.createTHead();
	var tr = thead.insertRow();
	for(var i=0; i<thLength;i++){
		var th = tr.insertCell();
		th.innerHTML = thArray[i];
	}
	
	/*
	 * Array degli indici per la lettura corretta delle proprietà
	 * per ogni oggetto json.
	 */
	var rowPos = {
			idWorker:0,
			genre:1,
			birth:2,
			resWorker:3,
			studyCode:4,
			citizenship:5,
			idAgency:6,
			sector:7,
			resAgency:8,
			cType:9,
			title:10,
			schedType:11,
			startDate:12,
			endDate:13
	}
	
	var tbody = table.createTBody();
	
	/* riempimento dinamico della tabella:
	 * si estrae ogni oggetto json dall'array,
	 * in ogni oggetto si esegue un mapping delle proprietà
	 * i cui valori verranno disposti in tabella in base alla
	 * struttura dell'header (entra in gioco rowPos).
	 * L'array tmpArray serve come array di appoggio per la corretta
	 * lettura dei dati e successivo inserimento nella tabella HTML.  
	 */
	for(var i = 0; i<json.length; i++){
		var tmpArray = Array(thLength).fill("");
		var row = tbody.insertRow();
		var child = json[i];
		
		Object.getOwnPropertyNames(child).forEach(function(k){
			tmpArray[rowPos[k]] = child[k];
		})
		
		for(var j = 0; j<thLength; j++){
			var cell = row.insertCell();
			cell.innerHTML = tmpArray[j];
			cell.setAttribute("id",i+""+j);
		}
	}
}