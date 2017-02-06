package com.myexercise.contracts.rest;


/**
 * @author Blasi Francesco
 */


import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.springframework.stereotype.Component;
import com.myexercise.contracts.Contract;
import com.myexercise.contracts.ContractRepository;

@Component
@Path("contracts")
@Produces("application/json")
public class ContractService {
	
	private final ContractRepository repo;
	private final String DATE_FORMAT = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-((19|20)\\d\\d)";
	
	@Autowired
	ContractService(ContractRepository repo){
		this.repo = repo;
	}
	
	@GET
	public Response findAll() throws JSONException{
		
		List<Contract> list = repo.findAll();
		String result = "";
		for(Contract obj : list) result += obj.toString();
		
		return Response.status(200).entity(result).build();
	}
	
	@Path("/date/ended")
	@GET
	public Response findEnded(
		@DefaultValue("01-01-2014")@QueryParam("di: "+DATE_FORMAT) String startDate,
		@DefaultValue("")@QueryParam("df: "+DATE_FORMAT) String endDate) throws JSONException{
		
		//MongoOperations mongo = (MongoOperations) ctx.getBean("mongoTemplate");
		List<Contract> list = repo.findAll();
		String result = "";
		Calendar start = new GregorianCalendar(
				Integer.parseInt(startDate.substring(6)),
				Integer.parseInt(startDate.substring(3, 5)),
				Integer.parseInt(startDate.substring(0, 2)));
		if(!endDate.equals("")){
			Calendar end = new GregorianCalendar(
					Integer.parseInt(endDate.substring(6)),
					Integer.parseInt(endDate.substring(3, 5)),
					Integer.parseInt(endDate.substring(0, 2)));
			for(Contract obj : list){
				if(obj.getStart().compareTo(start) >= 0 && obj.getEnd().compareTo(end) <= 0)
					result += obj.toString();
			}
		}
		else{
			for(Contract obj : list){
				if(obj.getStart().compareTo(start) >= 0)
					result += obj.toString();
			}
		}
				
		return Response.status(200).entity(result).build();
	}
	
	@Path("/date")
	@GET
	public Response findFromDatetoDate(
		@DefaultValue("01-01-2014")@QueryParam("di1: "+DATE_FORMAT) String sd1,
		@DefaultValue("")@QueryParam("di2: "+DATE_FORMAT) String sd2){
		List<Contract> list;
		String result = "";
		if(!sd1.equals(sd2)){
			Calendar start1 = new GregorianCalendar(
				Integer.parseInt(sd1.substring(6)),
				Integer.parseInt(sd1.substring(3, 5)),
				Integer.parseInt(sd1.substring(0, 2)));
			if(!sd2.equals("")){
				Calendar start2 = new GregorianCalendar(
					Integer.parseInt(sd2.substring(6)),
					Integer.parseInt(sd2.substring(3, 5)),
					Integer.parseInt(sd2.substring(0, 2)));
				int c = start1.compareTo(start2);
				if(c < 0){
					list = repo.findAll();
					for(Contract obj : list){
						if(obj.getStart().compareTo(start1) >= 0 && obj.getStart().compareTo(start2) <= 0)
							result += obj.toString();
					}
				}else return Response.status(400).entity("intervallo di date non valido").build();
			}else{
				list = repo.findAll();
				for(Contract obj : list){
					if(obj.getStart().compareTo(start1) >= 0) result += obj.toString();
				}
			}
		}
		return Response.status(200).entity(result).build();
	}
	
	@Path("/{idWorker: [PF][0-9]+}")
	@GET
	public Response findIdWorker(@PathParam("idWorker") String idWorker) throws JSONException{
		
		List<Contract> list = repo.findByIdWorker(idWorker);
		String result = "";
		for(Contract obj : list) result += obj.toString();
	
		return Response.status(200).entity(result).build();
	}
	
	@Path("/{idAgency: [PG][0-9]+}")
	@GET
	public Response findAgency(@PathParam("idAgency") String idAgency) throws JSONException{
		
		List<Contract> list = repo.findByIdAgency(idAgency);
		String result = "";
		for(Contract obj : list) result += obj.toString();
	
		return Response.status(200).entity(result).build();
	}
	
	@Path("/{tipo_contratto: [A-Z].\\d\\d.\\d\\d}")
	@GET
	public Response findType(@PathParam("tipo_contratto") String type) throws JSONException{
	
		List<Contract> list = repo.findByContractType(type);
		String result = "";
		for(Contract obj : list) result += obj.toString();
		
		return Response.status(200).entity(result).build();
	}
}
