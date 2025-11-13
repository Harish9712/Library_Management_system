package model;

public class Book {
    private String isbn;
    private String title;
    private int quantity;
    private double cost;
    private int borrowedCount = 0;

    public Book(String isbn, String title, int quantity, double cost) {
        this.isbn = isbn;
        this.title = title;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int q) { this.quantity = q; }
    public double getCost() { return cost; }
    public void incrementBorrowed() { borrowedCount++; }
    public int getBorrowedCount() { return borrowedCount; }
}