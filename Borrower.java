package model;

import java.time.LocalDate;
import java.util.*;

public class Borrower extends User {
    private Set<String> borrowedBooks = new HashSet<>();
    private double deposit = 1500.0;
    private List<String> fineHistory = new ArrayList<>();
    private List<String> borrowHistory = new ArrayList<>();
    private Map<String, LocalDate> borrowedDates = new HashMap<>();

    public Borrower(String email, String password) {
        super(email, password);
    }

    public String getRole() { return "Borrower"; }

    public boolean canBorrow(String isbn) {
        return borrowedBooks.size() < 3 && !borrowedBooks.contains(isbn) && deposit >= 500;
    }

    public void borrow(String isbn) {
        borrowedBooks.add(isbn);
        borrowHistory.add(isbn);
        borrowedDates.put(isbn, LocalDate.now());
    }

    public void returnBook(String isbn) {
        borrowedBooks.remove(isbn);
        borrowedDates.remove(isbn);
    }

    public LocalDate getBorrowDate(String isbn) {
        return borrowedDates.get(isbn);
    }

    public Set<String> getBorrowedBooks() { return borrowedBooks; }
    public void addFine(String fineDetail, double amount) {
        fineHistory.add(fineDetail);
        deposit -= amount;
    }

    public List<String> getFineHistory() { return fineHistory; }
    public List<String> getBorrowHistory() { return borrowHistory; }
    public double getDeposit() { return deposit; }
}
