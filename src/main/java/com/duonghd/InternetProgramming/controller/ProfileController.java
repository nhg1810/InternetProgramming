package com.duonghd.InternetProgramming.controller;

import com.duonghd.InternetProgramming.config.FileUploadUtil;
import com.duonghd.InternetProgramming.entity.Account.Account;
import com.duonghd.InternetProgramming.entity.Account.MoreInfAccount;
import com.duonghd.InternetProgramming.responsitories.AccountResponeitory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("thong-tin-ca-nhan")
public class ProfileController {
    private AccountResponeitory accountResponeitory;
    @GetMapping("/cua-toi")
    public ModelAndView index(@CookieValue(name="idUser",defaultValue ="0") Long cookies, RedirectAttributes attributes, ModelAndView modelAndView, HttpServletRequest request){
        if( cookies == 0){
            modelAndView.addObject("account", new Account());
            modelAndView.setViewName("login");
            return modelAndView;
        }
        modelAndView.setViewName("myProfile");
//        Long lIdUser = Long.parseLong(String.valueOf(ck_name));
        modelAndView.addObject("account",accountResponeitory.getOne(cookies));
        return modelAndView;
    }
    @PostMapping("/chinh-sua")
    public String edit(@CookieValue(name="idUser",defaultValue ="0") Long cookies,
                       @Valid @ModelAttribute("account") Account account,
                       BindingResult result
            , RedirectAttributes attributes, HttpServletResponse response, Model model, HttpServletRequest request){
            Account accountUpdate = new Account();
            accountUpdate.setName(account.getName());
            accountUpdate.setPassWord(accountResponeitory.getById(cookies).getPassWord());
            accountUpdate.setIdAccount(accountResponeitory.getById(cookies).getIdAccount());
            accountUpdate.setEmail(accountResponeitory.getById(cookies).getEmail());

            MoreInfAccount updateMoreInfAccount = account.getMoreInfAccount();
            updateMoreInfAccount.setAccount(accountUpdate);
            accountUpdate.setMoreInfAccount(updateMoreInfAccount);
            //save img
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            updateMoreInfAccount.setAvata(fileName);
//
//            String uploadDir = "C:/Users/ASUS/IdeaProjects/InternetProgramming/src/main/resources/static/vendors/images/" + updateMoreInfAccount.getIdMAcount();
////            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            accountResponeitory.save(accountUpdate);
        return "redirect:/thong-tin-ca-nhan/cua-toi";
    }
}
