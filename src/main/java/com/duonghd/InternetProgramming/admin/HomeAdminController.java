package com.duonghd.InternetProgramming.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class HomeAdminController {
    @GetMapping("/trang-chu")
    public String index(){
        return "home-admin";
    }
}
