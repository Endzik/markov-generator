package com.github.endzik.markov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarkovSentenceGeneratorTest {

    private SentenceGenerator generator = new MarkovSentenceGenerator();

    private List<WordsSource> sources = new ArrayList<>();

    @BeforeEach
    void buildDictionary() throws IOException {
        sources.add(new StringWordsSource(TestWords.DESIDERATA));
        sources.add(new WebPageWordsSource("http://e-opowiadania.pl/opowiadania/kryminal/122-historia-mercedes"));
        sources.forEach(generator::buildDictionary);
    }

    @Test
    void shouldGenerateASentenceWithWordsFromDictionary() {
        String sentence = generator.generateSentence();
        assertNotNull(sentence);
        assertFalse(sentence.isEmpty());
        Arrays.asList(sentence.split(" "))
                .forEach(word -> assertTrue(sources.stream().anyMatch(source -> source.getUniqueWords().contains(word)), "Unknown word: " + word));
    }

    @Test
    void shouldEndSentenceWithADot() {
        String sentence = generator.generateSentence();
        assertNotNull(sentence);
        assertTrue(sentence.endsWith("."));
    }

    @Test
    void shouldGenerateASingleSentence() {
        String sentence = generator.generateSentence();
        assertNotNull(sentence);
        assertEquals(sentence.length() - 1, sentence.indexOf("."));
    }

    @Test
    void shouldStartWithACapitalLetter() {
        String sentence = generator.generateSentence();
        assertNotNull(sentence);
        assertTrue(Character.isUpperCase(sentence.charAt(0)));
    }

    @Test
    void shouldNotGenerateOneWordSentences() {
        String sentence = generator.generateSentence();
        assertNotNull(sentence);
        assertTrue(sentence.contains(" "));
    }

    @Test
    void shouldGenerateSentenceWithStartingWord() {
        String sentence = generator.generateSentence("Lecz");
        assertTrue(sentence.startsWith("Lecz"));
    }

    @Test
    void shouldBuildDictionaryFromMultipleSources() {
        assertDoesNotThrow(() -> {
            generator.buildDictionary(new StringWordsSource("Some source"), new StringWordsSource("Another source"));
            generator.buildDictionary(new StringWordsSource("Yet another source"));
        });
    }

    @Test
    void shouldHandleNullSource() {
        assertDoesNotThrow(() -> generator.buildDictionary((WordsSource) null));
    }

}