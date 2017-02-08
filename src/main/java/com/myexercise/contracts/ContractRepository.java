package com.myexercise.contracts;

import java.util.Date;

/**
 * @author Blasi Francesco
 */

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends MongoRepository<Contract, String>, PagingAndSortingRepository<Contract, String> {
	
	@Query(value = "{}", fields = "{'_id' : 0}")
	public List<Contract> findAll();
	
	@Query(value = "{}", fields = "{'_id' : 0}")
	public Page<Contract> findAll(Pageable pageable);
	
	@Query(value = "{'$and' : [{'data_inizio' : {'$gt' : ?0}},{'data_fine' : {'$lt' : ?1}}]}", fields = "{'_id' : 0}")
	public List<Contract> findByStartDateAfterAndEndDateBefore(Date startDate, Date endDate);
	
	@Query(value = "{'$and' : [{'data_inizio' : {'$gt' : ?0}},{'data_inizio' : {'$lt' : ?1}}]}", fields = "{'_id' : 0}")
	public List<Contract> findByStartDateBetween(Date from, Date to);
	
	@Query(value = "{'identificativo_lavoratore' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByIdWorker(String idWorker);
	
	@Query(value = "{'identificativo_azienda' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByIdAgency(String idAgency);
	
	@Query(value = "{'data_inizio' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByStartDate(Date startDate);
	
	@Query(value = "{'data_inizio' : {'$gt' : ?0}}", fields = "{'_id' : 0}")
	public List<Contract> findByStartDateAfter(Date startDate);
	
	@Query(value = "{'data_fine' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByEndDate(String endDate);
	
	@Query(value = "{'cittadinanza' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByNation(String nation);
	
	@Query(value = "{'tipologia_contrattuale' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByContractType(String contractType);
	
}
