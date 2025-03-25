package spring.boot.library.springbootlibrary.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.library.springbootlibrary.dao.MessageRepository;
import spring.boot.library.springbootlibrary.entity.Message;
import spring.boot.library.springbootlibrary.requestmodels.AdminQuestionRequest;

import java.util.Optional;

@Service
@Transactional
public class MessagesService {

    private MessageRepository messageRepository;

    @Autowired
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
        if (!message.isPresent()) {
            throw new Exception("Message not found");
        }
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setAdminEmail(userEmail);
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }

}
