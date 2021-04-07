package com.crm.app.user.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.crm.app.user.profile.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{
	
}
