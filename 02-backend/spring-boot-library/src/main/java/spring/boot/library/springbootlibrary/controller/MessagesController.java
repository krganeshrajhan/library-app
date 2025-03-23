package spring.boot.library.springbootlibrary.controller;

import org.springframework.web.bind.annotation.*;
import spring.boot.library.springbootlibrary.entity.Message;
import spring.boot.library.springbootlibrary.service.MessagesService;
import spring.boot.library.springbootlibrary.utils.ExtractJWT;

import static spring.boot.library.springbootlibrary.utils.Constants.TOKEN_EXTRACTION;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;


    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value="Authorization") String token,
                            @RequestBody Message messageRequest) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION);
        messagesService.postMessage(messageRequest, userEmail);
    }

}
