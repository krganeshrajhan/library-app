package spring.boot.library.springbootlibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.library.springbootlibrary.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
