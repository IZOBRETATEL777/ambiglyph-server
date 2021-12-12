package com.izobretatel777.ambiglyphserver.service.impl;

import com.izobretatel777.ambiglyphserver.dao.entity.Word;
import com.izobretatel777.ambiglyphserver.dao.repo.UserRepo;
import com.izobretatel777.ambiglyphserver.dao.repo.WordRepo;
import com.izobretatel777.ambiglyphserver.dto.WordRequestDto;
import com.izobretatel777.ambiglyphserver.dto.WordResponseDto;
import com.izobretatel777.ambiglyphserver.mapper.WordMapper;
import com.izobretatel777.ambiglyphserver.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WordServiceImpl implements WordService {
    private final WordRepo wordRepo;
    private final WordMapper wordMapper;
    private final UserRepo userRepo;

    @Override
    public List<WordResponseDto> getWords() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        var entities = wordRepo.findWordsByUserId(userRepo.findIdByLogin(currentPrincipalName));
        return wordMapper.toResponseDto(entities);
    }

    @Override
    public WordResponseDto getWordById(Long id) {
        var entity = wordRepo.findById(id);
        return entity.map(wordMapper::toResponseDto).orElse(null);
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
            word.setUsers(new LinkedList<>());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = userRepo.findIdByLogin(authentication.getName());
        word.getUsers().add(userRepo.getById(userId));
        return wordRepo.save(word).getId();
    }

    @Override
    public void deleteWordById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        List<Word> entities = wordRepo.findWordsByUserId(userRepo.findIdByLogin(currentPrincipalName));
        if (entities.stream().anyMatch(w-> Objects.equals(w.getId(), id))) {
            wordRepo.deleteById(id);
        }
        else {
            throw new AccessDeniedException("");
        }
    }
}
