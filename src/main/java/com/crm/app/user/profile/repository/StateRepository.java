package com.crm.app.user.profile.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.app.user.profile.model.State;

@Repository
public interface StateRepository extends CrudRepository<State, Long>{

	@Query(value = "select t2.* from country t1, state t2 where t1.country_id = t2. country_id and t2.country_id = :countryId", nativeQuery=true)
	List<State> findByState(@Param("countryId") long countryId);
	
	@Query(value = "SELECT t2 FROM Country t1 inner join State t2 on t1.countryId = t2.country.countryId and t1.countryId= :countryId")
	List<State> fetchStatesByCountry(@Param("countryId") long countryId);
	
}
