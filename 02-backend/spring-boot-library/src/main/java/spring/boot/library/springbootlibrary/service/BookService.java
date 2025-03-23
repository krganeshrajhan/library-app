package spring.boot.library.springbootlibrary.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import spring.boot.library.springbootlibrary.dao.BookRepository;
import spring.boot.library.springbootlibrary.dao.CheckoutRepository;
import spring.boot.library.springbootlibrary.dao.HistoryRepository;
import spring.boot.library.springbootlibrary.entity.Book;
import spring.boot.library.springbootlibrary.entity.Checkout;
import spring.boot.library.springbootlibrary.entity.History;
import spring.boot.library.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;

    private HistoryRepository historyRepository;

    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository,
                       HistoryRepository historyRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
    }

    public Book checkoutBook(String userEmail, Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book does not exist or already checked out by user");
        }   

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());
        
        Checkout checkout = new Checkout(
            userEmail,
            LocalDate.now().toString(),
            LocalDate.now().plusDays(7).toString(),
            book.get().getId()
        );
        checkoutRepository.save(checkout);

        return book.get();
    }

    public Boolean checkoutBookByUser(String userEmail, long bookId) {
        Checkout checkout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        return checkout != null;
    }

    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {
        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
        List<Long> bookIds = new ArrayList<>();
        for(Checkout i : checkoutList) {
            bookIds.add(i.getBookId());
        }

        List<Book> books = bookRepository.findBooksByBookIds(bookIds);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Book book : books) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x -> x.getBookId().equals(book.getId())).findFirst();
            if (checkout.isPresent()) {
                Date d1 = dateFormat.parse(checkout.get().getReturnDate());
                Date d2 = dateFormat.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int) differenceInTime));
            }
        }

        return shelfCurrentLoansResponses;
    }

    public void returnBook(String userEmail, long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        bookRepository.save(book.get());
        checkoutRepository.deleteById(validateCheckout.getId());

        History history = new History(
            userEmail,
            validateCheckout.getCheckoutDate(),
            LocalDate.now().toString(),
            book.get().getTitle(),
            book.get().getAuthor(),
            book.get().getDescription(),
            book.get().getImg()
        );
        historyRepository.save(history);
    }

    public void renewLoan(String userEmail, long bookId) throws Exception {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
        Date d2 = sdFormat.parse(LocalDate.now().toString());

        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout);
        }
    }



}
