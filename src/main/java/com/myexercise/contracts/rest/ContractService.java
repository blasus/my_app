package com.myexercise.contracts.rest;

/**
 * @author Blasi Francesco
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
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import com.myexercise.contracts.Contract;
import com.myexercise.contracts.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("contracts")
@Produces("application/json")
public class ContractService {
	
	private final ContractRepository repo;
	static final String DATE_FORMAT = "dd/MM/yyyy";
	static final Logger logger = LoggerFactory.getLogger(ContractService.class);
	
	@Autowired
	ContractService(ContractRepository repo){
		this.repo = repo;
	}
	
	
	/** Query: db.collection.find({_id : 0}) **/
	@GET
	public Response findAll() throws JSONException{
		
		List<Contract> list = repo.findAll();
		String result = "";
		for(Contract obj : list){
			result += obj.toString();
			/* test my query */
			logger.info(obj.toString());
		}
		
		return Response.status(200).entity(result).build();
	}
	
	
	/** Query: db.collection.find({_id : 0}).limit(pageSize) **/
	@Path("/page/")
	@GET
	public Response findAll(
			@DefaultValue("0")@QueryParam("p") int nPage,
			@DefaultValue("50")@QueryParam("l") int length) throws JSONException{
		
		List<Contract> page = repo.findAll(new PageRequest(nPage,length)).getContent();
		String result = "";
		/* test my query */
		logger.debug("risultati restituiti: {}. \n",page.size());
		
		for(Contract obj : page){
			result += obj.toString();
			/* test my query */
			logger.info(obj.toString());
		}
		
		return Response.status(200).entity(result).build();
	}
	
	
	/** Query: db.collection.find({$and: [{data_inizio: {$gt: start}},{data_fine: {$lt: end}]},{_id : 0}) **/
	@Path("/date/period/")
	@GET
	public Response findByPeriod(
		@DefaultValue("01/01/2014")@QueryParam("di") String startDate,
		@DefaultValue("")@QueryParam("df") String endDate) throws JSONException{
		
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date start = null;
		try {
			start = formatter.parse(startDate);
		} catch (ParseException e) {
			logger.error("Parsing startDate: {}. ",e.getMessage());
			return Response.status(500).entity("Http error: 500 - Bad request for startDate").build();
		}
		List<Contract> list;
		String result = "";
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
		for(Contract obj : list){
			result += obj.toString();
			/* test my query */
			logger.info(obj.toString());
		}
				
		return Response.status(200).entity(result).build();
	}
	
	
	/** Query: db.collection.find({$and: [{data_inizio: {$gt: data1}},{data_inizio: {$lt: data2}}]}) **/
	@Path("/date/")
	@GET
	public Response findBetweenStartDates(
		@DefaultValue("01-01-2014")@QueryParam("from") String sd1,
		@DefaultValue("")@QueryParam("to") String sd2){
		
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date start1 = null;
		try {
			start1 = formatter.parse(sd1);
		} catch (ParseException e) {
			logger.error("Parsing startDate1: {}. ",e.getMessage());
			return Response.status(500).entity("Http error: 500 - Bad request for startDate").build();
		}
		List<Contract> list;
		String result = "";
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
		for(Contract obj : list){
			result += obj.toString();
			/* test my query */
			logger.info(obj.toString());
		}
		
		return Response.status(200).entity(result).build();
	}
	
	/** Query: db.collection.find({identificativo_lavoratore: idWorker}) **/
	@Path("/{idWorker: [P][F][0-9]+}")
	@GET
	public Response findWorker(@PathParam("idWorker") String idWorker) throws JSONException{
		
		List<Contract> list = repo.findByIdWorker(idWorker);
		String result = "";
		
		if(!list.isEmpty()) for(Contract obj : list) result += obj.toString();
		else result = idWorker + " not found!";
	
		return Response.status(200).entity(result).build();
	}
	
	/** Query: db.collection.find({identificativo_azienda: idAgency}) **/
	@Path("/{idAgency: [P][G][0-9]+}")
	@GET
	public Response findAgency(@PathParam("idAgency") String idAgency) throws JSONException{
		
		List<Contract> list = repo.findByIdAgency(idAgency);
		String result = "";
		
		if(!list.isEmpty()) for(Contract obj : list) result += obj.toString();
		else result = idAgency + " not found!";
	
		return Response.status(200).entity(result).build();
	}
	
	/** Query: db.collection.find({tipologia_contrattuale: contractType}) **/
	@Path("/{contractType: [A-Z].\\d\\d.\\d\\d}")
	@GET
	public Response findType(@PathParam("contractType") String type) throws JSONException{
	
		List<Contract> list = repo.findByContractType(type);
		String result = "";
		
		if(!list.isEmpty()) for(Contract obj : list) result += obj.toString();
		else result = type + "not found!";
		
		return Response.status(200).entity(result).build();
	}
	
	
}
