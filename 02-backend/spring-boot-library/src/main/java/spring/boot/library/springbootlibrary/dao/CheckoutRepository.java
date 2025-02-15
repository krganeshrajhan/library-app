package spring.boot.library.springbootlibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.library.springbootlibrary.entity.Checkout;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    
    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

}
