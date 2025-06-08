package com.aim.capstoneuserservice.repositories;

import com.aim.capstoneuserservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValueAndIsDeletedAndExpiryAtGreaterThan(String value,
                                                                boolean deleted,
                                                                Date expiryAt);

}
