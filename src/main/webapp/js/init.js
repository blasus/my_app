//
// Pipelining function for DataTables. To be used to the `ajax` option of DataTables
//
$.fn.dataTable.pipeline = function ( opts ) {
    // Configuration options
    var conf = $.extend( {
        pages: 5,     // number of pages to cache
        url: '',      // script url
        data: null,   // function or object with parameters to send to the server
                      // matching how `ajax.data` works in DataTables
        method: 'GET' // Ajax HTTP method
    }, opts );
 
    // Private variables for storing the cache
    var cacheLower = -1;
    var cacheUpper = null;
    var cacheLastRequest = null;
    var cacheLastJson = null;
 
    return function ( request, drawCallback, settings ) {
        var ajax          = false;
        var requestStart  = request.start;
        var drawStart     = request.start;
        var requestLength = request.length;
        var requestEnd    = requestStart + requestLength;
         
        if ( settings.clearCache ) {
            // API requested that the cache be cleared
            ajax = true;
            settings.clearCache = false;
        }
        else if ( cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper ) {
            // outside cached data - need to make a request
            ajax = true;
        }
        else if ( JSON.stringify( request.order )   !== JSON.stringify( cacheLastRequest.order ) ||
                  JSON.stringify( request.columns ) !== JSON.stringify( cacheLastRequest.columns ) ||
                  JSON.stringify( request.search )  !== JSON.stringify( cacheLastRequest.search )
        ) {
            // properties changed (ordering, columns, searching)
            ajax = true;
        }
         
        // Store the request for checking next time around
        cacheLastRequest = $.extend( true, {}, request );
 
        if ( ajax ) {
            // Need data from the server
            if ( requestStart < cacheLower ) {
                requestStart = requestStart - (requestLength*(conf.pages-1));
 
                if ( requestStart < 0 ) {
                    requestStart = 0;
                }
            }
             
            cacheLower = requestStart;
            cacheUpper = requestStart + (requestLength * conf.pages);
 
            request.start = requestStart;
            request.length = requestLength*conf.pages;
 
            // Provide the same `data` options as DataTables.
            if ( $.isFunction ( conf.data ) ) {
                // As a function it is executed with the data object as an arg
                // for manipulation. If an object is returned, it is used as the
                // data object to submit
                var d = conf.data( request );
                if ( d ) {
                    $.extend( request, d );
                }
            }
            else if ( $.isPlainObject( conf.data ) ) {
                // As an object, the data given extends the default
                $.extend( request, conf.data );
            }
 
            settings.jqXHR = $.ajax( {
                "type":     conf.method,
                "url":      conf.url,
                "data":     request,
                "dataType": "json",
                "cache":    false,
                "success":  function ( json ) {
                    cacheLastJson = $.extend(true, {}, json);
 
                    if ( cacheLower != drawStart ) {
                        json.data.splice( 0, drawStart-cacheLower );
                    }
                    if ( requestLength >= -1 ) {
                        json.data.splice( requestLength, json.data.length );
                    }
                     
                    drawCallback( json );
                }
            } );
        }
        else {
            json = $.extend( true, {}, cacheLastJson );
            json.draw = request.draw; // Update the echo for each response
            json.data.splice( 0, requestStart-cacheLower );
            json.data.splice( requestLength, json.data.length );
 
            drawCallback(json);
        }
    }
};
 
// Register an API method that will empty the pipelined data, forcing an Ajax
// fetch on the next draw (i.e. `table.clearPipeline().draw()`)
$.fn.dataTable.Api.register( 'clearPipeline()', function () {
    return this.iterator( 'table', function ( settings ) {
        settings.clearCache = true;
    } );
} );

//
//DataTables initialisation
//
$(document).ready( function(){
	
	var table = $('#contract').DataTable( {
		"serverSide": true,
		"processing": true,
		"stateSave": true,
		"paging": true,
		"select": true,
		"colReorder": true,
		"lengthMenu": [ 1000, 5000, 10000, 20000 ],
		"pageLength": 1000,
		"order": [[0, 'asc']],
		"ajax": $.fn.dataTable.pipeline( {
            url: 'rest/contracts/page',
            pages: 5, // number of pages to cache
            data: function(d){
            	d.extra_search = $('#col3_filter').val();
            }
        } ),
        "scrollY": "65vh",
		"scrollCollapse": true,
        "search": {
			"caseInsensitive": true,
			"regex": false,
			"smart": false
		},
		"searchDelay": 1000,
		"language": {
			"search": "Cerca&nbsp;",
			"processing": "Caricamento in corso...",
			"paginate": {
				first: "Primo",
				previous: "Precedente",
				next: "Successivo",
				last: "Ultimo"
			},
			"lengthMenu": "Mostra _MENU_ righe",
			"info": "Mostra da _START_ a _END_ su _TOTAL_ elementi",
			"thousands": "'",
			"decimals": ","
		},
		"columns": [
			{	"data": "id",
				"name": "id",
				"render": $.fn.dataTable.render.number('',','),
				"searchable": false
			},
			{	"data": "idWorker",
				"name": "identificativo_lavoratore",
				"render": $.fn.dataTable.render.text(),
				
			},
			{	"data": "genre",
				"name": "genere",
				"render": $.fn.dataTable.render.text(),
				"orderable": false,
				"searchable": false
			},
			{	"data": "birth",
				"name": "anno_nascita",
				"render": $.fn.dataTable.render.number('',''),
			},
			{ 	"data": "resWorker",
				"name": "provincia_domicilio",
				"render": $.fn.dataTable.render.number('',''),
				"orderable": false,
				"searchable": false
			},
			{ 	"data": "studyCode",
				"name": "codice_titolo_studio",
				"render": $.fn.dataTable.render.number('',''),
				"searchable": false
			},
			{ 	"data": "citizenship",
				"name": "cittadinanza",
				"render": $.fn.dataTable.render.text(),
				"orderable": false,
			},
			{ 	"data": "idAgency",
				"name": "identificativo_azienda",
				"render": $.fn.dataTable.render.text(),
			},
			{ 	"data": "sector",
				"name": "settore_ateco",
				"render": $.fn.dataTable.render.number('',',',2),
				"orderable": false
			},
			{ 	"data": "resAgency",
				"name": "provincia_sede_operativa",
				"render": $.fn.dataTable.render.number('',''),
				"orderable": false,
				"searchable": false
			},
			{ 	"data": "cType",
				"name": "tipologia_contrattuale",
				"render": $.fn.dataTable.render.text()
			},
			{ 	"data": "title",
				"name": "qualifica",
				"render": $.fn.dataTable.render.text(),
				"orderable": false,
				
			},
			{ 	"data": "schedType",
				"name": "tipo_orario",
				"render": $.fn.dataTable.render.text(),
				"orderable" : false,
				"searchable": false
			},
			{ 	"data": "startDate",
				"name": "data_inizio",
				"render": function(data, type, row){
					var d = new Date(data); 
					return d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getFullYear();
				},
			},
			{ 	"data": "endDate",
				"name": "data_fine",
				"defaultContent": "",
				"render": function(data, type, row){
					if(data !== undefined){
						var d = new Date(data); 
						return d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getFullYear();
					}
					else return data;  
				},
			}
		]
	} );
	
	//
	//Searching on a non-text column is allowed through the dedicated search boxes.
	//When it happens, this function gives new data to ajax and reloads the table data:
	//
	$('#col3_filter').on('keyup change', function(){
		setTimeout(function(){
			location.reload();
		}, 1500);
	});
	
} );