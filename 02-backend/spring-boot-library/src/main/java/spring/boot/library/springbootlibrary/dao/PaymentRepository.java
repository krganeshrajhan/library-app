package spring.boot.library.springbootlibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.library.springbootlibrary.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByUserEmail(String userEmail);

}
