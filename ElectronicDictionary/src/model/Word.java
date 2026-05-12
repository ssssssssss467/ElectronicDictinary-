package model;

public class Word {

    private String englishWord;

    private String russianWord;

    private static int wordCount = 0;

    public Word(String englishWord,
                String russianWord) {

        this.englishWord = englishWord;
        this.russianWord = russianWord;

        wordCount++;
    }

    public String getEnglishWord() {

        return englishWord;
    }

    public void setEnglishWord(String englishWord) {

        this.englishWord = englishWord;
    }

    public String getRussianWord() {

        return russianWord;
    }

    public void setRussianWord(String russianWord) {

        this.russianWord = russianWord;
    }

    public static int getWordCount() {

        return wordCount;
    }

    @Override
    public String toString() {

        return englishWord +
                " - " +
                russianWord;
    }
}