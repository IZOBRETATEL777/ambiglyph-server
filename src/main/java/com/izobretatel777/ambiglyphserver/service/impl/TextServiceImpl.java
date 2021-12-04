package com.izobretatel777.ambiglyphserver.service.impl;

import com.izobretatel777.ambiglyphserver.dao.entity.Word;
import com.izobretatel777.ambiglyphserver.dao.repo.UserRepo;
import com.izobretatel777.ambiglyphserver.dao.repo.WordRepo;
import com.izobretatel777.ambiglyphserver.dto.TextRequestDto;
import com.izobretatel777.ambiglyphserver.dto.TextResponseDto;
import com.izobretatel777.ambiglyphserver.service.TextService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {
    private final WordRepo wordService;

    @Override
    public TextResponseDto recoverText(TextRequestDto textRequestDto) {
        List<String> text = Arrays.asList(textRequestDto.getText().split("\\b"));
        List<String> dictionary = new ArrayList<>();
        for (Word word: wordService.findAll()) {
            if (word.getUsers().stream().map(u->u.getId()).anyMatch(id->id == textRequestDto.getUserId() || id == 1))
                dictionary.add(word.getText());
        }
        List<Integer> distances = new ArrayList<>(dictionary.size());
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        List<List<String>> candidates = new ArrayList<>();
        final String META = "<%%ambiglyph>%d<ambiglyph%%>";
        int candidateIdx = 0;
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).isBlank())
                continue;;
            for (int j = 0; j < dictionary.size(); j++) {
                if (distances.size() == j) {
                    distances.add(0);
                }
                distances.set(j, levenshteinDistance.apply(text.get(i), dictionary.get(j)));
            }
             if (!distances.isEmpty()) {
                 minDist= Collections.min(distances);
             }
            if (minDist != 0) {
                candidates.add(new ArrayList<>());
                for (int j = 0; j < distances.size(); j++) {
                    if (distances.get(j) == minDist) {
                        candidates.get(candidateIdx).add(dictionary.get(j));
                    }
                }
                text.set(i, String.format(META, candidateIdx));
                candidateIdx++;
            }
        }

        return TextResponseDto.builder()
                .text(String.join("", text))
                .candidates(candidates).build();
    }
}
