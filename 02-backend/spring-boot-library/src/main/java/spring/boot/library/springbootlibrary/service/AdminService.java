package spring.boot.library.springbootlibrary.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.library.springbootlibrary.dao.BookRepository;
import spring.boot.library.springbootlibrary.entity.Book;
import spring.boot.library.springbootlibrary.requestmodels.AddBookRequest;

@Service
@Transactional
public class AdminService {

    private BookRepository bookRepository;

    @Autowired
    public AdminService (BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        bookRepository.save(book);
    }

}
