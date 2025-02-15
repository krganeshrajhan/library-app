package spring.boot.library.springbootlibrary.controller;

import org.springframework.web.bind.annotation.*;

import spring.boot.library.springbootlibrary.entity.Book;
import spring.boot.library.springbootlibrary.service.BookService;
import spring.boot.library.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    public static final String TOKEN_EXTRACTION = "\"sub\"";
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader("Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION);
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader("Authorization") String token, @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION);
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader("Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token,   TOKEN_EXTRACTION);
        return bookService.currentLoansCount(userEmail);
    }

}
