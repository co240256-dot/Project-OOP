import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Provides the graphical user interface for the library management system.
 * The interface is composed of three tabs: one for managing books, one for
 * managing members and one for checking books in and out. The GUI uses
 * appropriate Swing components and layout managers to organize forms and
 * tables, and delegates all business logic to an instance of the Library class.
 */
public class LibraryGUI extends JFrame {
    private final Library library;

    // Book tab components
    private DefaultTableModel bookTableModel;
    private JTable bookTable;
    private JTextField isbnField;
    private JTextField titleField;

    // Member tab components
    private DefaultTableModel memberTableModel;
    private JTable memberTable;
    private JTextField memberIdField;
    private JTextField memberNameField;

    // Checkout tab components
    private JTextField checkoutMemberField;
    private JTextField checkoutIsbnField;

    /**
     * Constructs the GUI and initializes all components.
     */
    public LibraryGUI() {
        super("Library Management System");
        library = new Library();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        initComponents();
    }

    /**
     * Initializes and arranges all panels, tables and controls.
     */
    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Build each tab and add to the tabbed pane
        tabbedPane.addTab("Books", createBookPanel());
        tabbedPane.addTab("Members", createMemberPanel());
        tabbedPane.addTab("Check-Out / Return", createCheckoutPanel());

        add(tabbedPane);
    }

    /**
     * Creates the panel used for book management. Contains a form for adding
     * books and a table showing the current collection.
     */
    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Form for adding books
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(10);
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(10);
        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(e -> addBook());

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(isbnLabel, gbc);
        gbc.gridx = 1;
        form.add(isbnField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(titleLabel, gbc);
        gbc.gridx = 1;
        form.add(titleField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        form.add(addBookButton, gbc);

        // Table of books
        bookTableModel = new DefaultTableModel(new Object[]{"ISBN", "Title", "Available"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookTable = new JTable(bookTableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Adds a new book to the library and updates the table. Performs basic
     * validation to ensure required fields are filled.
     */
    private void addBook() {
        String isbn = isbnField.getText().trim();
        String title = titleField.getText().trim();
        if (isbn.isEmpty() || title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ISBN and title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        library.addBook(new Book(isbn, title));
        refreshBookTable();
        isbnField.setText("");
        titleField.setText("");
    }

    /**
     * Populates the book table with the current contents of the library.
     */
    private void refreshBookTable() {
        bookTableModel.setRowCount(0);
        for (Book book : library.getBooks()) {
            bookTableModel.addRow(new Object[]{book.getIsbn(), book.getTitle(), book.isAvailable() ? "Yes" : "No"});
        }
    }

    /**
     * Creates the panel used for member management. Contains a form for adding
     * new members and a table of existing members.
     */
    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Member ID:");
        memberIdField = new JTextField(10);
        JLabel nameLabel = new JLabel("Name:");
        memberNameField = new JTextField(10);
        JButton addMemberButton = new JButton("Add Member");
        addMemberButton.addActionListener(e -> addMember());

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(idLabel, gbc);
        gbc.gridx = 1;
        form.add(memberIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(nameLabel, gbc);
        gbc.gridx = 1;
        form.add(memberNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        form.add(addMemberButton, gbc);

        memberTableModel = new DefaultTableModel(new Object[]{"Member ID", "Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        memberTable = new JTable(memberTableModel);
        JScrollPane scrollPane = new JScrollPane(memberTable);

        panel.add(form, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Adds a new member to the library and updates the table.
     */
    private void addMember() {
        String id = memberIdField.getText().trim();
        String name = memberNameField.getText().trim();
        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member ID and name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        library.addMember(new Member(id, name));
        refreshMemberTable();
        memberIdField.setText("");
        memberNameField.setText("");
    }

    /**
     * Populates the member table from the library data.
     */
    private void refreshMemberTable() {
        memberTableModel.setRowCount(0);
        for (Member m : library.getMembers()) {
            memberTableModel.addRow(new Object[]{m.getMemberID(), m.getName()});
        }
    }

    /**
     * Creates the panel used for checking out and returning books.
     */
    private JPanel createCheckoutPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel memberLabel = new JLabel("Member ID:");
        checkoutMemberField = new JTextField(10);
        JLabel isbnLabel = new JLabel("Book ISBN:");
        checkoutIsbnField = new JTextField(10);
        JButton checkoutButton = new JButton("Check Out");
        JButton returnButton = new JButton("Return");

        checkoutButton.addActionListener(e -> checkoutBook());
        returnButton.addActionListener(e -> returnBook());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(memberLabel, gbc);
        gbc.gridx = 1;
        panel.add(checkoutMemberField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(isbnLabel, gbc);
        gbc.gridx = 1;
        panel.add(checkoutIsbnField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(checkoutButton, gbc);
        gbc.gridx = 1;
        panel.add(returnButton, gbc);

        return panel;
    }

    /**
     * Attempts to check out a book. Displays feedback via JOptionPane and
     * refreshes the book table.
     */
    private void checkoutBook() {
        String memberId = checkoutMemberField.getText().trim();
        String isbn = checkoutIsbnField.getText().trim();
        if (memberId.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member ID and Book ISBN are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean success = library.checkOutBook(memberId, isbn);
        if (success) {
            JOptionPane.showMessageDialog(this, "Book checked out successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Checkout failed. Ensure the member and book exist and the book is available.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshBookTable();
    }

    /**
     * Attempts to return a book. Provides feedback and updates the book table.
     */
    private void returnBook() {
        String isbn = checkoutIsbnField.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Book ISBN is required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean success = library.returnBook(isbn);
        if (success) {
            JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Return failed. Ensure the book exists and is currently checked out.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshBookTable();
    }

    /**
     * The entry point of the program. Creates and displays the GUI on the
     * event dispatch thread.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryGUI gui = new LibraryGUI();
            gui.setVisible(true);
        });
    }
}