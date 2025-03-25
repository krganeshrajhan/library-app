package spring.boot.library.springbootlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.boot.library.springbootlibrary.requestmodels.AddBookRequest;
import spring.boot.library.springbootlibrary.service.AdminService;
import spring.boot.library.springbootlibrary.utils.Constants;
import spring.boot.library.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value="Authorization") String token,
                         @RequestBody AddBookRequest addBookRequest) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, Constants.TOKEN_EXTRACTION_USER_TYPE);
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }
        adminService.postBook(addBookRequest);
    }

}
