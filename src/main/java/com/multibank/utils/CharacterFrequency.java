package com.multibank.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CharacterFrequency {

    private CharacterFrequency() {
    }

    public static Map<Character, Integer> getFrequency(String input) {
        if (input == null || input.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Character, Integer> frequency = new LinkedHashMap<>();
        for (char c : input.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        return frequency;
    }

    public static String formatOutput(Map<Character, Integer> frequencyMap) {
        if (frequencyMap == null || frequencyMap.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append(":").append(entry.getValue());
        }
        return sb.toString();
    }
}

