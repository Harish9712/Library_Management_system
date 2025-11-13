package app;

import model.*;
import service.AuthService;
import service.BookService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();
        BookService bookService = new BookService();

        while (true) {
            System.out.println("\n1. Register (Borrower)\n2. Login\n3. Exit");
            int ch = sc.nextInt(); sc.nextLine();
            if (ch == 1) {
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Password: ");
                String pass = sc.nextLine();
                if (auth.registerBorrower(email, pass)) {
                    System.out.println("Borrower registered.");
                } else {
                    System.out.println("Already exists or invalid.");
                }
            } else if (ch == 2) {
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Password: ");
                String pass = sc.nextLine();
                User user = auth.login(email, pass);
                if (user == null) {
                    System.out.println("Invalid login");
                    continue;
                }
                System.out.println("Welcome " + user.getRole() + " " + user.getEmail());

                if (user instanceof Admin) {
                    while (true) {
                        System.out.println("\nAdmin Menu:\n1. Add Book\n2. Modify Book Qty\n3. Delete Book\n4. View All\n5. Search\n6. Reports\n7. Logout");
                        int a = sc.nextInt(); sc.nextLine();
                        if (a == 1) {
                            System.out.print("ISBN: "); String isbn = sc.nextLine();
                            System.out.print("Title: "); String title = sc.nextLine();
                            System.out.print("Qty: "); int qty = sc.nextInt();
                            System.out.print("Cost: "); double cost = sc.nextDouble(); sc.nextLine();
                            bookService.addBook(new Book(isbn, title, qty, cost));
                            System.out.println("Book added successfully.");
                        } else if (a == 2) {
                            System.out.print("ISBN: "); String isbn = sc.nextLine();
                            System.out.print("New Qty: "); int qty = sc.nextInt(); sc.nextLine();
                            bookService.modifyBookQuantity(isbn, qty);
                        } else if (a == 3) {
                            System.out.print("ISBN to delete: ");
                            bookService.deleteBook(sc.nextLine());
                        } else if (a == 4) {
                            bookService.listAllBooks();
                        } else if (a == 5) {
                            System.out.print("Search by title: ");
                            List<Book> result = bookService.searchByName(sc.nextLine());
                            result.forEach(b -> System.out.println(b.getTitle()));
                        } else if (a == 6) {
                            bookService.reportLowStock();
                            bookService.reportMostBorrowed();
                        } else break;
                    }
                } else if (user instanceof Borrower) {
                    Borrower b = (Borrower) user;
                    while (true) {
                        System.out.println("\nBorrower Menu:\n1. View Books\n2. Borrow\n3. Return\n4. Fines\n5. History\n6. Logout");
                        int bch = sc.nextInt(); sc.nextLine();
                        if (bch == 1) bookService.listAllBooks();
                        else if (bch == 2) {
                            System.out.print("ISBN to borrow: ");
                            if (bookService.borrowBook(sc.nextLine(), b)) {
                                System.out.println("Book borrowed successfully.");
                            } else {
                                System.out.println("Borrow failed. Check limits or availability.");
                            }
                        } else if (bch == 3) {
                            System.out.print("ISBN to return: ");
                            String isbn = sc.nextLine();
                            System.out.print("Enter return date (DD/MM/YYYY): ");
                            String dateStr = sc.nextLine();
                            bookService.returnBook(isbn, b, dateStr);

                        } else if (bch == 4) {
                            System.out.println("Deposit Balance: ₹" + b.getDeposit());
                        } else if (bch == 5) {
                            System.out.println("Borrow History: " + b.getBorrowHistory());
                            System.out.println("Fine History: " + b.getFineHistory());
                        } else break;
                    }
                }
            } else break;
        }
    }
}