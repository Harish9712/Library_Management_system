package service;

import model.Book;
import model.Borrower;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BookService {
    private HashMap<String, Book> books = new HashMap<>();
    private Map<String, String> borrowedBy = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public void deleteBook(String isbn) {
        books.remove(isbn);
    }

    public void modifyBookQuantity(String isbn, int quantity) {
        if (books.containsKey(isbn)) {
            books.get(isbn).setQuantity(quantity);
        }
    }

    public Book searchByIsbn(String isbn) {
        return books.get(isbn);
    }

    public List<Book> searchByName(String name) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(name.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public void listAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        for (Book b : books.values()) {
            System.out.println(b.getIsbn() + " | " + b.getTitle() + " | Qty: " + b.getQuantity());
        }
    }


    public boolean borrowBook(String isbn, Borrower borrower) {
        Book book = books.get(isbn);
        if (book != null && book.getQuantity() > 0 && borrower.canBorrow(isbn)) {
            book.setQuantity(book.getQuantity() - 1);
            book.incrementBorrowed();
            borrower.borrow(isbn);
            borrowedBy.put(isbn, borrower.getEmail());
            return true;
        }
        return false;
    }

    public void returnBook(String isbn, Borrower borrower, String returnDateStr) {
        Book book = books.get(isbn);
        if (book != null && borrower.getBorrowedBooks().contains(isbn)) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate returnDate = LocalDate.parse(returnDateStr, formatter);
                LocalDate borrowDate = borrower.getBorrowDate(isbn);
                long daysBetween = ChronoUnit.DAYS.between(borrowDate, returnDate);

                if (daysBetween > 15) {
                    double fine = (daysBetween - 15) * 2;
                    borrower.addFine("Late return for book " + isbn + ": ₹" + fine, fine);
                    System.out.println("Late return! ₹" + fine + " deducted from deposit.");
                }

                book.setQuantity(book.getQuantity() + 1);
                borrower.returnBook(isbn);
                borrowedBy.remove(isbn);
                System.out.println("Book returned successfully.");
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter in DD/MM/YYYY.");
            }
        } else {
            System.out.println("Invalid return.");
        }
    }

    public void reportLowStock() {
        for (Book b : books.values()) {
            if (b.getQuantity() < 2) System.out.println("Low Stock: " + b.getTitle());
        }
    }

    public void reportMostBorrowed() {
        for (Book b : books.values()) {
            if (b.getBorrowedCount() >= 3) System.out.println("Heavily Borrowed: " + b.getTitle());
        }
    }

    public void reportBookStatus(String isbn) {
        String userEmail = borrowedBy.get(isbn);
        if (userEmail != null) {
            System.out.println("Book " + isbn + " is with: " + userEmail);
        } else {
            System.out.println("Book is available.");
        }
    }
}