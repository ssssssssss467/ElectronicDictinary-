package interfaces;

import exception.WordNotFoundException;

public interface Searchable {

    String search(String word)
            throws WordNotFoundException;
}