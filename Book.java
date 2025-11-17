// No package declaration to keep compilation simple for this assignment

/**
 * Represents a single Book in the library. A Book has an ISBN, a title and a flag
 * indicating whether it is currently available for checkout. This class
 * encapsulates its fields and exposes public getters and setters to maintain
 * proper encapsulation. New books are considered available by default.
 */
public class Book {
    private String isbn;
    private String title;
    private boolean available;

    /**
     * Constructs a new Book with the given ISBN and title. Newly created books
     * start off as available for checkout.
     *
     * @param isbn  the unique ISBN of the book
     * @param title the human‑readable title of the book
     */
    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
        this.available = true;
    }

    /**
     * Returns the ISBN of this book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Updates the ISBN of this book.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the title of this book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the title of this book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns whether or not this book is currently available for checkout.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the availability of this book. When a book is checked out the
     * availability should be set to false. When returned it should be set to true.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
}