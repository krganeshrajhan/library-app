package spring.boot.library.springbootlibrary.controller;

import org.springframework.web.bind.annotation.*;

import spring.boot.library.springbootlibrary.entity.Book;
import spring.boot.library.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;
import spring.boot.library.springbootlibrary.service.BookService;
import spring.boot.library.springbootlibrary.utils.ExtractJWT;

import java.util.List;

import static spring.boot.library.springbootlibrary.utils.Constants.TOKEN_EXTRACTION_EMAIL;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token)
            throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION_EMAIL);
        return bookService.currentLoans(userEmail);
    }
    
    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader("Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION_EMAIL);
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader("Authorization") String token, @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION_EMAIL);
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader("Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION_EMAIL);
        return bookService.currentLoansCount(userEmail);
    }

    @PutMapping("/secure/return")
    public void returnBook(@RequestHeader(value = "Authorization") String token,
                           @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION_EMAIL);
        bookService.returnBook(userEmail, bookId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value = "Authorization") String token,
                          @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION_EMAIL);
        bookService.renewLoan(userEmail, bookId);
    }

}
