package com.github.endzik.markov;

public interface SentenceGenerator {

    void buildDictionary(WordsSource... sources);

    String generateSentence();

    String generateSentence(String startingWord);
}
