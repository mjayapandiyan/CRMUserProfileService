package com.crm.app.user.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.app.user.profile.model.UserInterfaceConfig;

@Repository
public interface ConfigRepository extends JpaRepository<UserInterfaceConfig, Long> {

	public Optional<UserInterfaceConfig> findByConfigId(long configId);
}
