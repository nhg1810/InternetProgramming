package com.duonghd.InternetProgramming.controller;

import com.duonghd.InternetProgramming.entity.Account.Account;
import com.duonghd.InternetProgramming.entity.Account.MoreInfAccount;
import com.duonghd.InternetProgramming.responsitories.AccountResponeitory;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;

@Controller
@AllArgsConstructor
@RequestMapping("/trang-chu")
public class HomeController {
    private EntityManager entityManager;
    private AccountResponeitory accountResponeitory;

    @GetMapping("")
    public ModelAndView home(ModelAndView modelAndView){
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/dang-nhap")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.addObject("account", new Account());
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/dang-ky")
    public ModelAndView SignUp(ModelAndView modelAndView){
        modelAndView.addObject("account_sg", new Account());
        modelAndView.setViewName("registration");
        return modelAndView;
    }
    @PostMapping("/dang-ky")
    public String SignUp(@Valid @ModelAttribute("account_sg") Account account, BindingResult result
            , RedirectAttributes attributes, Model model)
    {

        if(result.hasErrors()){
            model.addAttribute("msg_error", "Chưa tạo thành công tài khoản, vui lòng kiểm tra lại !");
            return "registration";
        }
        if(accountResponeitory.findByEmail(account.getEmail()) != null){
            model.addAttribute("msg_error", "email này đã tồn tại !");
            return "registration";
        }
        Account acc = new  Account();
        acc.setName(account.getName());
        acc.setEmail(account.getEmail());
        acc.setPassWord(account.getPassWord());
        accountResponeitory.save(acc);
        model.addAttribute("msg_error", "");
        model.addAttribute("msg_success", "Tạo tài khoản thành công !");
        return "redirect:/trang-chu/dang-nhap";
    }

    @PostMapping("/dang-nhap")
    public String login(@Valid @ModelAttribute("account") Account account,
                        BindingResult result
            , RedirectAttributes attributes, HttpServletResponse response, Model model,
                        HttpServletRequest request) throws NoSuchAlgorithmException {
        Account checkEmailAccount = accountResponeitory.findByEmailAndPassWord(account.getEmail(), account.getPassWord());
        if(checkEmailAccount == null){
            model.addAttribute("msg_error", "sai tài khoản và mật khẩu !");
            return "login";
        }
//        Cookie[] cookies =request.getCookies();
        account = accountResponeitory.findByEmailAndPassWord(account.getEmail(),account.getPassWord());
        String idUser =String.valueOf(account.getIdAccount());
        Cookie newCookie = new Cookie("idUser",idUser);
        newCookie.setMaxAge(24*60*60);
//        newCookie.setSecure(true);
//        newCookie.setHttpOnly(true);
        newCookie.setPath("/");
        response.addCookie(newCookie);
        return "redirect:/trang-chu/";
    }
    @GetMapping("/dang-xuat")
    public String logout(HttpServletResponse response){
        Cookie newCookie = new Cookie("idUser","");
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);
        return "redirect:/trang-chu/dang-nhap";
    }

}
