package com.github.endzik.markov;

import java.util.Map;
import java.util.Set;

public interface WordsSource {

    Set<String> getUniqueWords();

    Map<String, Integer> getFollowUpWords(String word);
}
