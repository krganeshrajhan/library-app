package spring.boot.library.springbootlibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.library.springbootlibrary.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
