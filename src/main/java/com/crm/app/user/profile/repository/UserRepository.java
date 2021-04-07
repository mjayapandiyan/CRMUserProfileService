package com.crm.app.user.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.crm.app.user.profile.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	Optional<User> findByEmailId(String emailId);
	
	@Modifying
	@Query("update User u set u.loggedInDate =:loggedInDate where u.emailId =:emailId")
	void updateLoginTime(@Param("loggedInDate") String loggedInDate, @Param("emailId") String emailId);
	
	@Modifying(clearAutomatically = true)
	@Query("update User u set u.image =:image, filename =:filename where u.userId =:userId")
	int updateUserImage(@Param("image") byte[] image, @Param("filename") String filename, @Param("userId") long userId);
	
}
