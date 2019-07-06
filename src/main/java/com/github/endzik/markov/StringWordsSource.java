package com.github.endzik.markov;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringWordsSource implements WordsSource {

    private String source;

    public StringWordsSource(String source) {
        this.source = source;
    }

    @Override
    public Set<String> getUniqueWords() {
        return new HashSet<>(getAllWords());
    }

    @Override
    public Map<String, Integer> getFollowUpWords(String word) {
        List<String> allWords = getAllWords();
        Map<String, Integer> followUps = new HashMap<>();
        for (int i=0 ; i<allWords.size() - 1 ; i++) {
            if (allWords.get(i).equals(word)) {
                String followUp = allWords.get(i + 1);
                followUps.merge(followUp, 1, Integer::sum);
            }
        }
        return followUps;
    }

    private List<String> getAllWords() {
        return Arrays.asList(source.split("\\s+|-"));
    }
}
