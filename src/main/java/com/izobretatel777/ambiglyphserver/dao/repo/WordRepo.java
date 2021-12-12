package com.izobretatel777.ambiglyphserver.dao.repo;

import com.izobretatel777.ambiglyphserver.dao.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordRepo extends JpaRepository<Word, Long> {
    @Query(value = "select word.word from word join user where user.id=?1", nativeQuery = true)
    List<String> findWordsTextByUserId(Long id);
}
