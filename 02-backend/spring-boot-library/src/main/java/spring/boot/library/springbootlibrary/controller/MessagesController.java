package spring.boot.library.springbootlibrary.controller;

import org.springframework.web.bind.annotation.*;
import spring.boot.library.springbootlibrary.entity.Message;
import spring.boot.library.springbootlibrary.requestmodels.AdminQuestionRequest;
import spring.boot.library.springbootlibrary.service.MessagesService;
import spring.boot.library.springbootlibrary.utils.ExtractJWT;

import static spring.boot.library.springbootlibrary.utils.Constants.TOKEN_EXTRACTION_EMAIL;

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
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION_EMAIL);
        messagesService.postMessage(messageRequest, userEmail);
    }

    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value="Authorization") String token,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, TOKEN_EXTRACTION_EMAIL);
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }
        messagesService.putMessage(adminQuestionRequest, userEmail);
    }

}
