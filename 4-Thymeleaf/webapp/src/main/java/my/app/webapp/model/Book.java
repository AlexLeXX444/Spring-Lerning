package my.app.webapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Book {

    public static long sequence = 1L;

    private final long id;
    private final String name;
    private final int amount;

    public Book(String name, int amount) {
        this(sequence++, name, amount);
    }
}
