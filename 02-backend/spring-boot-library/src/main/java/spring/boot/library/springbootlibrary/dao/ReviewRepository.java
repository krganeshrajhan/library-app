package spring.boot.library.springbootlibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.library.springbootlibrary.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
}
