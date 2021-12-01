package com.izobretatel777.ambiglyphserver.service.impl;

import com.izobretatel777.ambiglyphserver.dao.entity.User;
import com.izobretatel777.ambiglyphserver.dao.entity.Word;
import com.izobretatel777.ambiglyphserver.dao.repo.UserRepo;
import com.izobretatel777.ambiglyphserver.dao.repo.WordRepo;
import com.izobretatel777.ambiglyphserver.dto.WordRequestDto;
import com.izobretatel777.ambiglyphserver.dto.WordResponseDto;
import com.izobretatel777.ambiglyphserver.mapper.WordMapper;
import com.izobretatel777.ambiglyphserver.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WordServiceImpl implements WordService {
    private final WordRepo wordRepo;
    private final WordMapper wordMapper;
    private final UserRepo userRepo;

    @Override
    public List<WordResponseDto> getWords() {
        var entities = wordRepo.findAll();
        var response = wordMapper.toResponseDto(entities);
        return response;
    }

    @Override
    public WordResponseDto getWordById(Long id) {
        var entity = wordRepo.findById(id).get();
        var response = wordMapper.toResponseDto(entity);
        return response;
    }

    @Override
    public Long saveWord(WordRequestDto wordRequestDto) {
        Word word = null;
        var matches = wordRepo.findAll().stream().filter(c->c.getText().equals(wordRequestDto.getText())).collect(Collectors.toList());
        if (!matches.isEmpty()) {
            word = matches.get(0);
        }
        else {
            word = new Word();
            word.setText(wordRequestDto.getText());
            word.setUsers(new LinkedList<User>());
        }
        word.getUsers().add(userRepo.getById(wordRequestDto.getUserId()));
        return wordRepo.save(word).getId();
    }

    @Override
    public void deleteWordById(Long id) {
        wordRepo.deleteById(id);
    }
}
