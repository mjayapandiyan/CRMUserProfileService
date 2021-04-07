package com.crm.app.user.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.app.user.profile.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
