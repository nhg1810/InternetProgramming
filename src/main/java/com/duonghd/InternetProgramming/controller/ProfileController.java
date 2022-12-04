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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@AllArgsConstructor
@RequestMapping("thong-tin-ca-nhan")
public class ProfileController {
    private AccountResponeitory accountResponeitory;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads/avata";
    @GetMapping("/cua-toi")
    public ModelAndView index(@CookieValue(name="idUser",defaultValue ="0") Long cookies,
                              RedirectAttributes attributes, ModelAndView modelAndView,
                              HttpServletRequest request){
        if( cookies == 0){
            modelAndView.addObject("account", new Account());
            modelAndView.setViewName("login");
            return modelAndView;
        }
        modelAndView.setViewName("myProfile");
//        Long lIdUser = Long.parseLong(String.valueOf(ck_name));
        modelAndView.addObject("account",accountResponeitory.getOne(cookies));
        if(accountResponeitory.getOne(cookies).getMoreInfAccount() != null){
            String avata = accountResponeitory.getOne(cookies).getMoreInfAccount().getAvata();
            modelAndView.addObject("avata",avata);
        }
        return modelAndView;
    }
    @PostMapping("/chinh-sua")
    public String edit(@CookieValue(name="idUser",defaultValue ="0") Long cookies,
                       @Valid @ModelAttribute("account") Account account,
                       @RequestParam("image-avata") MultipartFile file,
                       HttpSession session) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        MoreInfAccount moreInfAccount = new MoreInfAccount();
        if( file.getOriginalFilename() != ""){
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            String st_avata = file.getOriginalFilename();
            moreInfAccount.setAvata(st_avata);
        }else{
            moreInfAccount.setAvata(accountResponeitory.getOne(cookies).getMoreInfAccount().getAvata());
        }


        Account accountUpdate = new Account();
        accountUpdate.setIdAccount(cookies);

        accountUpdate.setEmail(accountResponeitory.getOne(cookies).getEmail());
        accountUpdate.setPassWord(accountResponeitory.getOne(cookies).getPassWord());

        moreInfAccount.setPhone(account.getMoreInfAccount().getPhone());
        moreInfAccount.setBirth(account.getMoreInfAccount().getBirth());
        moreInfAccount.setAddress(account.getMoreInfAccount().getAddress());
        moreInfAccount.setMyBlog(account.getMoreInfAccount().getMyBlog());
        moreInfAccount.setDescriptGraduation(account.getMoreInfAccount().getDescriptGraduation());
        moreInfAccount.setLink(account.getMoreInfAccount().getLink());
        moreInfAccount.setContext(account.getMoreInfAccount().getContext());


        moreInfAccount.setExperience(account.getMoreInfAccount().getExperience());


        moreInfAccount.setAccount(accountUpdate);
        accountUpdate.setMoreInfAccount(moreInfAccount);

        accountUpdate.setName(account.getName());
        accountResponeitory.save(accountUpdate);
        return "redirect:/thong-tin-ca-nhan/cua-toi";
    }
}
