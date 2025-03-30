package spring.boot.library.springbootlibrary.dao;

import org.hibernate.annotations.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.boot.library.springbootlibrary.entity.Checkout;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    
    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

    List<Checkout> findBooksByUserEmail(String userEmail);

    @Modifying
    @Query("delete from Checkout where bookId = :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);

}
