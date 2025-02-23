package spring.boot.library.springbootlibrary.responsemodels;

import lombok.Data;
import spring.boot.library.springbootlibrary.entity.Book;

@Data
public class ShelfCurrentLoansResponse {

    public ShelfCurrentLoansResponse(Book book, int daysLeft) {
        this.book = book;
        this.daysLeft = daysLeft;
    }

    private Book book;
    private int daysLeft;

}
