package com.andrew.natterbase.repository;

import com.andrew.natterbase.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

/**
 * @author andrew
 */
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    UserAccount findByUsername(String username);
}