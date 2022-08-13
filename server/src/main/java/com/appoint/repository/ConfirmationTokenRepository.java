package com.appoint.repository;

import com.appoint.model.ConfirmationToken;
import com.appoint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    ConfirmationToken findByUser(User user);
    @Override
    void delete(ConfirmationToken entity);
}
