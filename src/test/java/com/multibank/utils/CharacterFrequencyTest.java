package com.multibank.utils;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterFrequencyTest {

    @Test
    void helloWorld_frequency_preservesFirstAppearanceOrder() {
        Map<Character, Integer> expected = new LinkedHashMap<>();
        expected.put('h', 1);
        expected.put('e', 1);
        expected.put('l', 3);
        expected.put('o', 2);
        expected.put(' ', 1);
        expected.put('w', 1);
        expected.put('r', 1);
        expected.put('d', 1);

        Map<Character, Integer> actual = CharacterFrequency.getFrequency("hello world");

        assertEquals(expected, actual);
        assertEquals("h:1, e:1, l:3, o:2,  :1, w:1, r:1, d:1", CharacterFrequency.formatOutput(actual));
    }

    @Test
    void emptyString_returnsEmptyMap() {
        Map<Character, Integer> frequency = CharacterFrequency.getFrequency("");

        assertTrue(frequency.isEmpty());
        assertEquals("", CharacterFrequency.formatOutput(frequency));
    }
}

