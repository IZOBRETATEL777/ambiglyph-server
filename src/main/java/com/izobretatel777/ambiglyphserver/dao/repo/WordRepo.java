package com.izobretatel777.ambiglyphserver.dao.repo;

import com.izobretatel777.ambiglyphserver.dao.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepo extends JpaRepository<Word, Long> {
}
