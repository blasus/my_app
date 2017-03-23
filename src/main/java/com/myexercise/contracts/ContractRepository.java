package com.myexercise.contracts;

/**
 * @author Blasi Francesco
 * @version 0.0.1-SNAPSHOT
 */

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to access domain entities stored in MongoDB. 
 */

@Repository
public interface ContractRepository extends MongoRepository<Contract, String>, PagingAndSortingRepository<Contract, String> {
	
	// Used for test.
	@Query(value = "{}", fields = "{'_id' : 0}")
	public List<Contract> findAll();
	
	/* This query returns all entries organized in pages. 
	 * Pageable includes also sorting options.
	 */
	@Query(value = "{}", fields = "{'_id' : 0}")
	public Page<Contract> findAll(Pageable pageable);
	
	//This query simply counts the entries stored in DB.
	@Query(value = "{}", count = true)
	public long count();
	
	//This query will be used for full text search. 
	@Query(value = "{}", fields = "{'_id' : 0}")
	Page<Contract> findAllBy(TextCriteria criteria, Pageable pageable);
	
	//Useless at the moment
	@Query(value = "{'$and' : [{'data_inizio' : {'$gt' : ?0}},{'data_fine' : {'$lt' : ?1}}]}", fields = "{'_id' : 0}")
	public List<Contract> findByStartDateAfterAndEndDateBefore(Date startDate, Date endDate);
	
	//Useless at the moment
	@Query(value = "{'$and' : [{'data_inizio' : {'$gt' : ?0}},{'data_inizio' : {'$lt' : ?1}}]}", fields = "{'_id' : 0}")
	public List<Contract> findByStartDateBetween(Date from, Date to);
	
	//Useless at the moment
	@Query(value = "{'identificativo_lavoratore' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByIdWorker(String idWorker);
	
	//Useless at the moment
	@Query(value = "{'identificativo_azienda' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByIdAgency(String idAgency);
	
	//Useless at the moment
	@Query(value = "{'data_inizio' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByStartDate(Date startDate);
	
	//Useless at the moment
	@Query(value = "{'data_inizio' : {'$gt' : ?0}}", fields = "{'_id' : 0}")
	public List<Contract> findByStartDateAfter(Date startDate);
	
	//Useless at the moment
	@Query(value = "{'data_fine' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByEndDate(String endDate);
	
	//Useless at the moment
	@Query(value = "{'cittadinanza' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByNation(String nation);
	
	//Useless at the moment
	@Query(value = "{'tipologia_contrattuale' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByContractType(String contractType);
	
	//Useless at the moment
	@Query(value = "{'anno_nascita' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByBirth(int birth);
		
}
