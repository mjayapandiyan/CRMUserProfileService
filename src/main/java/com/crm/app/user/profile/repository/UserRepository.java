package com.crm.app.user.profile.repository;

import java.math.BigDecimal;
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
	@Query("update User u set u.image =:image, filename =:filename, fileType =:fileType where u.userId =:userId")
	int updateUserImage(@Param("image") byte[] image, @Param("filename") String filename, @Param("fileType") String fileType, @Param("userId") long userId);
	
	@Query(value = "select user_id_seq.nextval from dual", nativeQuery = true)
    public Long getNextSequenceNumber();
}
