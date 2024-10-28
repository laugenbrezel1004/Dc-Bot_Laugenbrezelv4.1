package de.laurenzschmidt.Messages;

import java.util.ArrayList;
import java.util.List;

public class InsultProvider {

    private final List<String> words = new ArrayList<>();

    public InsultProvider() {
        this.loadWords();
    }

    private void loadWords() {
        this.words.add("arschloch");
        this.words.add("wichser");
        this.words.add("hurensohn");
        this.words.add("penner");
    }

    public List<String> getWords() {
        return words;
    }

}