package com.crm.app.user.profile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.crm.app.user.profile.model.City;

@Repository
public interface CityRepository extends CrudRepository<City, Long>{

	@Query("FROM City c join State s on c.state.stateId = s.stateId"
			+ " and s.stateId = :stateId")
	List<City> findCities(@Param("stateId") long stateId);

}
