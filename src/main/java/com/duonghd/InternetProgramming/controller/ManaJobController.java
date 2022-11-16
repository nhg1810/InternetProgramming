package com.duonghd.InternetProgramming.controller;

import com.duonghd.InternetProgramming.entity.Account.Account;
import com.duonghd.InternetProgramming.entity.Account.Job;
import com.duonghd.InternetProgramming.entity.Account.Member;
import com.duonghd.InternetProgramming.entity.Account.Room;
import com.duonghd.InternetProgramming.responsitories.AccountResponeitory;
import com.duonghd.InternetProgramming.responsitories.JobReponsitory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/quan-ly-cong-viec")
public class ManaJobController {
    private JobReponsitory jobReponsitory;
    private AccountResponeitory accountResponeitory;
    @GetMapping("")
    public String index(@CookieValue(name="idUser",defaultValue ="0") Long cookies,
                              Model model,
                              RedirectAttributes attributes){
        if(cookies == 0l){
            return "redirect:/trang-chu/dang-nhap";
        }
        model.addAttribute("job",jobReponsitory.findAll());
        return "your-job";
    }

    @GetMapping("/tao-cong-viec")
    public ModelAndView CreateJob(ModelAndView modelAndView){
        modelAndView.setViewName("create-job");
        modelAndView.addObject("job",new Job());
        return modelAndView;
    }
    @PostMapping("/tao-cong-viec")
    public String PCreateJob(@CookieValue(name="idUser",defaultValue ="0") Long cookies,
                             @Valid @ModelAttribute("job") Job job, BindingResult result
            , RedirectAttributes attributes, Model model){

        if(cookies == 0l) {
            return "redirect:/trang-chu/dang-nhap";
        }

        Account accountHost= accountResponeitory.getById(cookies);
        Room room = new Room();
        Member member = new Member();

        job.setMember(member);
        job.setRoom(room);
        job.setAccount(accountHost);
        jobReponsitory.save(job);

        return "redirect:/quan-ly-cong-viec/tao-cong-viec";
    }
}

