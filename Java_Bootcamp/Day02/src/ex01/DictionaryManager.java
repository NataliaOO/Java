package ex01;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class DictionaryManager {
    private final Set<String> dictionary = new TreeSet<>();

    public void buildDictionary(String textA, String textB) {
        addWordsToDictionary(textA);
        addWordsToDictionary(textB);
    }

    private void addWordsToDictionary(String word) {
        for (String w : word.split(" ")) {
            if (!w.isEmpty()) {
                dictionary.add(w);
            }
        }
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String w : dictionary) {
                writer.write(w);
                writer.newLine();
            }
        }
    }
}
