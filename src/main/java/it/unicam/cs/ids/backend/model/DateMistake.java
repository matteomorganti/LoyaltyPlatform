package it.unicam.cs.ids.backend.model;

/**
 * Exception thrown when there is a mistake related to dates.
 */
public class DateMistake extends Exception {

    /**
     * Constructs a new DateMistake instance.
     */
    public DateMistake() {
        super();
    }

    /**
     * Constructs a new DateMistake instance with the specified error message.
     *
     * @param message The error message describing the date mistake.
     */
    public DateMistake(String message) {
        super(message);
    }
}
