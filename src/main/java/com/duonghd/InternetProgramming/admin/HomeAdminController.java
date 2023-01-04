package com.duonghd.InternetProgramming.admin;

import com.duonghd.InternetProgramming.entity.Account.Account;
import com.duonghd.InternetProgramming.responsitories.AccountResponeitory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class HomeAdminController {
    private AccountResponeitory accountResponeitory;

    @GetMapping("/trang-chu")
    public String index(){
        return "home-admin";
    }
    @GetMapping("/tai-khoan")
    public String index(Model model){
        List<Account> l_account = accountResponeitory.findAll();
        model.addAttribute("allaccount",l_account);
        return "account-admin";
    }
}
