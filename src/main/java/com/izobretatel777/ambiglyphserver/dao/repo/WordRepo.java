package com.izobretatel777.ambiglyphserver.dao.repo;

import com.izobretatel777.ambiglyphserver.dao.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordRepo extends JpaRepository<Word, Long> {
    @Query(value = "select word.word from user_word join word on word.id=user_word.word_id where user_word.user_id=?1", nativeQuery = true)
    List<String> findWordsTextByUserId(Long id);

    @Query(value = "select word.id, word.word from user_word join word on word.id=user_word.word_id where user_word.user_id=?1", nativeQuery = true)
    List<Word> findWordsByUserId(Long id);

    @Query(value = "select word.id, word.word from user_word join word on word.id=user_word.word_id where user_word.user_id=?1 and word.word=?2", nativeQuery = true)
    Long findWordsByUserIdAndText(Long userId, String text);
}
