package com.myexercise.contracts;

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
	
	@Query(value="{}", fields = "{'_id' : 0}")
	public List<Contract> findAll();
	
	@Query()
	public Page<Contract> findAll(Pageable pageable);
	
	@Query(value = "{'identificativo_lavoratore' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByIdWorker(String idWorker);
	
	@Query(value = "{'identificativo_azienda' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByIdAgency(String idAgency);
	
	@Query(value = "{'data_inizio' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByStartDate(String startDate);
	
	@Query(value = "{'data_fine' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByEndDate(String endDate);
	
	@Query(value = "{'cittadinanza' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByNation(String nation);
	
	@Query(value = "{'tipologia_contrattuale' : ?0}", fields = "{'_id' : 0}")
	public List<Contract> findByContractType(String contractType);
	
}
