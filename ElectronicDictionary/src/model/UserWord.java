package model;

public class UserWord extends Word {

    public UserWord(String englishWord,
                    String russianWord) {

        super(englishWord, russianWord);
    }

    @Override
    public String toString() {

        return "User word: "
                + getEnglishWord()
                + " - "
                + getRussianWord();
    }
}