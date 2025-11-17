// No package declaration to keep compilation simple for this assignment

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the core data and logic for the library system. It maintains
 * collections of books and members and provides methods to add new entries
 * and to check books in and out. Keeping all logic in one place means the
 * GUI layer can remain focused on presentation and event handling.
 */
public class Library {
    private final List<Book> books;
    private final List<Member> members;

    /**
     * Constructs an empty Library.
     */
    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    /**
     * Adds a new book to the library. No duplicate checking is performed – that
     * logic could be added as an enhancement.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Adds a new member to the library.
     */
    public void addMember(Member member) {
        members.add(member);
    }

    /**
     * Finds and returns a book by its ISBN. Returns null if no such book exists.
     */
    public Book findBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equalsIgnoreCase(isbn)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Finds and returns a member by their id. Returns null if not found.
     */
    public Member findMember(String memberId) {
        for (Member m : members) {
            if (m.getMemberID().equalsIgnoreCase(memberId)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Attempts to check out a book to a member. Returns true if successful,
     * otherwise false. A book can only be checked out if it exists, is
     * available and the member exists.
     */
    public boolean checkOutBook(String memberId, String isbn) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);
        if (book == null || member == null || !book.isAvailable()) {
            return false;
        }
        book.setAvailable(false);
        return true;
    }

    /**
     * Returns a book to the library. Returns true if successful. If the book
     * does not exist or is already available, returns false.
     */
    public boolean returnBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null || book.isAvailable()) {
            return false;
        }
        book.setAvailable(true);
        return true;
    }

    /**
     * Returns a list of all books in the library.
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Returns a list of all members in the library.
     */
    public List<Member> getMembers() {
        return members;
    }
}