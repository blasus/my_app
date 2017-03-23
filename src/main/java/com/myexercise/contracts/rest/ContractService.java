package com.myexercise.contracts.rest;

/**
 * @author Blasi Francesco
 * @version 0.0.1-SNAPSHOT
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.myexercise.contracts.Contract;
import com.myexercise.contracts.ContractRepository;
import com.myexercise.contracts.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to manage Rest service.
 * It's implemented by the integration of spring with jersey.
 * 
 */

@Path("contracts")
@Produces("application/json")
public class ContractService {
	
	private final ContractRepository repo;
	static final String DATE_FORMAT = "dd/MM/yyyy";
	static final Logger logger = LoggerFactory.getLogger(ContractService.class);
	
	@Autowired MongoOperations operations;
	
	@Autowired
	ContractService(ContractRepository repo){
		this.repo = repo;
	}
	
		
	/**
	 * This method is called when homepage is loaded. It provides a GET request based on
	 * the criteria acquired through most of the parameters extracted from the uriInfo.
	 * In particular it will be selected a certain page of "contracts" and will be sent it to client
	 * considering these parameters: 
	 * <ul>
	 * <li>draw - a key used to ackowledge a certain message in the comunication;
	 * <li>start - this number is used to identify the first element will be showed in a paga
	 * 			   of contracts;
	 * <li>length - this number is associated to the page length;
	 * <li>dir & column - these parameters are used to manage the sorting function;
	 * <li>search & extraS - parameters used for searching funtions;
	 * <li>flags - at the moment is useless;
	 * <li>pos - it carries 15 strings associated to the the properties for every contract object.
	 * 			 It will be useful to recognize and select every property in an object, for example
	 * 			 to determine the sorting criteria. 
	 * </ul>
	 * 
	 * The url to make the request from Ajax is "/my_app/rest/contracts/page/. Ajax will adds lots of
	 * parameters to this url for different functions.
	 * At the moment only param described above are useful.
	 * 
	 * This method returns a GET response collecting the list of contracts and the comunication http 
	 * status code.
	 * 
	 * 
	 * @param uriInfo  the set of parameters extracted from the uri context
	 * @return a GET rest response characterized from a status code and a message formatted in json. 
	 */
	@Path("/page/")
	@GET
	public Response findAll(@Context UriInfo uriInfo){
		
		String result = "";
		try{
			//extracts useful param
			MultivaluedMap<String, String> param = uriInfo.getQueryParameters();
			int draw = Integer.parseInt(param.getFirst("draw"));
			int start = Integer.parseInt(param.getFirst("start"));
			int length = Integer.parseInt(param.getFirst("length"));
			String dir = param.getFirst("order[0][dir]");
			int column = Integer.parseInt(param.getFirst("order[0][column]"));
			String search = param.getFirst("search[value]");
			String extraS = param.getFirst("extra_search");
			boolean[] flags = new boolean[15]; //useless at the moment
			String[] pos = new String[15];
			
			for(int i = 0; i<15; i++){
				flags[i] = Boolean.getBoolean(param.getFirst("columns["+i+"][searchable]"));
				pos[i] = param.getFirst("columns["+i+"][name]");
			}
			
			int nPage = (int) Math.floor(start/length); //page number
			long size = repo.count(); /*this will be useful to tell the total number of elements 
			 							stored in db to the client*/
			List<Contract> list;
			Result r;
			
			/* if search or extraS variables aren't null, it means that the client make a global text search
			 * (search variable) or local search (extraS one). In this case it will perform a new query with 
			 * a custom Criteria: 
			 */
			boolean s = !search.equals("");
			if(!extraS.equals("") || s){
				logger.debug("1. It will be executed one or more filtering search");
				//String[] s = search.split(" ");
				//for(int i=0; i<s.length; i++) logger.info(s[i]);
				Query query = new Query();
				
				if(s)
					query.addCriteria(TextCriteria.forLanguage("none").matching(search));
				
				query.addCriteria(Criteria.where("anno_nascita").is(Integer.parseInt(extraS)));
				query.with(new PageRequest(nPage,length,Direction.fromString(dir),pos[column]));
				list = operations.find(query, Contract.class);
				r = new Result(draw,size,list.size(),list);
				
			}else{
				//executes a simply find query considering only sorting and paging parameters:
				logger.debug("2. it won't be executed any searching");
				list = repo.findAll(new PageRequest(nPage,length,Direction.fromString(dir),pos[column])).getContent();
				r = new Result(draw,size,(int) size,list);
			}
			
			result = new Gson().toJson(r);
			return Response.status(200).entity(result).build();
		
		}catch(RuntimeException e){
			/*
			 * A runtime error will be catched and packed in a json object,
			 * then send it to the client.
			 */
			Result r = new Result();
			logger.error("Runtime error: {}. ",e.getMessage());
			r.setError("errore: "+e.toString());
			result = new Gson().toJson(r);
			return Response.status(200).entity(result).build();
		}
	}
	
	/*
	 * Maybe all these services will be useful for next implementations:
	 */
	
	/** Query: db.collection.find({$and: [{data_inizio: {$gt: start}},{data_fine: {$lt: end}]},{_id : 0}) **/
	@Path("/date/period/")
	@GET
	public Response findByPeriod(
		@DefaultValue("01/01/2014")@QueryParam("di") String startDate,
		@DefaultValue("")@QueryParam("df") String endDate) throws JsonSyntaxException{
		
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date start = null;
		try {
			start = formatter.parse(startDate);
		} catch (ParseException e) {
			logger.error("Parsing startDate: {}. ",e.getMessage());
			return Response.status(500).entity("Http error: 500 - Bad request for startDate").build();
		}
		List<Contract> list;
		if(!endDate.equals("")){
			Date end = null;
			try {
				end = formatter.parse(endDate);
			} catch (ParseException e) {
				logger.error("Parsing endDate: {}. ",e.getMessage());
				return Response.status(500).entity("Http error: 500 - Bad request for endDate").build();
			}
			
			if(end.before(start)) return Response.status(400).entity("Bad date interval requested!").build();
			
			logger.debug("1. intervallo valido.");
			list = repo.findByStartDateAfterAndEndDateBefore(start, end);
						
		}else{
			logger.debug("2. data fine non specificata.");
			list = repo.findByStartDateAfter(start);
		}
		String result = new Gson().toJson(list);		
		return Response.status(200).entity(result).build();
	}
	
	
	/** Query: db.collection.find({$and: [{data_inizio: {$gt: data1}},{data_inizio: {$lt: data2}}]}) **/
	@Path("/date/")
	@GET
	public Response findBetweenStartDates(
		@DefaultValue("01-01-2014")@QueryParam("from") String sd1,
		@DefaultValue("")@QueryParam("to") String sd2) throws JsonSyntaxException{
		
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date start1 = null;
		try {
			start1 = formatter.parse(sd1);
		} catch (ParseException e) {
			logger.error("Parsing startDate1: {}. ",e.getMessage());
			return Response.status(500).entity("Http error: 500 - Bad request for startDate").build();
		}
		List<Contract> list;
		if(!sd2.equals("")){
			Date start2 = null;
			try {
				start2 = formatter.parse(sd2);
			} catch (ParseException e) {
				logger.error("Parsing startDate2: {}. ",e.getMessage());
				return Response.status(500).entity("Http error: 500 - Bad request for startDate").build();
			}
			
			if(start2.before(start1)) return Response.status(400).entity("Bad date interval requested!").build();
			
			logger.debug("1. intervallo valido.");
			list = repo.findByStartDateBetween(start1, start2);
						
		}else{
			logger.debug("2. seconda data inizio non specificata");
			list = repo.findByStartDateAfter(start1);
		}
		String result = new Gson().toJson(list);
		return Response.status(200).entity(result).build();
	}
	
	/** Query: db.collection.find({identificativo_lavoratore: idWorker}) **/
	@Path("/{idWorker: [P][F][0-9]+}")
	@GET
	public Response findWorker(@PathParam("idWorker") String idWorker) throws JsonSyntaxException{
		
		List<Contract> list = repo.findByIdWorker(idWorker);
		String result = "";
		
		if(!list.isEmpty()){
			result = new Gson().toJson(list);
		}
		else result = idWorker + " not found!";
		return Response.status(200).entity(result).build();
	}
	
	/** Query: db.collection.find({identificativo_azienda: idAgency}) **/
	@Path("/{idAgency: [P][G][0-9]+}")
	@GET
	public Response findAgency(@PathParam("idAgency") String idAgency) throws JsonSyntaxException{
		
		List<Contract> list = repo.findByIdAgency(idAgency);
		String result = "";
		
		if(!list.isEmpty()){
			result = new Gson().toJson(list);
		}
		else result = idAgency + " not found!";
		return Response.status(200).entity(result).build();
	}
	
	/** Query: db.collection.find({tipologia_contrattuale: contractType}) **/
	@Path("/{contractType: [A-Z].\\d\\d.\\d\\d}")
	@GET
	public Response findType(@PathParam("contractType") String type) throws JsonSyntaxException{
	
		List<Contract> list = repo.findByContractType(type);
		String result = "";
		
		if(!list.isEmpty()){
			result = new Gson().toJson(list);
		}
		else result = type + " not found!";
		return Response.status(200).entity(result).build();
	}
	
	
}
