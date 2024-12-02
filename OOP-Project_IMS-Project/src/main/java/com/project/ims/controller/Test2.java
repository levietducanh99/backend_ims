
package com.project.ims.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
@RequestMapping
 
public class Test2 {
	@GetMapping("/api/imports")
    public String showImportsPage() {
        return "phieunk"; // Tên file HTML trong thư mục templates
    }
	@GetMapping("/api/suppliers")
    public String showSupplierPage() {
        return "supplierManagement"; // Tên file HTML trong thư mục templates
    }
}
 