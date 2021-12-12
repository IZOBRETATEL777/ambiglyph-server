package com.izobretatel777.ambiglyphserver.dao.repo;

import com.izobretatel777.ambiglyphserver.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @Query(value = "select id from user where login=?1", nativeQuery = true)
    Long findIdByLogin(String login);
}
