package service;

import exception.WordNotFoundException;
import interfaces.Searchable;
import model.UserWord;
import model.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryService implements Searchable {

    private static final String JSON_FILE_PATH = "dictionary.json";

    private HashMap<String, String> dictionary;
    private List<String> history;

    public DictionaryService() {
        dictionary = new HashMap<>();
        history = new ArrayList<>();

        loadFromJson();

        if (dictionary.isEmpty()) {
            addDefaultWords();
            saveToJson();
        }
    }
    private void loadFromJson() {
        File file = new File(JSON_FILE_PATH);
        if (!file.exists()) {
            System.out.println("dictionary.json жок, демейки сөздөр колдонулат.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }

            String json = sb.toString().trim();

            if (json.startsWith("{") && json.endsWith("}")) {
                json = json.substring(1, json.length() - 1).trim();
            } else {
                return;
            }

            if (json.isEmpty()) return;

            String[] pairs = json.split(",(?=\\s*\")");

            for (String pair : pairs) {
                pair = pair.trim();
                int colonIndex = pair.indexOf("\":\"");
                if (colonIndex == -1) continue;

                String key   = pair.substring(1, colonIndex);
                String value = pair.substring(colonIndex + 3, pair.length() - 1);

                dictionary.put(key, value);
            }

            System.out.println("JSON дан жүктөлдү: " + dictionary.size() + " сөз");

        } catch (IOException e) {
            System.out.println("Файл окуу катасы: " + e.getMessage());
        }
    }

    private void saveToJson() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_PATH))) {
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");

            int i = 0;
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                sb.append("  \"")
                        .append(entry.getKey())
                        .append("\":\"")
                        .append(entry.getValue())
                        .append("\"");

                if (i < dictionary.size() - 1) {
                    sb.append(",");
                }
                sb.append("\n");
                i++;
            }

            sb.append("}");
            writer.write(sb.toString());
            System.out.println("JSON файлга сакталды.");

        } catch (IOException e) {
            System.out.println("Файлга жазуу катасы: " + e.getMessage());
        }
    }


    private void addDefaultWords() {
        dictionary.put("hello",    "привет");
        dictionary.put("apple",    "яблоко");
        dictionary.put("cat",      "кот");
        dictionary.put("dog",      "собака");
        dictionary.put("java",     "язык программирования");
        dictionary.put("computer", "компьютер");
        dictionary.put("keyboard", "клавиатура");
        dictionary.put("phone",    "телефон");
        dictionary.put("sun",      "солнце");
        dictionary.put("book",     "книга");
    }


    @Override
    public String search(String word) throws WordNotFoundException {
        word = word.trim().toLowerCase();

        if (word.isEmpty()) {
            throw new WordNotFoundException("Введите слово");
        }

        String translation = dictionary.get(word);

        if (translation == null) {
            throw new WordNotFoundException("Слово не найдено");
        }

        history.add(word);
        return translation;
    }


    public void addWord(UserWord word) {
        dictionary.put(
                word.getEnglishWord().toLowerCase(),
                word.getRussianWord()
        );
        saveToJson();
    }


    public List<String> getHistory() {
        return history;
    }

    public int getDictionarySize() {
        return dictionary.size();
    }

    public int getWordCount() {
        return Word.getWordCount();
    }
}