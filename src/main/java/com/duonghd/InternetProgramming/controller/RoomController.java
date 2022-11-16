package com.duonghd.InternetProgramming.controller;

import com.duonghd.InternetProgramming.entity.Account.Job;
import com.duonghd.InternetProgramming.responsitories.JobReponsitory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/quan-ly-phong-hop")
public class RoomController {
    private JobReponsitory jobReponsitory;
    @GetMapping("")
    public String index(ModelAndView modelAndView,
                        @CookieValue(name="idUser",defaultValue ="0") Long cookies,
                        Model model){
        if(cookies == 0l){
            return "redirect:/trang-chu/dang-nhap";
        }
        model.addAttribute("job",jobReponsitory.findAll());
        return "room";
    }
}
