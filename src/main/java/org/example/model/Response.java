package org.example.model;

import java.util.*;

public class Response {

    private final String text;
    private final Map<Character, Integer> uniqueCharacters = new TreeMap<>();

    public Response(String text) {
        this.text = text;
        countUniqueCharacters();
    }

    public String getText() {
        return text;
    }

    private void countUniqueCharacters() {
        char[] chars = text.replaceAll("[^a-zA-Z0-9]","").toCharArray();

        for (char c : chars) {
            if(!uniqueCharacters.containsKey(c)){
                uniqueCharacters.put(c, 1);
            }
            else {
                uniqueCharacters.put(c, uniqueCharacters.get(c) + 1);
            }
        }
    }

    public Map<Character, Integer> getUniqueCharacters() {
        return new TreeMap<>(uniqueCharacters);
    }
}
