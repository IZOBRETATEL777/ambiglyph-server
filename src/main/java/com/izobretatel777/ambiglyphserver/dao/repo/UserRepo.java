package com.izobretatel777.ambiglyphserver.dao.repo;

import com.izobretatel777.ambiglyphserver.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
