package com.izobretatel777.ambiglyphserver.service.impl;

import com.izobretatel777.ambiglyphserver.dao.entity.Homoglyph;
import com.izobretatel777.ambiglyphserver.dao.entity.Word;
import com.izobretatel777.ambiglyphserver.dao.repo.HomoglyphRepo;
import com.izobretatel777.ambiglyphserver.dao.repo.WordRepo;
import com.izobretatel777.ambiglyphserver.dto.HomoglyphResponseDto;
import com.izobretatel777.ambiglyphserver.dto.TextRequestDto;
import com.izobretatel777.ambiglyphserver.dto.TextResponseDto;
import com.izobretatel777.ambiglyphserver.mapper.HomoglyphMapper;
import com.izobretatel777.ambiglyphserver.service.TextService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Character.*;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {
    private final WordRepo wordService;
    private final HomoglyphRepo homoglyphRepo;
    private final HomoglyphMapper homoglyphMapper;

    final String META_DETECTED = "<%%ambiglyph-detected>%d<ambiglyph-detected%%>";
    final String META_WARNING = "<%%ambiglyph-warning>%d<ambiglyph-warning%%>";

    @Override
    public TextResponseDto recoverText(TextRequestDto textRequestDto) {
        List<String> text = Arrays.asList(textRequestDto.getText().split("\\b"));
        List<String> dictionary = new ArrayList<>();
        List<Homoglyph> homoglyphList = homoglyphRepo.findAll()
                .stream().filter(h->textRequestDto.getText().indexOf(h.getSpoofed()) != -1).collect(Collectors.toList());
        for (Word word: wordService.findAll()) {
            if (word.getUsers().stream().map(u->u.getId()).anyMatch(id->id == textRequestDto.getUserId() || id == 1))
                dictionary.add(word.getText());
        }
        List<Integer> distances = new ArrayList<>(dictionary.size());
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        List<List<String>> candidates = new ArrayList<>();
        List<List<HomoglyphResponseDto>> warnings = new ArrayList<>();

        int candidateIdx = 0;
        int warningIdx = 0;
        int minDist = Integer.MAX_VALUE;

        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).isBlank())
                continue;
            for (int j = 0; j < dictionary.size(); j++) {
                if (distances.size() == j) {
                    distances.add(0);
                }
                distances.set(j, levenshteinDistance.apply(text.get(i).toLowerCase(Locale.ROOT), dictionary.get(j)));
            }
             if (!distances.isEmpty()) {
                 minDist= Collections.min(distances);
             }
            if (minDist != 0) {
                List<String> candidatesForWord = new ArrayList<>();
                for (int j = 0; j < distances.size(); j++) {
                    if (distances.get(j) == minDist) {
                        candidatesForWord.add(normalizeCase(dictionary.get(j), text.get(i)));
                    }
                }
                if (!candidatesForWord.isEmpty() && candidatesForWord.size() <= textRequestDto.getSuggestionsNumber()) {
                    text.set(i, String.format(META_DETECTED, candidateIdx));
                    candidates.add(candidatesForWord);

                } else  {
                    String mutable = text.get(i);
                    for (int j = 0; j < mutable.length(); j++) {
                        char curChar = mutable.charAt(j);
                        List<Homoglyph> alike = homoglyphList.stream().filter(h->h.getSpoofed() == curChar).collect(Collectors.toList());
                        if (!alike.isEmpty()) {
                            warnings.add(alike.stream().map(homoglyphMapper::toResponseDto).collect(Collectors.toList()));
                            mutable = mutable.replace(String.valueOf(curChar), String.format(META_WARNING, warningIdx));
                            warningIdx++;
                            text.set(i, mutable);
                        }
                        if (warnings.size() > textRequestDto.getWarningsNumber()) {
                            warnings.clear();
                        }
                    }
                }
                candidateIdx++;
            }
        }

        return TextResponseDto.builder()
                .text(String.join("", text))
                .candidates(candidates).haveDetections(candidateIdx > 0)
                .warnings(warnings).haveWarnings(warningIdx > 0).build();
    }

    private String normalizeCase(String word, String patterWord) throws IllegalArgumentException {
        if (word.length() != patterWord.length()) {
            throw new IllegalArgumentException();
        }
        StringBuilder mutable = new StringBuilder(word);
        for (int i = 0; i < patterWord.length(); i++) {
            if (isUpperCase(word.charAt(i)) && isLowerCase(mutable.charAt(i))) {
                mutable.setCharAt(i, toUpperCase(mutable.charAt(i)));
            }
            else if (isLowerCase(word.charAt(i)) && isUpperCase(mutable.charAt(i))) {
                mutable.setCharAt(i, toLowerCase(mutable.charAt(i)));
            }
        }
        return mutable.toString();
    }

}
