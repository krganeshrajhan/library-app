package spring.boot.library.springbootlibrary.dao;

import org.hibernate.annotations.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.library.springbootlibrary.entity.Checkout;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    
    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

    List<Checkout> findBooksByUserEmail(String userEmail);

}
