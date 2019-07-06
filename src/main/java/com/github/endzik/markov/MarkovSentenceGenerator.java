package com.github.endzik.markov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MarkovSentenceGenerator implements SentenceGenerator {

    private Map<String, Map<String, Integer>> dictionary = new HashMap<>();

    @Override
    public void buildDictionary(WordsSource... sources) {
        if (sources != null) {
            for (WordsSource source : sources) {
                if (source != null) {
                    addWordsFromSource(source);
                }
            }
        }
    }

    @Override
    public String generateSentence() {
        String word = randomStartingWord();
        return generateSentence(word);
    }

    @Override
    public String generateSentence(String startingWord) {
        String word = startingWord;
        StringBuilder builder = new StringBuilder(word);
        while (dictionary.get(word) != null) {
            word = randomFollowUp(word);
            if (word == null) {
                break;
            }
            builder.append(" ").append(word);
            if (word.endsWith(".")) {
                break;
            }
        }
        String sentence = builder.toString();
        if (!sentence.endsWith(".")) {
            sentence += ".";
        }
        System.out.println(sentence);
        return sentence;
    }

    private String randomFollowUp(String word) {
        Map<String, Integer> followUps = dictionary.get(word);
        int totalWeight = followUps.values().stream().mapToInt(Integer::intValue).sum();
        List<String> words = new ArrayList<>(followUps.keySet());
        double randomWeight = Math.random() * totalWeight;
        for (String candidate : words) {
            randomWeight -= followUps.get(candidate);
            if (randomWeight <= 0.0d) {
                return candidate;
            }
        }
        return null;
    }

    private String randomStartingWord() {
        List<String> startingWords = new ArrayList<>(dictionary.keySet());
        startingWords.removeIf(word -> word.isEmpty() || !Character.isUpperCase(word.charAt(0)) || word.endsWith("."));
        return startingWords.get(new Random().nextInt(startingWords.size()));
    }

    private void addWordsFromSource(WordsSource source) {
        Set<String> words = source.getUniqueWords();
        words.forEach(word -> addWordFromSource(word, source));
    }

    private void addWordFromSource(String word, WordsSource source) {
        Map<String, Integer> newFollowUps = source.getFollowUpWords(word);
        Map<String, Integer> followUps = dictionary.get(word);
        if (followUps == null) {
            dictionary.put(word, newFollowUps);
        } else {
            newFollowUps.forEach((newWord, count) -> followUps.merge(newWord, count, Integer::sum));
        }
    }

}
