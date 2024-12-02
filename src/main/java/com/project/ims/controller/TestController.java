
package com.project.ims.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
@RequestMapping
 
public class TestController {
	@GetMapping("/api/products")
    public String showHomePage() {
        return "introIMS"; // Tên file HTML trong thư mục templates
    }
}
 