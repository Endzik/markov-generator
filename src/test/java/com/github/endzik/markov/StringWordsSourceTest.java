package com.github.endzik.markov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StringWordsSourceTest {

    private WordsSource wordsSource = new StringWordsSource("One Two Three One Two Four One One Four One Five");

    @Test
    void shouldReadAllUniqueWords() {
        assertNotNull(wordsSource.getUniqueWords());
        assertAll(
                () -> assertTrue(wordsSource.getUniqueWords().contains("One")),
                () -> assertTrue(wordsSource.getUniqueWords().contains("Two")),
                () -> assertTrue(wordsSource.getUniqueWords().contains("Three")),
                () -> assertTrue(wordsSource.getUniqueWords().contains("Four")),
                () -> assertTrue(wordsSource.getUniqueWords().contains("Five"))
        );
    }

    @Test
    void shouldCorrectlyCountFollowUpWords() {
        Map<String, Integer> oneFollowUps = wordsSource.getFollowUpWords("One");
        assertNotNull(oneFollowUps);
        assertAll(
                () -> assertEquals(1, oneFollowUps.get("One")),
                () -> assertEquals(2, oneFollowUps.get("Two")),
                () -> assertNull( oneFollowUps.get("Three")),
                () -> assertEquals(1, oneFollowUps.get("Four")),
                () -> assertEquals(1, oneFollowUps.get("Five"))
        );
    }
}